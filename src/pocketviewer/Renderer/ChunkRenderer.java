/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pocketviewer.Renderer;

import java.nio.FloatBuffer;

import pocketviewer.Blocks.Block;
import static pocketviewer.Objects.BlockFace.*;
import pocketviewer.Objects.Chunk;
import pocketviewer.Objects.ChunkManager;
import pocketviewer.Objects.World;
import static pocketviewer.Utils.MathUtils.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

/**
 *
 * @author Jocopa3
 */
public class ChunkRenderer {
	public WorldRenderer worldRenderer;
	BlockRenderer blockRenderer;
	
	public Buffer[][] buffers;
	
	FloatBuffer vertexArray;
    public boolean updated = false; //One chunk update per frame only (for now?)
    public int chunksRendered = 0;
    
    boolean AO = true; //Use Ambient Occlusion or not (smooth shading)
    
	public ChunkRenderer(WorldRenderer worldRenderer){
		this.worldRenderer = worldRenderer;
		
		blockRenderer = new BlockRenderer();
		buffers = new Buffer[worldRenderer.world.chunks.length][worldRenderer.world.chunks.width];
	}
	
    //Rename to renderChunks();
	public void renderAllChunks(){
        for(int x = 0; x < World.world.chunks.width; x++){
			for(int z = 0; z < World.world.chunks.length; z++){
                //send chunk instead of x,z
				renderChunk(x, z);
			}
		}
        updated = false;
        //System.out.println(chunksRendered);
        chunksRendered = 0;
        //System.out.println(worldRenderer.player.camera.getYaw());
	}

	public void renderChunk(int x, int z){
		if(x < 0 || z < 0 || x >= World.world.chunks.width || z >= World.world.chunks.length)
			return;
        
        Chunk c = World.world.chunks.getChunk(x, z);
        
        if(c == null)
            World.world.generateChunk(x, z);
        
        //Check range first for speed, frustum next for added boost
        if(!worldRenderer.player.isChunkInRange(c) || !worldRenderer.frustum.cubeInFrustum(c.pos.x, c.minHeight-1, c.pos.z, c.pos.x + c.width, c.maxHeight+1, c.pos.z + c.length))
            return;

		Buffer chunkBuffer = getChunkBuffer(x, z);	

		if(!updated && (c.needsUpdate || chunkBuffer == null)){
			World.world.chunks.setChunk(x, z, updateChunkVBO(c, x, z)); //Update and set the chunk as updated
            chunkBuffer = getChunkBuffer(x, z);
        }

        //If the buffer is still null from not being updated or waiting to be updated, then screw rendering it!
        if(chunkBuffer == null)
            return;
        
		glPushMatrix();
		glTranslatef(x << 4, 0, z << 4);
		
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        
		for(int i = 0; i < 1; i++){
			if(chunkBuffer.getBuffer(i) <= 0 || chunkBuffer.getBufferLength(i) <= 0) //Ignore any empty buffers
				continue;
			
			glBindBuffer(GL_ARRAY_BUFFER, chunkBuffer.getBuffer(i));
            
            glVertexPointer(3, GL_FLOAT, 32, 0);
            glColorPointer(3, GL_FLOAT, 32, 12); 
            glTexCoordPointer(2, GL_FLOAT, 32, 24);
			
			//System.out.println(chunkBufferLengths[i]+" "+chunkBuffer[i]); //Debug
			
			glDrawArrays(GL_QUADS, 0, chunkBuffer.getBufferLength(i));
		}
        
        glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);
			
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glPopMatrix();
        
        chunksRendered++;
	}
    
	//Do NOT update the VBO unless a block is placed/removed/updated!
	//Updates the VBO for a given chunk and returns the updated chunk
	public Chunk updateChunkVBO(Chunk chunk, int cx, int cz){
		//long g = System.currentTimeMillis(); //Measure performance
		
        int posx = cx << 4;
        int posz = cz << 4;
        
		VBOHelper vboHelper = new VBOHelper(); //Create a new VBOHelper
        //vboHelper.shouldTexture(false); //For now, do nothing with texture offsets
		vboHelper.start(); //Start the VBOHelper

		//Occlusion culler; needs eventual fixing to check if block is solid/opaque
        int id;
        int light;
        boolean[] blocks;
        
        Vertex[] face;
        Block block;
		for(int x = 0; x < chunk.width; x++){
			for(int y = 0; y < chunk.height; y++){
				for(int z = 0; z < chunk.length; z++){
                    id = World.world.getBlockID(posx + x, y, posz + z);
                    block = World.world.getBlock(posx + x, y, posz + z);
					if(id > 0 && Block.blocks[id] != null){
                        light = World.world.getSkylight(posx + x, y, posz + z);
                        
						if(World.world.getBlockID(posx + x-1, y, posz + z) == 0){ //Check left
                            light = World.world.getSkylight(posx + x-1, y, posz + z);
                            if(AO){
                                blocks = new boolean[]{
                                    World.world.getBlock(posx + x-1, y-1, posz + z-1).hasShadow,
                                    World.world.getBlock(posx + x-1, y-1, posz + z).hasShadow,
                                    World.world.getBlock(posx + x-1, y-1, posz + z+1).hasShadow,
                                    World.world.getBlock(posx + x-1, y, posz + z+1).hasShadow,
                                    World.world.getBlock(posx + x-1, y+1, posz + z+1).hasShadow,
                                    World.world.getBlock(posx + x-1, y+1, posz + z).hasShadow,
                                    World.world.getBlock(posx + x-1, y+1, posz + z-1).hasShadow,
                                    World.world.getBlock(posx + x-1, y, posz + z-1).hasShadow};
                                face = BlockRenderer.getLeftWithAO(block.getFace(LEFT), x, y, z, light, blocks);
                            }else
                                face = BlockRenderer.getLeft(id, x, y, z, light);
                                //TINT BASED ON BIOME COLOR
							vboHelper.addVertices(face);
						}
                        
						if(World.world.getBlockID(posx + x+1, y, posz + z) == 0){ //Check right
                            light = World.world.getSkylight(posx + x+1, y, posz + z);
                            if(AO){
                                blocks = new boolean[]{
                                    World.world.getBlock(posx + x+1, y-1, posz + z-1).hasShadow,
                                    World.world.getBlock(posx + x+1, y-1, posz + z).hasShadow,
                                    World.world.getBlock(posx + x+1, y-1, posz + z+1).hasShadow,
                                    World.world.getBlock(posx + x+1, y, posz + z+1).hasShadow,
                                    World.world.getBlock(posx + x+1, y+1, posz + z+1).hasShadow,
                                    World.world.getBlock(posx + x+1, y+1, posz + z).hasShadow,
                                    World.world.getBlock(posx + x+1, y+1, posz + z-1).hasShadow,
                                    World.world.getBlock(posx + x+1, y, posz + z-1).hasShadow};
                                face = BlockRenderer.getRightWithAO(block.getFace(RIGHT), x, y, z, light, blocks);
                            }else
                                face = BlockRenderer.getRight(id, x, y, z, light);
                                //TINT BASED ON BIOME COLOR
							vboHelper.addVertices(face);
						}
                        
						if(World.world.getBlockID(posx + x, y-1, posz + z) == 0){ //Check bottom
							light = World.world.getSkylight(posx + x, y+1, posz + z);
                            if(AO){
                                blocks = new boolean[]{
                                    World.world.getBlock(posx + x-1, y-1, posz + z-1).hasShadow,
                                    World.world.getBlock(posx + x, y-1, posz + z-1).hasShadow,
                                    World.world.getBlock(posx + x+1, y-1, posz + z-1).hasShadow,
                                    World.world.getBlock(posx + x+1, y-1, posz + z).hasShadow,
                                    World.world.getBlock(posx + x+1, y-1, posz + z+1).hasShadow,
                                    World.world.getBlock(posx + x, y-1, posz + z+1).hasShadow,
                                    World.world.getBlock(posx + x-1, y-1, posz + z+1).hasShadow,
                                    World.world.getBlock(posx + x-1, y-1, posz + z).hasShadow};
                                face = BlockRenderer.getBottomWithAO(block.getFace(BOTTOM), x, y, z, light, blocks);
                            }else
                                face = BlockRenderer.getBottom(id, x, y, z, light);
                                //TINT BASED ON BIOME COLOR
							vboHelper.addVertices(face);
						}
                        
						if(World.world.getBlockID(posx + x, y+1, posz + z) == 0){ //Check top
							light = World.world.getSkylight(posx + x, y+1, posz + z);
                            if(AO){
                                blocks = new boolean[]{
                                    World.world.getBlock(posx + x-1, y+1, posz + z-1).hasShadow,
                                    World.world.getBlock(posx + x, y+1, posz + z-1).hasShadow,
                                    World.world.getBlock(posx + x+1, y+1, posz + z-1).hasShadow,
                                    World.world.getBlock(posx + x+1, y+1, posz + z).hasShadow,
                                    World.world.getBlock(posx + x+1, y+1, posz + z+1).hasShadow,
                                    World.world.getBlock(posx + x, y+1, posz + z+1).hasShadow,
                                    World.world.getBlock(posx + x-1, y+1, posz + z+1).hasShadow,
                                    World.world.getBlock(posx + x-1, y+1, posz + z).hasShadow};
                                face = BlockRenderer.getTopWithAO(block.getFace(TOP), x, y, z, light, blocks);
                            }else
                                face = BlockRenderer.getTop(id, x, y, z, light);
                                //TINT BASED ON BIOME COLOR
							vboHelper.addVertices(face);
						}
                        
						if(World.world.getBlockID(posx + x, y, posz + z-1) == 0){ //Check front
                            light = World.world.getSkylight(posx + x, y, posz + z-1);
                            if(AO){
                                blocks = new boolean[]{
                                    World.world.getBlock(posx + x-1, y-1, posz + z-1).hasShadow,
                                    World.world.getBlock(posx + x, y-1, posz + z-1).hasShadow,
                                    World.world.getBlock(posx + x+1, y-1, posz + z-1).hasShadow,
                                    World.world.getBlock(posx + x+1, y, posz + z-1).hasShadow,
                                    World.world.getBlock(posx + x+1, y+1, posz + z-1).hasShadow,
                                    World.world.getBlock(posx + x, y+1, posz + z-1).hasShadow,
                                    World.world.getBlock(posx + x-1, y+1, posz + z-1).hasShadow,
                                    World.world.getBlock(posx + x-1, y, posz + z-1).hasShadow};
                                face = BlockRenderer.getFrontWithAO(block.getFace(FRONT), x, y, z, light, blocks);
                            }else
                                face = BlockRenderer.getFront(id, x, y, z, light);
                                //TINT BASED ON BIOME COLOR
							vboHelper.addVertices(face);
						}
                        
						if(World.world.getBlockID(posx + x, y, posz + z+1) == 0){ //Check back
                            light = World.world.getSkylight(posx + x, y, posz + z+1);
                            if(AO){
                                blocks = new boolean[]{
                                    World.world.getBlock(posx + x-1, y-1, posz + z+1).hasShadow,
                                    World.world.getBlock(posx + x, y-1, posz + z+1).hasShadow,
                                    World.world.getBlock(posx + x+1, y-1, posz + z+1).hasShadow,
                                    World.world.getBlock(posx + x+1, y, posz + z+1).hasShadow,
                                    World.world.getBlock(posx + x+1, y+1, posz + z+1).hasShadow,
                                    World.world.getBlock(posx + x, y+1, posz + z+1).hasShadow,
                                    World.world.getBlock(posx + x-1, y+1, posz + z+1).hasShadow,
                                    World.world.getBlock(posx + x-1, y, posz + z+1).hasShadow};
                                face = BlockRenderer.getBackWithAO(block.getFace(BACK), x, y, z, light, blocks);
                            }else
                                face = BlockRenderer.getBack(id, x, y, z, light);
                                //TINT BASED ON BIOME COLOR
							vboHelper.addVertices(face);
						}
					}
				}
			}
        }
        
		vboHelper.stop(); //End the VBOHelper
		
		buffers[cx][cz] = vboHelper.getBuffer(); //Get VBO buffer handles
        
        vboHelper.cleanUp(true);
		
		//System.out.println("Buffer ID: "+buffers[cx][cz][0]+" Element Length: "+bufferLengths[cx][cz][0]); //Debug buffer and length
		
		chunk.needsUpdate = false; //Prevents updating the VBO again until necessary
		//System.out.println("Chunk VBO time: " + (System.currentTimeMillis()-g) + "ms"); //Debug
        
		updated = true;
        
		return chunk; //Return the updated chunk back to the caller
	}
	
	//Deletes all buffers for all chunks
	public void deleteAllChunkBuffers(){
		for(int x = 0; x < World.world.chunks.length; x++){
			for(int z = 0; z < World.world.chunks.width; z++){
				deleteChunkBuffer(x, z);
			}
		}
	}
	
	//Deletes all chunk buffers and clears stored buffers for a given chunk
	public void deleteChunkBuffer(int x, int z){
        if(buffers[x][z] == null)
            return;
        
		buffers[x][z].deleteAllBuffers();
	}
    
    public Buffer getChunkBuffer(int x, int z){
        return buffers[x][z];
    }
}

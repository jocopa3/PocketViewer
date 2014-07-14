/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pocketviewer.Renderer;

import java.nio.FloatBuffer;

import static pocketviewer.Objects.BlockFace.*;
import pocketviewer.Objects.Chunk;
import pocketviewer.Objects.ChunkManager;
import pocketviewer.Objects.World;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import pocketviewer.Blocks.Block;
import pocketviewer.Renderer.Frustum;

/**
 *
 * @author Jocopa3
 */
public class ChunkRenderer {
	
	public World world;
	public ChunkManager chunks;
	
	public WorldRenderer worldRenderer;
	BlockRenderer blockRenderer;
	
	public int[][][] buffers;
	public int[][][] bufferLengths;
	
	FloatBuffer vertexArray;
    public boolean updated = false; //One chunk update per frame only (for now?)
    public int chunksRendered = 0;
    
	public ChunkRenderer(WorldRenderer worldRenderer, ChunkManager chunks){
		this.worldRenderer = worldRenderer;
		world = worldRenderer.world;
        this.chunks = chunks;
		
		blockRenderer = new BlockRenderer();
		buffers = new int[chunks.length][chunks.width][VBOHelper.DEFAULT_BUFFER_AMOUNT];
		bufferLengths = new int[chunks.length][chunks.width][VBOHelper.DEFAULT_BUFFER_AMOUNT];
	}
	
	public void renderAllChunks(){
        for(int x = 0; x < chunks.width; x++){
			for(int z = 0; z < chunks.length; z++){
				renderChunk(x, z);
			}
		}
        updated = false;
        //System.out.println(chunksRendered);
        chunksRendered = 0;
        //System.out.println(worldRenderer.player.camera.getYaw());
	}

	public void renderChunk(int x, int z){
		if(x < 0 || z < 0 || x >= chunks.width || z >= chunks.length)
			return;
        
        Chunk c = chunks.getChunk(x, z);
        
        //Check range first for speed, frustum next for added boost
        if(!worldRenderer.player.isChunkInRange(c) || !worldRenderer.frustum.cubeInFrustum(c.pos.x, c.minHeight-1, c.pos.z, c.pos.x + c.width, c.maxHeight+1, c.pos.z + c.length))
            return;

		int[] chunkBuffer = getChunkBuffer(x, z);
		int[] chunkBufferLengths = getChunkBufferLength(x, z);		

		if(!updated && (c.needsUpdate || chunkBuffer == null))
			chunks.setChunk(x, z, updateChunkVBO(c, x, z)); //Update and set the chunk as updated

		glPushMatrix();
		glTranslatef(x << 4, 0, z << 4);
		
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        
		for(int i = 0; i < 1; i++){
			if(chunkBuffer[i] <= 0 || chunkBufferLengths[i] <= 0) //Ignore any empty buffers
				continue;
			
			glBindBuffer(GL_ARRAY_BUFFER, chunkBuffer[i]);
            
            glVertexPointer(3, GL_FLOAT, 32, 0);
            glColorPointer(3, GL_FLOAT, 32, 12);
            glTexCoordPointer(2, GL_FLOAT, 32, 24);
			
			//System.out.println(chunkBufferLengths[i]+" "+chunkBuffer[i]); //Debug
			
			glDrawArrays(GL_QUADS, 0, chunkBufferLengths[i]);
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
		for(int x = 0; x < chunk.width; x++){
			for(int y = 0; y < chunk.height; y++){
				for(int z = 0; z < chunk.length; z++){
                    id = world.getBlockID(posx + x, y, posz + z);
					if(id > 0 && Block.blocks[id] != null){
                        light = world.getSkylight(posx + x, y, posz + z);
						if(Block.blocks[world.getBlockID(posx + x-1, y, posz + z)] == null || world.getBlockID(posx + x-1, y, posz + z) == 0){ //Check left
							vboHelper.addVertices(BlockRenderer.getLeft(id, x, y, z, light));
						}
						if(Block.blocks[world.getBlockID(posx + x+1, y, posz + z)] == null || world.getBlockID(posx + x+1, y, posz + z) == 0){ //Check right
							vboHelper.addVertices(BlockRenderer.getRight(id, x, y, z, light));
						}
						if(Block.blocks[world.getBlockID(posx + x, y-1, posz + z)] == null || world.getBlockID(posx + x, y-1, posz + z) == 0){ //Check bottom
							vboHelper.addVertices(BlockRenderer.getBottom(id, x, y, z, light));
						}
						if(Block.blocks[world.getBlockID(posx + x, y+1, posz + z)] == null || world.getBlockID(posx + x, y+1, posz + z) == 0){ //Check top
							vboHelper.addVertices(BlockRenderer.getTop(id, x, y, z, light));
						}
						if(Block.blocks[world.getBlockID(posx + x, y, posz + z-1)] == null || world.getBlockID(posx + x, y, posz + z-1) == 0){ //Check front
							vboHelper.addVertices(BlockRenderer.getFront(id, x, y, z, light));
						}
						if(Block.blocks[world.getBlockID(posx + x, y, posz + z+1)] == null || world.getBlockID(posx + x, y, posz + z+1) == 0){ //Check back
							vboHelper.addVertices(BlockRenderer.getBack(id, x, y, z, light));
						}
					}
				}
			}
        }
        
		vboHelper.stop(); //End the VBOHelper
		
		buffers[cx][cz] = vboHelper.getBufferHandlers(); //Get VBO buffer handles
		bufferLengths[cx][cz] = vboHelper.getBufferLengths(); //Get VBO element lengths
        
        vboHelper.cleanUp(true);
		
		//System.out.println("Buffer ID: "+buffers[cx][cz][0]+" Element Length: "+bufferLengths[cx][cz][0]); //Debug buffer and length
		
		chunk.needsUpdate = false; //Prevents updating the VBO again until necessary
		//System.out.println("Chunk VBO time: " + (System.currentTimeMillis()-g) + "ms"); //Debug
        
		updated = true;
        
		return chunk; //Return the updated chunk back to the caller
	}
	
	//Deletes all buffers for all chunks
	public void deleteAllChunkBuffers(){
		for(int x = 0; x < chunks.length; x++){
			for(int z = 0; z < chunks.width; z++){
				deleteChunkBuffer(x, z);
			}
		}
	}
	
	//Deletes all chunk buffers and clears stored buffers for a given chunk
	public void deleteChunkBuffer(int x, int z){
		int[] chunkBuffers = getChunkBuffer(x, z);
		for(int i : chunkBuffers){
			glDeleteBuffers(i);
		}
		bufferLengths[x][z] = null;
		buffers[x][z] = null;
	}
	
	//Returns int array of buffer handlers for a given chunk
	public int[] getChunkBuffer(int x, int z){
		return buffers[x][z];
	}
	
	//Returns int array of buffer lenghts for a given chunk
	public int[] getChunkBufferLength(int x, int z){
		return bufferLengths[x][z];
	}
}

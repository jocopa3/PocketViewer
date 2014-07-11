/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import pocketviewer.Objects.*;
import pocketviewer.Utils.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import org.lwjgl.BufferUtils;
import java.nio.FloatBuffer;

public class Chunk {
	public World world;
	
	public final int width = 16;
	public final int length = 16;
	public final int height; //Done this way incase a 256 height limit is added
	
	public int xPos;
	public int zPos;
	
	public int chunkx;
	public int chunkz;
	
	public boolean needsUpdate = true;
	public int vertexBufferObject;
	public int colorBuffer;
	public int bufferLength;
	public int textureBuffer;
	
	public int blockTotal = 0;
	
	public StorageArray[] DataArrays;
	public byte[][] heights;
	
	public NibbleArray dirtyColumns;
	
	public Chunk(World world, int x, int z){
		this.world = world;
		this.chunkx = x;
		this.chunkz = z;
		
		heights = new byte[width][length];
		
		height = 128; //Default for pocket as of 0.9.0
		dirtyColumns = new NibbleArray(width*length);
		DataArrays = new StorageArray[height >> 4];
		for(int i = 0; i < DataArrays.length; i++){
			DataArrays[i] = new StorageArray();
		}
	}
	
	public void setBlockID(int x, int y, int z, int id){
		int sect = y >> 4;
		x&=15;
		z&=15;
        if (sect >= DataArrays.length){
            return;
        }else if(DataArrays[sect] != null){
            DataArrays[sect].setBlock(x, y & 15, z, id);
			blockTotal++;
        }
		needsUpdate = true;
    }
	
	public void setBlockData(int x, int y, int z, int data){
		int sect = y >> 4;
		x&=15;
		z&=15;
        if (sect >= DataArrays.length){
            return;
        }else if(DataArrays[sect] != null){
            DataArrays[sect].setBlockData(x, y & 15, z, data);
        }
		needsUpdate = true;
    }
	
	public void setBlockAndData(int x, int y, int z, int id, int data){
		int sect = y >> 4;
		x&=15;
		z&=15;
        if (sect >= DataArrays.length){
            return;
        }else if(DataArrays[sect] != null){
            DataArrays[sect].setBlockAndData(x, y & 15, z, id, data);
        }
		needsUpdate = true;
    }
	
	public void setLight(int x, int y, int z, int lightVal){
		int sect = y >> 4;
		x&=15;
		z&=15;
        if (sect >= DataArrays.length){
            return;
        }else if(DataArrays[sect] != null){
            DataArrays[sect].setLight(x, y & 15, z, lightVal);
        }
		needsUpdate = true;
    }
	
	public void setSkylight(int x, int y, int z, int skylightVal){
		int sect = y >> 4;
		x&=15;
		z&=15;
        if (sect >= DataArrays.length){
            return;
        }else if(DataArrays[sect] != null){
            DataArrays[sect].setSkylight(x, y & 15, z, skylightVal);
        }
		needsUpdate = true;
    }
	
	//Fix
	public void setColumnAsDirty(int x, int z){
		
	}
	
	public int getBlockID(int x, int y, int z){
		int sect = y >> 4;
		x&=15;
		z&=15;
        if (sect >= DataArrays.length || sect < 0){
            return 0;
        }else{
            return DataArrays[sect] != null ? DataArrays[sect].getBlock(x, y & 15, z) : 0;
        }
    }
	
	public int getBlockData(int x, int y, int z){
		int sect = y >> 4;
		x&=15;
		z&=15;
        if (sect >= DataArrays.length){
            return 0;
        }else{
            return DataArrays[sect] != null ? DataArrays[sect].getBlockData(x, y & 15, z) : 0;
        }
    }
	
	public int getLight(int x, int y, int z){
		int sect = y >> 4;
		x&=15;
		z&=15;
        if (sect >= DataArrays.length){
            return 0;
        }else{
            return DataArrays[sect] != null ? DataArrays[sect].getLight(x, y & 15, z) : 0;
        }
    }
	
	public int getSkylight(int x, int y, int z){
		int sect = y >> 4;
		x&=15;
		z&=15;
        if (sect >= DataArrays.length){
            return 0;
        }else{
            return DataArrays[sect] != null ? DataArrays[sect].getSkylight(x, y, z) : 0;
        }
    }
	
	//Fix
	public int getIsColumnDirty(int x, int z){
		return 0;
	}
	
	//Fix
	public int getMaxHeight(int x, int z){
		x = x & 15; z = z & 15;
		return heights[x][z];
	}
	
	public void setMaxHeight(int x, int z, int height){
		x = x & 15; z = z & 15;
		heights[x][z] = (byte)(height & 0xff);
	}

	//Do NOT update this unless a block is placed/removed/updated!
	public void updateVBO(){
		long g = System.currentTimeMillis();
		java.util.ArrayList<Float> buff = new java.util.ArrayList<Float>();
		java.util.ArrayList<Float> colorBuff = new java.util.ArrayList<Float>();
		//java.util.ArrayList<Float> textureBuff = new java.util.ArrayList<Float>();
        
        Block b;
        float[][] face;
		for(int x = 0; x < width; x++){
			for(int y = 0; y < height; y++){
				for(int z = 0; z < length; z++){
					if(getBlockID(x,y,z)>0){
                        b = new Tests.Block(x,y,z);
						if(world.getBlockID(chunkx*16+x-1,y,chunkz*16+z)==0){
							//BlockRender.getRight(id);
							face = b.getRightC();
							for(int i = 0; i < face[0].length; i++){
								buff.add(face[0][i]);
								colorBuff.add(face[1][i]-((0.05f*getSkylight(x,y,z))));
							}
							
						}
						if(world.getBlockID(chunkx*16+x+1,y,chunkz*16+z)==0){
							face = b.getLeftC();
							for(int i = 0; i < face[0].length; i++){
								buff.add(face[0][i]);
								colorBuff.add(face[1][i]-((0.05f*getSkylight(x,y,z))));
							}
						}
						if(world.getBlockID(chunkx*16+x,y-1,chunkz*16+z)==0){
							face = b.getBottomC();
							for(int i = 0; i < face[0].length; i++){
								buff.add(face[0][i]);
								colorBuff.add(face[1][i]-((0.05f*getSkylight(x,y,z))));
							}
						}
						if(world.getBlockID(chunkx*16+x,y+1,chunkz*16+z)==0){
							face = b.getTopC();
							for(int i = 0; i < face[0].length; i++){
								buff.add(face[0][i]);
								colorBuff.add(face[1][i]-((0.05f*getSkylight(x,y,z))));
							}
						}
						if(world.getBlockID(chunkx*16+x,y,chunkz*16+z-1)==0){
							face = b.getFrontC();
							for(int i = 0; i < face[0].length; i++){
								buff.add(face[0][i]);
								colorBuff.add(face[1][i]-((0.05f*getSkylight(x,y,z))));
							}
						}
						if(world.getBlockID(chunkx*16+x,y,chunkz*16+z+1)==0){
							face = b.getBackC();
							for(int i = 0; i < face[0].length; i++){
								buff.add(face[0][i]);
								colorBuff.add(face[1][i]-((0.05f*getSkylight(x,y,z))));
							}
						}
					}
				}
			}
		}
		bufferLength = buff.size();
		//System.out.println(bufferLength);
		
		//vboBuffer = new float[bufferLength];
        FloatBuffer vertexArray = BufferUtils.createFloatBuffer(bufferLength);
		FloatBuffer colorArray = BufferUtils.createFloatBuffer(bufferLength);
		//FloatBuffer textureArray = BufferUtils.createFloatBuffer(bufferLength);
        // Iterate over all the points and store them in the FloatBuffer

		for(int i = 0; i < bufferLength; i++) {
			vertexArray.put(buff.get(i));
			colorArray.put(colorBuff.get(i));
			//textureArray.put(textureBuff.get(i));
        }
		buff.clear();
		colorBuff.clear();
		//textureBuff.clear();
        // Make the buffer read-able for OpenGL
        vertexArray.flip();
		colorArray.flip();
		//textureArray.flip();
		

        // Create the handle for the VBO
        vertexBufferObject = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);
        glBufferData(GL_ARRAY_BUFFER, vertexArray, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		// Create the handle for the VBO
        colorBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, colorBuffer);
        glBufferData(GL_ARRAY_BUFFER, colorArray, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		//textureBuffer = glGenBuffers();
		//glBindBuffer(GL_ARRAY_BUFFER, textureBuffer);
		//glBufferData(GL_ARRAY_BUFFER, textureArray, GL_STATIC_DRAW);
		//glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		needsUpdate = false;
		//System.out.println(System.currentTimeMillis()-g);
		if(chunkx == 0 && chunkz == 0 && false){
		for(int x = 0; x < width; x++){
			for(int z = 0; z < length; z++){
				System.out.print(" "+getSkylight(x,70,z));
			}
			System.out.println();
		}
		}
	}
	
	public int getBuffer(){
		if(needsUpdate)
			updateVBO();
		
		return vertexBufferObject;
	}
	
	public int getColorBuffer(){
		if(needsUpdate)
			updateVBO();
		
		return colorBuffer;
	}
	
	public int getTextureBuffer(){
		if(needsUpdate)
			updateVBO();
		
		return textureBuffer;
	}
	
	public int getBufferLength(){
		if(needsUpdate)
			updateVBO();
		
		return bufferLength/3;
	}
}
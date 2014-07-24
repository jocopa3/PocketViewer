/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pocketviewer.Objects;

import pocketviewer.Blocks.*;
/**
 *
 * @author Jocopa3
 */
public class World {
    public static World world;
	public final int height = 128;
	public final long seed;
	public ChunkManager chunks;
	
	public World(){
        world = this;
		seed = System.currentTimeMillis();
		chunks = new ChunkManager(this, 16, 16);
	}
	
	public World(long seed){
        world = this;
		this.seed = seed;
		chunks = new ChunkManager(this, 16, 16);
	}
	
	public World(int cacheSize){
        world = this;
		seed = System.currentTimeMillis();
		chunks = new ChunkManager(this, cacheSize, cacheSize);
	}
	public World(int cacheSize, long seed){
        world = this;
        this.seed = seed;
		chunks = new ChunkManager(this, cacheSize, cacheSize);
	}
    
    public void generateChunk(int x, int z){
        chunks.genChunk(x, z);
    }
	
	public int getBlockID(int x, int y, int z){
		return chunks.getBlockID(x, y, z);
	}
	
	public void setBlockID(int x, int y, int z, int id){
		chunks.setBlockID(x, y, z, id);
	}
    
    public int getSkylight(int x, int y, int z){
		return chunks.getSkylight(x, y, z);
	}
    
    public Block getBlock(int x, int y, int z){
		return Block.blocks[getBlockID(x, y, z)];
	}
}

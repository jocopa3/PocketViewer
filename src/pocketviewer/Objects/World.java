/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pocketviewer.Objects;

/**
 *
 * @author Jocopa3
 */
public class World {
	public final int height = 128;
	public final long seed;
	public ChunkManager chunks;
	
	public World(){
		seed = System.currentTimeMillis();
		chunks = new ChunkManager(this, 16, 16);
		chunks.genAllChunks();
	}
	
	public World(long seed){
		this.seed = seed;
		chunks = new ChunkManager(this, 16, 16);
		chunks.genAllChunks();
	}
	
	public World(int cacheSize){
		seed = System.currentTimeMillis();
		chunks = new ChunkManager(this, cacheSize, cacheSize);
		chunks.genAllChunks();
	}
	public World(int cacheSize, long seed){
		this.seed = seed;
		chunks = new ChunkManager(this, cacheSize, cacheSize);
		chunks.genAllChunks();
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
}

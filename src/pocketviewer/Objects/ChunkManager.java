/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pocketviewer.Objects;

import pocketviewer.Generator.WorldSource;
import static pocketviewer.Utils.MathUtils.max;
/**
 *
 * @author Jocopa3
 */
public class ChunkManager {
	
	public Chunk[][] chunks;
	public World world;
	public WorldSource worldsource;
	
	public int posx;
	public int posz;
	public int width;
	public int length;
	
	public ChunkManager(World world, int size){
		this.world = world;
		chunks = new Chunk[size][size];
		
		posx = 0;
		posz = 0;
        
		this.width = size;
		this.length = size;
		
		worldsource = new WorldSource(world);
	}
	
	public ChunkManager(World world, int width, int length){
		this.world = world;
		chunks = new Chunk[width][length];
		
		posx = 0;
		posz = 0;
		
		this.width = width;
		this.length = length;
		
		worldsource = new WorldSource(world);
	}
	
	public void genAllChunks(){
		for(int x = 0; x < width; x++){
			for(int z = 0; z < length; z++){
				genChunk(x, z);
			}
		}
		//System.out.println("Done");
	}
	
	public void genChunk(int x, int z){
        x%=width;
        z%=length;
		if(x<0||x>=chunks.length||z<0||z>=chunks[0].length)
			return;
		
		//System.out.println(x+" "+z);
		chunks[x][z] = worldsource.getChunk(posx*width+x, posz*length+z);
	}
	
	public Chunk getChunk(int x, int z){
        x%=width;
        z%=length;
		if(x<0||x>=chunks.length||z<0||z>=chunks[0].length)
			return null;
		
		return chunks[x][z];
	}
	//fix to allow for chunk pos in world and not in cache
	public void setChunk(int x, int z, Chunk chunk){
        x%=width;
        z%=length;
		if(x<0||x>=chunks.length||z<0||z>=chunks[0].length)
			return;
		
		chunks[x][z] = chunk;
	}
	
	public int getBlockID(int x, int y, int z){
		int cx = x >> 4;
		int cz = z >> 4;
		
		if(cx < posx || cx >= posx + width || cz < posz || cz >= posz + length || chunks[cx][cz] == null)
			return 0;
        
		return chunks[cx][cz].getBlockID(x & 15, y, z & 15);
	}
	
	public void setBlockID(int x, int y, int z, int id){
		int cx = x >> 4;
		int cz = z >> 4;
		
		if(cx < posx || cx >= posx + width || cz < posz || cz >= posz + length || chunks[cx][cz] == null)
			return;
		
		chunks[cx][cz].setBlockID(x, y, z, id);
	}
    
    public int getSkylight(int x, int y, int z){
		int cx = x >> 4;
		int cz = z >> 4;
		
		if(cx < posx || cx >= posx + width || cz < posz || cz >= posz + length || chunks[cx][cz] == null)
			return 0;
        
		return chunks[cx][cz].getSkylight(x & 15, y, z & 15);
	}
}

package pocketviewer.Generator;

import pocketviewer.Generator.Noise.*;
import pocketviewer.Objects.Chunk;
import pocketviewer.Objects.World;

/**
 *
 * @author Jocopa3
 */
public class WorldSource {
	public Noise noise;
	public World world;
	
	public WorldSource(){
		noise = new SimplexNoise();
	}
	
	public WorldSource(long seed){
		noise = new SimplexNoise(seed);
	}
	
	public WorldSource(World world){
		noise = new SimplexNoise(world.seed);
		this.world = world;
	}
	
	public Chunk getChunk(int x, int z){
		Chunk chunk = new Chunk(world);
		
		int height = 0;
		for(int ax = 0; ax < 16; ax++){
			for(int az = 0; az < 16; az++){
				height = (int)(noise.noise(ax/70f, az/70f, 0)*chunk.height/2);
				for(int ay = 0; ay < height; ay++){
					chunk.setBlockID(ax, ay, az, 1);
				}
			}
		}
		return chunk;
	}
}

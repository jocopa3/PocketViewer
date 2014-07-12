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
        this.world = world;
		noise = new SimplexNoise(world.seed);
	}
	
	public Chunk getChunk(int x, int z){
		Chunk chunk = new Chunk(world, x, z);
		int posx = x << 4;
        int posz = z << 4;
        
		int height;
        
        int X;
        int Z;
		for(int ax = 0; ax < 16; ax++){
			for(int az = 0; az < 16; az++){
                X = ax + posx;
                Z = az + posz;
                int max = 0;
				height = (int)(noise.noise(X/150f, Z/150f, 0)*chunk.height/2); //Heightmap
				for(int ay = 0; ay < height; ay++){
                    //if(noise.octaveNoise(2, X/70f, ay/70f, Z/70f) > 2f){ //Create a "cave" effect via 3D noise
                        if(height-ay == 1)
                            chunk.setBlockID(ax, ay, az, 2); //Grass
                        else if(height-ay < 5)
                            chunk.setBlockID(ax, ay, az, 3); //Dirt
                        else
                            chunk.setBlockID(ax, ay, az, 1); //Stone
                        if(ay > max)
                            max = ay;
                    //}
				}
                chunk.setMaxHeight(ax, az, max);
			}
		}
        
        //chunk = updateSkyLight(chunk);
        
		return chunk;
	}
    
    public Chunk updateSkyLight(Chunk chunk){
        return chunk;
    }
}

package pocketviewer.Generator;

import pocketviewer.Generator.Noise.*;
import static pocketviewer.Generator.Noise.NoiseUtils.*;
import pocketviewer.Objects.Chunk;
import pocketviewer.Objects.World;
import static pocketviewer.Utils.MathUtils.*;

/**
 *
 * @author Jocopa3
 */
public class WorldSource {
	public Noise noise;
	public World world;
    public NoiseMap map1, map2, map3, map4, map5, biomeMap;
    
	
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
    
    /*
    public Chunk getChunk(int x, int z){
		Chunk chunk = new Chunk(world, x, z);
		int posx = x << 4;
        int posz = z << 4;
        
        
        int X;
        int Z;
		for(int ax = 0; ax < 16; ax++){
			for(int az = 0; az < 16; az++){
                X = ax + posx;
                Z = az + posz;
                if(ax%2==az%2)
                    chunk.setBlockID(ax, 0, az, X);
            }
        }
        return chunk;
    }
    */
	
    /*Sphere
    public Chunk getChunk(int x, int z){
		Chunk chunk = new Chunk(world, x, z);
		int posx = x << 4;
        int posz = z << 4;
        
        
        int X;
        int Z;
        int max;
		for(int ax = 0; ax < 16; ax++){
			for(int az = 0; az < 16; az++){
                max = 0;
                for(int Y = 0; Y < chunk.height; Y++){
                    X = ax + posx;
                    Z = az + posz;
                    //int z
                    if(Math.floor(256/(sqr(X-128)+sqr(Y-64)+sqr(Z-128)+0.000001)) > 0){
                        chunk.setBlockID(ax, Y, az, 1);
                        if(max < Y)
                            max = Y;
                    }
                    
                }
                chunk.setMaxHeightAt(ax, az, max);
            }
        }
        
        chunk = updateSkyLight(chunk);
        
        return chunk;
    }
    */
    
    ///*
	public Chunk getChunk(int x, int z){
		Chunk chunk = new Chunk(world, x, z);
		int posx = x << 4;
        int posz = z << 4;
        
        map1 = new NoiseMap(noise, new NoiseParams(16,1f,1f,200f), chunk.width, chunk.length, posx, posz);
        map2 = new NoiseMap(noise, new NoiseParams(16,1f,1f,35f), chunk.width, chunk.length, posx, posz);
        map3 = new NoiseMap(noise, new NoiseParams(10,1f,1f,50f), chunk.width, chunk.length, posx, posz);
        map4 = new NoiseMap(noise, new NoiseParams(12,1f,1f,900f), chunk.width, chunk.length, posx, posz);
        biomeMap = new NoiseMap(noise, new NoiseParams(16,1f,1f,1000f), chunk.width, chunk.length, posx, posz);
        
		float height1, height2, height3, height4, max, biome, biomeAdditive;
        
        float density;
        
        int X;
        int Z;
		for(int ax = 0; ax < 16; ax++){
			for(int az = 0; az < 16; az++){
                X = ax + posx;
                Z = az + posz;

                height1 = map1.get(ax, az)*2f+1/1.25f; //Mountain
                height2 = map2.get(ax, az)/12f + 1/2.5f; //Plains 1
                height3 = map3.get(ax, az)/16f + 1/2.25f; //Backup Noise
                height4 = (height2*2.8f - height1)/3f + 1/3f;
                height4 = sCurveNormalized(-height4*8,0.4f,0.95f); //Turn the noise into a value between 0.4,0.95
                biome = biomeMap.get(ax, az);
                biomeAdditive = map4.get(ax, az);
                max = max(height3*chunk.height, height4*chunk.height);
                //map4 = new NoiseMap(noise, new NoiseParams(12,1f,1f,70f), 1, round(max), 1, posx, posz);
                //height3 = max(height3, (noise.octaveNoise(12, 1f, 1f, X/50f, Z/50f, 0)*chunk.height/8)+chunk.height/3f);
                //ADD BIOME COLOR STUFF
				for(int Y = 0; Y < max; Y++){
                    //if(biome+biomeAdditive>0.75)
                        //continue;
                    density = height4+(noise.octaveNoise3D(12, 1f, 1f, X/30f, Y/25f, Z/30f))/12;
                    
                    if(max > 1) //Create a "cave" effect via 3D noise
                    //if(density > 0.6f)
                        if(max-Y <= 1){
                            chunk.setBlockID(ax, Y, az, 2); //Grass
                        }else if(max-Y < 4.2){
                            chunk.setBlockID(ax, Y, az, 3); //Dirt
                        }else{
                            chunk.setBlockID(ax, Y, az, 1); //Stone
                        }
                    else if(Y < height3*chunk.height){
                        chunk.setBlockID(ax, Y, az, 1);
                    }
                    
                    //if(Y > max)
                        //max = Y;
				}
                chunk.setMaxHeightAt(ax, az, round(max));
			}
		}
        
        chunk = updateSkyLight(chunk);
        
		return chunk;
	}
    //*/
    
    /*
    public Chunk updateSkyLight(Chunk chunk){
        int light;
        for(int ax = 0; ax < 16; ax++){
            for(int az = 0; az < 16; az++){
                light = 15;
                for(int ay = chunk.height-1; ay >= 0; ay--){
                    if(chunk.getBlockID(ax, ay, az) > 0)
                        break;
                    
                    chunk.setSkylight(ax, ay, az, light);
                }
            }
        }
        
        for(int i = 0; i < 15; i++)
            for(int ax = 0; ax < 16; ax++){
                for(int az = 0; az < 16; az++){
                    for(int ay = chunk.height-1; ay >= 0; ay--){
                        light = max(max(max(max(chunk.getSkylight(ax,ay,az), chunk.getSkylight(ax+1,ay,az)-1), chunk.getSkylight(ax-1,ay,az)-1), chunk.getSkylight(ax,ay,az+1)-1), chunk.getSkylight(ax,ay,az-1)-1);
                        chunk.setSkylight(ax, ay, az, light);
                    }
                }
            }
            
        
        return chunk;
    }
    */
    
    //Old algorithm
    public Chunk updateSkyLight(Chunk chunk){
		//int height;
        int light = 15;
		for(int ax = 0; ax < 16; ax++){
			for(int az = 0; az < 16; az++){
				//height = chunk.getMaxHeightAt(ax, az); //Heightmap
				for(int ay = chunk.height-1; ay >= 0; ay--){
                    if(chunk.getBlockID(ax, ay, az) > 0 && light > 0)
                        light--;
                    chunk.setSkylight(ax, ay, az, light);
				}
                light = 15;
			}
		}
        
        return chunk;
    }
    //*/
}

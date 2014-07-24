/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pocketviewer.Generator.Noise;

import org.lwjgl.util.vector.Vector2f;
/**
 * Used to pre-generate and cache noise
 * @author Jocopa3
 */
public class NoiseMap {
    public Noise noise;
    public NoiseParams parameters;
    
    public float[] noiseMap;
    public boolean is3D = false;
    
    public int posX;
    public int posZ;
    
    public int xSize;
    public int ySize;
    public int zSize;
    
    public NoiseMap(Noise noise, NoiseParams params, int sizeX, int sizeZ, int posX, int posZ){
        this.noise = noise;
        parameters = params;
        
        noiseMap = new float[sizeX*sizeZ];
        is3D = false;
        
        xSize = sizeX;
        ySize = 0;
        zSize = sizeZ;
        this.posX = posX;
        this.posZ = posZ;
        
        genMap2D();
    }
    
    public NoiseMap(Noise noise, NoiseParams params, int sizeX, int sizeY, int sizeZ, int posX, int posZ){
        this.noise = noise;
        parameters = params;
        
        noiseMap = new float[sizeX*sizeY*sizeZ];
        is3D = true;
        
        xSize = sizeX;
        ySize = sizeY;
        zSize = sizeZ;
        this.posX = posX;
        this.posZ = posZ;
        
        genMap3D();
    }
    
    public float get(int x, int z){
        if(is3D)
            return noiseMap[x+z*xSize*ySize]; //x, 0, z
        else
            return noiseMap[x+z*xSize]; //x, z
    }
    
    public float get(int x, int y, int z){
        if(is3D)
            return noiseMap[x+y*xSize+z*xSize*ySize]; //x, y, z
        else
            return noiseMap[x+z*xSize]; //x, z
    }
    
    public void genMap2D(){
        for(int x = 0; x < xSize; x++){
            for(int z = 0; z < zSize; z++){
                noiseMap[x+z*xSize] = noise.octaveNoise2D(parameters.Octaves, parameters.Frequency, parameters.Amplitude, (posX+x)/parameters.Scale, (posZ+z)/parameters.Scale);
            }
        }
    }
    
    public void genMap3D(){
        for(int x = 0; x < xSize; x++){
            for(int y = 0; y < ySize; y++){
                for(int z = 0; z < zSize; z++){
                    noiseMap[x+y*xSize+z*xSize*ySize] = noise.octaveNoise3D(parameters.Octaves, parameters.Frequency, parameters.Amplitude, (posX+x)/parameters.Scale, y/parameters.Scale, (posZ+z)/parameters.Scale);
                }
            }
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pocketviewer.Objects;

import pocketviewer.Utils.*;
import org.lwjgl.util.vector.Vector3f;

public class Chunk {
	public World world;
	
	public final int width = 16;
	public final int length = 16;
	public final int height; //Done this way incase a 256 height limit is added
	
	public int xPos;
	public int zPos;
	public Vector3f pos;
    
	public boolean needsUpdate = true;
	
	public StorageArray[] DataArrays;
	
	public byte[] heightMap;
	
	public NibbleArray dirtyColumns; //Not implemented yet
    
    public int[] biomeColors;
    
    public int maxHeight;
    public int minHeight;
	
	public Chunk(World world, int x, int z){
		this.world = world;
		height = world.height;
        this.xPos = x;
        this.zPos = z;
        this.pos = new Vector3f(x << 4, 0, z << 4);
        
		heightMap = new byte[width*height];
        biomeColors = new int[width*height];
		dirtyColumns = new NibbleArray(width*length);
		DataArrays = new StorageArray[height >> 4];
        for(int i = 0; i < DataArrays.length; i++){
            DataArrays[i] = new StorageArray();
        }
	}
	
	public void setBlockID(int x, int y, int z, int id){
		int sect = y >> 4;
        if (sect >= DataArrays.length || sect < 0){
            return;
        }else if(DataArrays[sect] != null){
            DataArrays[sect].setBlock(x&15, y & 15, z&15, id);
            if(y>maxHeight)
                maxHeight = y;
            if(y<minHeight)
                minHeight = y;
			needsUpdate = true;
        }
    }
	
	public void setBlockData(int x, int y, int z, int data){
		int sect = y >> 4;
        if (sect >= DataArrays.length || sect < 0){
            return;
        }else if(DataArrays[sect] != null){
            DataArrays[sect].setBlockData(x&15, y & 15, z&15, data);
			needsUpdate = true;
        }
    }
	
	public void setBlockAndData(int x, int y, int z, int id, int data){
		int sect = y >> 4;
        if (sect >= DataArrays.length || sect < 0){
            return;
        }else if(DataArrays[sect] != null){
            DataArrays[sect].setBlockAndData(x&15, y & 15, z&15, id, data);
            if(y>maxHeight)
                maxHeight = y;
            if(y<minHeight)
                minHeight = y;
			needsUpdate = true;
        }
    }
	
	public void setLight(int x, int y, int z, int lightVal){
		int sect = y >> 4;
        if (sect >= DataArrays.length || sect < 0){
            return;
        }else if(DataArrays[sect] != null){
            DataArrays[sect].setLight(x&15, y & 15, z&15, lightVal);
			needsUpdate = true;
        }
    }
	
	public void setSkylight(int x, int y, int z, int skylightVal){
		int sect = y >> 4;
        if (sect >= DataArrays.length || sect < 0){
            return;
        }else if(DataArrays[sect] != null){
            DataArrays[sect].setSkylight(x&15, y & 15, z&15, skylightVal);
			needsUpdate = true;
        }
    }
	
	//Fix
	public void setColumnAsDirty(int x, int z){
		
	}
	
	public int getBlockID(int x, int y, int z){
        x&=15;
        z&=15;
		int sect = y >> 4;
        if (sect >= DataArrays.length || sect < 0){
            return 0;
        }else{
            return DataArrays[sect] != null ? DataArrays[sect].getBlock(x&15, y & 15, z&15) : 0;
        }
    }
	
	public int getBlockData(int x, int y, int z){
		int sect = y >> 4;
        if (sect >= DataArrays.length || sect < 0){
            return 0;
        }else{
            return DataArrays[sect] != null ? DataArrays[sect].getBlockData(x&15, y & 15, z&15) : 0;
        }
    }
	
	public int getLight(int x, int y, int z){
		int sect = y >> 4;
        if (sect >= DataArrays.length || sect < 0){
            return 0;
        }else{
            return DataArrays[sect] != null ? DataArrays[sect].getLight(x&15, y & 15, z&15) : 0;
        }
    }
	
	public int getSkylight(int x, int y, int z){
		int sect = y >> 4;
        if (sect >= DataArrays.length || sect < 0){
            return 0;
        }else{
            return DataArrays[sect] != null ? DataArrays[sect].getSkylight(x&15, y & 15, z&15) : 0;
        }
    }
	
	//Fix
	public int getIsColumnDirty(int x, int z){
		return 0;
	}
	
	public void setMaxHeightAt(int x, int z, int max){
		heightMap[(z << 4) | x] = (byte)(max&0xff);
	}
	
	//Fix
	public int getMaxHeightAt(int x, int z){
		return heightMap[(z << 4) | x];
	}
    
    public int getMaxHeight(){
        return maxHeight;
    }
    
    public int getMinHeight(){
        return minHeight;
    }
    
    public void setBiomeColorAt(int x, int z, int color){
		biomeColors[(z << 4) | x] = color;
	}
	
	//Fix
	public int getBiomeColorAt(int x, int z){
		return biomeColors[(z << 4) | x];
	}
}
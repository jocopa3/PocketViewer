package pocketviewer.Utils;

/*
 * StorageArray for storing a 16x16x16 section of blocks
 * 
 * Holds all block data and lighting for the given section
 */
public class StorageArray {
	//Switch between Anvil format (YZX) and Pocket Editions format (XZY)
	//Pocket Edition format does not want to work at the moment
	public boolean useAnvil = true;
	
	// Contains all blocks
	public byte[] blocks;
	
	// Contains block data
	public NibbleArray blockData;
	
	// Conatins all block light data
	public NibbleArray blockLightData;
	
	// Contains all skylight data for blocks
	public NibbleArray skylightData;
	
	//Create a new storage array
	public StorageArray(){	
		blocks = new byte[4096]; //16*16*16
		blockData = new NibbleArray(blocks.length, useAnvil);
		blockLightData = new NibbleArray(blocks.length, useAnvil);
		skylightData = new NibbleArray(blocks.length, useAnvil);
	}
	
	public void setBlock(int x, int y, int z, int block){
		x&=15;
		y&=15;
		z&=15;
		blocks[useAnvil ? ((y << 8) | (z << 4) | x) : ((x << 8) | (z << 4) | y)] = (byte)(block & 255);
	}
	
	public void setBlockData(int x, int y, int z, int data){
		x&=15;
		y&=15;
		z&=15;
		blockData.set(x,y,z,data);
	}
	
	public void setBlockAndData(int x, int y, int z, int block, int data){
		x&=15;
		y&=15;
		z&=15;
		setBlock(x,y,z,data);
		blockData.set(x,y,z,data);
	}
	
	public void setLight(int x, int y, int z, int lightVal){
		x&=15;
		y&=15;
		z&=15;
		blockLightData.set(x,y,z,lightVal);
	}
	
	public void setSkylight(int x, int y, int z, int skylightVal){
		x&=15;
		y&=15;
		z&=15;
		skylightData.set(x,y,z,skylightVal);
	}
	
	public int getBlock(int x, int y, int z){
		x&=15;
		y&=15;
		z&=15;
		return blocks[useAnvil ? ((y << 8) | (z << 4) | x) : ((x << 8) | (z << 4) | y) | x];
	}
	
	public int getBlockData(int x, int y, int z){
		x&=15;
		y&=15;
		z&=15;
		return blockData.get(x, y, z);
	}
	
	public int[] getBlockAndData(int x, int y, int z, int block){
		x&=15;
		y&=15;
		z&=15;
		return new int[] {getBlock(x, y, z), blockData.get(x, y, z)};
	}
	
	public int getLight(int x, int y, int z){
		x&=15;
		y&=15;
		z&=15;
		return blockLightData.get(x, y, z);
	}
	
	public int getSkylight(int x, int y, int z){
		x&=15;
		y&=15;
		z&=15;
		return skylightData.get(x, y, z);
	}
}

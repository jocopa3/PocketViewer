/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pocketviewer.Blocks;

import pocketviewer.Objects.Face;
/**
 *
 * @author Jocopa3
 */
public class Block {
	public int x;
	public int y;
	public int z;
	public int id;
	public int data;
	
	public Face[] faces;
	
	public static Block[] blocks = new Block[256];
	
	public static void initBlocks(){
		blocks[0] = new AirBlock(0);
		blocks[1] = new StoneBlock(1);
		blocks[2] = new GrassBlock(2);
	}
	
	public Block(int x, int y, int z, int id){
		this.x = x;
		this.y = y;
		this.z = z;
		this.id = id;
		this.data = 0;
		faces = new Face[6];
	}
	
	public Block(int x, int y, int z, int id, int data){
		this.x = x;
		this.y = y;
		this.z = z;
		this.id = id;
		this.data = data;
		faces = new Face[6];
	}
	
	public Block(int id){
		this.id = id;
		this.data = 0;
		faces = new Face[6];
	}
	
	public Block(int id, int data){
		this.id = id;
		this.data = data;
		faces = new Face[6];
	}
	
	public int getID(){
		return id;
	}
	
	public int getData(){
		return data;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getZ(){
		return z;
	}
	
	public int[] getXYZ(){
		return new int[]{x,y,z};
	}
	
	public Face getFace(int side){
		return faces[side];
	}
	
	public void setFace(int side, Face face){
		faces[side] = face;
	}
}

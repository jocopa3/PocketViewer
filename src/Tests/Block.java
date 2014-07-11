package Tests;

/**
 *
 * @author Jocopa3
 */
public class Block {
	final float x;
	final float y;
	final float z;

	public Block(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float[] getCube(){
		return new float[]{
			x, y, z, x+1, y, z, x+1, y+1, z, x, y+1, z, //front
			x, y+1, z, x+1, y+1, z, x+1, y+1, z+1, x, y+1, z+1, //top
			x, y+1, z+1, x+1, y+1, z+1, x+1, y, z+1, x, y, z+1, //back
			x, y, z+1, x+1, y, z+1, x+1, y, z, x, y, z, //bottom
			x+1, y, z, x+1, y+1, z, x+1, y+1, z+1, x+1, y, z+1, //left
			x, y, z+1, x, y+1, z+1, x, y+1, z, x, y, z // right
		}; 
	}
	
	public float[] getFront(){
		return new float[]{x, y, z, x+1, y, z, x+1, y+1, z, x, y+1, z};
	}
	
	public float[] getBack(){
		return new float[]{x, y+1, z+1, x+1, y+1, z+1, x+1, y, z+1, x, y, z+1};
	}
	
	public float[] getTop(){
		return new float[]{x, y+1, z, x+1, y+1, z, x+1, y+1, z+1, x, y+1, z+1};
	}
	
	public float[] getBottom(){
		return new float[]{x, y, z+1, x+1, y, z+1, x+1, y, z, x, y, z};
	}
	
	public float[] getLeft(){
		return new float[]{x+1, y, z, x+1, y+1, z, x+1, y+1, z+1, x+1, y, z+1};
	}
	
	public float[] getRight(){
		return new float[]{x, y, z+1, x, y+1, z+1, x, y+1, z, x, y, z};
	}
	
	public float[][] getFrontC(){
		return new float[][]{
			{x, y, z, x+1, y, z, x+1, y+1, z, x, y+1, z},
			{1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0}
		};
	}
	
	public float[][] getBackC(){
		return new float[][]{
			{x, y+1, z+1, x+1, y+1, z+1, x+1, y, z+1, x, y, z+1},
			{0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1}
		};
	}
	
	public float[][] getTopC(){
		return new float[][]{
			{x, y+1, z, x+1, y+1, z, x+1, y+1, z+1, x, y+1, z+1},
			{0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0}
		};
	}
	
	public float[][] getBottomC(){
		return new float[][]{
			{x, y, z+1, x+1, y, z+1, x+1, y, z, x, y, z},
			{1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1}
		};
	}
	
	public float[][] getLeftC(){
		return new float[][]{
			{x+1, y, z, x+1, y+1, z, x+1, y+1, z+1, x+1, y, z+1},
			{0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f, 0f, 0f, 1f}
		};
	}
	
	public float[][] getRightC(){
		return new float[][]{
			{x, y, z+1, x, y+1, z+1, x, y+1, z, x, y, z},
			{1f, 1f, 0f, 1f, 1f, 0f, 1f, 1f, 0f, 1f, 1f, 0f}
		};
	}
}

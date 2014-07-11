package pocketviewer.Renderer;

/**
 * Needs to include handling for specific blocks later
 * @author Jocopa3
 */
public class BlockRenderer {
	
	public static Vertex[] getFront(float x, float y, float z){
		Vertex v1,v2,v3,v4;
		v1 = new Vertex(x,y,z,1,0,0,0,0);
		v2 = new Vertex(x+1,y,z,1,0,0,0,0);
		v3 = new Vertex(x+1,y+1,z,1,0,0,0,0);
		v4 = new Vertex(x,y+1,z,1,0,0,0,0);
		return new Vertex[]{v1,v2,v3,v4};
	}
	
	public static Vertex[] getBack(float x, float y, float z){
		Vertex v1,v2,v3,v4;
		v1 = new Vertex(x,y+1,z+1,0,1,1,0,0);
		v2 = new Vertex(x+1,y+1,z+1,0,1,1,0,0);
		v3 = new Vertex(x+1,y,z+1,0,1,1,0,0);
		v4 = new Vertex(x,y,z+1,0,1,1,0,0);
		return new Vertex[]{v1,v2,v3,v4};
	}
	
	public static Vertex[] getLeft(float x, float y, float z){
		Vertex v1,v2,v3,v4;
		v1 = new Vertex(x+1,y,z,0,0,1,0,0);
		v2 = new Vertex(x+1,y+1,z,0,0,1,0,0);
		v3 = new Vertex(x+1,y+1,z+1,0,0,1,0,0);
		v4 = new Vertex(x+1,y,z+1,0,0,1,0,0);
		return new Vertex[]{v1,v2,v3,v4};
	}
	
	public static Vertex[] getRight(float x, float y, float z){
		Vertex v1,v2,v3,v4;
		v1 = new Vertex(x,y,z+1,1,1,0,0,0);
		v2 = new Vertex(x,y+1,z+1,1,1,0,0,0);
		v3 = new Vertex(x,y+1,z,1,1,0,0,0);
		v4 = new Vertex(x,y,z,1,1,0,0,0);
		return new Vertex[]{v1,v2,v3,v4};
	}
	
	public static Vertex[] getTop(float x, float y, float z){
		Vertex v1,v2,v3,v4;
		v1 = new Vertex(x,y+1,z+1,0,1,0,0,0);
		v2 = new Vertex(x+1,y+1,z+1,0,1,0,0,0);
		v3 = new Vertex(x+1,y+1,z,0,1,0,0,0);
		v4 = new Vertex(x,y+1,z,0,1,0,0,0);
		return new Vertex[]{v1,v2,v3,v4};
	}
	
	public static Vertex[] getBottom(float x, float y, float z){
		Vertex v1,v2,v3,v4;
		v1 = new Vertex(x,y,z+1,1,0,1,0,0);
		v2 = new Vertex(x+1,y,z+1,1,0,1,0,0);
		v3 = new Vertex(x+1,y,z,1,0,1,0,0);
		v4 = new Vertex(x,y,z,1,0,1,0,0);
		return new Vertex[]{v1,v2,v3,v4};
	}
	
	//Blank initializer
	public BlockRenderer(){}
	
	/*
	 * Rest of this should be re-written, or adapt the above methods
	public BlockRenderer(float x, float y, float z) {
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
	 */
}

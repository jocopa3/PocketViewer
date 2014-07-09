package pocketviewer.Renderer;

/**
 * Generic vertex class that makes it easy to organize and transfer data for one vertex
 * @author Jocopa3
 */
public class Vertex {
	public float x;
	public float y;
	public float z;
	
	public float r;
	public float g;
	public float b;
	
	public float u;
	public float v;
	
	public Vertex(){}
	
	public Vertex(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vertex(float x, float y, float z, float r, float g, float b){
		this.x = x;
		this.y = y;
		this.z = z;
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public Vertex(float x, float y, float z, float r, float g, float b, float u, float v){
		this.x = x;
		this.y = y;
		this.z = z;
		this.r = r;
		this.g = g;
		this.b = b;
		this.u = u;
		this.v = v;
	}
	
	public void setXYZ(float x, float y, float z){
		this.x  = x;
		this.y = y;
		this.z = z;
	}
	
	public void setRGB(float r, float g, float b){
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public void setUV(float u, float v){
		this.u = u;
		this.v = v;
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public void setY(float y){
		this.y = y;
	}
	
	public void setZ(float z){
		this.z = z;
	}
	
	public void setR(float r){
		this.r = r;
	}
	
	public void setG(float g){
		this.g = g;
	}
	
	public void setB(float b){
		this.b = b;
	}
	
	public void setU(float u){
		this.u = u;
	}
	
	public void setV(float v){
		this.v = v;
	}
	
	public float[] getXYZ(){
		return new float[]{x,y,z};
	}
	
	public float[] getRGB(){
		return new float[]{r,g,b};
	}
	
	public float[] getUV(){
		return new float[]{u,v};
	}
	
	public float[] getVertexData(){
		return new float[]{x,y,z,r,g,b,u,v};
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public float getZ(){
		return z;
	}
	
	public float getR(){
		return r;
	}
	
	public float getG(){
		return g;
	}
	
	public float getB(){
		return b;
	}
	
	public float getU(){
		return u;
	}
	
	public float getV(){
		return v;
	}
}

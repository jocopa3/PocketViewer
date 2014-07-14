package pocketviewer.Objects;

import static pocketviewer.Objects.BlockFace.*;

/**
 *
 * @author Jocopa3
 */
public class Face {
	public final float minU;
	public final float minV;
	public final float maxU;
	public final float maxV;
    
    public float r;
    public float g;
    public float b;
	
    /** 
     * Creates a new block face object to store face-specific texture
     * coordinates.
     * <p>
     * Texture coordinates are based on the location on the texture
     * atlas. All coordinates should be a float number in the range of 
     * 0 and 1.
     *
     * @param u1  the x coordinate of the top-left corner of the texture
     * @param v1  the y coordinate of the top-left corner of the texture
     * @param u2 the x coordinate of the bottom-right corner of the texture
     * @param v2 the y coordinate of the bottom-right corner of the texture
     */
	public Face(float u1, float v1, float u2, float v2){
		this.minU = u1;
		this.minV = v1;
		this.maxU = u2;
		this.maxV = v2;
        
        //Default face color is white
        this.r = 1;
        this.g = 1;
        this.b = 1;
	}
    
    /** 
     * Creates a new block face object to store face-specific texture
     * coordinates.
     * <p>
     * Texture coordinates are based on the location on the texture
     * atlas. All coordinates should be a float number in the range of 
     * 0 and 1. Colors should also be a float number in the range of 0
     * and 1.
     *
     * @param u1  the x coordinate of the top-left corner of the texture
     * @param v1  the y coordinate of the top-left corner of the texture
     * @param u2 the x coordinate of the bottom-right corner of the texture
     * @param v2 the y coordinate of the bottom-right corner of the texture
     * @param gray the brightness of the texture (1 = normal, 0 = dark)
     */
	public Face(float u1, float v1, float u2, float v2, float gray){
		this.minU = u1;
		this.minV = v1;
		this.maxU = u2;
		this.maxV = v2;
        
        //Set face color as grayish tone (used in lighting)
        this.r = gray;
        this.g = gray;
        this.b = gray;
	}
	
    /** 
     * @return the x coordinate of the top-left corner of the texture
     */
	public float getMinU(){
		return minU;
	}
	
    /** 
     * @return the y coordinate of the top-left corner of the texture
     */
	public float getMinV(){
		return minV;
	}
	
    /** 
     * @return the x coordinate of the bottom-right corner of the texture
     */
	public float getMaxU(){
		return maxU;
	}
	
    /** 
     * @return the y coordinate of the bottom-right corner of the texture
     */
	public float getMaxV(){
		return maxV;
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
    
    public void setGray(float gray){
        r = gray;
        g = gray;
        b = gray;
    }
}

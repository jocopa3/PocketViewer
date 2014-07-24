package pocketviewer.Objects;

import static pocketviewer.Objects.BlockFace.*;

/**
 *
 * @author Jocopa3
 */
public class Face {
	public float minU;
	public float minV;
	public float maxU;
	public float maxV;
    
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
    
    /** 
     * @return the red component of the face color
     */
    public float getR(){
        return r;
    }
    
    /** 
     * @return the green component of the face color
     */
    public float getG(){
        return g;
    }
    
    /** 
     * @return the blue component of the face color
     */
    public float getB(){
        return b;
    }
    
    /** 
     * Sets the color of the face given the red, green and blue
     * components of the new color.
     * 
     * @param r the red component of the new color
     * @param g the green component of the new color
     * @param b the blue component of the new color
     */
    public void setRGB(float r, float g, float b){
        this.r = r;
        this.g = g;
        this.b = b;
    }
    
    /**
     * Sets the face color to a gray tint given a float used as
     * the gray-scale value.
     * 
     * @param gray the gray-scale value; a float value between 0 and 1
     */
    public void setGray(float gray){
        r = gray;
        g = gray;
        b = gray;
    }
    
    /**
     * Sets the face color and tint to an appropriate value given
     * the brightness of the face and a light value.
     * 
     * @param brightness how bright the face is; 0 = dark, 1 = bright
     * @param lightVal the intensity of the light source; 0 = dark, 15 = bright
     */
    public void setLight(float brightness, int lightVal){
        setGray(brightness * (lightVal*0.05f + 0.25f));
        
        tint(1f, 1f, 1f);
    }
    
    /** 
     * Tints the face color given a red, green and blue component.
     * 
     * @param r the red component of the tint
     * @param g the green component of the tint
     * @param b the blue component of the tint
     */
    public void tint(float r, float g, float b){
        this.r *= r;
        this.g *= g;
        this.b *= b;
    }
    
    //Experimental; no extrodinary commenting for now
    public void rotate(int direction){
        float tnu = minU, txu = maxU, tnv = minV, txv = maxV;
        
        if(direction == CLOCKWISE){
            minU = txu;
            maxU = tnu;
        }else if(direction == COUNTER_CLOCKWISE){
            minV = txv;
            maxV = tnv;
        }else if(direction == HALF_ROTATION){
            minU = txu;
            maxU = tnu;
            minV = txv;
            maxV = tnv;
        }
    }
}

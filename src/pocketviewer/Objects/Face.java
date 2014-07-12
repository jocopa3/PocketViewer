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
	
    /** 
     * Creates a new block face object to store face-specific texture
     * coordinates.
     * <p>
     * Texture coordinates are based on the location on the texture
     * atlas. All coordinates should be a float number in the range of 
     * 0 and 1.
     *
     * @param u  the x coordinate of the top-left corner of the texture
     * @param v  the y coordinate of the top-left corner of the texture
     * @param width the width of the texture along the v axis
     * @param height the height of the texture along the u axis
     */
	public Face(float u, float v, float width, float height){
		this.minU = u;
		this.minV = v;
		this.maxU = u + width;
		this.maxV = v + height;
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
}

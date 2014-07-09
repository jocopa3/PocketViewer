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
	
	public Face(float u, float v, float size){
		this.minU = u;
		this.minV = v;
		this.maxU = u+size;
		this.maxV = v+size;
	}
	
	public float getMinU(){
		return minU;
	}
	
	public float getMinV(){
		return minV;
	}
	
	public float getMaxU(){
		return maxU;
	}
	
	public float getMaxV(){
		return maxV;
	}
}

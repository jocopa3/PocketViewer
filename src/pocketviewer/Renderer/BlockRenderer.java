package pocketviewer.Renderer;

import static pocketviewer.Objects.BlockFace.*;
import pocketviewer.Objects.Face;
import pocketviewer.Blocks.*;

/**
 * Needs to include handling for specific blocks later
 * @author Jocopa3
 */
public class BlockRenderer {
    
	//Blank initializer for now
	public BlockRenderer(){}
    
    public static Vertex[][] get(int id, float x, float y, float z){
        /*
        if(Block.blocks[id].isCube){
            return getCuboid(id, x, y, z);
        }else{
            return getCuboid(1, x, y, z); //Default is air for now
        }
        */
        return getCuboid(1,x,y,z);
    }
    
    public static Vertex[][] getCuboid(int id, float x, float y, float z){
        Face[] faceTextures = new StoneBlock(1).faces;
        Vertex[][] faces = new Vertex[TOTAL_FACES][4];
        
        faces[TOP] = getTop(faceTextures[TOP],x,y,z);
        faces[BOTTOM] = getTop(faceTextures[BOTTOM],x,y,z);
        faces[LEFT] = getTop(faceTextures[LEFT],x,y,z);
        faces[RIGHT] = getTop(faceTextures[RIGHT],x,y,z);
        faces[FRONT] = getTop(faceTextures[FRONT],x,y,z);
        faces[BACK] = getTop(faceTextures[BACK],x,y,z);
        
        return faces;
    }
    
	public static Vertex[] getFront(Face face, float x, float y, float z){
		Vertex v1,v2,v3,v4;
		v1 = new Vertex(x,y+1,z,1,1,1,face.minU,face.minV); //x,y,z,r,g,b,u,v
		v2 = new Vertex(x,y,z,1,1,1,face.minU,face.maxV);
		v3 = new Vertex(x+1,y,z,1,1,1,face.maxU,face.maxV);
		v4 = new Vertex(x+1,y+1,z,1,1,1,face.maxU,face.minV);
		return new Vertex[]{v1,v2,v3,v4};
	}
	
	public static Vertex[] getBack(Face face, float x, float y, float z){
		Vertex v1,v2,v3,v4;
		v1 = new Vertex(x+1,y+1,z+1,1,1,1,face.minU,face.minV);
		v2 = new Vertex(x+1,y,z+1,1,1,1,face.minU,face.maxV);
		v3 = new Vertex(x,y,z+1,1,1,1,face.maxU,face.maxV);
		v4 = new Vertex(x,y+1,z+1,1,1,1,face.maxU,face.minV);
		return new Vertex[]{v1,v2,v3,v4};
	}
	
	public static Vertex[] getLeft(Face face, float x, float y, float z){
		Vertex v1,v2,v3,v4;
		v1 = new Vertex(x,y+1,z+1,1,1,1,face.minU,face.minV);
		v2 = new Vertex(x,y,z+1,1,1,1,face.minU,face.maxV);
		v3 = new Vertex(x,y,z,1,1,1,face.maxU,face.maxV);
		v4 = new Vertex(x,y+1,z,1,1,1,face.maxU,face.minV);
		return new Vertex[]{v1,v2,v3,v4};
	}
	
	public static Vertex[] getRight(Face face, float x, float y, float z){
		Vertex v1,v2,v3,v4;
		v1 = new Vertex(x+1,y+1,z,1,1,1,face.minU,face.minV);
		v2 = new Vertex(x+1,y,z,1,1,1,face.minU,face.maxV);
		v3 = new Vertex(x+1,y,z+1,1,1,1,face.maxU,face.maxV);
		v4 = new Vertex(x+1,y+1,z+1,1,1,1,face.maxU,face.minV);
		return new Vertex[]{v1,v2,v3,v4};
	}
	
	public static Vertex[] getTop(Face face, float x, float y, float z){
		Vertex v1,v2,v3,v4;
		v1 = new Vertex(x,y+1,z+1,1,1,1,face.minU,face.minV);
		v2 = new Vertex(x,y+1,z,1,1,1,face.minU,face.maxV);
		v3 = new Vertex(x+1,y+1,z,1,1,1,face.maxU,face.maxV);
		v4 = new Vertex(x+1,y+1,z+1,1,1,1,face.maxU,face.minV);
		return new Vertex[]{v1,v2,v3,v4};
	}
	
	public static Vertex[] getBottom(Face face, float x, float y, float z){
		Vertex v1,v2,v3,v4;
		v1 = new Vertex(x,y,z,1,1,1,face.minU,face.minV);
		v2 = new Vertex(x,y,z+1,1,1,1,face.minU,face.maxV);
		v3 = new Vertex(x+1,y,z+1,1,1,1,face.maxU,face.maxV);
		v4 = new Vertex(x+1,y,z,1,1,1,face.maxU,face.minV);
		return new Vertex[]{v1,v2,v3,v4};
	}
}
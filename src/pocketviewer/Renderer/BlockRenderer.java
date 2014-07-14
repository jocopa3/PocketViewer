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
        if(Block.blocks[id] != null && Block.blocks[id].isCube){
            return getCuboid(id, x, y, z);
        }else{
            return getCuboid(0, x, y, z); //Default is air for now
        }
    }
    
    public static Vertex[][] getCuboid(int id, float x, float y, float z){
        Face[] faceTextures = Block.blocks[id].faces;
        Vertex[][] faces = new Vertex[TOTAL_FACES][4];
        
        faces[TOP] = getTop(faceTextures[TOP],x,y,z);
        faces[BOTTOM] = getBottom(faceTextures[BOTTOM],x,y,z);
        faces[LEFT] = getLeft(faceTextures[LEFT],x,y,z);
        faces[RIGHT] = getRight(faceTextures[RIGHT],x,y,z);
        faces[FRONT] = getFront(faceTextures[FRONT],x,y,z);
        faces[BACK] = getBack(faceTextures[BACK],x,y,z);
        
        return faces;
    }
    
    public static Vertex[] getFront(Face face, float x, float y, float z){
		return getFront(face, x, y, z, 15);
	}
    
	public static Vertex[] getFront(Face face, float x, float y, float z, float light){
        face.setGray(1-(0.75f-(0.05f*light)));
        
		Vertex v1,v2,v3,v4;
		v1 = new Vertex(x,y+1,z,face.r,face.g,face.b,face.minU,face.minV); //x,y,z,r,g,b,u,v
		v2 = new Vertex(x,y,z,face.r,face.g,face.b,face.minU,face.maxV);
		v3 = new Vertex(x+1,y,z,face.r,face.g,face.b,face.maxU,face.maxV);
		v4 = new Vertex(x+1,y+1,z,face.r,face.g,face.b,face.maxU,face.minV);
		return new Vertex[]{v1,v2,v3,v4};
	}
	
    public static Vertex[] getBack(Face face, float x, float y, float z){
		return getBack(face, x, y, z, 15);
	}
    
	public static Vertex[] getBack(Face face, float x, float y, float z, float light){
        face.setGray(1-(0.75f-(0.05f*light)));
        
		Vertex v1,v2,v3,v4;
		v1 = new Vertex(x+1,y+1,z+1,face.r,face.g,face.b,face.minU,face.minV);
		v2 = new Vertex(x+1,y,z+1,face.r,face.g,face.b,face.minU,face.maxV);
		v3 = new Vertex(x,y,z+1,face.r,face.g,face.b,face.maxU,face.maxV);
		v4 = new Vertex(x,y+1,z+1,face.r,face.g,face.b,face.maxU,face.minV);
		return new Vertex[]{v1,v2,v3,v4};
	}
	
    public static Vertex[] getLeft(Face face, float x, float y, float z){
		return getLeft(face, x, y, z, 15);
	}
    
	public static Vertex[] getLeft(Face face, float x, float y, float z, float light){
        face.setGray(1-(0.75f-(0.05f*light)));
        
		Vertex v1,v2,v3,v4;
		v1 = new Vertex(x,y+1,z+1,face.r,face.g,face.b,face.minU,face.minV);
		v2 = new Vertex(x,y,z+1,face.r,face.g,face.b,face.minU,face.maxV);
		v3 = new Vertex(x,y,z,face.r,face.g,face.b,face.maxU,face.maxV);
		v4 = new Vertex(x,y+1,z,face.r,face.g,face.b,face.maxU,face.minV);
		return new Vertex[]{v1,v2,v3,v4};
	}
	
    public static Vertex[] getRight(Face face, float x, float y, float z){
		return getRight(face, x, y, z, 15);
	}
    
	public static Vertex[] getRight(Face face, float x, float y, float z, float light){
        face.setGray(1-(0.75f-(0.05f*light)));
        
		Vertex v1,v2,v3,v4;
		v1 = new Vertex(x+1,y+1,z,face.r,face.g,face.b,face.minU,face.minV);
		v2 = new Vertex(x+1,y,z,face.r,face.g,face.b,face.minU,face.maxV);
		v3 = new Vertex(x+1,y,z+1,face.r,face.g,face.b,face.maxU,face.maxV);
		v4 = new Vertex(x+1,y+1,z+1,face.r,face.g,face.b,face.maxU,face.minV);
		return new Vertex[]{v1,v2,v3,v4};
	}
	
    public static Vertex[] getTop(Face face, float x, float y, float z){
		return getTop(face, x, y, z, 15);
	}
    
	public static Vertex[] getTop(Face face, float x, float y, float z, float light){
        face.setGray(1-(0.75f-(0.05f*light)));
        
		Vertex v1,v2,v3,v4;
		v1 = new Vertex(x,y+1,z+1,face.r,face.g,face.b,face.minU,face.minV);
		v2 = new Vertex(x,y+1,z,face.r,face.g,face.b,face.minU,face.maxV);
		v3 = new Vertex(x+1,y+1,z,face.r,face.g,face.b,face.maxU,face.maxV);
		v4 = new Vertex(x+1,y+1,z+1,face.r,face.g,face.b,face.maxU,face.minV);
		return new Vertex[]{v1,v2,v3,v4};
	}
	
    public static Vertex[] getBottom(Face face, float x, float y, float z){
		return getBottom(face, x, y, z, 15);
	}
    
	public static Vertex[] getBottom(Face face, float x, float y, float z, float light){
        face.setGray(1-(0.75f-(0.05f*light)));
        
		Vertex v1,v2,v3,v4;
		v1 = new Vertex(x,y,z,face.r,face.g,face.b,face.minU,face.minV);
		v2 = new Vertex(x,y,z+1,face.r,face.g,face.b,face.minU,face.maxV);
		v3 = new Vertex(x+1,y,z+1,face.r,face.g,face.b,face.maxU,face.maxV);
		v4 = new Vertex(x+1,y,z,face.r,face.g,face.b,face.maxU,face.minV);
		return new Vertex[]{v1,v2,v3,v4};
	}
    
    
    
    public static Vertex[] getFront(int id, float x, float y, float z, float light){
        Block block = Block.blocks[id];
        if(block == null)
            return getFront(Block.blocks[0].getFace(FRONT), x, y, z, light);
		else
            return getFront(block.getFace(FRONT), x, y, z, light);
	}
	
	public static Vertex[] getBack(int id, float x, float y, float z, float light){
        Block block = Block.blocks[id];
        if(block == null)
            return getBack(Block.blocks[0].getFace(BACK), x, y, z, light);
		else
            return getBack(block.getFace(BACK), x, y, z, light);
	}
	
	public static Vertex[] getLeft(int id, float x, float y, float z, float light){
        Block block = Block.blocks[id];
        if(block == null)
            return getLeft(Block.blocks[0].getFace(LEFT), x, y, z, light);
		else
            return getLeft(block.getFace(LEFT), x, y, z, light);
	}
	
	public static Vertex[] getRight(int id, float x, float y, float z, float light){
        Block block = Block.blocks[id];
        if(block == null)
            return getRight(Block.blocks[0].getFace(RIGHT), x, y, z, light);
		else
            return getRight(block.getFace(RIGHT), x, y, z, light);
	}
	
	public static Vertex[] getTop(int id, float x, float y, float z, float light){
        Block block = Block.blocks[id];
        if(block == null)
            return getTop(Block.blocks[0].getFace(TOP), x, y, z, light);
		else
            return getTop(block.getFace(TOP), x, y, z, light);
	}
	
	public static Vertex[] getBottom(int id, float x, float y, float z, float light){
        Block block = Block.blocks[id];
        if(block == null)
            return getBottom(Block.blocks[0].getFace(BOTTOM), x, y, z, light);
		else
            return getBottom(block.getFace(BOTTOM), x, y, z, light);
	}
}
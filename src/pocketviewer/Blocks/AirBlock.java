package pocketviewer.Blocks;

import static pocketviewer.Objects.BlockFace.*;
import pocketviewer.Objects.Face;

/**
 *
 * @author Jocopa3
 */
public class AirBlock extends Block{
	public AirBlock(int id){
		super(id);
        this.isOpaque = false;
        this.isCube = true;
        this.hasShadow = false;
	}
	
	public AirBlock(int id, int data){
		super(id, data);
        this.isOpaque = false;
        this.isCube = true;
        this.hasShadow = false;
	}
    
    @Override
    public void initTextures(){
        this.faces = new Face[TOTAL_FACES];
        this.faces[TOP] = new Face(0,0,0,0);
        this.faces[BOTTOM] = new Face(0,0,0,0);
        this.faces[LEFT] = new Face(0,0,0,0);
        this.faces[RIGHT] = new Face(0,0,0,0);
        this.faces[FRONT] = new Face(0,0,0,0);
        this.faces[BACK] = new Face(0,0,0,0);
    }
}

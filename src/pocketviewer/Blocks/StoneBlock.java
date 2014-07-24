package pocketviewer.Blocks;

import static pocketviewer.Objects.BlockFace.*;
import pocketviewer.Objects.Face;

/**
 *
 * @author Jocopa3
 */
public class StoneBlock extends Block{
	public StoneBlock(int id){
		super(id);
        this.isOpaque = true;
        this.isCube = true;
        this.hasShadow = true;
	}
	
	public StoneBlock(int id, int data){
		super(id, data);
        this.isOpaque = true;
        this.isCube = true;
        this.hasShadow = true;
	}
    
    @Override
    public void initTextures(){
        this.faces = new Face[TOTAL_FACES];
        this.faces[TOP] = new Face(0.5938f, 0.0f, 0.625f, 0.0625f);
        this.faces[BOTTOM] = new Face(0.5938f, 0.0f, 0.625f, 0.0625f);
        this.faces[LEFT] = new Face(0.5938f, 0.0f, 0.625f, 0.0625f);
        this.faces[RIGHT] = new Face(0.5938f, 0.0f, 0.625f, 0.0625f);
        this.faces[FRONT] = new Face(0.5938f, 0.0f, 0.625f, 0.0625f);
        this.faces[BACK] = new Face(0.5938f, 0.0f, 0.625f, 0.0625f);
    }
}

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
        initTextures();
	}
	
	public StoneBlock(int id, int data){
		super(id, data);
        this.isOpaque = true;
        this.isCube = true;
        initTextures();
	}
    
    @Override
    public void initTextures(){
        this.faces = new Face[TOTAL_FACES];
        
        this.faces[TOP] = new Face(0.625f, 0.0f, 0.03125f, 0.0625f);
        this.faces[BOTTOM] = new Face(0.625f, 0.0f, 0.03125f, 0.0625f);
        this.faces[LEFT] = new Face(0.625f, 0.0f, 0.03125f, 0.0625f);
        this.faces[RIGHT] = new Face(0.625f, 0.0f, 0.03125f, 0.0625f);
        this.faces[FRONT] = new Face(0.625f, 0.0f, 0.03125f, 0.0625f);
        this.faces[BACK] = new Face(0.625f, 0.0f, 0.03125f, 0.0625f);
    }
}

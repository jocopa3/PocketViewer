package pocketviewer.Blocks;

import static pocketviewer.Objects.BlockFace.*;
import pocketviewer.Objects.Face;

/**
 *
 * @author Jocopa3
 */
public class DirtBlock extends Block{
	public DirtBlock(int id){
		super(id);
        this.isOpaque = true;
        this.isCube = true;
        initTextures();
	}
	
	public DirtBlock(int id, int data){
		super(id, data);
        this.isOpaque = true;
        this.isCube = true;
        initTextures();
	}
    
    //In the future, use grass colors instead of UV coordinates
    @Override
    public void initTextures(){
        this.faces = new Face[TOTAL_FACES];
        this.faces[TOP] = new Face(0.34375f, 0.0625f, 0.03125f, 0.0625f);
        this.faces[BOTTOM] = new Face(0.34375f, 0.0625f, 0.03125f, 0.0625f);
        this.faces[LEFT] = new Face(0.34375f, 0.0625f, 0.03125f, 0.0625f);
        this.faces[RIGHT] = new Face(0.34375f, 0.0625f, 0.03125f, 0.0625f);
        this.faces[FRONT] = new Face(0.34375f, 0.0625f, 0.03125f, 0.0625f);
        this.faces[BACK] = new Face(0.34375f, 0.0625f, 0.03125f, 0.0625f);
    }
}

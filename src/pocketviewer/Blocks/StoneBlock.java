package pocketviewer.Blocks;

/**
 *
 * @author Jocopa3
 */
public class StoneBlock extends Block{
	public StoneBlock(int x, int y, int z, int id){
		super(x, y, z, id);
	}
	
	public StoneBlock(int x, int y, int z, int id, int data){
		super(x, y, z, id, data);
	}
	
	public StoneBlock(int id){
		super(id);
	}
	
	public StoneBlock(int id, int data){
		super(id, data);
	}
	
	public void initTextures(){
		
	}
}

package pocketviewer.Blocks;

/**
 *
 * @author Jocopa3
 */
public class AirBlock extends Block{
	public AirBlock(int x, int y, int z, int id){
		super(x, y, z, id);
	}
	
	public AirBlock(int x, int y, int z, int id, int data){
		super(x, y, z, id, data);
	}
	
	public AirBlock(int id){
		super(id);
	}
	
	public AirBlock(int id, int data){
		super(id, data);
	}
}

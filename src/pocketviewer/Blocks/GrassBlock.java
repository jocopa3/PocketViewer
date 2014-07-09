package pocketviewer.Blocks;

/**
 *
 * @author Jocopa3
 */
public class GrassBlock extends Block{
	public GrassBlock(int x, int y, int z, int id){
		super(x, y, z, id);
	}
	
	public GrassBlock(int x, int y, int z, int id, int data){
		super(x, y, z, id, data);
	}
	
	public GrassBlock(int id){
		super(id);
	}
	
	public GrassBlock(int id, int data){
		super(id, data);
	}
}

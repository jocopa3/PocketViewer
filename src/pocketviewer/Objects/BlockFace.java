package pocketviewer.Objects;

/**
 *
 * @author Jocopa3
 */
public class BlockFace {
	public static final int FRONT = 0;
	public static final int BACK = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int TOP = 4;
	public static final int BOTTOM = 5;
    
    public static final int TOTAL_FACES = 6;
    
    public static final int NONE = 0; //0
    public static final int CLOCKWISE = 1; //-90
    public static final int COUNTER_CLOCKWISE = 2; //90
    public static final int HALF_ROTATION = 3; //180
}
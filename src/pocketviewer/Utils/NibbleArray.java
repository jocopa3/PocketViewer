package pocketviewer.Utils;

/*
 * NibbleArray, which saves two 4 bit numbers per byte (A.K.A. a nibble)
 * 
 * To be used during saving and to reduce total amount of memory used for
 * storing blocks.
 */
public class NibbleArray{
	public final byte[] arr; //Stores all bytes containing the nibbles

	//Switch between Anvil format (YZX) and Alpha format (XZY)
	public final boolean useAnvil; 

	//Create an array of nibbles of a given size
	public NibbleArray(int size){
		arr = new byte[size >> 1]; //New array half the given size
		useAnvil = false; //Default format is Anvil
	}
	
	//Create an array of nibbles of a given size and format type (Anvil, Alpha/old)
	public NibbleArray(int size, boolean format){
		arr = new byte[size >> 1]; //New array half the given size
		useAnvil = format;
	}

	//Use an array of nibbles from a given array
	public NibbleArray(byte[] arrOld){
		arr = arrOld;
		useAnvil = false; //Default format is Anvil
	}
	
	//Use an array of nibbles from a given array and format type (Anvil, Alpha/old)
	public NibbleArray(byte[] arrOld, boolean format){
		arr = arrOld;
		useAnvil = format;
	}

	//Get the value of the nibble at the x,y,z coordinate
	public int get(int x, int y, int z){
		int pos = useAnvil ? ((y << 8) | (z << 4) | x) : ((x << 8) | (z << 4) | y); //Extract the pos in the array from the x,y,z coordinates
		int halfPos = pos >> 1; //Get the pos of the byte that stores the desired nibble from the given pos
		int nibblePos = pos & 1; //Get the pos in the byte (high or low) of the desired nibble

		return nibblePos == 0 ? arr[halfPos] & 15 : (arr[halfPos] >> 4) & 15; //Returns the appropriate high or low nibble
	}

	//Set the value of the nibble at the x,y,z, coordinate to the given value
	public void set(int x, int y, int z, int val){
		int pos = useAnvil ? ((y << 8) | (z << 4) | x) : ((x << 8) | (z << 4) | y); //Extract the pos in the array from the x,y,z coordinates
		int halfPos = pos >> 1; //Get the pos of the byte that stores the desired nibble from the given pos
		int nibblePos = pos & 1; //Get the pos in the byte (high or low) of the desired nibble

		if (nibblePos == 0)
			arr[halfPos] = (byte)((arr[halfPos] & 240) | (val & 15)); //Set the low nibble
		else
			arr[halfPos] = (byte)((arr[halfPos] & 15) | ((val & 15) << 4)); //Set the high nibble
	}
}
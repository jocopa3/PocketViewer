package pocketviewer.Renderer;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL15.*;

/**
 * v0.2: Added the ability to choose which stuff to render (vertices, colors, textures)
 * @author Jocopa3
 */
public class VBOHelper {
	public int vertexCount = 0; //Total amount of verticies in the buffer
	public int[] bufferHandler; //Array of ID's for the buffer; two or more are used if overflow happens
	public int[] bufferLength; //Array of buffer lengths
	
	public int current = 0; //Current buffer index
	public int previous = 0; //Previous buffer index
	public boolean running = false; //Whether the VBO is accepting new elements or not
	
	public boolean drawing = true; //Whether or not the VBO will add vertices
	public boolean coloring = true; //Whether or not the VBO will add color values
	public boolean texturing = true; //Whether or not the VBO will add texture coords
	public int vertexPos = 0; //Start pos for verticies
	public int colorPos = 3; //Start pos for colors
	public int texturePos = 6; //Start pos for textures
	
	public int bufferSize; //Custom or default size of the buffer
	public FloatBuffer buffer; //Buffer being used for rendering
	
	public static final int DEFAULT_BUFFER_SIZE = 1572864; //16*16*128*6*8
	public static final int DEFAULT_BUFFER_AMOUNT = 16; //16
	public static int VERTEX_SIZE = 8; //Amount of components per vertex (X,Y,Z,R,G,B,U,V)
	
	public boolean shouldDraw = false;
	
	//Create a new buffer using the default size
	public VBOHelper(){
		bufferHandler = new int[DEFAULT_BUFFER_AMOUNT];
		bufferLength = new int[DEFAULT_BUFFER_AMOUNT];
		bufferSize = DEFAULT_BUFFER_SIZE;
	}
	
	//Create a new buffer using a given size
	public VBOHelper(int size){
		bufferHandler = new int[DEFAULT_BUFFER_AMOUNT];
		bufferLength = new int[DEFAULT_BUFFER_AMOUNT];
		bufferSize = size;
	}
	
	//Create a new buffer using a given size and given amount of buffers; size can be -1 to use the default size
	public VBOHelper(int size, int buffers){	
		bufferHandler = new int[buffers];
		bufferLength = new int[buffers];
		
		if(size < 0)
			bufferSize = DEFAULT_BUFFER_SIZE;
		else
			bufferSize = size;
	}
	
	//Set whether or not the VBO should add/use vertex coords
	public void shouldDraw(boolean draw){
		if(drawing == draw || running)
			return;
		
		if(drawing && !draw){
			VERTEX_SIZE -= 3;
			colorPos -= 3;
			texturePos -= 3;
		}else if(!drawing && draw){
			VERTEX_SIZE += 3;
			colorPos += 3;
			texturePos += 3;
		}
		
		drawing = draw;
	}
	
	//Set whether or not the VBO should add/use color values
	public void shouldColor(boolean color){
		if(coloring == color || running)
			return;
		
		if(coloring && !color){
			VERTEX_SIZE -= 3;
			texturePos -= 3;
		}else if(!coloring && color){
			VERTEX_SIZE += 3;
			texturePos += 3;
		}
		
		coloring = color;
	}
	
	//Set whether or not the VBO should add/use texture coords
	public void shouldTexture(boolean texture){
		if(texturing == texture || running)
			return;
		
		if(texturing && !texture)
			VERTEX_SIZE -= 2;
		else if(!texturing && texture)
			VERTEX_SIZE += 2;
		
		
		texturing = texture;
	}
	
	//Initialize the buffer to prepare for vertices
	public void start(){
		buffer = BufferUtils.createFloatBuffer(bufferSize);
		running = true;
		addVertices(BlockRenderer.getBack(2,2,2));
		
	}
	
	//Handles and binds the buffer; returns the current buffer handler
	public int stop(){
		running = false;
		buffer.flip();
		
		bufferHandler[current] = glGenBuffers();
		bufferLength[current] = vertexCount;

        glBindBuffer(GL_ARRAY_BUFFER, bufferHandler[current]);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		nextBuffer();
		return previous;
	}
	
	//Returns all buffer handlers
	public int[] getBufferHandlers(){
		return bufferHandler;
	}
	
	//Returns all buffer lengths
	public int[] getBufferLengths(){
		return bufferLength;
	}
	
	//Reuse the same buffer to save memory
	public void reuseBuffer(int buffer){
		//Will add code to re-use previous buffers later
	}
	
	//Reuse the same buffer array to save memory
	public void reuseBuffer(int[] buffers){
		//Will add code to re-use previous buffers later
	}
	
	//Check if there are still a valid amount of buffers left, then use
	public void nextBuffer(){
		previous = current;
		if(current < bufferHandler.length - 1)
			current++;
	}
	
	//Clean the buffers up; full clean removes all buffer handlers/lengths; returns if clean-up was successful or not
	public boolean cleanUp(boolean fullClean){
		buffer.clear();
		if(fullClean){
			//Arrays.fill(bufferHandler, 0);
			//Arrays.fill(bufferLength, 0);
			bufferHandler = null;
			bufferLength = null;
		}
		
		//glCheckError stuff here
		return true;
	}
	
	//Binds a texture to the buffer to use; shouldn't be used more than once for the same buffer
	public void useTexture(int texture){
		//Needs implementation
	}
	
	//Returns if the buffer is too full to hold another quad
	public boolean isFull(){
		//return vertexPos + 4*VERTEX_SIZE > vertexCount*VERTEX_SIZE;
		return false;
	}
	
	//Used in emergency; intializes a new buffer to continue accepting vertices
	public void handleFullBuffer(){
		stop();
		//draw();
		cleanUp(false);
		start();
	}
	
	//Add an array of vertices
	public void addVertices(Vertex[] verticies){
		for(Vertex v : verticies)
			addVertex(v);
	}
	
	//Add a single vertex
	public void addVertex(Vertex vertex){
		if(isFull())
			handleFullBuffer();
		
		if(drawing)
			buffer.put(vertexPos, vertex.getX()).put(vertexPos+1, vertex.getY()).put(vertexPos+2, vertex.getZ());
		if(coloring)
			buffer.put(colorPos, vertex.getR()).put(colorPos+1, vertex.getG()).put(colorPos+2, vertex.getB());
		if(texturing)
			buffer.put(texturePos, vertex.getU()).put(texturePos+1, vertex.getV());
		
		vertexPos += VERTEX_SIZE;
		colorPos += VERTEX_SIZE;
		texturePos += VERTEX_SIZE;
		vertexCount++;
	}
	
	//Add a vertex at the specific x,y,z coords with the r,g,b ccolor and u,v texture
	public void addVertex(float x, float y, float z, float r, float g, float b, float u, float v){
		if(isFull())
			handleFullBuffer();
		
		if(drawing)
			buffer.put(vertexPos, x).put(vertexPos+1, y).put(vertexPos+2, z);
		if(coloring)
			buffer.put(colorPos, r).put(colorPos+1, g).put(colorPos+2, b);
		if(texturing)
			buffer.put(texturePos+6, u).put(texturePos+1, v);
		
		vertexPos += VERTEX_SIZE;
		colorPos += VERTEX_SIZE;
		texturePos += VERTEX_SIZE;
		vertexCount++;
	}
	
	//Add a single x,y,z coordinate
	public void addVertex(float x, float y, float z){
		if(!drawing)
			return;
		if(isFull())
			handleFullBuffer();
		
		buffer.put(vertexPos,x).put(vertexPos+1,y).put(vertexPos+2,z);
		
		vertexPos += VERTEX_SIZE;
		vertexCount++;
	}
	
	//Add a single color
	public void addColor(float r, float g, float b){
		if(!coloring)
			return;
		if(!drawing && !texturing && isFull())
			handleFullBuffer();
		
		buffer.put(colorPos, r).put(colorPos+1, g).put(colorPos+2, b);
		
		colorPos += VERTEX_SIZE;
		
		if(!drawing && !texturing)
			vertexCount++;
	}

	//Add a single texture coord
	public void addTexture(float u, float v){
		if(!texturing)
			return;
		if(!drawing && !coloring && isFull())
			handleFullBuffer();
		
		buffer.put(texturePos, u).put(texturePos+1, v);
		
		texturePos += VERTEX_SIZE;
		
		if(!drawing && !coloring)
			vertexCount++;
	}
}
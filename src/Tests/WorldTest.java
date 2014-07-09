/*
 * Used for testing any features until the main code is finished
 */
package Tests;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;


import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL12;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class WorldTest {
	static int fps;
	static long lastFPS;
	
	static Noise noise;

	static Perspective camera;
		
	static float dx = 0.0f;
	static float dy = 0.0f;
	static float dt = 0.0f; //length of frame
	static float lastTime = 0.0f; // when the last frame was
	static float time = 0.0f;
 
	static float mouseSensitivity = 0.1f;
	static float movementSpeed = 0.75f; //move 10 units per second
	
	static int blockColorBuffer;
	static int blockVertexBuffer;
	static int bufferLength;

    public static void main(String[] args) throws java.io.IOException{
		loadNatives();
        
		initDisplay();
		initMatrix();
		
		World world = new World(64l);
		world.genAllChunks();
		world.genAllLights();

		camera = new Perspective(0, 0, 0);
		Mouse.setGrabbed(true);
		start();
		
		System.out.println("Starting render loop!");
		
        while(!Display.isCloseRequested()&&!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glColor3f(1,1,1); //Default color
			
			handleCamera();
			
			//glEnable(GL_CULL_FACE);
			//glCullFace(GL_BACK);
			world.renderAllChunks();
			//glDisable(GL_CULL_FACE);
			
            Display.update();
            //Display.sync(60);
			updateFPS();
        }
		
		world.deleteAllChunkBuffers();

        Display.destroy();
        System.exit(0);
    }
	
	public static void handleCamera(){
		//distance in mouse movement from the last getDX() call.
		dx = Mouse.getDX();
		//distance in mouse movement from the last getDY() call.
		dy = Mouse.getDY();
 
		//controll camera yaw from x movement fromt the mouse
		camera.yaw(dx * mouseSensitivity);
		//controll camera pitch from y movement fromt the mouse
		camera.pitch(-dy * mouseSensitivity);
		
		if (Keyboard.isKeyDown(Keyboard.KEY_W)){
		    camera.moveForward(movementSpeed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)){
		    camera.moveBackward(movementSpeed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)){
			camera.moveLeft(movementSpeed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)){
		    camera.moveRight(movementSpeed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
		    camera.moveUp(movementSpeed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
		    camera.moveDown(movementSpeed);
		}
		
		glLoadIdentity();
        //look through the camera before you draw anything
        camera.lookThrough();
	}
	
	public static void initDisplay(){
		try {
            Display.setDisplayMode(new DisplayMode(640, 640));
            Display.setTitle("World Edit");
            //Display.setVSyncEnabled(true);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(1);
        }
	}
	
	public static void initMatrix(){
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		
		gluPerspective((float) 30, Display.getWidth() / Display.getHeight(), 0.001f, 10000L);
		glMatrixMode(GL_MODELVIEW);
		
		glEnable(GL_DEPTH_TEST);
		
		glClearColor(0.4f, 0.6f, 0.9f, 0f);
	}
	
	public static float[] cube(float x, float y, float z){
		return new float[]{
			x,y,z,   x+1,y,z,   x+1,y+1,z,   x,y+1,z, //front
			x, y+1, z, x+1, y+1, z, x+1, y+1, z+1,   x, y+1, z+1, //top
			x, y+1, z+1,   x+1, y+1, z+1,   x+1, y, z+1,   x, y, z+1, //back
			x, y, z+1,   x+1, y, z+1,   x+1, y, z,   x, y, z, //bottom
			x+1, y, z,   x+1, y+1, z,   x+1, y+1, z+1,   x+1, y, z+1, //left
			x, y, z+1,   x, y+1, z+1,   x, y+1, z,   x, y, z // right
		}; 
	}
	
	public static float[] getCube(float x, float y, float z){
		return new float[]{
			x,y,z, x+1,y,z, x+1,y+1,z, x+1,y+1,z, x,y+1,z, x,y,z, //front
			x+1,y,z+1, x,y,z+1, x,y+1,z+1, x,y+1,z+1, x+1,y+1,z+1, x+1,y,z+1, //back
			x,y,z+1, x,y,z, x,y+1,z, x,y+1,z, x,y+1,z+1, x,y,z+1, //left
			x+1,y,z, x+1,y+1,z, x+1,y+1,z+1, x+1,y+1,z+1, x+1,y,z+1, x+1,y,z, //right
			x,y+1,z, x+1,y+1,z, x+1,y+1,z+1, x+1,y+1,z+1, x,y+1,z+1, x,y+1,z, //top
			x+1,y,z+1, x,y,z+1, x,y,z, x,y,z, x+1,y,z, x+1,y,z+1 //bottom
		};
	}

	private static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
	
	public static void start() {
		lastFPS = getTime(); //set lastFPS to current Time
	}

	public static void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps); 
			fps = 0; //reset the FPS counter
			lastFPS += 1000; //add one second
		}
		fps++;
	}
	
	public static int[] xyz(int pos){
		return new int[]{pos & 0xf, pos >> 8, (pos >> 4) & 0xf};
	}
	
	public static int pos(int x, int y, int z){
		return (y << 8) | (z << 4) | x;
	}
	
	public static void loadNatives(){
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.indexOf("win") >= 0) {
            System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir")+"/native/windows");
        } else if (OS.indexOf("mac") >= 0) {
            System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir")+"/native/macosx");
        } else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0) {
            System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir")+"/native/linux");
        } else if (OS.indexOf("sunos") >= 0) {
            System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir")+"/native/solaris");
        } else {
            javax.swing.JOptionPane.showMessageDialog(null,"Your operating system isn't supported by LWJGL.\n\nSorry!","Not supported",javax.swing.JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
	}

	private static final int BYTES_PER_PIXEL = 4;
	public static int loadTexture(BufferedImage image){
      
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
	
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB
		for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
                buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
                buffer.put((byte) (pixel & 0xFF));               // Blue component
                buffer.put((byte) ((pixel >> 24) & 0xFF));    // Alpha component. Only for RGBA
            }
        }

        buffer.flip(); //FOR THE LOVE OF GOD DO NOT FORGET THIS

        // You now have a ByteBuffer filled with the color data of each pixel.
        // Now just create a texture ID and bind it. Then you can load it using 
        // whatever OpenGL method you want, for example:

      int textureID = glGenTextures(); //Generate texture ID
        glBindTexture(GL_TEXTURE_2D, textureID); //Bind texture ID
        
        //Setup wrap mode
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        //Setup texture scaling filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        
        //Send texel data to OpenGL
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
      
        //Return the texture ID so we can bind it later again
      return textureID;
   }
}
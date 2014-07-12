/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pocketviewer;

import pocketviewer.Objects.World;
import pocketviewer.Renderer.WorldRenderer;
import pocketviewer.Utils.Timer;

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

/**
 *
 * @author Jocopa3
 */
public class PocketViewer {
	
	public static Timer timer = new Timer();
	public static World world;
	public static WorldRenderer worldRenderer;
    
    public static float fov = 80; //Now a variable!
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws java.io.IOException{
		loadNatives();
        
		initDisplay();
		initMatrix();
		
		world = new World(16, 456);
		worldRenderer = new WorldRenderer(world);
		
		System.out.println("Starting render loop!");
		timer.start();
		
		Mouse.setGrabbed(true);
		
        while(!Display.isCloseRequested()&&!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			
			worldRenderer.render();
			
            Display.update();
            //Display.sync(60);
			timer.updateFPS();
			Display.setTitle("FPS: "+timer.getFPS());
        }
        
        worldRenderer.chunkRenderer.deleteAllChunkBuffers();

        Display.destroy();
        System.exit(0);
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
		
		gluPerspective(fov, Display.getWidth() / Display.getHeight(), 0.001f, 10000L);
		glMatrixMode(GL_MODELVIEW);
		
		glEnable(GL_DEPTH_TEST);
		
		glClearColor(0.4f, 0.6f, 0.9f, 0f);
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
}
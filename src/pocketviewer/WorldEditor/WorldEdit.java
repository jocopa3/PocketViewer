/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pocketviewer.WorldEditor;

import javax.swing.JFrame;

import pocketviewer.Objects.World;
import pocketviewer.Renderer.WorldRenderer;
import pocketviewer.Utils.Timer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.AWTGLCanvas;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;


/**
 *
 * @author Jocopa3
 */
public class WorldEdit {
    public JFrame parent;
    
	public Timer timer = new Timer();
	public World world;
	public WorldRenderer worldRenderer;
    
    public float fov = 80; //Now a variable!
	
    public WorldEdit(JFrame parent){
		this.parent = parent;
    }
    
	public WorldEdit(JFrame parent, World world){
		this.parent = parent;
        this.world = world;
    }
    
    public void loop(){
        loadNatives();
        
		initDisplay();
		initMatrix();
		
        if(world == null)
            world = new World(16, 456);
		worldRenderer = new WorldRenderer(world);
		
		//System.out.println("Starting render loop!");
		timer.start();
		
		Mouse.setGrabbed(true);
		
        while(!Display.isCloseRequested()&&!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            //update();
            render();
			
            handleDisplay();
        }
        
        worldRenderer.chunkRenderer.deleteAllChunkBuffers();

        Display.destroy();
        System.exit(0);
    }
    
    public void update(){}
    
    public void render(){
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        worldRenderer.render();
    }
    
    public void handleDisplay(){
        Display.update();
        //Display.sync(60);
        timer.updateFPS();
		Display.setTitle("FPS: "+timer.getFPS());
    }
	
	public void initDisplay(){
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
	
	public void initMatrix(){
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		
		gluPerspective(fov, Display.getWidth() / Display.getHeight(), 0.001f, 10000L);
		glMatrixMode(GL_MODELVIEW);
		
		glEnable(GL_DEPTH_TEST);
		
		glClearColor(0.4f, 0.6f, 0.9f, 0f);
	}
	
	public void loadNatives(){
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
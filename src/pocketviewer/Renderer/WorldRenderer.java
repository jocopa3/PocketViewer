/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pocketviewer.Renderer;

import java.io.IOException;
import static org.lwjgl.opengl.GL11.*;
import pocketviewer.Objects.*;
/**
 *
 * @author Jocopa3
 */
public class WorldRenderer {
	public float renderDistance;
	public World world;
    
	public ChunkRenderer chunkRenderer;
	public BlockRenderer blockRenderer;
	public PlayerRenderer player;
    
    public int textureHandler; //ID of texture atlas
	
	public WorldRenderer(World world){
		this.world = world;
		player = new PlayerRenderer(this);
		//blockRenderer = new BlockRenderer(this);
		chunkRenderer = new ChunkRenderer(this);
        
        try{
            String file = System.getProperty("user.dir")+"/res/images/terrain-atlas.tga"; //Load texture atlas from res files
            textureHandler = new Texture(file).getHandler();
            System.out.println("Texture ID: "+textureHandler);
        }catch(IOException ohShit){ //Not the best variable name in the world
            System.out.println(ohShit.toString());
        }
    }

	public void render() {
		player.updateCamera();
        
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glBindTexture(GL_TEXTURE_2D, textureHandler);
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        
		chunkRenderer.renderAllChunks(); //Render chunks with texture atlas
        
        glBindTexture(GL_TEXTURE_2D, 0);
	}
}

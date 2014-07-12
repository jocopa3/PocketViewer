/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pocketviewer.Renderer;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import pocketviewer.IO.TargaReader;

/**
 *
 * @author Jocopa3
 */
public class Texture {
    public final int textureHandler;
    public BufferedImage texture;
    
    public Texture(String file) throws IOException {
        //Handle loading by extension type eventually
		texture = TargaReader.getImage(file);
        
        textureHandler = bind();
    }
    
    //Use already loaded texture
    public Texture(BufferedImage texture){
        this.texture = texture;
        textureHandler = bind();
    }
    
    //Takes the buffered image and puts it into a buffer; returns texture ID
    public int bind(){
        if(textureHandler != 0)
            return textureHandler;
        
        int[] pixels = new int[texture.getWidth() * texture.getHeight()];
        texture.getRGB(0, 0, texture.getWidth(), texture.getHeight(), pixels, 0, texture.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(texture.getWidth() * texture.getHeight() * 4);
        
        //RGBA format
        for(int y = 0; y < texture.getHeight(); y++){
            for(int x = 0; x < texture.getWidth(); x++){
                int pixel = pixels[y * texture.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        buffer.flip();

        int handler = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, handler);
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, texture.getWidth(), texture.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        
        glBindTexture(GL_TEXTURE_2D, 0);
        
        return handler;
    }
    
    public int getHandler(){
        return textureHandler;
    }
}

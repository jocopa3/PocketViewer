package pocketviewer;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import pocketviewer.IO.TargaReader;

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;

/**
 *
 * @author Jocopa3
 */
public class TextureTest {
	
    public static String location = "/res/images/terrain-atlas.tga";
    public static int scale = 3;
    
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws IOException {
		String file = System.getProperty("user.dir")+location;
		BufferedImage img = TargaReader.getImage(file);
		
        //Scale the image up
		img = scaledImage(img, img.getWidth()*scale, img.getHeight()*scale);
		
		JFrame window = new JFrame();
        window.setTitle(location);
        window.setBackground(java.awt.Color.green);
        window.setSize(img.getWidth()+16,img.getHeight());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

		window.add(new ImagePane(img));
	}
	
    //Used for scaling, not important
	public static BufferedImage scaledImage(BufferedImage image, int width, int height){
        BufferedImage newImage = createCompatibleImage(width,height);
        Graphics graphics = newImage.createGraphics();
      
        graphics.drawImage(image,0,0,width,height,null);
      
        graphics.dispose();
        return newImage;
    }
	
	public static BufferedImage createCompatibleImage(int width, int height) {
        GraphicsConfiguration configuration = GraphicsEnvironment.getLocalGraphicsEnvironment()
        .getDefaultScreenDevice().getDefaultConfiguration();
        return configuration.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
    }
	
}

//This stuff used for rendering the image on the jPanel; not very important otherwise
class ImagePane extends javax.swing.JPanel {

        private BufferedImage background;

		public ImagePane(BufferedImage image) {
            background = image;
        }

        @Override
        public java.awt.Dimension getPreferredSize() {
            return background == null ? super.getPreferredSize() : new java.awt.Dimension(background.getWidth(), background.getHeight());
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (background != null) {
                g.drawImage(background, 0, 0, this);
            }
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pocketviewer;

import javax.swing.JFrame;

import pocketviewer.Objects.World;
import pocketviewer.Renderer.WorldRenderer;
import pocketviewer.Utils.Timer;
import pocketviewer.WorldEditor.WorldEdit;
import static pocketviewer.Utils.MathUtils.*;


/**
 *
 * @author Jocopa3
 */
public class PocketViewer {
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
        WorldEdit editor = new WorldEdit(new JFrame()); //Will use Main GUI JFrame in the future
        editor.loop();
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pocketviewer.Blocks;

import static pocketviewer.Objects.BlockFace.*;
import pocketviewer.Objects.Face;

/**
 *
 * @author Jocopa3
 */
public class DataBlock extends Block {
    
    public Face[][] faces;
    
    public DataBlock(int amount, int id, int data){
        super(id, data);
        faces = new Face[amount][TOTAL_FACES];
    }
}

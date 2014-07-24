/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pocketviewer.Renderer;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;

/**
 * Buffer utility that stores all buffers for an object
 * @author Jocopa3
 */
public class Buffer {
    public int[] buffers; //Buffer handler ID's
    public int[] bufferLengths; //Buffer lengths
    
    public int length;

    //Amount of buffers to create; a new buffer is used if buffer-overflow happens
    public static final int DEFAULT_BUFFER_AMOUNT = 4;
    
    public Buffer(){
        length = DEFAULT_BUFFER_AMOUNT;
        buffers = new int[DEFAULT_BUFFER_AMOUNT];
        bufferLengths = new int[DEFAULT_BUFFER_AMOUNT];
    }
    
    public Buffer(int amount){
        length = amount;
        buffers = new int[amount];
        bufferLengths = new int[amount];
    }
    
    public Buffer(int[] buffs, int[] lengths){
        length = buffers.length;
        buffers = buffs;
        bufferLengths = lengths;
    }
    
    //Set the buffer array to a given array
    public void setBuffers(int[] buffer){
        buffers = buffer;
    }
    
    //Set the buffer at a given index
    public void setBuffer(int index, int buffer){
        buffers[index] = buffer;
    }
    
    //Set the buffer length array
    public void setBufferLengths(int[] lengths){
        bufferLengths = lengths;
    }
    
    //Set the length of a buffer at a given index
    public void setBufferLength(int index, int length){
        bufferLengths[index] = length;
    }
    
    //Set the buffer array and length array to a new array (Potential for memory leak: should it delete buffers first?)
    public void setBuffersAndLengths(int[] buffer, int[] lengths){
        buffers = buffer;
        bufferLengths = lengths;
    }
    
    //Set the buffer and length at a specified index
    public void setBufferAndLength(int index, int buffer, int lengths){
        buffers[index] = buffer;
        bufferLengths[index] = lengths;
    }
    
    //Get the array of buffers
    public int[] getBuffers(){
        return buffers;
    }
    
    //Get the buffer at a specified index
    public int getBuffer(int index){
        return buffers[index];
    }
    
    //Get the array of buffer lengths
    public int[] getBufferLengths(){
        return bufferLengths;
    }
    
    //Get the length of a given buffer
    public int getBufferLength(int index){
        return bufferLengths[index];
    }
    
    //Returns whether deletion was successful or not
    public boolean deleteBuffer(int index){
        try{
            glDeleteBuffers(buffers[index]);
            
            buffers[index] = 0;
            bufferLengths[index] = 0;
            
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    //Returns whether deletion was successful or not
    public boolean deleteAllBuffers(){
        try{
            for(int i : buffers)
                glDeleteBuffers(i);
            
            buffers = null;
            bufferLengths = null;
            
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}

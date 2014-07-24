/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pocketviewer.Generator.Noise;

/**
 * ADD SEPARATE SCALE FOR X,Y,Z AXIS
 * ADD HANDLER FOR TURBULANCE
 * @author Jocopa3
 */
public class NoiseParams {
    public int Octaves = 1;
    public float Frequency = 1;
    public float Amplitude = 1;
    public float Scale = 50;
    
    public NoiseParams(){}
    
    public NoiseParams(int octaves, float scale){
        Octaves = octaves;
        Scale = scale;
    }
    
    public NoiseParams(int octaves, float freq, float amp, float scale){
        Octaves = octaves;
        Frequency = freq;
        Amplitude = amp;
        Scale = scale;
    }
    
    public void setOctaves(int octaves){
        Octaves = octaves;
    }
    
    public void setFrequency(float freq){
        Frequency = freq;
    }
    
    public void setAmplitude(float amp){
        Amplitude = amp;
    }
    
    public void setScale(float scale){
        Scale = scale;
    }
    
    public int getOctaves(){
        return Octaves;
    }
    
    public float getFrequency(){
        return Frequency;
    }
    
    public float getAmplitude(){
        return Amplitude;
    }
    
    public float getScale(){
        return Scale;
    }
}

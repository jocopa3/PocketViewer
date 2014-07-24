/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pocketviewer.Generator.Noise;
import static pocketviewer.Utils.MathUtils.*;
/**
 *
 * @author Jocopa3
 */
public class NoiseUtils {
    
    //Normalized to 0,1
    public static float sCurve(float x){
        return 1/(1+exp(-x));
    }
    
    //Normalized to -1,1
    public static float sCurveNormalized(float x){
        return (1/(1+exp(-x)))*2-1;
    }
    
    //Normalized to min,max
    public static float sCurveNormalized(float x, float min, float max){
        return (1/(1+exp(-x)))*(max-min)+min;
    }
}

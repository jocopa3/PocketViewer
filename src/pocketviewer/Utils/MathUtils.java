/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pocketviewer.Utils;

/**
 *
 * @author Jocopa3
 */
public class MathUtils {
    public static int min(int a, int b) {
        return (a <= b) ? a : b;
    }
    
    public static int max(int a, int b) {
        return (a >= b) ? a : b;
    }
    
    public static float min(float a, float b) {
        return (a <= b) ? a : b;
    }
    
    public static float max(float a, float b) {
        return (a >= b) ? a : b;
    }
    
    public static int sqr(int a){
        return a*a;
    }
    
    public static float sqr(float a){
        return a*a;
    }
    
    public static int distSqrd(int a1, int a2, int b1, int b2){
        return sqr(b1-a1)+sqr(b2-a2);
    }
    
    public static float distSqrd(float a1, float a2, float b1, float b2){
        return sqr(b1-a1)+sqr(b2-a2);
    }
    
    //Returns the ceil of a float
    public static int ceil(float a){
        return (int)a+1;
    }
    
    //Returns the floor of a float
    public static int floor(float a){
        return (int)a;
    }
    
    //Rounds a float up or down depending on the decimal
    public static int round(float a){
        return (a-(int)a) >= 0.5 ? (int)a+1 : (int)a;
    }
    
    //A quick version of Math.exp; uses the definition exp where e^x = lim(n->infinity, (1+1/n)^n)
    //Sacrafices accuracy for speed, but I'm already using floats, do you think I'd care about precise accuracy :p
    //Max value for precision before it becomes wrong is 22, but anything higher than 10 probably won't make any difference
    public static float exp(float x, int precision) {
        int pow = 2 << (precision-1); //2^precision
        x = 1 + x/pow;
        
        for(int i = 0; i < precision; i++)
            x *= x;
        
        return x;
    }
    
    public static float exp(float x){
        return exp(x, 10); //Default precision is 2^10;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pocketviewer.Utils;

/**
 * Precomputed stuff
 * @author Jocopa3
 */
public class Table {
    public static float[] cos = new float[361];
    public static float[] sin = new float[361];
    public static double num = 2 << 13;
    
    public static Table table = new Table(); //Singleton instance of table for static access

    private Table() {
        for (int i = 0; i <= 360; i++) {
            cos[i] = (float)Math.cos(Math.toRadians(i));
            sin[i] = (float)Math.sin(Math.toRadians(i));
        }
    }

    public static int round(float x){
        return (int)((x + num + 0.5) - num);
    }
    
    public static float getSine(float angle) {
        int angleCircle = round(angle) % 360;
        return sin[angleCircle];
    }

    public static float getCos(float angle) {
        int angleCircle = round(angle) % 360;
        return cos[angleCircle];
    }
}

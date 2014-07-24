package pocketviewer.Generator.Noise;

/**
 *
 * @author Jocopa3
 */
public abstract class Noise {
	public abstract float noise2D(float x, float z);
    public abstract float noise3D(float x, float y, float z);
    public abstract float octaveNoise2D(int octaves, float x, float z);
    public abstract float octaveNoise2D(int octaves, float freq, float amp, float x, float z);
	public abstract float octaveNoise3D(int octaves, float x, float y, float z);
    public abstract float octaveNoise3D(int octaves, float freq, float amp, float x, float y, float z);
    //Add turbulance handlers
}

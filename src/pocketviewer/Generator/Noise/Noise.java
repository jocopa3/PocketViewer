package pocketviewer.Generator.Noise;

/**
 *
 * @author Jocopa3
 */
public abstract class Noise {
	public abstract float noise(float x, float y, float z);
	public abstract float octaveNoise(int octaves, float x, float y, float z);
}

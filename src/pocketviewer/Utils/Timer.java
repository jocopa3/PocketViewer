package pocketviewer.Utils;

/**
 * Portions of code accessed from the LWJGL Wiki
 * 
 * Modified for use with PocketViewer
 * (Am too lazy for the time being to write my own)
 */
public class Timer {
	public int FPS;
	public int fpsTemp;
	public long lastFPS;
	
	public boolean running;
	
	public Timer(){
		FPS = 0;
		fpsTemp = 0;
		lastFPS = getTime(); //set lastFPS to current Time
	}
	
	private long getTime(){
        return System.currentTimeMillis();
		//return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
	
	public void start(){
		running = true;
	}
	
	public void stop(){
		running = false;
	}
	
	public boolean isRunning(){
		return running;
	}

	public void updateFPS(){
		if(!running)
			return;

		if(getTime() - lastFPS > 1000) {
			FPS = fpsTemp;
			fpsTemp = 0; //reset the FPS counter
			lastFPS += 1000; //add one second
		}
		fpsTemp++;
	}
	
	public int getFPS(){
		return FPS;
	}
}

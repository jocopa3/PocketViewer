package Tests;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import java.util.Random;

/**
 *
 * @author Jocopa3
 */
public class World {
	private Noise noise;
	private Random r;
	
	private Chunk[][] chunks;
	
	public World(){
		noise = new Noise();
		chunks = new Chunk[16][16];
		r = new Random();
	}
	
	public World(long seed){
		noise = new Noise(seed);
		chunks = new Chunk[16][16];
		r = new Random(seed);
	}
	
	public World(int cacheSize){
		noise = new Noise();
		chunks = new Chunk[cacheSize][cacheSize];
		r = new Random();
	}
	public World(int cacheSize, long seed){
		noise = new Noise(seed);
		chunks = new Chunk[cacheSize][cacheSize];
		r = new Random(seed);
	}
	
	public int getBlockID(int x, int y, int z){
		int chunkx = x>>4, chunkz = z>>4;

		if(chunkx >= chunks.length || chunkz >= chunks[0].length || chunkx<0 || chunkz<0){
			return 0;
		}else
			return chunks[chunkx][chunkz].getBlockID(x&15, y, z&15);
	}
	
	public void setBlockID(int x, int y, int z, int id){
		int chunkx = x>>4, chunkz = z>>4;

		if(chunkx >= chunks.length || chunkz >= chunks[0].length || chunkx<0 || chunkz<0){
			return;
		}else
			chunks[chunkx][chunkz].setBlockID(x&15, y, z&15, id);
	}
	
	public void genAllChunks(){
		for(int x = 0; x < chunks.length; x++){
			for(int z = 0; z < chunks[x].length; z++){
				genChunk(x,z);
				
			}
		}
	}
	
	
	public void genChunk(int x, int z){
		if(x<0||x>=chunks.length||z<0||z>=chunks[0].length)
			return;
		
		chunks[x][z] = new Chunk(this,x,z);

        int max = 0;
        
		for(int ax = 16*x; ax < 16*x+16; ax++){
			for(int az = 16*z; az < 16*z+16; az++){
				float ah = noise.noise(ax/150f, az/150f, 0)*chunks[x][z].height/2;
				for(int ay = 0; ay < ah; ay++){
					if(noise.simplex_noise(2,ax/70f, ay/70f, az/70f)>2){
						chunks[x][z].setBlockID(ax, ay, az, 1);
                        if(ay>max)
                            max = ay;
					}
				}
                chunks[x][z].setMaxHeight(ax,az,max);
                max = 0;
			}
		}
	}
	
	/*
	public void genChunk(int x, int z){
		if(x<0||x>=chunks.length||z<0||z>=chunks[0].length)
			return;
		
		chunks[x][z] = new Chunk(this,x,z);

		for(int ax = 16*x; ax < 16*x+16; ax++){
			for(int az = 16*z; az < 16*z+16; az++){
				float ah = noise.simplex_noise(2,ax/150f, az/150f, 0)*chunks[x][z].height/3;
				chunks[x][z].setMaxHeight(ax&15,az&15,(int)ah);
				for(int ay = 0; ay < ah; ay++){
					chunks[x][z].setBlockID(ax, ay, az, 1);
				}
			}
		}
	}*/
	
	public int min(int a, int b){
		return (a < b) ? a : b;
	}
	
	public int max(int a, int b){
		return (a > b) ? a : b;
	}
	
	public void genAllLights(){
		for(int x = 0; x < chunks.length; x++){
			for(int z = 0; z < chunks[x].length; z++){
				genLighting(x,z);
			}
		}
	}
	
	public void genLighting(int x, int z){
		if(x<0||x>=chunks.length||z<0||z>=chunks[0].length)
			return;
		for(int ax = 16*x; ax < 16*x+16; ax++){
			for(int ay = chunks[x][z].height-1; ay >= 0; ay--){
				for(int az = 16*z; az < 16*z+16; az++){
					int l = chunks[x][z].getMaxHeight(ax, az);
					if(ay>l){
						chunks[x][z].setSkylight(ax, ay, az, 15);
					}else{
						int s = min(l-ay, 15);
						chunks[x][z].setSkylight(ax, ay, az, s);
						//if(s == 0)
							//continue;
					}
				}
			}
		}
	}
	
	public void renderAllChunks(){
		//int rand = r.nextInt(8388608);
		//setBlockID(rand & 255, rand >> 8 & 0x7f ,rand >> 15, 1);
		for(int x = 0; x < chunks.length; x++){
			for(int z = 0; z < chunks[x].length; z++){
				renderChunk(x,z);
			}
		}
	}
	
	public void renderChunk(int x, int z){
		if(x<0||z<0||x>=chunks.length||z>=chunks[0].length)
			return;
		
		glPushMatrix();
		glTranslatef(x*16,0,z*16);
		
		glBindBuffer(GL_ARRAY_BUFFER, getChunkBuffer(x,z));
		glVertexPointer(3, GL_FLOAT, 4*3, 0L);

		glBindBuffer(GL_ARRAY_BUFFER, getChunkColorBuffer(x,z));
		glColorPointer(3, GL_FLOAT, 0, 0L);
					
        glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_COLOR_ARRAY);
		
		glDrawArrays(GL_QUADS, 0, getChunkBufferLength(x,z));
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);
		
		glPopMatrix();
	}
	
	public void deleteAllChunkBuffers(){
		for(int x = 0; x < chunks.length; x++){
			for(int z = 0; z < chunks[x].length; z++){
				deleteChunkBuffer(x,z);
			}
		}
	}
	
	public void deleteChunkBuffer(int x, int z){
		glDeleteBuffers(getChunkBuffer(x,z));
		glDeleteBuffers(getChunkColorBuffer(x,z));
	}
	
	public int getChunkBuffer(int x, int z){
		return chunks[x][z].getBuffer();
	}
	
	public int getChunkColorBuffer(int x, int z){
		return chunks[x][z].getColorBuffer();
	}
	
	public int getChunkBufferLength(int x, int z){
		return chunks[x][z].getBufferLength();
	}
}

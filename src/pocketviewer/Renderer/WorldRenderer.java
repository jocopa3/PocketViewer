/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pocketviewer.Renderer;

import pocketviewer.Objects.*;
/**
 *
 * @author Jocopa3
 */
public class WorldRenderer {
	public float renderDistance;
	public World world;
	public ChunkRenderer chunkRenderer;
	public BlockRenderer blockRenderer;
	public PlayerRenderer player;
	
	public WorldRenderer(World world){
		this.world = world;
		player = new PlayerRenderer(this);
		//blockRenderer = new BlockRenderer(this);
		chunkRenderer = new ChunkRenderer(this);
	}

	public void render() {
		player.updateCamera();
		chunkRenderer.renderAllChunks();
	}
}

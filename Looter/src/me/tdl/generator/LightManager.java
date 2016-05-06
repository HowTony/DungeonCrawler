package me.tdl.generator;

import java.awt.Graphics2D;
import java.util.concurrent.CopyOnWriteArrayList;


public class LightManager {

	private CopyOnWriteArrayList<LightSource> mLights;
	private CopyOnWriteArrayList<Block> mLoadBlocks;
	
	

	public LightManager(CopyOnWriteArrayList<Block> loadedBlocks) {
		mLoadBlocks = loadedBlocks;

	}
	
	LightSource light;
	

	public void init() {
		mLights = new CopyOnWriteArrayList<LightSource>();
		light = new LightSource(200, 200, 10);
		mLights.add(light);

	}

	public void render(Graphics2D g) {
		for (LightSource eachLight : mLights) {
			eachLight.render(g);
		
		}
		
		
	}

	public void tick() {
		for (LightSource eachLight : mLights) {
			eachLight.tick();
		}
		
		light.getLightLocation().mXPosition = World.getPlayer().getPos().mXPosition 
				- World.getPlayer().getPos().getWorldLocation().mXPosition 
				+ World.getPlayer().getPos().mXPosition - 8;
		
		light.getLightLocation().mYPosition = World.getPlayer().getPos().mYPosition 
				- World.getPlayer().getPos().getWorldLocation().mYPosition 
				+ World.getPlayer().getPos().mYPosition - 8;
	}
	
	public void addNewLight(int yPos, int xPos, int lightDistance){
		mLights.add(new LightSource(xPos, yPos, lightDistance));
	}
	
	
	public CopyOnWriteArrayList<LightSource> getLights() {
		return mLights;
	}
	
	
}
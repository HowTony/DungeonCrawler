package me.tdl.generator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.tdl.MoveableObjects.Player;
import me.tdl.generator.Block.BlockType;
import me.tdl.main.Main;
import my.project.gop.main.Vector2F;
import my.project.gop.main.loadImageFrom;

public class Map {


	public static BufferedImage mMap = null;
	
	public World mWorld;

	public Map() {

	}

	public void init() {
		
		try {
			mMap = loadImageFrom.LoadImageFrom(Main.class, "map.png");
		} catch (Exception e) {

		}

		
		mWorld = new World("world_1", mMap, 100, 100);
		mWorld.generateWorld();
		
		
	


		
	
	}

	public void tick(double deltaTime) {
		mWorld.tick(deltaTime);
	}

	public void redner(Graphics2D g) {
		mWorld.redner(g);
	}

}

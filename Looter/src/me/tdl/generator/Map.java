package me.tdl.generator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.tdl.MoveableObjects.Player;
import me.tdl.generator.Block.BlockType;
import me.tdl.main.Main;
import my.project.gop.main.Vector2F;
import my.project.gop.main.loadImageFrom;

public class Map {

	TileManager mTiles = new TileManager();
	Player player = new Player();

	public Map() {

	}

	public void init() {
		player.init();

		BufferedImage map = null;

		try {
			map = loadImageFrom.LoadImageFrom(Main.class, "map.png");
		} catch (Exception e) {

		}

		// loop through all the pixels in the 100x100 pixel image
		for (int x = 0; x < 100; x++) {
			for (int y = 0; y < 100; y++) {

				int color = map.getRGB(x, y);

				// 0xFFFFFF is hex for white
				// 0x808080 is hex for the gray color used

				switch (color & 0xFFFFFF) {
				case 0x808080:
					TileManager.mBlocks.add(new Block(new Vector2F(x * 48, y * 48), BlockType.STONE_1).isSolid(false));
					break;

				case 0x404040:
					TileManager.mBlocks.add(new Block(new Vector2F(x * 48, y * 48), BlockType.WALL_1).isSolid(true));
					break;
					
				case 0x707070:
					TileManager.mBlocks.add(new Block(new Vector2F(x * 48, y * 48), BlockType.LARGE_STONE_FLOOR).isSolid(false));
					break;	
					
				}
			}
		}
	}

	public void tick(double deltaTime) {
		mTiles.tick(deltaTime);
		player.tick(deltaTime);
	}

	public void redner(Graphics2D g) {
		mTiles.render(g);
		player.render(g);
	}

}

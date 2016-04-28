package me.tdl.generator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import me.tdl.MoveableObjects.Player;
import me.tdl.generator.Block.BlockType;
import my.project.gop.main.Vector2F;

public class World {

	private String mWorldName;
	private BufferedImage mWorldImage;
	private int mWorldWidth;
	private int mWorldHeight;

	public static ArrayList<BlockEntity> mBlockEntityList = new ArrayList<BlockEntity>();

	private TileManager mTiles = new TileManager();
	private Player mPlayer = new Player();

	public World(String worldName, BufferedImage worldImage, int worldW, int worldH) {
		mWorldName = worldName;
		mWorldImage = worldImage;
		mWorldWidth = worldW;
		mWorldHeight = worldH;
	}

	public void generateWorld() {
		// loop through all the pixels in the 100x100 pixel image
		for (int x = 0; x < mWorldWidth; x++) {
			for (int y = 0; y < mWorldHeight; y++) {

				int color = mWorldImage.getRGB(x, y);

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
					TileManager.mBlocks
							.add(new Block(new Vector2F(x * 48, y * 48), BlockType.LARGE_STONE_FLOOR).isSolid(false));
					break;

				}
			}
		}

		mPlayer.init(this);

	}

	public static void dropBlockEntity(Vector2F pos, BufferedImage block_img) {
		BlockEntity ent = new BlockEntity(pos, block_img);
		if (!mBlockEntityList.contains(ent)) {
			mBlockEntityList.add(ent);
		}

	}

	public void tick(double deltaTime) {
		mTiles.tick(deltaTime);

		// Tick BlockEntity
		for (BlockEntity eachEnt : mBlockEntityList) {
			if (mPlayer.mRender.intersects(eachEnt)) {
				eachEnt.tick(deltaTime);
			}

		}
		mPlayer.tick(deltaTime);
	}

	public void redner(Graphics2D g) {
		mTiles.render(g);

		// Render BlockEntity
		for (BlockEntity eachEnt : mBlockEntityList) {
			if (mPlayer.mRender.intersects(eachEnt)) {
				eachEnt.render(g);
			}

		}

		mPlayer.render(g);
		g.drawString(mBlockEntityList.size() + "", 150, 150);
	}

	public String getWorldName() {
		return this.mWorldName;
	}

	public BufferedImage getWorld_Image() {
		return this.mWorldImage;
	}

}

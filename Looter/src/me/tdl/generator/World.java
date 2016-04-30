package me.tdl.generator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.tdl.MoveableObjects.Player;
import me.tdl.generator.Block.BlockType;
import me.tdl.main.Main;
import my.project.gop.main.Vector2F;
import my.project.gop.main.loadImageFrom;

public class World {

	private String mWorldName;
	private BufferedImage mMap;
	private int mWorldWidth;
	private int mWorldHeight;
	private int mBlockSize = 48;
	
	private Player mPlayer;
	
	
	//Lists
	private TileManager mTiles;
	
	
	
	//Booleans
	private boolean mHasSize = false;
	
	
	public World(String world) {
		mWorldName = world;
	}

	public void init() {
		mTiles = new TileManager();
		if(mPlayer != null){
			mPlayer.init(this);
		}
		
	}

	public void tick(double deltaTime) {
		mTiles.tick(deltaTime);
		
		if(mPlayer != null){
			mPlayer.tick(deltaTime);
		}
	}

	public void render(Graphics2D g) {
		mTiles.render(g);
		
		if(mPlayer != null){
			mPlayer.render(g);
		}
		
		
	}

	public void generate(String world_image_name) {
		mMap = null;
		
		if(mHasSize){
			try {
				mMap = loadImageFrom.LoadImageFrom(Main.class, world_image_name+ ".png");
			} catch (Exception e) {

			}
			for(int x = 0; x < mWorldWidth; x++){
				for(int y = 0; y < mWorldHeight; y++){
					
					int col = mMap.getRGB(x, y);
					

					switch (col & 0xFFFFFF) {
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
		}
		
		
	

		
	}

	public void setSize(int worldWidth, int worldHeight) {
		this.mWorldWidth = worldWidth;
		this.mWorldHeight = worldHeight;
		mHasSize = true;
		
	}

	public void addPlayer(Player player) {
		this.mPlayer = player;
		
	}

	public String getWorldName() {
		return this.mWorldName;
	}
}

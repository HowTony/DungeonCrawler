package me.tdl.generator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

import me.tdl.MoveableObjects.Player;
import me.tdl.generator.Block.BlockType;
import me.tdl.main.Main;
import my.project.gop.main.Vector2F;
import my.project.gop.main.loadImageFrom;

public class World {

	private String mWorldName;
	public static Vector2F s_MapPosition = new Vector2F();
	private BufferedImage mMap;
	private int mWorldWidth;
	private int mWorldHeight;
	private int mBlockSize = 48;
	
	private Player mPlayer;
	
	
	//Lists
	private CopyOnWriteArrayList<BlockEntity> mBlockEnts;
	private TileManager mTiles;
	
	
	
	
	//Booleans
	private boolean mHasSize = false;
	
	
	public World(String world) {
		mWorldName = world;
		Vector2F.setWorldVariables(s_MapPosition.mXPosition, s_MapPosition.mYPosition);
	}

	public void init() {
		mBlockEnts = new CopyOnWriteArrayList<BlockEntity>();
		mTiles = new TileManager();
		if(mPlayer != null){
			mPlayer.init(this);
		}
		
	}

	public void tick(double deltaTime) {
		Vector2F.setWorldVariables(s_MapPosition.mXPosition, s_MapPosition.mYPosition);
		
		mTiles.tick(deltaTime);
		
		if(!mBlockEnts.isEmpty()){
			for(BlockEntity eachBlockEnt : mBlockEnts){
				if(mPlayer.s_Render.intersects(eachBlockEnt)){
					eachBlockEnt.tick(deltaTime);
					eachBlockEnt.setAlive(true);
				}else{
					eachBlockEnt.setAlive(false);
				}
			}
		}
		
		if(mPlayer != null){
			mPlayer.tick(deltaTime);
		}
	}

	public void render(Graphics2D g) {
		mTiles.render(g);
		
		
		if(!mBlockEnts.isEmpty()){
			for(BlockEntity eachBlockEnt : mBlockEnts){
				if(mPlayer.s_Render.intersects(eachBlockEnt)){
					eachBlockEnt.render(g);
				}
			}
		}
		
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
	public Vector2F getWorldPos(){
		return s_MapPosition;
	}
	
	public float getWorldXpos(){
		return s_MapPosition.mXPosition;
	}
	
	public float getWorldYpos(){
		return s_MapPosition.mYPosition;
	}

	public void addPlayer(Player player) {
		this.mPlayer = player;
		
	}
	
	public void dropBlockEntity(Vector2F pos, BufferedImage block_img){
		BlockEntity ent = new BlockEntity(pos, block_img);
		if(!mBlockEnts.contains(ent)){
			mBlockEnts.add(ent);
		}
	}
	
	
	public void removeDroppedBlockEntity(BlockEntity blockEnt){
		if(mBlockEnts.contains(blockEnt)){
			mBlockEnts.remove(blockEnt);
		}
	}

	public String getWorldName() {
		return this.mWorldName;
	}
}

package me.tdl.generator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

import com.sun.javafx.geom.transform.GeneralTransform3D;

import me.tdl.MoveableObjects.Player;
import me.tdl.gamestate.GameStateManager;
import me.tdl.gamestates.DungeonLevelLoader;
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

	private boolean mHasGenerated;

	private static Player s_Player;

	private LightManager mLightManager;

	// Lists
	private CopyOnWriteArrayList<BlockEntity> mBlockEnts;
	private TileManager mTiles;

	// world spawn

	private Block mSpawn;

	// Booleans
	private boolean mHasSize = false;

	private GameStateManager mGameStateManager;

	public World(String world, GameStateManager gameStateManager) {
		mWorldName = world;
		Vector2F.setWorldVariables(s_MapPosition.mXPosition, s_MapPosition.mYPosition);
		this.mGameStateManager = gameStateManager;
	}

	public void init() {
		mBlockEnts = new CopyOnWriteArrayList<BlockEntity>();
		mTiles = new TileManager(this);

		mLightManager = new LightManager(mTiles.getBlocks());
		mLightManager.init();

		s_MapPosition.mXPosition = mSpawn.getBlockLocation().mXPosition - s_Player.getPos().mXPosition;
		s_MapPosition.mYPosition = mSpawn.getBlockLocation().mYPosition - s_Player.getPos().mYPosition;

		if (s_Player != null) {
			s_Player.init(this);
		}

	}

	public void tick(double deltaTime) {
		Vector2F.setWorldVariables(s_MapPosition.mXPosition, s_MapPosition.mYPosition);

		if (!s_Player.hasSpawned()) {
			mSpawn.tick(deltaTime);
		}

		mTiles.tick(deltaTime);
		mLightManager.tick();

		if (!mBlockEnts.isEmpty()) {
			for (BlockEntity eachBlockEnt : mBlockEnts) {
				if (s_Player.s_Render.intersects(eachBlockEnt)) {
					eachBlockEnt.tick(deltaTime);
					eachBlockEnt.setAlive(true);
				} else {
					eachBlockEnt.setAlive(false);
				}
			}
		}

		if (s_Player != null) {
			s_Player.tick(deltaTime);
		}
	}

	public void render(Graphics2D g) {
		mTiles.render(g);

		if (!s_Player.hasSpawned()) {
			mSpawn.render(g);
		}

		for (BlockEntity eachBlockEnt : mBlockEnts) {
			if (s_Player.s_Render.intersects(eachBlockEnt)) {
				eachBlockEnt.render(g);
			}
		}
		

		if (s_Player != null) {
			s_Player.render(g);
		}

		for (Block eachBlock : TileManager.mBlocks) {
			if (s_Player.s_Render.intersects(eachBlock)) {
				eachBlock.renderBlockLightLevel(g);
			}
		}
		

		mLightManager.render(g);

		
		//black boarder bars
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.mWidth, Main.mHeight / 6);
		g.fillRect(0, 1000, Main.mWidth, Main.mHeight / 6);
		g.clipRect(0, 0, Main.mWidth, Main.mHeight);

	}

	public void generate(String world_image_name) {
		mMap = null;

		if (mHasSize) {
			try {
				mMap = loadImageFrom.LoadImageFrom(Main.class, world_image_name + ".png");
			} catch (Exception e) {

			}
			for (int x = 0; x < mWorldWidth; x++) {
				for (int y = 0; y < mWorldHeight; y++) {

					int col = mMap.getRGB(x, y);

					switch (col & 0xFFFFFF) {
					case 0x808080:
						mTiles.mBlocks.add(new Block(new Vector2F(x * 48, y * 48), BlockType.STONE_1).isSolid(false));
						break;

					case 0x404040:
						mTiles.mBlocks.add(new Block(new Vector2F(x * 48, y * 48), BlockType.WALL_1).isSolid(true));
						break;

					case 0x707070:
						mTiles.mBlocks.add(
								new Block(new Vector2F(x * 48, y * 48), BlockType.LARGE_STONE_FLOOR).isSolid(false));
						break;

					}
				}
			}
		}

		mHasGenerated = true;
	}

	public void setSize(int worldWidth, int worldHeight) {
		this.mWorldWidth = worldWidth;
		this.mWorldHeight = worldHeight;
		mHasSize = true;

	}

	public Vector2F getWorldPos() {
		return s_MapPosition;
	}

	public float getWorldXpos() {
		return s_MapPosition.mXPosition;
	}

	public float getWorldYpos() {
		return s_MapPosition.mYPosition;
	}

	public void addPlayer(Player player) {
		this.s_Player = player;

	}

	public void setWorldSpawn(float xPos, float yPos) {
		if (xPos < mWorldWidth) {
			if (yPos < mWorldHeight) {
				Block spawn = new Block(new Vector2F(xPos * mBlockSize, yPos * mBlockSize));
				this.mSpawn = spawn;
			}
		}
	}

	public Vector2F getWorldSpawn() {
		return mSpawn.mPostion;
	}

	public void dropBlockEntity(Vector2F pos, BufferedImage block_img) {
		BlockEntity ent = new BlockEntity(pos, block_img);
		if (!mBlockEnts.contains(ent)) {
			mBlockEnts.add(ent);
		}
	}

	public void removeDroppedBlockEntity(BlockEntity blockEnt) {
		if (mBlockEnts.contains(blockEnt)) {
			mBlockEnts.remove(blockEnt);
		}
	}

	public String getWorldName() {
		return this.mWorldName;
	}

	public static Player getPlayer() {
		return World.s_Player;
	}

	public boolean hasGenerated() {
		return mHasGenerated;
	}

	public void resetWorld() {
		mTiles.getBlocks().clear();
		mTiles.getLoadedBlocks().clear();
		mBlockEnts.clear();
		mSpawn = null;
	}

	public void changeToWorld(String world_name, String map_name) {

		if (mWorldName != world_name) {
			resetWorld();
			mGameStateManager.s_States.push(new DungeonLevelLoader(mGameStateManager, world_name, map_name));
			mGameStateManager.s_States.peek().init();
		}

	}

	public LightManager getLightManager() {
		return mLightManager;
	}

}

package me.tdl.generator;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import me.tdl.main.Assets;
import my.project.gop.main.Vector2F;

public class Block extends Rectangle {

	private static final long serialVersionUID = 1L;
	Vector2F mPostion = new Vector2F();
	final private int BLOCK_SIZE = 48;
	private BlockType mBlockType;
	private BufferedImage mBlock;
	private boolean isSolid;
	private boolean isAlive;
	private boolean mDropped = false;

	private float mLightLevel = .5f;

	public Block(Vector2F pos) {
		setBounds((int) pos.mXPosition, (int) pos.mYPosition, BLOCK_SIZE, BLOCK_SIZE);
		this.mPostion = pos;
		isAlive = true;
	}

	public Block(Vector2F pos, BlockType someBlock) {
		setBounds((int) pos.mXPosition, (int) pos.mYPosition, BLOCK_SIZE, BLOCK_SIZE);
		this.mPostion = pos;
		isAlive = true;
		this.mBlockType = someBlock;
		init();
	}

	public Block isSolid(boolean isSolid) {
		this.isSolid = isSolid;
		return this;
	}

	public void init() {
		switch (mBlockType) {
		case STONE_1:
			mBlock = Assets.getStone_1();
			break;

		case WALL_1:
			mBlock = Assets.getWall_1();
			break;

		case LARGE_STONE_FLOOR:
			mBlock = Assets.getStoneFloorLarge();
			break;
		}
	}

	public void tick(double deltaTime) {
		if (isAlive) {
			setBounds((int) mPostion.mXPosition, (int) mPostion.mYPosition, BLOCK_SIZE, BLOCK_SIZE);
		}
		
		if(mLightLevel > 0.050f && mLightLevel < 0.40f){
			mLightLevel = 0.250f;
		}
	}

	// Light blocks
	public void renderBlockLightLevel(Graphics2D g) {
		if(mLightLevel > 0f){
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, mLightLevel));
			g.setColor(Color.BLACK);
			g.fillRect((int) mPostion.getWorldLocation().mXPosition, (int) mPostion.getWorldLocation().mYPosition,
					BLOCK_SIZE, BLOCK_SIZE);
			g.setColor(Color.WHITE);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		}
	}

	public void render(Graphics2D g) {
		if (isAlive) {

			if (mBlock != null) {

				g.drawImage(mBlock, (int) mPostion.getWorldLocation().mXPosition,
						(int) mPostion.getWorldLocation().mYPosition, BLOCK_SIZE, BLOCK_SIZE, null);

			} else {
				g.fillRect((int) mPostion.getWorldLocation().mXPosition, (int) mPostion.getWorldLocation().mYPosition,
						BLOCK_SIZE, BLOCK_SIZE);

				if (isSolid) {
					g.drawRect((int) mPostion.getWorldLocation().mXPosition,
							(int) mPostion.getWorldLocation().mYPosition, BLOCK_SIZE, BLOCK_SIZE);
				} else {
					if (!mDropped) {
						float xPos = mPostion.mXPosition + 24 - 12;
						float yPos = mPostion.mYPosition + 24 - 12;

						Vector2F newPos = new Vector2F(xPos, yPos);

						mDropped = true;

					}
				}
			}
		}
	}

	public float getLightLevel() {
		return mLightLevel;
	}

	public void addShadows(LightSource source, float amount) {
		if (mLightLevel != 1) {
			if (!this.getBounds().intersects(source.getLightDetection())) {

				if (mLightLevel < 0.981000) {
					mLightLevel += amount;
				}
			}
		}
	}

	public void removeShadows(float amount) {

		if (mLightLevel > 0.001000) {
			mLightLevel -= amount;
		}

	}

	public Vector2F getBlockLocation() {
		return mPostion;
	}

	public enum BlockType {
		STONE_1, WALL_1, LARGE_STONE_FLOOR
	}

	public boolean isSolid() {
		return isSolid;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

}

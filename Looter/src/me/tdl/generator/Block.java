package me.tdl.generator;

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

	public Block(Vector2F pos, BlockType someBlock) {
		setBounds((int) pos.mXPosition, (int) pos.mYPosition, BLOCK_SIZE, BLOCK_SIZE);
		this.mPostion = pos;
		isAlive = false;
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
	}

	public void render(Graphics2D g) {
		if (isAlive) {
			g.drawImage(mBlock, (int) mPostion.getWorldLocation().mXPosition,
					(int) mPostion.getWorldLocation().mYPosition, BLOCK_SIZE, BLOCK_SIZE, null);
			if (isSolid) {
				g.drawRect((int) mPostion.getWorldLocation().mXPosition, (int) mPostion.getWorldLocation().mYPosition,
						BLOCK_SIZE, BLOCK_SIZE);
			}else{
				if(!mDropped){
					float xPos = mPostion.mXPosition + 24 - 12;
					float yPos = mPostion.mYPosition + 24 - 12;
					
					Vector2F newPos = new Vector2F(xPos, yPos);
					
					
					mDropped = true;
				}
			}
		}
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

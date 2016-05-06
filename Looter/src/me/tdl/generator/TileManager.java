package me.tdl.generator;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import me.tdl.MoveableObjects.Player;

public class TileManager {

	public static CopyOnWriteArrayList<Block> mBlocks = new CopyOnWriteArrayList<Block>();
	public static CopyOnWriteArrayList<Block> mLoadedBlocks = new CopyOnWriteArrayList<Block>();

	private World mWorld;
	



	public TileManager(World world) {
		mWorld = world;
		
	}

	public void tick(double deltaTime) {
		for (Block eachBlock : mBlocks) {
			eachBlock.tick(deltaTime);

			// we can move the blocks to the right
			// eachBlock.mPostion.add(new Vector2F(1, 0));
			
			
			

			if (Player.s_Render.intersects(eachBlock)) {
				eachBlock.setAlive(true);

				if (!mLoadedBlocks.contains(eachBlock)) {
					mLoadedBlocks.add(eachBlock);
				}

			} else {
				if (mLoadedBlocks.contains(eachBlock)) {
					mLoadedBlocks.remove(eachBlock);
				}
				eachBlock.setAlive(false);
			}
		}
		
		

		if (!mWorld.getPlayer().isDebugging()) {
			if (!mLoadedBlocks.isEmpty()) {
				mLoadedBlocks.clear();
			}
		}
	}

	public void render(Graphics2D g) {
		
		for (Block eachBlock : mBlocks) {
			eachBlock.render(g);
		}
		
		
	}

	public static CopyOnWriteArrayList<Block> getBlocks() {
		return mBlocks;
	}
	
	public static CopyOnWriteArrayList<Block> getLoadedBlocks(){
		return mLoadedBlocks;
	}

}

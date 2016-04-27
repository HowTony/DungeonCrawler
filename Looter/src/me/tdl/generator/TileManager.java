package me.tdl.generator;

import java.awt.Graphics2D;
import java.util.ArrayList;
import me.tdl.MoveableObjects.Player;

public class TileManager {

	public static ArrayList<Block> mBlocks = new ArrayList<Block>();

	public TileManager() {
	}

	public void tick(double deltaTime) {
		for (Block eachBlock : mBlocks) {
			eachBlock.tick(deltaTime);
			
			// we can move the blocks to the right
			//eachBlock.mPostion.add(new Vector2F(1, 0));
			
			if(Player.mRender.intersects(eachBlock.getBounds())){
				eachBlock.setAlive(true);
			}else{
				eachBlock.setAlive(false);
			}
		}
	}

	public void render(Graphics2D g) {
		for (Block eachBlock : mBlocks) {
			eachBlock.render(g);
		}
	}
}

package me.tdl.main;

import java.awt.Point;

import me.tdl.generator.Block;
import me.tdl.generator.TileManager;

public class Check {

	public static boolean CollisionPlayerBlock(Point point, Point point2) {
		for (Block eachBlock : TileManager.mBlocks) {
			if (eachBlock.isSolid()) {
				if (eachBlock.contains(point) || eachBlock.contains(point2)) {
					
					return true;
				}
			}
		}
		return false;
	}
}

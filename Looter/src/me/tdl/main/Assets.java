package me.tdl.main;

import java.awt.image.BufferedImage;

import my.project.gop.main.SpriteSheet;
import my.project.gop.main.loadImageFrom;

public class Assets {

	private SpriteSheet mBlocks = new SpriteSheet();
	public static SpriteSheet mPlayer = new SpriteSheet();
	
	public static BufferedImage stone_1;
	public static BufferedImage wall_1;
	public static BufferedImage stone_floor_large_1;
	public static BufferedImage mouse_1;
	public static BufferedImage mouse_2;
	public static BufferedImage button_notHeldOver;
	public static BufferedImage button_heldOver;
	public static BufferedImage health_bar;
	public static BufferedImage take_damage;

	public void init() {
		mBlocks.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "spritesheet.png"));
		mPlayer.setSpriteSheet(loadImageFrom.LoadImageFrom(Main.class, "PlayerSheet.png"));

		stone_1 = mBlocks.getTile(0, 0, 16, 16);
		wall_1 = mBlocks.getTile(16, 0, 16, 16);
		stone_floor_large_1 = mBlocks.getTile(0, 16, 48, 48);
		mouse_1 = mPlayer.getTile(48, 0, 8, 8);
		mouse_2 = mPlayer.getTile(55, 0, 8, 8);
		button_notHeldOver = mPlayer.getTile(48, 16, 16, 8);
		button_heldOver = mPlayer.getTile(48, 24, 16, 8);
		health_bar = mPlayer.getTile(48, 8, 32, 8);
		take_damage  = mPlayer.getTile(0, 64, 16, 16);
	}

	public static BufferedImage getStone_1() {
		return stone_1;
	}

	public static BufferedImage getWall_1() {
		return wall_1;
	}
	
	public static BufferedImage getStoneFloorLarge(){
		return stone_floor_large_1;
	}
	
	public static BufferedImage getMouse_1(){
		return mouse_1;
	}
	
	public static BufferedImage getMouse_2(){
		return mouse_2;
	}
	
	public static BufferedImage getButtonNotHeldover(){
		return button_notHeldOver;
	}
	
	public static BufferedImage getButtonHeldover(){
		return button_heldOver;
	}
	
	public static BufferedImage getHealthBar(){
		return health_bar;
	}
	
	public static BufferedImage getTake_damage() {
		return take_damage;
	}
	

}

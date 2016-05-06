package me.tdl.MoveableObjects;

import java.awt.Color;
import java.awt.Graphics2D;

import me.tdl.main.Assets;

public class PlayerStats {

	private Player mPlayer;
	
	//HEALTH

	
	private float mHealth = 100;
	private float mMaxHealth = 1;
	private float mHealthScale = mHealth / mMaxHealth;
	
	
	
	public PlayerStats(Player player) {
		mPlayer = player;
	}
	
	
	public void tick(){
		damage(5f);
		
	}
	
	public void render(Graphics2D g){
		g.setColor(Color.RED);
		g.fillRect((int)mPlayer.getPos().mXPosition - 20, (int)mPlayer.getPos().mYPosition - 60, 86, 32 - 10);
		g.setColor(Color.GREEN);
		g.fillRect((int)mPlayer.getPos().mXPosition - 20, (int)mPlayer.getPos().mYPosition - 60, (int)mMaxHealth * (int)mHealthScale - 14, 32 - 10);
		g.drawImage(Assets.getHealthBar(), (int)mPlayer.getPos().mXPosition - 27, (int)mPlayer.getPos().mYPosition - 70, 32*3, 32,null);
	}
	
	
	public void damage(float amount){
		if(mHealthScale > 0 && !mPlayer.isDamaged()){
			mHealthScale -= amount;
			mPlayer.setDamaged(true);
		}else{
			
		}
		
	}

	
	
	
}

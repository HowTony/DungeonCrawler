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
	private float mDamageRedTime = mHealth /mMaxHealth;
	private int mWaitTime = 20;
	
	
	
	public PlayerStats(Player player) {
		mPlayer = player;
	}
	
	
	public void tick(){
		//damage(5f);
		
	}
	
	public void render(Graphics2D g){
		
//		g.setColor(Color.YELLOW);
//		if(!mPlayer.isDamaged()){
//			if(mDamageRedTime > mHealthScale){
//				if(mWaitTime != 0){
//					mWaitTime--;
//				}
//				if(mWaitTime == 0){
//					mDamageRedTime -= 1f;
//					if(mDamageRedTime <= mHealthScale){
//						mWaitTime = 20;
//					}
//				}
//			}
//			g.fillRect((int)mPlayer.getPos().mXPosition - 21, (int)mPlayer.getPos().mYPosition - 60, (int) mDamageRedTime, 32 - 10);
//		}
//		
		
		g.setColor(Color.RED);
		g.fillRect((int)mPlayer.getPos().mXPosition - 21, (int)mPlayer.getPos().mYPosition - 60, 87, 32 - 10);
		g.setColor(Color.GREEN);
		g.fillRect((int)mPlayer.getPos().mXPosition - 21, (int)mPlayer.getPos().mYPosition - 60, (int)mMaxHealth * (int)mHealthScale - 14, 32 - 10);
		g.drawImage(Assets.getHealthBar(), (int)mPlayer.getPos().mXPosition - 27, (int)mPlayer.getPos().mYPosition - 70, 32*3, 32,null);
	}
	
	
	public void damage(float amount){
		if(mHealthScale > 0 && !mPlayer.isDamaged()){
			mHealthScale -= amount;
			mPlayer.setDamaged(true);
			System.out.println("Player took damage");
			
		}else{
			System.out.println("Player took NO damage");
		}
		
	}

	
	
	
}

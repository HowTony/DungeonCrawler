package me.tdl.generator;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import my.project.gop.main.Vector2F;

public class BlockEntity extends Rectangle{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector2F pos;
	private BufferedImage mBlock_image;
	private double mRotation;
	private double mRotationSpeed = 0.8;
	private double mBlockSize = 24;
	
	private boolean mIsAlive;
	private int mLifeTime;
	private boolean mIsDying;
	private float mLifeFade = 1;
	
	
	
	public BlockEntity(Vector2F pos, BufferedImage block_img) {
		this.pos = pos;
		this.mBlock_image = block_img;
		mRotation = new Random().nextInt(180);
		
		mLifeTime = new Random().nextInt(500);
		if(mLifeTime < 300){
			mLifeTime = new Random().nextInt(500);
		}
		setBounds((int)pos.mXPosition, (int)pos.mYPosition, (int)mBlockSize, (int)mBlockSize);
		mIsAlive = true;
	}
	
	
	public void tick(double deltaTime){
		if(mIsAlive){
		
			setBounds((int)pos.mXPosition, (int)pos.mYPosition, (int)mBlockSize, (int)mBlockSize);
			mRotation -= mRotationSpeed;
			
			if(!mIsDying){
				if(mLifeTime != 0){
					mLifeTime--;
				}
				if(mLifeTime == 0){
					mIsDying = true;
				}
			}
			if(mIsDying){
				if(mLifeFade != 0.000010000){
					mLifeFade-= 0.01;
				}
				
				if(mLifeFade < 0.8){
					mBlockSize-= 0.2;
					pos.mXPosition += 0.1;
					pos.mYPosition += 0.1;
				}
				
				if(mLifeFade <= 0.000010000){
					//World.removeDropedEntity(this);
					mIsAlive = false;
				}
			}
		}
	}
	
	public void render(Graphics2D g){
		if(mIsAlive){
			if(mIsDying){
				g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, mLifeFade));
			}	
		}
		
		
		g.rotate(Math.toRadians(mRotation),
				pos.getWorldLocation().mXPosition + mBlockSize / 2, 
				pos.getWorldLocation().mYPosition + mBlockSize / 2);
		//############################################
		
		g.drawImage(mBlock_image, 
				(int)pos.getWorldLocation().mXPosition, 
				(int)pos.getWorldLocation().mYPosition, 
				(int)mBlockSize, (int)mBlockSize, null);
		
		
		g.drawRect(
				(int)pos.getWorldLocation().mXPosition, 
				(int)pos.getWorldLocation().mYPosition, 
				(int)mBlockSize, (int)mBlockSize);
		
		
		//############################################
		g.rotate(-Math.toRadians(mRotation),
				pos.getWorldLocation().mXPosition + mBlockSize / 2, 
				pos.getWorldLocation().mYPosition + mBlockSize / 2);
		
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		
	}


	public void setAlive(boolean b) {
		this.mIsAlive = b;
	}
	
	
	
	
	
	
	
	
	

}

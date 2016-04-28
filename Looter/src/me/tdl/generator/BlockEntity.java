package me.tdl.generator;

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
	
	
	
	public BlockEntity(Vector2F pos, BufferedImage block_img) {
		this.pos = pos;
		this.mBlock_image = block_img;
		mRotation = new Random().nextInt(180);
		setBounds((int)pos.mXPosition, (int)pos.mYPosition, (int)mBlockSize, (int)mBlockSize);
	}
	
	
	public void tick(double deltaTime){
		setBounds((int)pos.mXPosition, (int)pos.mYPosition, (int)mBlockSize, (int)mBlockSize);
		mRotation -= mRotationSpeed;
	}
	
	public void render(Graphics2D g){
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
		
		
		
	}
	
	
	
	
	
	
	
	
	

}

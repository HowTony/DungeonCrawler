package me.tdl.gamestate;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import me.tdl.main.Assets;
import me.tdl.managers.Mousemanager;
import my.project.gop.main.Vector2F;

public class GameStateButton extends Rectangle{

	private static final long serialVersionUID = 1L;
	private Vector2F mPosition = new Vector2F();
	private GameState mGamesState;
	private GameStateManager mGameStateManagerGSB;
	private boolean mIsHeldOver;
	private int mWidth = 32*6;
	private int mHeight = 64;
	private BufferedImage mButtonDefaultImage;
	private String mButtonMessage;
	
	
	
	public GameStateButton(float xPos, float yPos, GameState gs, GameStateManager gsm, String button_msg) {
		this.mGamesState = gs;
		this.mGameStateManagerGSB = gsm;
		this.mPosition.mXPosition = xPos;
		this.mPosition.mYPosition = yPos;
		this.mButtonMessage = button_msg;
		setBounds((int)mPosition.mXPosition, (int)mPosition.mYPosition, mWidth, mHeight);
		mButtonDefaultImage = Assets.getButtonNotHeldover();
		
		
	}
	
	public GameStateButton(float xPos, float yPos, String button_msg){
		this.mPosition.mXPosition = xPos;
		this.mPosition.mYPosition = yPos;
		this.mButtonMessage = button_msg;
		setBounds((int)mPosition.mXPosition, (int)mPosition.mYPosition, mWidth, mHeight);
		mButtonDefaultImage = Assets.getButtonNotHeldover();
	}
	
	public void tick(){
		setBounds((int)mPosition.mXPosition, (int)mPosition.mYPosition, mWidth, mHeight);
		
		
		if(Mousemanager.mMouse != null){
			if(getBounds().contains(Mousemanager.mMouse)){
				mIsHeldOver = true;
			}else{
				mIsHeldOver = false;
			}
		}
			
		if(mIsHeldOver){
			if(mButtonDefaultImage != Assets.getButtonHeldover()){
				mButtonDefaultImage = Assets.getButtonHeldover();
			}
		}else{
			if(mButtonDefaultImage != Assets.getButtonNotHeldover()){
				mButtonDefaultImage = Assets.getButtonNotHeldover();
			}
		}
		
		if(mGamesState != null){
			if(mIsHeldOver){
				if(isPressed()){
					mGameStateManagerGSB.s_States.push(mGamesState);
					mGameStateManagerGSB.s_States.peek().init();
					mIsHeldOver = false;
					Mousemanager.s_Pressed = false;
					
				}
			}
		}
	}
	
	Font mFont = new Font("Serif", 10, 30);
	
	public void render(Graphics2D g){
		g.drawImage(mButtonDefaultImage, (int)mPosition.mXPosition, (int)mPosition.mYPosition, mWidth, mHeight, null);
		
		g.setFont(mFont);
		AffineTransform at = new AffineTransform();
		FontRenderContext frc = new FontRenderContext(at, true, true);
		int tw = (int) mFont.getStringBounds(mButtonMessage, frc).getWidth();
		g.drawString(mButtonMessage, mPosition.mXPosition + mWidth / 2 - tw / 2, mPosition.mYPosition + mHeight / 2 + 8);
		
	}
	
	
	public boolean isHeldOver(){
		return mIsHeldOver;
	}
	
	public boolean isPressed(){
		return Mousemanager.s_Pressed;
	}
	

}

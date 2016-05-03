package me.tdl.MoveableObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import me.tdl.generator.World;
import me.tdl.main.Animator;
import me.tdl.main.Assets;
import me.tdl.main.Check;
import me.tdl.main.Main;
import me.tdl.managers.GUImanager;
import me.tdl.managers.HUDmanager;
import me.tdl.managers.Mousemanager;
import my.project.gop.main.Vector2F;

public class Player implements KeyListener {

	Vector2F pos;
	private World mWorld;
	private int mWidth = 32;
	private int mHeight = 32;
	private int mScale = 2;
	
	Color mColorTranslucent = new Color(0, 0, 0, 0);

	private static boolean mMovingUp, mMovingDown, mMovingLeft, mMovingRight, mIsRunning;
	
	private final float RUN_SPEED = 48;
	private float mCurrentMoveSpeed = 4 * 32F;
	private float mFixDeltaTime = 1.5F / 60F;
	private final float SLOWDOWN = 3F;
	
	Mousemanager mPlayerMM = new Mousemanager();

	private float mStartSpeedUp = 0;
	private float mStartSpeedDown = 0;
	private float mStartSpeedLeft = 0;
	private float mStartSpeedRight = 0;
	
	private long mAnimationSpeed = 120;
	
	private HUDmanager mHud;
	private GUImanager mGui;
	
	private static boolean mMoving;
	private static boolean s_Spawned;
	
	
	//rendering
	private int mRenderDistanceWidth = 64;
	private int mRenderDistanceHeight = 28;
	public static Rectangle s_Render;
	
	
	//Debug
	private static boolean s_Debug = false;
	
	//Button
	
	private int animationState = 0;
	private ArrayList<BufferedImage> mAnimationUp;
	Animator ani_up;
	private ArrayList<BufferedImage> mAnimationDown;
	Animator ani_down;
	private ArrayList<BufferedImage> mAnimationLeft;
	Animator ani_left;
	private ArrayList<BufferedImage> mAnimationRight;
	Animator ani_right;
	private ArrayList<BufferedImage> mAnimationIdle;
	Animator ani_idle;
	

	public Player() {
		/*
		 * set pos to put player in the middle of screen.
		 */
		pos = new Vector2F(Main.mWidth / 2 - mWidth / 2, Main.mHeight / 2 - mHeight / 2);
	

	
	}

	public void init(World world) {
		mGui = new GUImanager();
		mHud = new HUDmanager(mWorld);
		this.mWorld = world;
		
		
		System.out.println(mWorld.getWorldName() + "");
		
		
		s_Render = new Rectangle(((int)pos.mXPosition), (int)pos.mYPosition, mRenderDistanceWidth * 32, mRenderDistanceHeight * 32);

		mAnimationDown = new ArrayList<BufferedImage>();
		mAnimationUp = new ArrayList<BufferedImage>();
		mAnimationLeft = new ArrayList<BufferedImage>();
		mAnimationRight = new ArrayList<BufferedImage>();
		mAnimationIdle = new ArrayList<BufferedImage>();

		mAnimationDown.add(Assets.mPlayer.getTile(0, 0, 16, 16));
		mAnimationDown.add(Assets.mPlayer.getTile(16, 0, 16, 16));
		mAnimationDown.add(Assets.mPlayer.getTile(32, 0, 16, 16));

		mAnimationUp.add(Assets.mPlayer.getTile(0, 16, 16, 16));
		mAnimationUp.add(Assets.mPlayer.getTile(16, 16, 16, 16));
		mAnimationUp.add(Assets.mPlayer.getTile(32, 16, 16, 16));

		mAnimationRight.add(Assets.mPlayer.getTile(0, 32, 16, 16));
		mAnimationRight.add(Assets.mPlayer.getTile(16, 32, 16, 16));
		mAnimationRight.add(Assets.mPlayer.getTile(32, 32, 16, 16));

		mAnimationLeft.add(Assets.mPlayer.getTile(0, 48, 16, 16));
		mAnimationLeft.add(Assets.mPlayer.getTile(16, 48, 16, 16));
		mAnimationLeft.add(Assets.mPlayer.getTile(32, 48, 16, 16));
		
		mAnimationIdle.add(Assets.mPlayer.getTile(16, 0, 16, 16));

		// DOWN
		ani_down = new Animator(mAnimationDown);
		ani_down.setSpeed(mAnimationSpeed);
		ani_down.play();

		// UP
		ani_up = new Animator(mAnimationUp);
		ani_up.setSpeed(mAnimationSpeed);// in miliseconds
		ani_up.play();

		// LEFT
		ani_left = new Animator(mAnimationLeft);
		ani_left.setSpeed(mAnimationSpeed);
		ani_left.play();

		// RIGHT
		ani_right = new Animator(mAnimationRight);
		ani_right.setSpeed(mAnimationSpeed);
		ani_right.play();

		// IDLE
		ani_idle = new Animator(mAnimationIdle);
		ani_idle.setSpeed(mAnimationSpeed);
		ani_idle.play();
		
		s_Spawned = true;
		
	}

	public void tick(double deltaTime) {
		
		mPlayerMM.tick();
		
		s_Render = new Rectangle(
				(int) (pos.mXPosition - pos.getWorldLocation().mXPosition + pos.mXPosition - mRenderDistanceWidth * 32 / 2 + mWidth / 2),
				(int) (pos.mYPosition - pos.getWorldLocation().mYPosition + pos.mYPosition - mRenderDistanceHeight* 32 / 2 + mHeight / 2),
				mRenderDistanceWidth * 32,
				mRenderDistanceHeight * 32);
		
		float moveAmountUP = (float) (mStartSpeedUp * mFixDeltaTime);
		float moveAmountDOWN = (float) (mStartSpeedDown * mFixDeltaTime);
		float moveAmountLEFT = (float) (mStartSpeedLeft * mFixDeltaTime);
		float moveAmountRIGHT = (float) (mStartSpeedRight * mFixDeltaTime);

		if (mMovingUp) {
			moveMapUp(moveAmountUP);
			animationState = 0;
		} else {
			moveMapUpGlide(moveAmountUP);
		}
		if (mMovingDown) {
			moveMapDown(moveAmountDOWN);
			animationState = 1;
		} else {
			moveMapDownGlide(moveAmountDOWN);
		}
		if (mMovingLeft) {
			moveMapLeft(moveAmountLEFT);
			animationState = 2;
		} else {
			moveMapLeftGlide(moveAmountLEFT);
		}
		if (mMovingRight) {
			moveMapRight(moveAmountRIGHT);
			animationState = 3;
		} else {
			moveMapRightGlide(moveAmountRIGHT);
		}

		if (!mMovingUp && !mMovingDown && !mMovingRight && !mMovingLeft) {
			/*
			 * standing still
			 * 
			 */
			animationState = 4;
			if(mMoving){
				mMoving = false;
			}
		}
		
		if(mIsRunning){
			if(mAnimationSpeed != 120){
				mAnimationSpeed = 120;
				ani_down.setSpeed(mAnimationSpeed);
				ani_up.setSpeed(mAnimationSpeed);
				ani_left.setSpeed(mAnimationSpeed);
				ani_right.setSpeed(mAnimationSpeed);
				ani_idle.setSpeed(mAnimationSpeed);
				mCurrentMoveSpeed += RUN_SPEED;
				
			}
		}else{
			if(mAnimationSpeed != 180){
				mAnimationSpeed = 180;
				ani_down.setSpeed(mAnimationSpeed);
				ani_up.setSpeed(mAnimationSpeed);
				ani_left.setSpeed(mAnimationSpeed);
				ani_right.setSpeed(mAnimationSpeed);
				ani_idle.setSpeed(mAnimationSpeed);
				mCurrentMoveSpeed -= RUN_SPEED;
				
			}
		}
	}

	/*
	 * public void playerMoveCode(){ if (!mapMove) {
	 * 
	 * if (up) {
	 * 
	 * if (!Check.CollisionPlayerBlock(
	 * 
	 * new Point((int) (pos.mXPosition + GameLoop.map.mXPosition), (int)
	 * (pos.mYPosition + GameLoop.map.mYPosition - moveAmountUP)),
	 * 
	 * new Point((int) (pos.mXPosition + GameLoop.map.mXPosition + width), (int)
	 * (pos.mYPosition + GameLoop.map.mYPosition - moveAmountUP)))) {
	 * 
	 * if (speedUP < maxSpeed) { speedUP += SLOWDOWN; } else { speedUP =
	 * maxSpeed; }
	 * 
	 * pos.mYPosition -= moveAmountUP;
	 * 
	 * } else { speedUP = 0; }
	 * 
	 * } else {
	 * 
	 * if (!Check.CollisionPlayerBlock(
	 * 
	 * new Point((int) (pos.mXPosition + GameLoop.map.mXPosition), (int)
	 * (pos.mYPosition + GameLoop.map.mYPosition - moveAmountUP)),
	 * 
	 * new Point((int) (pos.mXPosition + GameLoop.map.mXPosition + width), (int)
	 * (pos.mYPosition + GameLoop.map.mYPosition - moveAmountUP)))) {
	 * 
	 * if (speedUP != 0) { speedUP -= SLOWDOWN;
	 * 
	 * if (speedUP < 0) { speedUP = 0; } }
	 * 
	 * pos.mYPosition -= moveAmountUP;
	 * 
	 * } else { speedUP = 0; }
	 * 
	 * } if (down) {
	 * 
	 * if (!Check.CollisionPlayerBlock(
	 * 
	 * new Point((int) (pos.mXPosition + GameLoop.map.mXPosition), (int)
	 * (pos.mYPosition + GameLoop.map.mYPosition + height + moveAmountDOWN)),
	 * 
	 * new Point((int) (pos.mXPosition + GameLoop.map.mXPosition + width), (int)
	 * (pos.mYPosition + GameLoop.map.mYPosition + height + moveAmountDOWN)))) {
	 * 
	 * if (speedDOWN < maxSpeed) { speedDOWN += SLOWDOWN; } else { speedDOWN =
	 * maxSpeed;
	 * 
	 * pos.mYPosition += moveAmountDOWN; } } else { speedDOWN = 0; }
	 * 
	 * } else {
	 * 
	 * if (!Check.CollisionPlayerBlock(
	 * 
	 * new Point((int) (pos.mXPosition + GameLoop.map.mXPosition), (int)
	 * (pos.mYPosition + GameLoop.map.mYPosition + height + moveAmountDOWN)),
	 * 
	 * new Point((int) (pos.mXPosition + GameLoop.map.mXPosition + width), (int)
	 * (pos.mYPosition + GameLoop.map.mYPosition + height + moveAmountDOWN)))) {
	 * 
	 * if (speedDOWN != 0) { speedDOWN -= SLOWDOWN;
	 * 
	 * if (speedDOWN < 0) { speedDOWN = 0; } }
	 * 
	 * pos.mYPosition += moveAmountDOWN;
	 * 
	 * } else { speedDOWN = 0;
	 * 
	 * }
	 * 
	 * }
	 * 
	 * if (left) {
	 * 
	 * if (!Check.CollisionPlayerBlock( new Point((int) (pos.mXPosition +
	 * GameLoop.map.mXPosition - moveAmountLEFT), (int) (pos.mYPosition +
	 * GameLoop.map.mYPosition + height)),
	 * 
	 * new Point((int) (pos.mXPosition + GameLoop.map.mXPosition -
	 * moveAmountLEFT), (int) (pos.mYPosition + GameLoop.map.mYPosition)))) {
	 * 
	 * if (speedLEFT < maxSpeed) { speedLEFT += SLOWDOWN; } else { speedLEFT =
	 * maxSpeed; }
	 * 
	 * pos.mXPosition -= moveAmountLEFT;
	 * 
	 * } else { speedLEFT = 0; }
	 * 
	 * } else {
	 * 
	 * if (!Check.CollisionPlayerBlock( new Point((int) (pos.mXPosition +
	 * GameLoop.map.mXPosition - moveAmountLEFT), (int) (pos.mYPosition +
	 * GameLoop.map.mYPosition + height)),
	 * 
	 * new Point((int) (pos.mXPosition + GameLoop.map.mXPosition -
	 * moveAmountLEFT), (int) (pos.mYPosition + GameLoop.map.mYPosition)))) {
	 * 
	 * if (speedLEFT != 0) { speedLEFT -= SLOWDOWN;
	 * 
	 * if (speedLEFT < 0) { speedLEFT = 0; } } else { speedLEFT = 0; } }
	 * 
	 * pos.mXPosition -= moveAmountLEFT; } if (right) {
	 * 
	 * if (!Check.CollisionPlayerBlock( new Point((int) (pos.mXPosition +
	 * GameLoop.map.mXPosition + width + moveAmountRIGHT), (int) (pos.mYPosition
	 * + GameLoop.map.mYPosition)),
	 * 
	 * new Point((int) (pos.mXPosition + GameLoop.map.mXPosition + width +
	 * moveAmountRIGHT), (int) (pos.mYPosition + GameLoop.map.mYPosition +
	 * height)))) {
	 * 
	 * if (speedRIGHT < maxSpeed) { speedRIGHT += SLOWDOWN; } else { speedRIGHT
	 * = maxSpeed; }
	 * 
	 * pos.mXPosition += moveAmountRIGHT; } else { speedRIGHT = 0; }
	 * 
	 * } else {
	 * 
	 * if (!Check.CollisionPlayerBlock( new Point((int) (pos.mXPosition +
	 * GameLoop.map.mXPosition + width + moveAmountRIGHT), (int) (pos.mYPosition
	 * + GameLoop.map.mYPosition)),
	 * 
	 * new Point((int) (pos.mXPosition + GameLoop.map.mXPosition + width +
	 * moveAmountRIGHT), (int) (pos.mYPosition + GameLoop.map.mYPosition +
	 * height)))) {
	 * 
	 * if (speedRIGHT != 0) { speedRIGHT -= SLOWDOWN;
	 * 
	 * if (speedRIGHT < 0) { speedRIGHT = 0; } }
	 * 
	 * pos.mXPosition += moveAmountRIGHT; } else { speedRIGHT = 0; } }
	 * 
	 * 
	 * }
	 * 
	 */

	public void moveMapUp(float speed) {
		if (!Check.CollisionPlayerBlock(

				new Point((int) (pos.mXPosition + mWorld.getWorldXpos()),
						(int) (pos.mYPosition + mWorld.getWorldYpos() - speed)),

				new Point((int) (pos.mXPosition + mWorld.getWorldXpos() + mWidth),
						(int) (pos.mYPosition + mWorld.getWorldYpos() - speed)))) {

			if (mStartSpeedUp < mCurrentMoveSpeed) {
				mStartSpeedUp += SLOWDOWN;
			} else {
				mStartSpeedUp = mCurrentMoveSpeed;
			}

			mWorld.s_MapPosition.mYPosition -= speed;
		} else {
			mStartSpeedUp = 0;
		}
	}
	
	
	public void moveMapUpGlide(float speed) {
		if (!Check.CollisionPlayerBlock(

				new Point((int) (pos.mXPosition + mWorld.getWorldXpos()),
						(int) (pos.mYPosition + mWorld.getWorldYpos() - speed)),

				new Point((int) (pos.mXPosition + mWorld.getWorldXpos() + mWidth),
						(int) (pos.mYPosition + mWorld.getWorldYpos() - speed)))) {

			if (mStartSpeedUp != 0) {
				mStartSpeedUp -= SLOWDOWN;

				if (mStartSpeedUp < 0) {
					mStartSpeedUp = 0;
				}
			}

			mWorld.s_MapPosition.mYPosition -= speed;

		} else {
			mStartSpeedUp = 0;
		}
	}

	public void moveMapDown(float speed) {
		if (!Check.CollisionPlayerBlock(

				new Point((int) (pos.mXPosition + mWorld.getWorldXpos()),
						(int) (pos.mYPosition + mWorld.getWorldYpos() + mHeight + speed)),

				new Point((int) (pos.mXPosition + mWorld.getWorldXpos() + mWidth),
						(int) (pos.mYPosition + mWorld.getWorldYpos() + mHeight + speed)))) {

			if (mStartSpeedDown < mCurrentMoveSpeed) {
				mStartSpeedDown += SLOWDOWN;
			} else {
				mStartSpeedDown = mCurrentMoveSpeed;
			}

			mWorld.s_MapPosition.mYPosition += speed;
		} else {
			mStartSpeedDown = 0;
		}
	}

	public void moveMapDownGlide(float speed) {
		if (!Check.CollisionPlayerBlock(

				new Point((int) (pos.mXPosition + mWorld.getWorldXpos()),
						(int) (pos.mYPosition + mWorld.getWorldYpos() + mHeight + speed)),

				new Point((int) (pos.mXPosition + mWorld.getWorldXpos() + mWidth),
						(int) (pos.mYPosition + mWorld.getWorldYpos() + mHeight + speed)))) {

			if (mStartSpeedDown != 0) {
				mStartSpeedDown -= SLOWDOWN;

				if (mStartSpeedDown < 0) {
					mStartSpeedDown = 0;
				}
			}

			mWorld.s_MapPosition.mYPosition += speed;

		} else {
			mStartSpeedDown = 0;
		}
	}

	public void moveMapLeft(float speed) {
		if (!Check.CollisionPlayerBlock(new Point((int) (pos.mXPosition + mWorld.getWorldXpos() - speed),
				(int) (pos.mYPosition + mWorld.getWorldYpos() + mHeight)),

				new Point((int) (pos.mXPosition + mWorld.getWorldXpos() - speed),
						(int) (pos.mYPosition + mWorld.getWorldYpos())))) {

			if (mStartSpeedLeft < mCurrentMoveSpeed) {
				mStartSpeedLeft += SLOWDOWN;
			} else {
				mStartSpeedLeft = mCurrentMoveSpeed;
			}

			mWorld.s_MapPosition.mXPosition -= speed;

		} else {
			mStartSpeedLeft = 0;
		}
	}

	public void moveMapLeftGlide(float speed) {
		if (!Check.CollisionPlayerBlock(new Point((int) (pos.mXPosition + mWorld.getWorldXpos() - speed),
				(int) (pos.mYPosition + mWorld.getWorldYpos() + mHeight)),

				new Point((int) (pos.mXPosition + mWorld.getWorldXpos() - speed),
						(int) (pos.mYPosition + mWorld.getWorldYpos())))) {

			if (mStartSpeedLeft != 0) {
				mStartSpeedLeft -= SLOWDOWN;

				if (mStartSpeedLeft < 0) {
					mStartSpeedLeft = 0;
				}
			}

			mWorld.s_MapPosition.mXPosition -= speed;

		} else {
			mStartSpeedLeft = 0;
		}

	}

	public void moveMapRight(float speed) {
		if (!Check.CollisionPlayerBlock(new Point((int) (pos.mXPosition + mWorld.getWorldXpos() + mWidth + speed),
				(int) (pos.mYPosition + mWorld.getWorldYpos())),

				new Point((int) (pos.mXPosition + mWorld.getWorldXpos() + mWidth + speed),
						(int) (pos.mYPosition + mWorld.getWorldYpos() + mHeight)))) {

			if (mStartSpeedRight < mCurrentMoveSpeed) {
				mStartSpeedRight += SLOWDOWN;
			} else {
				mStartSpeedRight = mCurrentMoveSpeed;
			}

			mWorld.s_MapPosition.mXPosition += speed;
		} else {
			mStartSpeedRight = 0;
		}
	}

	public void moveMapRightGlide(float speed) {
		if (!Check.CollisionPlayerBlock(new Point((int) (pos.mXPosition + mWorld.getWorldXpos() + mWidth + speed),
				(int) (pos.mYPosition + mWorld.getWorldYpos())),

				new Point((int) (pos.mXPosition + mWorld.getWorldXpos() + mWidth + speed),
						(int) (pos.mYPosition + mWorld.getWorldYpos() + mHeight)))) {

			if (mStartSpeedRight != 0) {
				mStartSpeedRight -= SLOWDOWN;

				if (mStartSpeedRight < 0) {
					mStartSpeedRight = 0;
				}
			}

			mWorld.s_MapPosition.mXPosition += speed;

		} else {
			mStartSpeedRight = 0;
		}
	}

	public void render(Graphics2D g) {
		g.setColor(mColorTranslucent);
		g.fillRect((int) pos.mXPosition, (int) pos.mYPosition, mWidth, mHeight);

		

		if (animationState == 0) {
			// UP
			g.drawImage(ani_up.mSprite, (int) pos.mXPosition - mWidth / 2, (int) pos.mYPosition - mHeight, mWidth * mScale, mHeight * mScale,
					null);
			if (mMovingUp) {
				ani_up.update(System.currentTimeMillis());
			}
		}
		if (animationState == 1) {
			// DOWN
			g.drawImage(ani_down.mSprite, (int) pos.mXPosition - mWidth / 2, (int) pos.mYPosition - mHeight, mWidth * mScale, mHeight * mScale,
					null);
			if (mMovingDown) {
				ani_down.update(System.currentTimeMillis());
			}
		}
		if (animationState == 2) {
			// LEFT
			g.drawImage(ani_left.mSprite, (int) pos.mXPosition - mWidth / 2, (int) pos.mYPosition - mHeight, mWidth * mScale, mHeight * mScale,
					null);
			if (mMovingLeft) {
				ani_left.update(System.currentTimeMillis());
			}
		}
		if (animationState == 3) {
			// RIGHT
			g.drawImage(ani_right.mSprite, (int) pos.mXPosition - mWidth / 2, (int) pos.mYPosition - mHeight, mWidth * mScale, mHeight * mScale,
					null);
			if (mMovingRight) {
				ani_right.update(System.currentTimeMillis());
			}

		}
		if (animationState == 4) {
			// IDLE
			g.drawImage(ani_idle.mSprite, (int) pos.mXPosition - mWidth / 2, (int) pos.mYPosition - mHeight, mWidth * mScale, mHeight * mScale,
					null);
			
			ani_idle.update(System.currentTimeMillis());
			
		}
		
		g.drawRect((int)pos.mXPosition - mRenderDistanceWidth * 32 /2 + mWidth / 2, (int)pos.mYPosition - mRenderDistanceHeight * 32 / 2 + mHeight / 2, mRenderDistanceWidth * 32, mRenderDistanceHeight * 32);
		
		mHud.render(g);
		mGui.render(g);
		mPlayerMM.render(g);

		
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W) {
			if(!mMoving){
				mMoving = true;
				
			}
			mMovingUp = true;
		}
		if (key == KeyEvent.VK_S) {
			if(!mMoving){
				mMoving = true;
				
			}
			mMovingDown = true;
		}
		if (key == KeyEvent.VK_A) {
			if(!mMoving){
				mMoving = true;
				
			}
			mMovingLeft = true;
		}
		if (key == KeyEvent.VK_D) {
			if(!mMoving){
				mMoving = true;
				
			}
			mMovingRight = true;
		}
		

		//Debug
		if(key == KeyEvent.VK_F3)
			if(!s_Debug){
				s_Debug = true;
			}else{
				s_Debug = false;
			}
		
		//Sprint
		if(key == KeyEvent.VK_SHIFT){
			mIsRunning = true;
			
		}
		
		
		//Exit game
		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(1);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W) {
			mMovingUp = false;
		}
		if (key == KeyEvent.VK_S) {
			mMovingDown = false;
		}
		if (key == KeyEvent.VK_A) {
			mMovingLeft = false;
		}
		if (key == KeyEvent.VK_D) {
			mMovingRight = false;
		}
		if(key == KeyEvent.VK_SHIFT){
			mIsRunning = false;

		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	
	
	////////////////////////////////////
	///////////////////////////////////
	
	
	public Vector2F getPos() {
		return pos;
	}
	
	public float getMaxSpeed() {
		return mCurrentMoveSpeed;
	}
	
	public float getSLOWDOWN() {
		return SLOWDOWN;
	}
	
	
	public boolean isDebugging(){
		return s_Debug;
	}
	
	public boolean isMoving(){
		return mMoving;
	}
	
	public boolean hasSpawned(){
		return s_Spawned;
	}
	
	
	
}

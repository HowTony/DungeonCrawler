package me.tdl.MoveableObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import me.tdl.gameloop.GameLoop;
import me.tdl.main.Animator;
import me.tdl.main.Assets;
import me.tdl.main.Check;
import me.tdl.main.Main;
import me.tdl.managers.GUImanager;
import me.tdl.managers.HUDmanager;
import my.project.gop.main.Vector2F;

public class Player implements KeyListener {

	Vector2F pos;
	private int width = 32;
	private int height = 32;
	private int mScale = 2;
	
	Color mColorTranslucent = new Color(0, 0, 0, 0);

	private static boolean up, down, left, right;
	private float maxSpeed = 3 * 32F;
	private float fixDT = 1.5F / 60F;

	private final float SLOWDOWN = 3F;

	private float speedUP = 0;
	private float speedDOWN = 0;
	private float speedLEFT = 0;
	private float speedRIGHT = 0;
	
	private HUDmanager mHud;
	private GUImanager mGui;
	
	
	//rendering
	private int mRenderDistanceWidth = 64;
	private int mRenderDistanceHeight = 28;
	public static Rectangle mRender;
	
	
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
		pos = new Vector2F(Main.mWidth / 2 - width / 2, Main.mHeight / 2 - height / 2);
		mGui = new GUImanager();
		mHud = new HUDmanager(this);

		
		// TODO Auto-generated constructor stub
	}

	public void init() {
		
		mRender = new Rectangle(((int)pos.mXPosition), (int)pos.mYPosition, mRenderDistanceWidth * 32, mRenderDistanceHeight * 32);

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
		ani_down.setSpeed(140);
		ani_down.play();

		// UP
		ani_up = new Animator(mAnimationUp);
		ani_up.setSpeed(140);// in miliseconds
		ani_up.play();

		// LEFT
		ani_left = new Animator(mAnimationLeft);
		ani_left.setSpeed(140);
		ani_left.play();

		// RIGHT
		ani_right = new Animator(mAnimationRight);
		ani_right.setSpeed(140);
		ani_right.play();

		// IDLE
		ani_idle = new Animator(mAnimationIdle);
		ani_idle.setSpeed(140);
		ani_idle.play();

	}

	public void tick(double deltaTime) {
		
		mRender = new Rectangle(
				(int) (pos.mXPosition - pos.getWorldLocation().mXPosition + pos.mXPosition - mRenderDistanceWidth * 32 / 2 + width / 2),
				(int) (pos.mYPosition - pos.getWorldLocation().mYPosition + pos.mYPosition - mRenderDistanceHeight* 32 / 2 + height / 2),
				mRenderDistanceWidth * 32,
				mRenderDistanceHeight * 32);
		
		float moveAmountUP = (float) (speedUP * fixDT);
		float moveAmountDOWN = (float) (speedDOWN * fixDT);
		float moveAmountLEFT = (float) (speedLEFT * fixDT);
		float moveAmountRIGHT = (float) (speedRIGHT * fixDT);

		if (up) {
			moveMapUp(moveAmountUP);
			animationState = 0;
		} else {
			moveMapUpGlide(moveAmountUP);
		}
		if (down) {
			moveMapDown(moveAmountDOWN);
			animationState = 1;
		} else {
			moveMapDownGlide(moveAmountDOWN);
		}
		if (left) {
			moveMapLeft(moveAmountLEFT);
			animationState = 2;
		} else {
			moveMapLeftGlide(moveAmountLEFT);
		}
		if (right) {
			moveMapRight(moveAmountRIGHT);
			animationState = 3;
		} else {
			moveMapRightGlide(moveAmountRIGHT);
		}

		if (!up && !down && !right && !left) {
			/*
			 * standing still
			 * 
			 */
			animationState = 4;
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

				new Point((int) (pos.mXPosition + GameLoop.map.mXPosition),
						(int) (pos.mYPosition + GameLoop.map.mYPosition - speed)),

				new Point((int) (pos.mXPosition + GameLoop.map.mXPosition + width),
						(int) (pos.mYPosition + GameLoop.map.mYPosition - speed)))) {

			if (speedUP < maxSpeed) {
				speedUP += SLOWDOWN;
			} else {
				speedUP = maxSpeed;
			}

			GameLoop.map.mYPosition -= speed;
		} else {
			speedUP = 0;
		}
	}

	public void moveMapUpGlide(float speed) {
		if (!Check.CollisionPlayerBlock(

				new Point((int) (pos.mXPosition + GameLoop.map.mXPosition),
						(int) (pos.mYPosition + GameLoop.map.mYPosition - speed)),

				new Point((int) (pos.mXPosition + GameLoop.map.mXPosition + width),
						(int) (pos.mYPosition + GameLoop.map.mYPosition - speed)))) {

			if (speedUP != 0) {
				speedUP -= SLOWDOWN;

				if (speedUP < 0) {
					speedUP = 0;
				}
			}

			GameLoop.map.mYPosition -= speed;

		} else {
			speedUP = 0;
		}
	}

	public void moveMapDown(float speed) {
		if (!Check.CollisionPlayerBlock(

				new Point((int) (pos.mXPosition + GameLoop.map.mXPosition),
						(int) (pos.mYPosition + GameLoop.map.mYPosition + height + speed)),

				new Point((int) (pos.mXPosition + GameLoop.map.mXPosition + width),
						(int) (pos.mYPosition + GameLoop.map.mYPosition + height + speed)))) {

			if (speedDOWN < maxSpeed) {
				speedDOWN += SLOWDOWN;
			} else {
				speedDOWN = maxSpeed;
			}

			GameLoop.map.mYPosition += speed;
		} else {
			speedDOWN = 0;
		}
	}

	public void moveMapDownGlide(float speed) {
		if (!Check.CollisionPlayerBlock(

				new Point((int) (pos.mXPosition + GameLoop.map.mXPosition),
						(int) (pos.mYPosition + GameLoop.map.mYPosition + height + speed)),

				new Point((int) (pos.mXPosition + GameLoop.map.mXPosition + width),
						(int) (pos.mYPosition + GameLoop.map.mYPosition + height + speed)))) {

			if (speedDOWN != 0) {
				speedDOWN -= SLOWDOWN;

				if (speedDOWN < 0) {
					speedDOWN = 0;
				}
			}

			GameLoop.map.mYPosition += speed;

		} else {
			speedDOWN = 0;
		}
	}

	public void moveMapLeft(float speed) {
		if (!Check.CollisionPlayerBlock(new Point((int) (pos.mXPosition + GameLoop.map.mXPosition - speed),
				(int) (pos.mYPosition + GameLoop.map.mYPosition + height)),

				new Point((int) (pos.mXPosition + GameLoop.map.mXPosition - speed),
						(int) (pos.mYPosition + GameLoop.map.mYPosition)))) {

			if (speedLEFT < maxSpeed) {
				speedLEFT += SLOWDOWN;
			} else {
				speedLEFT = maxSpeed;
			}

			GameLoop.map.mXPosition -= speed;

		} else {
			speedLEFT = 0;
		}
	}

	public void moveMapLeftGlide(float speed) {
		if (!Check.CollisionPlayerBlock(new Point((int) (pos.mXPosition + GameLoop.map.mXPosition - speed),
				(int) (pos.mYPosition + GameLoop.map.mYPosition + height)),

				new Point((int) (pos.mXPosition + GameLoop.map.mXPosition - speed),
						(int) (pos.mYPosition + GameLoop.map.mYPosition)))) {

			if (speedLEFT != 0) {
				speedLEFT -= SLOWDOWN;

				if (speedLEFT < 0) {
					speedLEFT = 0;
				}
			}

			GameLoop.map.mXPosition -= speed;

		} else {
			speedLEFT = 0;
		}

	}

	public void moveMapRight(float speed) {
		if (!Check.CollisionPlayerBlock(new Point((int) (pos.mXPosition + GameLoop.map.mXPosition + width + speed),
				(int) (pos.mYPosition + GameLoop.map.mYPosition)),

				new Point((int) (pos.mXPosition + GameLoop.map.mXPosition + width + speed),
						(int) (pos.mYPosition + GameLoop.map.mYPosition + height)))) {

			if (speedRIGHT < maxSpeed) {
				speedRIGHT += SLOWDOWN;
			} else {
				speedRIGHT = maxSpeed;
			}

			GameLoop.map.mXPosition += speed;
		} else {
			speedRIGHT = 0;
		}
	}

	public void moveMapRightGlide(float speed) {
		if (!Check.CollisionPlayerBlock(new Point((int) (pos.mXPosition + GameLoop.map.mXPosition + width + speed),
				(int) (pos.mYPosition + GameLoop.map.mYPosition)),

				new Point((int) (pos.mXPosition + GameLoop.map.mXPosition + width + speed),
						(int) (pos.mYPosition + GameLoop.map.mYPosition + height)))) {

			if (speedRIGHT != 0) {
				speedRIGHT -= SLOWDOWN;

				if (speedRIGHT < 0) {
					speedRIGHT = 0;
				}
			}

			GameLoop.map.mXPosition += speed;

		} else {
			speedRIGHT = 0;
		}
	}

	public void render(Graphics2D g) {
		g.setColor(mColorTranslucent);
		g.fillRect((int) pos.mXPosition, (int) pos.mYPosition, width, height);

		

		if (animationState == 0) {
			// UP
			g.drawImage(ani_up.mSprite, (int) pos.mXPosition - width / 2, (int) pos.mYPosition - height, width * mScale, height * mScale,
					null);
			if (up) {
				ani_up.update(System.currentTimeMillis());
			}
		}
		if (animationState == 1) {
			// DOWN
			g.drawImage(ani_down.mSprite, (int) pos.mXPosition - width / 2, (int) pos.mYPosition - height, width * mScale, height * mScale,
					null);
			if (down) {
				ani_down.update(System.currentTimeMillis());
			}
		}
		if (animationState == 2) {
			// LEFT
			g.drawImage(ani_left.mSprite, (int) pos.mXPosition - width / 2, (int) pos.mYPosition - height, width * mScale, height * mScale,
					null);
			if (left) {
				ani_left.update(System.currentTimeMillis());
			}
		}
		if (animationState == 3) {
			// RIGHT
			g.drawImage(ani_right.mSprite, (int) pos.mXPosition - width / 2, (int) pos.mYPosition - height, width * mScale, height * mScale,
					null);
			if (right) {
				ani_right.update(System.currentTimeMillis());
			}

		}
		if (animationState == 4) {
			// IDLE
			g.drawImage(ani_idle.mSprite, (int) pos.mXPosition - width / 2, (int) pos.mYPosition - height, width * mScale, height * mScale,
					null);
			
			ani_idle.update(System.currentTimeMillis());
			
		}
		
		g.drawRect((int)pos.mXPosition - mRenderDistanceWidth * 32 /2 + width / 2, (int)pos.mYPosition - mRenderDistanceHeight * 32 / 2 + height / 2, mRenderDistanceWidth * 32, mRenderDistanceHeight * 32);
		
		mHud.render(g);
		mGui.render(g);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W) {
			up = true;
		}
		if (key == KeyEvent.VK_S) {
			down = true;
		}
		if (key == KeyEvent.VK_A) {
			left = true;
		}
		if (key == KeyEvent.VK_D) {
			right = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_W) {
			up = false;
		}
		if (key == KeyEvent.VK_S) {
			down = false;
		}
		if (key == KeyEvent.VK_A) {
			left = false;
		}
		if (key == KeyEvent.VK_D) {
			right = false;
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
		return maxSpeed;
	}
	
	public float getSLOWDOWN() {
		return SLOWDOWN;
	}
	
	
	
}

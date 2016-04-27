package me.tdl.main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animator {

	private ArrayList<BufferedImage> mFrames;
	private volatile boolean mRunning = false;
	public BufferedImage mSprite;

	private long mPreviousTime, mSpeed;
	private int mFrameAtPause, mCurrentFrame;

	public Animator(ArrayList<BufferedImage> frames) {
		this.mFrames = frames;
	}

	public void setSpeed(long speed) {
		this.mSpeed = speed;
	}

	public void update(long time) {
		if (mRunning) {
			if (time - mPreviousTime >= mSpeed) {
				mCurrentFrame++;
				try {
					mSprite = mFrames.get(mCurrentFrame);
				} catch (IndexOutOfBoundsException e) {
					reset();
					mSprite = mFrames.get(mCurrentFrame);

				}
				mPreviousTime = time;
			}
		}
	}

	public void play() {
		mRunning = true;
		mPreviousTime = 0;
		mFrameAtPause = 0;
		mCurrentFrame = 0;
	}

	public void stop() {
		mRunning = false;
		mPreviousTime = 0;
		mFrameAtPause = 0;
		mCurrentFrame = 0;
	}

	public void pause() {
		mFrameAtPause = mCurrentFrame;
		mRunning = false;
	}

	public void resume() {
		mCurrentFrame = mFrameAtPause;
	}

	public void reset() {
		mCurrentFrame = 0;
	}

	public boolean isDoneAnimating() {
		if (mCurrentFrame == mFrames.size()) {
			return true;
		} else {
			return false;
		}
	}

}

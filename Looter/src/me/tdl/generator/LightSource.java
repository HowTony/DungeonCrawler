package me.tdl.generator;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import my.project.gop.main.Vector2F;

public class LightSource {

	private Vector2F mLightLocation = new Vector2F();
	private double mLightSize = 48;
	private double mLightDistance;

	private Rectangle mLightDetection;

	public LightSource(float xPos, float yPos, double lightDistance) {
		mLightLocation.mXPosition = xPos;
		mLightLocation.mYPosition = yPos;
		mLightDistance = lightDistance;

	}

	public void tick() {
		mLightDetection = new Rectangle(
				(int) (mLightLocation.mXPosition - 42 * mLightDistance / 2 + mLightSize / 2),
				(int) (mLightLocation.mYPosition - mLightDistance / 2 +  42 * mLightSize / 2), 
				(int) (mLightDistance * 42), (int) (mLightDistance * 42));

	}


	public Vector2F getLightLocation() {
		return mLightLocation;
	}

	public Rectangle getLightDetection() {
		return mLightDetection;
	}

}

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

	public void render(Graphics2D g) {
//		g.fillRect(
//				(int) mLightLocation.getWorldLocation().mXPosition,
//				(int) mLightLocation.getWorldLocation().mYPosition, (int) mLightSize, (int) mLightSize);

//		g.drawRect(
//				(int) (mLightLocation.getWorldLocation().mXPosition - mLightDistance / 2 + mLightSize / 2),
//				(int) (mLightLocation.getWorldLocation().mYPosition - mLightDistance / 2 + mLightSize / 2),
//				(int) mLightDistance, (int) mLightDistance);

		for (Block eachBlock : TileManager.mBlocks) {
			if (eachBlock != null) {
				if (mLightDetection != null) {
					if (mLightDetection.intersects(eachBlock.getBounds())) {
						float distance = (float) Vector2F.getDistanceOnScreen(mLightLocation,eachBlock.getBlockLocation());

						for (int dis = 48; dis < mLightDistance * 42; dis++) {
							if (distance < dis) {
								switch (dis) {
								case 42:
									if(eachBlock.getLightLevel() > 0.20){
										eachBlock.removeShadows(0.045f);
									}
									break;
								case 42 * 2:
									if(eachBlock.getLightLevel() > 0.30){
										eachBlock.removeShadows(0.035f);
									}
									break;
								case 42 * 3:
									if(eachBlock.getLightLevel() > 0.40){
										eachBlock.removeShadows(0.025f);
									}
									break;
								case 42 * 4:
									if(eachBlock.getLightLevel() > 0.50){
										eachBlock.removeShadows(0.015f);
									}
									break;
								case 42 * 5:
									if(eachBlock.getLightLevel() > 0.60){
										eachBlock.removeShadows(0.0090f);
									}
									break;
								case 42 * 6:
									if(eachBlock.getLightLevel() > 0.70){
										eachBlock.removeShadows(0.0080f);
									}
									break;
								case 42 * 7:
									if(eachBlock.getLightLevel() > 0.80){
										eachBlock.removeShadows(0.00700f);
									}
									break;
								case 42 * 8:
									if(eachBlock.getLightLevel() > 0.90){
										eachBlock.removeShadows(0.0060f);
									}
									break;

								}
							}
						}
					}
				}
			}
		}
	}

	public Vector2F getLightLocation() {
		return mLightLocation;
	}

	public Rectangle getLightDetection() {
		return mLightDetection;
	}

}

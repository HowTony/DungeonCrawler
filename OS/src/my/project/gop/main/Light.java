package my.project.gop.main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.RadialGradientPaint;


public class Light {

	public Vector2F mLightPos = new Vector2F();
	private int mLightRadius;
	private BufferedImage mImage;
	private int mLightPower;
	
	
	public Light(float xpos, float ypos, int radius, int lightpPower) {
		mLightPos.mXPosition = xpos;
		mLightPos.mYPosition = ypos;
		this.mLightRadius = radius;
		this.mLightPower = lightpPower;
		
		
		Point2D center = new Point(radius, radius);
		Point2D focus = new Point(radius, radius);
		
		float [] distance = {0F, 1F};
		Color [] colorList = {new Color(0,0,0, mLightPower), new Color(0,0,0,0)};
		
		mImage = new BufferedImage(radius * 2, radius * 2, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) mImage.getGraphics();
		
		RadialGradientPaint rgp = new RadialGradientPaint(center, radius, distance, colorList);
		g2.setPaint(rgp);
		g2.fillOval(0, 0, radius * 2, radius * 2);
	}
	
	
	public void render(Graphics2D g){
		g.drawImage(mImage, (int)mLightPos.mXPosition, (int)mLightPos.mYPosition, null);
	}
	
	
}

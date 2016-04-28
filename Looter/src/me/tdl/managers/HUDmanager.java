package me.tdl.managers;

//import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;
//import java.util.ArrayList;

import me.tdl.MoveableObjects.Player;
import me.tdl.main.Main;
import my.project.gop.main.IDGameLoop;
//import my.project.gop.main.Light;
//import my.project.gop.main.Vector2F;
//import my.project.gop.main.loadImageFrom;

public class HUDmanager {
	
	//private Color mColorTranslucent;
	
//	private BufferedImage mLightMap = new BufferedImage(100*32, 100*32, BufferedImage.TYPE_INT_ARGB);
//	private ArrayList<Light> mLights = new ArrayList<Light>();
//	private Vector2F mLightVector = new Vector2F();
	
	//private BufferedImage mLight;
	private Player mPlayer;

	
	
	public HUDmanager(Player player) {
		this.mPlayer = player;
		//mLight = loadImageFrom.LoadImageFrom(Main.class, "Light.png");
	
		
//		addLights();
		
	}
	
//	public void addLights(){
//		mLights.add(new Light(mPlayer.getPos().getWorldLocation().mXPosition, mPlayer.getPos().getWorldLocation().mYPosition,300,240));
//		
//		
//	}
//	
//	public void updateLights(){
//		
//		Graphics2D g2d = null;
//		if(g2d == null){
//			g2d = (Graphics2D) mLightMap.getGraphics();
//		}
//		
//		g2d.setColor(new Color(0, 0, 0, 250));
//		g2d.fillRect(0, 0, mLightMap.getWidth(), mLightMap.getHeight());
//		g2d.setComposite(AlphaComposite.DstOut);
//		
//		for(Light eachLight : mLights){
//			eachLight.render(g2d);
//		}
//		g2d.dispose();
//		
//	}

	public void render(Graphics2D g) {
		
		
		
		//g.drawImage(mLightMap, (int)mLightVector.getWorldLocation().mXPosition, (int)mLightVector.getWorldLocation().mYPosition, null);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Main.mWidth, Main.mHeight / 6);
		g.fillRect(0, 1000, Main.mWidth, Main.mHeight / 6);
		
		g.clipRect(0, 0, Main.mWidth, Main.mHeight);
		//g.drawImage(mLight, 0, 0, Main.mWidth, Main.mHeight, null);
	
	
		g.setColor(Color.WHITE);
		g.drawString("FPS: " + IDGameLoop.mFPS, 100, 100);
		
//		updateLights();


		
		
	
	}

}

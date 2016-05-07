package me.tdl.managers;

//import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
//import java.awt.image.BufferedImage;
//import java.util.ArrayList;
import java.awt.image.BufferedImage;

import me.tdl.MoveableObjects.Player;
import me.tdl.generator.World;
import me.tdl.main.Main;
import my.project.gop.main.IDGameLoop;
//import my.project.gop.main.Light;
//import my.project.gop.main.Vector2F;
import my.project.gop.main.loadImageFrom;

public class HUDmanager {


	// private BufferedImage mLightMap = new BufferedImage(100*32, 100*32,
	// BufferedImage.TYPE_INT_ARGB);
	// private ArrayList<Light> mLights = new ArrayList<Light>();
	// private Vector2F mLightVector = new Vector2F();

	//private BufferedImage mLight;

	private World mWorld;
	
	private static Polygon s_Up;
	private static Polygon s_Down;
	private static Polygon s_Right;
	private static Polygon s_Left;
	
	
	private Color mColorTranslucent = new Color(0, 0, 0, 0);
	
	

	public HUDmanager(World world) {
		this.mWorld = world;
		//mLight = loadImageFrom.LoadImageFrom(Main.class, "Light.png");
		
		makePolygons();

		// addLights();

	}
	
	
	public void makePolygons(){
		//UP
		int[] ux = new int[]{Main.mWidth, Main.mWidth / 2, Main.mWidth / 2, 0};
		int[] uy = new int[]{0, Main.mHeight / 2, Main.mHeight / 2, 0};
		s_Up = new Polygon(ux, uy, ux.length);
		
		//DOWN
		int[] dx = new int[]{Main.mWidth, Main.mWidth / 2, Main.mWidth / 2, 0};
		int[] dy = new int[]{Main.mHeight, Main.mHeight / 2, Main.mHeight / 2, Main.mHeight};
		s_Down = new Polygon(dx, dy, dx.length);
		
		//RIGHT
		int[] rx = new int[]{Main.mWidth, Main.mWidth / 2, Main.mWidth / 2, Main.mWidth};
		int[] ry = new int[]{Main.mHeight, Main.mHeight / 2, Main.mHeight / 2, 0};
		s_Right = new Polygon(rx, ry, rx.length);
		
		//LEFT
		int[] lx = new int[]{0, Main.mWidth / 2, Main.mWidth / 2, 0};
		int[] ly = new int[]{Main.mHeight, Main.mHeight / 2, Main.mHeight / 2, 0};
		s_Left = new Polygon(lx, ly, lx.length);
	}
	
	
	public void drawPolygonsWHITE(Graphics2D g){
		g.setColor(Color.WHITE);
		g.drawPolygon(s_Up);
		g.drawPolygon(s_Down);
		g.drawPolygon(s_Right);
		g.drawPolygon(s_Left);
	}
	
	public void drawPolyonsCLEAR(Graphics2D g){
		g.setColor(mColorTranslucent);
		g.drawPolygon(s_Up);
		g.drawPolygon(s_Down);
		g.drawPolygon(s_Right);
		g.drawPolygon(s_Left);
	}
	

	// public void addLights(){
	// mLights.add(new Light(mPlayer.getPos().getWorldLocation().mXPosition,
	// mPlayer.getPos().getWorldLocation().mYPosition,300,240));
	//
	//
	// }
	//
	// public void updateLights(){
	//
	// Graphics2D g2d = null;
	// if(g2d == null){
	// g2d = (Graphics2D) mLightMap.getGraphics();
	// }
	//
	// g2d.setColor(new Color(0, 0, 0, 250));
	// g2d.fillRect(0, 0, mLightMap.getWidth(), mLightMap.getHeight());
	// g2d.setComposite(AlphaComposite.DstOut);
	//
	// for(Light eachLight : mLights){
	// eachLight.render(g2d);
	// }
	// g2d.dispose();
	//
	// }

	public void render(Graphics2D g) {

		// g.drawImage(mLightMap,
		// (int)mLightVector.getWorldLocation().mXPosition,
		// (int)mLightVector.getWorldLocation().mYPosition, null);
		
		//g.drawImage(mLight, 0, 0, Main.mWidth, Main.mHeight, null);

//		if (mWorld.getPlayer().isDebugging()) {
//			g.setColor(Color.WHITE);
//			g.drawString("[DEBUG]", 20, 20);
//			g.drawString("FPS: " + IDGameLoop.mFPS, 30, 50);
//		}
		// g.drawString("[Map X position]" + mWorld.s_MapPosition.mXPosition,
		// 30, 100);
		// g.drawString("[Map Y position]" + mWorld.s_MapPosition.mYPosition,
		// 30, 150);
		// g.drawString("[Current World Blocks]" +
		// mWorld.getWorldBlocks().getBlocks().size(), 30, 150);
		// g.drawString("[Current Loaded World Blocks]" +
		// mWorld.getWorldBlocks(), 30, 150);
		// g.drawString("[Map Y position]" + mWorld.getWorldYpos(), 20, 60);
		
		
		drawPolyonsCLEAR(g);
		
		
		}
	
	public static Polygon getS_Up() {
		return s_Up;
	}
	
	public static Polygon getS_Down() {
		return s_Down;
	}
	
	public static Polygon getS_Right() {
		return s_Right;
	}
	
	public static Polygon getS_Left() {
		return s_Left;
	}
	
	

	// updateLights();

}

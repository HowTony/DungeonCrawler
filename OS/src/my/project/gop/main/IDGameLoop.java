package my.project.gop.main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class IDGameLoop extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Thread mThread;
	private boolean mRunning;
	
	public static double mFPS = 0.0;
	private int mFramesPerSecond; // frames per second
	private int mTicksPerSecond; // ticks per second

	private int mWidth;
	private int mHeight;

	public Graphics2D mGraphics2D;
	private BufferedImage mImage;

	public static double currentFPS = 120D;

	public IDGameLoop(int width, int height) {
		this.mWidth = width;
		this.mHeight = height;

		setPreferredSize(new Dimension(width, height));
		setFocusable(false);
		requestFocus();

	}
	@Override
	public void addNotify() {
		super.addNotify();
		if (mThread == null) {
			mThread = new Thread(this);
			mThread.start();
		}
	}

	@Override
	public void run() {

		init();

		long lastTime = System.nanoTime(); //
		double nsPerTick = 1000000000D / currentFPS;
		int frames = 0;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();
		double deltaTime = 0;

		while (mRunning) {
			long now = System.nanoTime();
			deltaTime += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = false;

			while (deltaTime >= 1) {
				ticks++;
				/* TICK + DeltaTime */
				tick(deltaTime);
				deltaTime -= 1;
				shouldRender = true;
			}
			
			if(shouldRender){
				frames++;
				render();
			}

			// sleepy
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				mTicksPerSecond = frames;
				mFramesPerSecond = ticks; 
				mFPS = mTicksPerSecond;
				frames = 0;
				ticks = 0;

			}
		}
	}

	public void init() {
		mImage = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_RGB); // draw everything inside the bufferedImage
		mGraphics2D = (Graphics2D) mImage.getGraphics();
		mRunning = true;
	}
	
	// tick updates game and speeds up and physics that are going slow
	public void tick(double deltaTime) { 
		// TODO Auto-generated method stub

	}

	public void render() {
		mGraphics2D.clearRect(0, 0, mWidth, mHeight);
	}
	
	public void clear(){
		Graphics g2 = getGraphics();
		if(mImage != null){
			g2.drawImage(mImage, 0, 0, null);
		}
		g2.dispose();
	}
	

}

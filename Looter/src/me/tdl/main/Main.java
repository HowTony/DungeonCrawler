package me.tdl.main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import me.tdl.MoveableObjects.Player;
import me.tdl.gameloop.GameLoop;
import my.project.gop.main.GameWindow;

/**
 * Created by Tony Howarth on 4/21/2016.
 */
public class Main {
	
	
	
	public static GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	public static int mWidth = gd.getDisplayMode().getWidth();
	public static int mHeight = gd.getDisplayMode().getHeight();
	
    public static void main(String[] args){
    	GameWindow frame = new GameWindow("TheDLooter", mWidth, mHeight);
    	frame.setFullscreen(1);
    	frame.addKeyListener(new Player());
    	frame.add(new GameLoop(mWidth, mHeight));
    	frame.setVisible(true);

    }


}
 
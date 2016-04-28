package me.tdl.main;

import java.awt.Cursor;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;

import me.tdl.MoveableObjects.Player;
import me.tdl.gameloop.GameLoop;
import me.tdl.managers.Mousemanager;
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
    	Toolkit toolkit = Toolkit.getDefaultToolkit();
    	Cursor cursor = toolkit.createCustomCursor(toolkit.getImage(""), new Point(0, 0), "Cursor");
    	frame.setCursor(cursor);
    	
    	frame.addMouseListener(new Mousemanager());
    	frame.addMouseMotionListener(new Mousemanager());
    	frame.addMouseWheelListener(new Mousemanager());
    	
    	frame.addKeyListener(new Player());
    	frame.add(new GameLoop(mWidth, mHeight));
    	frame.setVisible(true);

    }


}
 
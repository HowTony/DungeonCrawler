package me.tdl.managers;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import me.tdl.main.Assets;

public class Mousemanager implements MouseListener, MouseMotionListener, MouseWheelListener {

	private static int mMouseMovedX, mMouseMovedY;
	public static Point mMouse;

	public static boolean s_Pressed;

	public void tick() {
		mMouse = new Point(mMouseMovedX, mMouseMovedY);
	}

	public void render(Graphics2D g) {
		g.fillRect(mMouseMovedX, mMouseMovedY, 4, 4);

		if (s_Pressed) {
			g.drawImage(Assets.getMouse_2(), mMouseMovedX, mMouseMovedY, 32, 32, null);
		} else {
			g.drawImage(Assets.getMouse_1(), mMouseMovedX, mMouseMovedY, 32, 32, null);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			s_Pressed = true;
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			s_Pressed = false;
		}

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mMouseMovedX = e.getX();
		mMouseMovedY = e.getY();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mMouseMovedX = e.getX();
		mMouseMovedY = e.getY();

	}

}
package my.project.gop.main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {

	boolean mFullScreenEnable = false;
	int mFullScreenMode = 0;
	GraphicsDevice mDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[1];

	public GameWindow(String title, int width, int height) {
		setSize(width, height);
		setTitle(title);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

	}

	private void setfullscreen() {
		switch (mFullScreenMode) {
		case 0:
			System.out.println("No Fullscreen");
			break;
		case 1:
			setUndecorated(true);// removes boarder around window
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			break;
		case 2:
			setUndecorated(true);
			mDevice.setFullScreenWindow(this);
			break;
		}
	}

	public void setFullscreen(int FullscreenNewMode) {
		mFullScreenEnable = true;
		if (mFullScreenMode <= 2) {
			this.mFullScreenMode = FullscreenNewMode;
			setfullscreen();
		}

		else {
			System.err.println("Error " + FullscreenNewMode + " is not supported!");
		}
	}
}

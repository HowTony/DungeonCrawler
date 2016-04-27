package my.project.gop.main;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	
	private BufferedImage mSpriteSheet;

	public SpriteSheet() {
		// TODO Auto-generated constructor stub
	}
	
	public void setSpriteSheet(BufferedImage mSpriteSheet) {
		this.mSpriteSheet = mSpriteSheet;
	}
	
	//can change tile size here for 16bit 32bit ect
	public BufferedImage getTile(int xTile, int yTile, int width, int height){
		BufferedImage sprite = mSpriteSheet.getSubimage(xTile, yTile, width, height);
		return sprite;
	}

}

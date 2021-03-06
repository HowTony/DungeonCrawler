package me.tdl.gameloop;

import me.tdl.gamestate.GameStateManager;
import me.tdl.main.Assets;
import my.project.gop.main.IDGameLoop;
import my.project.gop.main.Vector2F;

public class GameLoop extends IDGameLoop {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameStateManager gsm;
	public static Assets s_Assets = new Assets();

	

	public GameLoop(int fwidth, int fheight) {
		super(fwidth, fheight);
	}
	
	
	@Override
	public void init() {
		s_Assets.init();  //makes sure all textures are loaded before rendering them.
		
		gsm = new GameStateManager();
		gsm.init();
		super.init();
	}
	
	@Override
	public void tick(double deltaTime) {
		
		gsm.tick(deltaTime);
	}
	
	@Override
	public void clear() {
		super.clear();
	}
	
	@Override
	public void render() {
		super.render();
		gsm.render(mGraphics2D);
		clear();
	}
	
	

}

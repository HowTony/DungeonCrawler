package me.tdl.gamestates;

import java.awt.Graphics2D;

import me.tdl.gamestate.GameState;
import me.tdl.gamestate.GameStateManager;
import me.tdl.generator.Map;
import my.project.gop.main.SpriteSheet;


public class DungeonLevelLoader extends GameState{
	
	SpriteSheet test = new SpriteSheet();
	Map map;

	public DungeonLevelLoader(GameStateManager gsm) {
		super(gsm);
		
	}

	@Override
	public void init() {
		map = new Map();
		map.init();
		
	}

	@Override
	public void tick(double deltaTime) {
		map.tick(deltaTime);
		
	}

	@Override
	public void render(Graphics2D g) {
//		g.drawImage(test.getTile(0, 0, 16, 16), 0, 0, 64, 64, null);
		map.redner(g);
	}

}

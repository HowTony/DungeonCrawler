package me.tdl.gamestates;

import java.awt.Graphics2D;

import me.tdl.MoveableObjects.Player;
import me.tdl.gamestate.GameState;
import me.tdl.gamestate.GameStateManager;
import me.tdl.generator.World;
import me.tdl.main.Main;

public class DungeonLevelLoader extends GameState{
	
	private World mWorld;

	public DungeonLevelLoader(GameStateManager gsm) {
		super(gsm);	
	}

	@Override
	public void init() {
		mWorld = new World("World");
		mWorld.setSize(100,100);	
		mWorld.addPlayer(new Player());
		
		mWorld.init();
		mWorld.generate("map");
		
	}

	@Override
	public void tick(double deltaTime) {
		mWorld.tick(deltaTime);
		
	}

	@Override
	public void render(Graphics2D g) {
		mWorld.render(g);
		g.clipRect(0, 0, Main.mWidth, Main.mHeight);
	}

}

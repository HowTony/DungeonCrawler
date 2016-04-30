package me.tdl.gamestate;

import java.awt.Graphics2D;

public abstract class GameState {

	public static GameStateManager s_GameStateManager;
	
	public GameState(GameStateManager gsm) {
		this.s_GameStateManager = gsm;
	}
	
	public abstract void init();
	public abstract void tick(double deltaTime);
	public abstract void render(Graphics2D g);


	

}

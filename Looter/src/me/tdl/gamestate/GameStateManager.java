package me.tdl.gamestate;

import java.awt.Graphics2D;
import java.util.Stack;

import me.tdl.gamestates.MenuState;

public class GameStateManager {
	
	public static Stack<GameState> s_States;

	public GameStateManager() {
		s_States = new Stack<GameState>();
		s_States.push(new MenuState(this));
	}
	
	public void tick(double deltaTime){
		s_States.peek().tick(deltaTime);
	}
	
	public void render(Graphics2D g){
		s_States.peek().render(g);
	}
	
	public void init(){
		s_States.peek().init();
	}

}

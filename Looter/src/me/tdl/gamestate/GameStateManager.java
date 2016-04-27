package me.tdl.gamestate;

import java.awt.Graphics2D;
import java.util.Stack;

import me.tdl.gamestates.DungeonLevelLoader;

public class GameStateManager {
	
	public static Stack<GameState> mStates;

	public GameStateManager() {
		mStates = new Stack<GameState>();
		mStates.push(new DungeonLevelLoader(this));
	}
	
	public void tick(double deltaTime){
		mStates.peek().tick(deltaTime);
	}
	
	public void render(Graphics2D g){
		mStates.peek().render(g);
	}
	
	public void init(){
		mStates.peek().init();
	}

}

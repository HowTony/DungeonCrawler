package me.tdl.gamestates;

import java.awt.Graphics2D;

import me.tdl.MoveableObjects.Player;
import me.tdl.gamestate.GameState;
import me.tdl.gamestate.GameStateManager;
import me.tdl.generator.World;
import me.tdl.main.Main;

public class DungeonLevelLoader extends GameState{
	
	public static World mWorld2;
	private String mWorldName;
	private String mMapName;

	public DungeonLevelLoader(GameStateManager gsm) {
		super(gsm);	
	}
	
	public DungeonLevelLoader(GameStateManager gsm, String worldName, String mapName) {
		super(gsm);	
		this.mWorldName = worldName;
		this.mMapName = mapName;
		
	}

	@Override
	public void init() {
		
		if(mWorldName == null){
			mWorldName = "NULL";
			mMapName = "map";
		}
		
		mWorld2 = new World(mWorldName, s_GameStateManager);
		mWorld2.setSize(100,100);	
		
		mWorld2.setWorldSpawn(50, 50);
		
		mWorld2.addPlayer(new Player());
		
		mWorld2.init();
		mWorld2.generate(mMapName);
		
	}

	@Override
	public void tick(double deltaTime) {
		if(mWorld2.hasGenerated()){
			mWorld2.tick(deltaTime);
		}
		
		
	}

	@Override
	public void render(Graphics2D g) {
		
		if(mWorld2.hasGenerated()){
			mWorld2.render(g);
		}
		
		g.clipRect(0, 0, Main.mWidth, Main.mHeight);
	}
	
	

}

package me.tdl.gamestates;

import java.awt.Graphics2D;

import me.tdl.gamestate.GameState;
import me.tdl.gamestate.GameStateButton;
import me.tdl.gamestate.GameStateManager;
import me.tdl.main.Main;
import me.tdl.managers.Mousemanager;



public class MenuState extends GameState{
	
	private GameStateButton mStartGame;
	private GameStateButton mMultiplayer;
	private GameStateButton mOptions;
	private GameStateButton mQuit;
	private Mousemanager mMouse;

	public MenuState(GameStateManager gsm) {
		super(gsm);
		
	}

	@Override
	public void init() {
		mMouse = new Mousemanager();
		mStartGame = new GameStateButton(Main.mWidth / 2 - 100, 200, new DungeonLevelLoader(s_GameStateManager), s_GameStateManager, "Start Game");
		mMultiplayer = new GameStateButton(Main.mWidth / 2 - 100, 300, new DungeonLevelLoader(s_GameStateManager), s_GameStateManager, "Multiplayer");
		mOptions = new GameStateButton(Main.mWidth / 2 - 100, 400, new DungeonLevelLoader(s_GameStateManager), s_GameStateManager, "Game Options");
		mQuit = new GameStateButton(Main.mWidth / 2 - 100, 600, new QuitState(s_GameStateManager), s_GameStateManager, "Quit Game");


	}

	@Override
	public void tick(double deltaTime) {
		mMouse.tick();
		mStartGame.tick();
		mMultiplayer.tick();
		mOptions.tick();
		mQuit.tick();
		
	}

	@Override
	public void render(Graphics2D g) {
		mStartGame.render(g);
		mMultiplayer.render(g);
		mOptions.render(g);
		mQuit.render(g);
		mMouse.render(g);
		g.clipRect(0, 0, Main.mWidth, Main.mHeight);
	}

}

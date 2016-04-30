package me.tdl.gamestates;

import java.awt.Graphics2D;

import me.tdl.gamestate.GameState;
import me.tdl.gamestate.GameStateButton;
import me.tdl.gamestate.GameStateManager;
import me.tdl.main.Main;
import me.tdl.managers.Mousemanager;



public class QuitState extends GameState{
	
	private GameStateButton mYes;
	private GameStateButton mNo;
	
	private Mousemanager mMouse;

	public QuitState(GameStateManager gsm) {
		super(gsm);
		
	}

	@Override
	public void init() {
		mMouse = new Mousemanager();
		mYes = new GameStateButton(Main.mWidth / 3, 200, "Yes");
		mNo = new GameStateButton(Main.mWidth / 3, 300, "No");
	

	}

	@Override
	public void tick(double deltaTime) {
		mMouse.tick();
		mYes.tick();
		mNo.tick();
		
		if(mYes.isHeldOver()){
			if(mYes.isPressed()){
			System.exit(1);
			
			}
		}
		if(mNo.isHeldOver()){
			if(mNo.isPressed()){
				s_GameStateManager.s_States.push(new MenuState(s_GameStateManager));
				s_GameStateManager.s_States.peek();
			
			}
		}
	}

	@Override
	public void render(Graphics2D g) {
		mYes.render(g);
		mNo.render(g);
		mMouse.render(g);
		g.clipRect(0, 0, Main.mWidth, Main.mHeight);
	}

}

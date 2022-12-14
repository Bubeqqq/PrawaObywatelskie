package buba.main.states;

import java.awt.Graphics;

import buba.main.Handler;
import buba.main.input.Button;

public class MenuState extends State{

	private Button startButton, muteButton;
	
	private final int width = 500, height = 250;
	
	//menu button animation
	
	private byte state = 0;
	
	private final byte UP = 0, DOWN = 1, HOVER = 2, UNHOVER = 3;
	
	private final float speed = 1, DEFAULTACCEL = 30;
	
	private float yOffset = 0;
	
	private float accel = DEFAULTACCEL;
	
	private final int defaultY;
	
	private boolean mute = false;
	
	public MenuState(Handler handler) {
		super(handler);

		handler.getLoop().getSound().setVolume(0.5f);
		
		defaultY = handler.getWindowHeight() / 2 - height / 2;
		
		startButton = new Button(handler.getWindowWidth() / 2 - width / 2, handler.getWindowHeight() / 2 - height / 2, width, height, null, handler) {
			@Override
			public void onClick(){
				State.setCurrentState(handler.getLoop().getSelectState());
			}
		};
		
		muteButton = new Button(100, handler.getWindowHeight() - 300, 200, 200, null, handler) {
			@Override
			public void onClick(){
				setMute(!isMute());
			}
		};
	}

	@Override
	public void tick() {
		//start Button
		
		muteButton.tick();
		startButton.tick();
		
		if(startButton.getBounds().contains(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY())) {
			state = HOVER;
		}
		
		if(state == UP) {
			yOffset += speed;
			accel -= speed;
			
			if(accel < 1) {
				accel = DEFAULTACCEL;
				state = DOWN;
			}
		}else if(state == DOWN) {
			yOffset -= speed;
			accel -= speed;
			
			if(accel < 1) {
				accel = DEFAULTACCEL;
				state = UP;
			}
		}else if(state == HOVER) {
			if(yOffset <= -DEFAULTACCEL * 2) {
				state = UNHOVER;
			}else
				yOffset -= speed * 4;
		}else if(state == UNHOVER) {
			yOffset += speed * 4;
			if(yOffset >= 0) {
				state = UP;
				accel = DEFAULTACCEL;
			}
		}
		
		//System.out.println(yOffset + " / " + state);
		
		startButton.moveTo(startButton.getX(), (int) (defaultY + yOffset));
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(handler.getAssets().getLoadedImages()[0], 0, 0, handler.getWindowWidth(), handler.getWindowHeight(), null);
		g.drawImage(handler.getAssets().getLoadedImages()[2], 0, 0, handler.getWindowWidth(), handler.getWindowHeight(), null);

		startButton.render(g);
		muteButton.render(g);
	}

	@Override
	public void reset() {

	}

	@Override
	public void load() {
		handler.getAssets().loadMenu();
		startButton.setTexture(handler.getAssets().getLoadedImages()[1]);
		
		if(mute) {
			muteButton.setTexture(handler.getAssets().getLoadedImages()[3]);
		}else {
			muteButton.setTexture(handler.getAssets().getLoadedImages()[4]);
		}
	}
	
	public void setMute(boolean mute) {
		this.mute = mute;
		
		if(mute) {
			handler.getLoop().getSound().setVolume(0);
			muteButton.setTexture(handler.getAssets().getLoadedImages()[3]);
		}else {
			muteButton.setTexture(handler.getAssets().getLoadedImages()[4]);
			handler.getLoop().getSound().setVolume(0.5f);
		}
	}

	public boolean isMute() {
		return mute;
	}
	
	
}

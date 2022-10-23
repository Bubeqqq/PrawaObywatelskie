package buba.main;

import buba.main.Assets.Assets;
import buba.main.gfx.Display;
import buba.main.input.MouseManager;

public class Handler {

	private Loop loop;
	
	public Handler(Loop loop) {
		this.loop = loop;
	}

	public Display getDisplay() {
		return loop.getDisplay();
	}
	
	public int getWindowWidth() {
		return loop.getDisplay().getJFrame().getWidth();
	}
	
	public int getWindowHeight() {
		return loop.getDisplay().getJFrame().getHeight();
	}
	
	public MouseManager getMouseManager() {
		return loop.getMouseManager();
	}

	public Loop getLoop() {
		return loop;
	}
	
	public Assets getAssets() {
		return loop.getAssets();
	}
	
	public void playSound(int ID) {
		loop.getSound().playSound(ID);
	}
}

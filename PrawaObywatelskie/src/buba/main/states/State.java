package buba.main.states;

import java.awt.Graphics;

import buba.main.Handler;

public abstract class State {
	
	protected Handler handler;
	
	public State(Handler handler) {
		this.handler = handler;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract void reset();
	public abstract void load();
	
	//static
	
	private static State currentState;

	public static State getCurrentState() {
		return currentState;
	}

	public static void setCurrentState(State currentState) {
		currentState.load();
		State.currentState = currentState;
	}
	
	
}

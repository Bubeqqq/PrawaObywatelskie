package buba.main.states;

import java.awt.Graphics;

import buba.main.Handler;
import buba.main.games.Game;

public class GameState extends State{

	private Game game;
	
	public GameState(Handler handler, Game game) {
		super(handler);

		this.game = game;
	}

	@Override
	public void tick() {
		game.tick();
	}

	@Override
	public void render(Graphics g) {
		game.render(g);
	}

	@Override
	public void reset() {
		game.reset();
	}

	@Override
	public void load() {
		game.load();
	}
}

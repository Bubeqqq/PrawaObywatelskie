package buba.main.games.Jigsaw;

import java.awt.Graphics;
import java.awt.Rectangle;

import buba.main.Handler;

public class Space {

	private Rectangle bounds;
	
	private Word word;
	
	private int ID;
	
	private Handler handler;
	
	public Space(int x, int y, int width, int height, int ID, Handler handler) {
		bounds = new Rectangle(x, y, width, height);
		this.ID = ID;
		this.handler = handler;
	}
	
	public void setPosition(int x, int y) {
		bounds.x = x;
		bounds.y = y;
	}
	
	public void setWidth(int width) {
		bounds.width = width;
	}
	
	public void render(Graphics g) {
		if(word == null)
			g.drawImage(handler.getAssets().getLoadedImages()[1], bounds.x, bounds.y, bounds.width, bounds.height, null);
		else
			g.drawString(word.getWord(), bounds.x, bounds.y + g.getFontMetrics().getHeight());
	}

	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public Rectangle getBounds() {
		return bounds;
	}
	
	public boolean goodWord() {
		if(word == null)
			return false;
		
		return word.getID() == ID;
	}
}

package buba.main.games.Jigsaw;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Word {

	private String word;
	
	private int x, y;
	
	private Rectangle bounds;
	
	private int ID;
	
	public Word(String word, int x, int y, int ID) {
		this.word = word;
		this.x = x;
		this.y = y;
		this.ID = ID;
	}
	
	public void render(Graphics g) {
		if(bounds == null) {
			bounds = new Rectangle(x, y - g.getFontMetrics().getHeight(), g.getFontMetrics().stringWidth(word), g.getFontMetrics().getHeight());
		}
		
		g.drawString(word, x, y);
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public String getWord() {
		return word;
	}

	public int getID() {
		return ID;
	}
	
	
}

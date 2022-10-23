package buba.main.games.Maze;

import java.awt.Graphics;

public class Letter {

	private char letter;
	
	private int x, y, size;
	
	public Letter(char letter, int x, int y, int size) {
		this.letter = letter;
		this.x = x;
		this.y = y;
		this.size = size;
	}
	
	public void render(Graphics g, int xOffset, int yOffset) {
		g.drawString(letter + "", 
				x - xOffset + size / 2 - g.getFontMetrics().stringWidth(letter + "") / 2, 
				y - yOffset + g.getFontMetrics().getHeight() / 2 + size / 2);
	}
	
	public char getLetter() {
		return letter;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	
}

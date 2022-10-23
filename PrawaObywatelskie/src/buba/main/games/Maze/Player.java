package buba.main.games.Maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import buba.main.Handler;
import buba.main.input.Button;

public class Player {

	private Handler handler;
	
	private int x, y, size, xOffset, yOffset;
	
	private Maze maze;
	
	private Button left, up, right, down;
	
	private boolean update = true;
	
	private int speed = 10, xMove, yMove;
	
	//direction
	
	private byte state = 1;
	
	private final byte LEFT = 0, UP = 1, RIGHT = 2, DOWN = 3;
	
	public Player(int x, int y, int size, Handler handler, Maze maze) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.maze = maze;
		this.handler = handler;
		
		right = new Button(0, 0, size, size, handler.getAssets().getLoadedImages()[15], handler) {
			@Override
			public void onClick() {
				handler.playSound(2);
				state = RIGHT;
				move(2, 0);
				setUpdate(true);
			}
		};
		
		left = new Button(0, 0, size, size, handler.getAssets().getLoadedImages()[14], handler) {
			@Override
			public void onClick() {
				handler.playSound(2);
				state = LEFT;
				move(-2, 0);
				setUpdate(true);
			}
		};
		
		up = new Button(0, 0, size, size, handler.getAssets().getLoadedImages()[12], handler) {
			@Override
			public void onClick() {
				handler.playSound(2);
				state = UP;
				move(0, -2);
				setUpdate(true);
			}
		};
		
		down = new Button(0, 0, size, size, handler.getAssets().getLoadedImages()[13], handler) {
			@Override
			public void onClick() {
				handler.playSound(2);
				state = DOWN;
				move(0, 2);
				setUpdate(true);
			}
		};
		
		right.setSound(false);
		left.setSound(false);
		down.setSound(false);
		up.setSound(false);
	}
	
	public void tick() {
		if(xMove == 0 && yMove == 0) {
			left.tick();
			right.tick();
			up.tick();
			down.tick();
		}
		
		if(xMove != 0) {
			if(xMove > 0) {
				xMove -= speed;
				x += speed;
			}
			else {
				xMove += speed;
				x -= speed;
			}
		}else
			update = true;
		
		if(yMove != 0) {
			if(yMove > 0) {
				yMove -= speed;
				y += speed;
			}
			else {
				yMove += speed;
				y -= speed;
			}
		}else
			update = true;
		
		if(!update)
			return;
		
		setCamera();
		
		if(maze.getMap()[getX() / getSize()][getY() / getSize() + 1] == 4 || maze.getMap()[getX() / getSize()][getY() / getSize() + 1] == 5) { //down
			down.setActive(true);
			down.moveTo(x - xOffset, y + size - yOffset);
		}else {
			down.setActive(false);
		}
		
		if(maze.getMap()[getX() / getSize()][getY() / getSize() - 1] == 4||maze.getMap()[getX() / getSize()][getY() / getSize() - 1] == 5) { //up
			up.setActive(true);
			up.moveTo(x - xOffset, y - size - yOffset);
		}else {
			up.setActive(false);
		}
		
		if(maze.getMap()[getX() / getSize() + 1][getY() / getSize()] == 4||maze.getMap()[getX() / getSize() + 1][getY() / getSize()] == 5) { //right
			right.setActive(true);
			right.moveTo(x - xOffset + size, y - yOffset);
		}else {
			right.setActive(false);
		}
		
		if(maze.getMap()[getX() / getSize() - 1][getY() / getSize()] == 4||maze.getMap()[getX() / getSize() - 1][getY() / getSize()] == 5) { //left
			left.setActive(true);
			left.moveTo(x - xOffset - size, y - yOffset);
		}else {
			left.setActive(false);
		}

		//check For Letters
		
		if(xMove == 0 && yMove == 0) {
			for(Letter l : maze.getLetters()) {
				if(x == l.getX() && y == l.getY()) {
					maze.foundLetter(l);
					handler.playSound(3);
					break;
				}
			}
		}
		
		update = false;
	}
	
	public void render(Graphics g) {
		if(xMove == 0 && yMove == 0) {
			g.setColor(Color.yellow);
			left.render(g);
			right.render(g);
			down.render(g);
			up.render(g);
		}

		BufferedImage texture = null;
		
		switch(state) {
			case UP:
				texture = handler.getAssets().getLoadedImages()[0];
				break;
			case DOWN:
				texture = handler.getAssets().getLoadedImages()[2];
				break;
			case LEFT:
				texture = handler.getAssets().getLoadedImages()[1];
				break;
			case RIGHT:
				texture = handler.getAssets().getLoadedImages()[3];
				break;
		}
		
		g.drawImage(texture, x - xOffset, y - yOffset, size, size, null);
	}
	
	private void setCamera() {
		xOffset = x - handler.getWindowWidth() / 2 + size / 2;
		yOffset = y - handler.getWindowHeight() / 2 + size / 2;

		if(xOffset < 0) {
			xOffset = 0;
		}else if(xOffset > maze.getMap().length * size - handler.getWindowWidth()) {
			xOffset = maze.getMap().length * size - handler.getWindowWidth();
		}
		
		if(yOffset < 0) {
			yOffset = 0;
		}else if(yOffset > maze.getMap()[0].length * size - handler.getWindowHeight()) {
			yOffset = maze.getMap()[0].length * size - handler.getWindowHeight();
		}
	}
	
	public void move(int x, int y) {
		xMove += x * size;
		yMove += y * size;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSize() {
		return size;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public int getxOffset() {
		return xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}
	
	
}

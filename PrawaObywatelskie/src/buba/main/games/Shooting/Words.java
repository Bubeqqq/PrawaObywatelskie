package buba.main.games.Shooting;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import buba.main.Handler;

public class Words {
	
	private float x, y, speedX, speedY;
	
	private final float resistance = 5;
	
	private boolean up = true;
	
	private String msg;
	
	private boolean active = false, die = false;
	
	private Handler handler;
	
	private int height = -1, width = -1;
	
	private int index;
	
	public Words(Handler handler, String msg, int index) {
		this.handler = handler;
		this.msg = msg;
		this.index = index;
	}
	
	public boolean tick() {
		if(!active)
			return false;
		
		if(up) {
			y -= speedY;
			speedY *= 1 - (resistance / 100);
			
			if(speedY < 1)
				up = false;
			
			x += speedX;
		}else {
			y += speedY;
			speedY *= 1 + (resistance / 100);
			
			x += speedX;
		}
		
		if(y >= handler.getWindowHeight()) {
			active = false;
			
			if(die)
				return true;
			else
				return false;
		}
		return false;
	}
	
	public void render(Graphics g) {
		if(!active || die)
			return;
		
		Font myFont = new Font("Serif", Font.BOLD, 50);
		g.setFont(myFont);
		g.setColor(Color.white);
		
		g.drawString(msg, (int)x, (int)y);
		
		if(width == -1 || height == -1) {
			height = g.getFontMetrics().getHeight();
			width = g.getFontMetrics().stringWidth(msg);
		}
		
		//g.drawRect((int)x, (int)y - g.getFontMetrics().getHeight(), g.getFontMetrics().stringWidth(msg), g.getFontMetrics().getHeight());
	}
	
	public void setActive() {
		Random r = new Random();
		
		x = handler.getWindowWidth() / 2;
		y = 1080;
		speedX = r.nextInt(5) - 2;
		speedY = r.nextInt(20) + 25;
		up = true;
		active = true;
	}
	
	public void die() {
		die = true;
	}

	public boolean isActive() {
		return active;
	}
	
	public Rectangle getBounds() {
		if(width == -1 || height == -1)
			return null;
		
		return new Rectangle((int)x, (int)y - height, width, height);
	}

	public int getIndex() {
		return index;
	}
	
	
}

package buba.main.input;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import buba.main.Handler;
import buba.main.Sounds.SoundManager;

public class Button {

	private int x, y, width, height;
	
	private BufferedImage texture;
	
	private Rectangle bounds;
	
	private Handler handler;
	
	private int index = -1;
	
	private boolean pressed = false, active = true, sound = true;
	
	public Button(int x, int y, int width, int height, BufferedImage texture, Handler handler) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.texture = texture;
		this.handler = handler;
		
		bounds = new Rectangle(x, y, width, height);
	}
	
	public void onClick() {}
	
	public void tick() {
		if(!active)
			return;
		
		if(!pressed) {
			if(handler.getMouseManager().isLeftPressed()) {
				if(bounds.contains(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY()))
					pressed = true;
			}
		}else {
			if(!handler.getMouseManager().isLeftPressed()) {
				if(!handler.getMouseManager().isLastDragged()) {
					if(bounds.contains(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY())) {
						if(sound)
							handler.playSound(0);
						onClick();
					}
				}else {
					if(bounds.contains(handler.getMouseManager().getDraggedX(), handler.getMouseManager().getDraggedY())) {
						if(sound)
							handler.playSound(0);
						onClick();
					}
				}
				pressed = false;
			}
		}
	}
	
	public void render(Graphics g) {
		if(!active)
			return;
		
		if(texture != null) {
			g.drawImage(texture, x, y, width, height, null);
		}else {
			g.fillRect(x, y, width, height);
		}
	}
	
	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;
		
		bounds.setLocation(this.x, this.y);
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public int getX() {
		return x;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setSound(boolean sound) {
		this.sound = sound;
	}
}

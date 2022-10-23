package buba.main.games.Shooting;

import java.awt.Graphics;
import java.util.ArrayList;

import buba.main.Handler;

public class Bullet {
	
	private int x, y, tx, ty;
	
	private int speed = 15;
	
	private Handler handler;
	
	private Shooting shooting;
	
	private final int BULLETSIZE = 10;
	
	public Bullet(Handler handler, int x, int y, int tx, int ty, Shooting shooting) {
		this.x = x;
		this.y = y;
		this.tx = tx;
		this.ty = ty;
		this.handler = handler;
		this.shooting = shooting;
	}
	
	public boolean tick() {
		float dx = tx - x;
        float dy = ty - y;
        float distance = (float) Math.sqrt(dx*dx + dy*dy);
        if (distance > 0) {
            dx = dx * speed / distance;
            dy = dy * speed / distance;
        }
        x += dx;
        y += dy;
        tx += dx;
        ty += dy;
        
        //check for collision with words
        
        for(Words w : shooting.getWords()) {
        	if(w.getBounds() == null || !w.isActive())
        		continue;
        	
        	if(w.getBounds().contains(x, y)) {
        		shooting.removeWord(w);
        		break;
        	}
        }
        
        if(x >= 0 && x <= handler.getWindowWidth()) {
        	if(y >= 0 && y <= handler.getWindowHeight())
        		return false;
        }
        
        return true;
	}
	
	public void render(Graphics g) {
		g.drawImage(handler.getAssets().getLoadedImages()[1], x, y, BULLETSIZE, BULLETSIZE, null);
		//g.drawRect(x, y, BULLETSIZE, BULLETSIZE);
	}
}

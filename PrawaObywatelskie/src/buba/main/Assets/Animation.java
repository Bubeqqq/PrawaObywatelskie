package buba.main.Assets;

import java.awt.image.BufferedImage;

public class Animation {
	
	private int index, speed;
	private long lastTime, timer;
	private BufferedImage[] frames;

	public Animation(BufferedImage[] frames, int speed) {
		this.speed = speed;
		this.frames = frames;
		lastTime = System.currentTimeMillis();
		index = 0;
		timer = 0;
	}

	public void tick() {
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer > speed) {
			index++;
			timer = 0;
			if(index >= frames.length)
				index = 0;
		}
	}
	
	public BufferedImage GetCurrentFrame() {
		return frames[index];
	}


}

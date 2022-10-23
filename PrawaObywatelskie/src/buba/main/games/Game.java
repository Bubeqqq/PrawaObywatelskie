package buba.main.games;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import buba.main.Handler;
import buba.main.Assets.Animation;
import buba.main.states.State;

public abstract class Game {
	
	protected Handler handler;
	
	protected String msg;
	
	private Animation animation;
	
	private boolean description, changing = false;
	
	private float yOffset, speed;
	
	private byte state = 0;
	
	private final byte FIRSTDOWN = 0, FIRSTUP = 1, SECONDUP = 2, SECONDDOWN = 3;
	
	private int y = -1, height = -1, index;
	
	protected boolean win = false;
	
	protected String[] desWords;
	
	public Game(Handler handler, Animation animation, int index) {
		this.handler = handler;
		this.animation = animation;
		this.index = index;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract void reset();
	public abstract void load();

	public Animation getAnimation() {
		return animation;
	}
	
	public void renderAnimation(Graphics g, int index, int width, int height, int offset) {
		if(y == -1 || height == -1) {
			y = handler.getWindowHeight() / 2 - height / 2;
			this.height = height;
		}
		
		if(animation != null) {
			if(!description)
				g.drawImage(animation.GetCurrentFrame(), (int)(index * width * 1.5f - offset), (int) (handler.getWindowHeight() / 2 - height / 2 - yOffset), width, height, null);
			else {			
				int x = (int)(index * width * 1.5f - offset);
				int y = (int) (handler.getWindowHeight() / 2 - height / 2 - yOffset);
				
				g.setColor(Color.gray);
				g.fillRect(x, y, width, height);
				
				if(desWords == null)
					return;
				
				g.setColor(Color.white);
				Font myFont = new Font("Serif", Font.BOLD, 30);
				g.setFont(myFont);
				
				for(int i = 0; i < desWords.length; i++) {
					if(x + g.getFontMetrics().stringWidth(desWords[i]) >= width + index * width * 1.5f - offset) {
						x = (int)(index * width * 1.5f - offset);
						y += g.getFontMetrics().getHeight();
					}
					
					g.drawString(desWords[i], x, y + g.getFontMetrics().getHeight());
					x+=g.getFontMetrics().stringWidth(desWords[i] + " ");
				}
			}
		}else
			g.fillRect((int)(index * width * 1.5f - offset), (int) (handler.getWindowHeight() / 2 - height / 2 - yOffset), width, height);

	}
	
	public void changeDescription() {
		yOffset = 0;
		changing = true;
		state = FIRSTDOWN;
		speed = 5;
	}
	
	public void tickAnimation() {
		if(!changing || y == -1)
			return;
		
		if(state == FIRSTDOWN) {
			yOffset -= speed;
			
			speed -= speed * 0.04f;
			
			if(speed <= 1) {
				state = FIRSTUP;
				speed = 20;
			}
		}else if(state == FIRSTUP) {
			yOffset += speed;
			
			speed += speed * 0.03f;
			
			if(y + height - yOffset <= 0) {
				state = SECONDUP;
				speed = 35;
				description = !description;
				yOffset = -handler.getWindowHeight();
			}
		}else if(state == SECONDUP) {
			yOffset += speed;
			
			speed -= speed * 0.028f;
			
			if(speed <= 3) {
				state = SECONDDOWN;
				speed = 5;
			}
		}else if(state == SECONDDOWN) {
			yOffset -= speed;
			
			speed -= speed * 0.03f;
			
			if(yOffset <= 0) {
				handler.getLoop().getSelectState().setClickedButton(false);
				handler.getLoop().getSelectState().getButtons()[index].setActive(true);
				changing = false;
			}
		}
	}
	
	//win
	
	private int size = 0;
	private float sizeSpeed = 5;
	
	protected void resetWin() {
		size = 0;
	}
	
	protected void win(Graphics g) {
		size+=sizeSpeed;
		sizeSpeed *= 1.1f;
		g.setColor(Color.black);
		
		g.fillOval(handler.getWindowWidth() / 2 - size / 2, handler.getWindowHeight() / 2 - size / 2, size, size);
		
		if(size >= handler.getWindowWidth() * 1.5f) {
			State.setCurrentState(handler.getLoop().getMenuState());	
		}
	}
	
	public void loadMsg() {
		String[] m = handler.getLoop().getMessages();
		Random r = new Random();
		
		msg = m[r.nextInt(m.length)];
	}
}

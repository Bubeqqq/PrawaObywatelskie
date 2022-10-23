package buba.main.games.Shooting;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import buba.main.Handler;
import buba.main.Assets.Animation;
import buba.main.games.Game;
import buba.main.states.State;

public class Shooting extends Game{

	private final int playerX, playerY, playerWidth = 150, playerHeight = 150;
	
	private final int shootingSpeed = 500;
	
	private long lastTime, timer;
	
	private long timer2;
	
	private boolean canShoot = true;
	
	private ArrayList<Bullet> bullets;
	
	private ArrayList<Words> words;
	
	private String[] w;
	
	private boolean[] wordsFound;
	
	private int targetX, targetY;
	
	private boolean update = true;
	
	private BufferedImage player;
	
	public Shooting(Handler handler, Animation animation, int index) {
		super(handler, animation, index);
		
		playerX = 240;
		playerY = 450;
	
		bullets = new ArrayList<>();
		words = new ArrayList<>();
		
		String des = "W tej grze twoim zadaniem jest trafienie pociskiem w s³owa i u³o¿enie z nich praw obywatelskich. Gra koñczy siê w momencie kiedy uda³o ulo¿yæ ci siê ca³e zdanie.";
		desWords = des.split(" ");
	}

	@Override
	public void tick() {
		if(win) {
			return;
		}
		
		if(words.isEmpty()) {
			win = true;
			handler.playSound(6);
		}
		
		timer2 += System.currentTimeMillis() - lastTime;
		
		if(timer2 > 1000) {
			timer2 = 0;

			Random r = new Random();
			
			Words w = words.get(r.nextInt(words.size()));
			
			if(!w.isActive()) {
				w.setActive();
			}
		}
		
		if(!canShoot) {
			timer += System.currentTimeMillis() - lastTime;
			
			if(timer > shootingSpeed) {
				canShoot = true;
				timer = 0;
			}
		}else if(handler.getMouseManager().isLeftPressed()) {
			int x, y;
			
			if(handler.getMouseManager().isDragged()) {
				x = handler.getMouseManager().getDraggedX();
				y = handler.getMouseManager().getDraggedY();
			}else {
				x = handler.getMouseManager().getMouseX();
				y = handler.getMouseManager().getMouseY();
			}
			
			canShoot = false;
			lastTime = System.currentTimeMillis();

			targetX = x;
			targetY = y;
			
			update = true;
			
			handler.playSound(8);
			bullets.add(new Bullet(handler, playerX + playerWidth, playerY + playerHeight / 2, x, y, this));
		}
		
		Iterator<Bullet> it = bullets.iterator();
		while(it.hasNext()){
			if(it.next().tick())
				it.remove();
		}
		
		Iterator<Words> ite = words.iterator();
		while(ite.hasNext()){
			if(ite.next().tick())
				ite.remove();
		}
		
		lastTime = System.currentTimeMillis();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(handler.getAssets().getLoadedImages()[2], 0, 0, handler.getWindowWidth(), handler.getWindowHeight(), null);
		
		//draw player
		if(update) {
			float angle = (float) Math.toDegrees(Math.atan2(targetY - playerY, targetX - playerX));
			player = rotateImage(handler.getAssets().getLoadedImages()[0], (int) angle - 90);
			update = false;
		}
		
		g.drawImage(player, playerX, playerY, playerWidth, playerHeight, null);
		
		for(Bullet b : bullets) {
			b.render(g);
		}
		
		for(Words w : words) {
			w.render(g);
		}
		
		int x = 500, y = 100, width = 0;
		
		Font myFont = new Font("Serif", Font.BOLD, 50);
		g.setFont(myFont);
		
		g.setColor(Color.white);
		
		for(int i = 0; i < wordsFound.length; i++) {
			if(!wordsFound[i])
				continue;
			
			if(x + g.getFontMetrics().stringWidth(w[i] + " ") >= handler.getWindowWidth() - 100) {
				x = 500;
				y += g.getFontMetrics().getHeight();
			}
			
			g.drawString(w[i], x, y);
			width += g.getFontMetrics().stringWidth(w[i] + " ");
			
			x += g.getFontMetrics().stringWidth(w[i] + " ");
		}
		
		if(win)
			win(g);
	}

	@Override
	public void reset() {
		resetWin();
		lastTime = System.currentTimeMillis();
		
		bullets.clear();
		
		w = msg.split(" ");
		for(int i = 0; i < w.length; i++) {
			words.add(new Words(handler, w[i], i));
		}
		
		wordsFound = new boolean[w.length];
	}

	public ArrayList<Words> getWords() {
		return words;
	}

	public void removeWord(Words word) {
		handler.playSound(9);
		wordsFound[word.getIndex()] = true;
		words.remove(word);
	}
	
	public BufferedImage rotateImage (BufferedImage imag, int n) {

       double rotationRequired = Math.toRadians (n);
       double locationX = imag.getWidth() / 2;
       double locationY = imag.getHeight() / 2;
       AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
       AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);         
       BufferedImage newImage =new BufferedImage(imag.getWidth(), imag.getHeight(), imag.getType()); 
       op.filter(imag, newImage);

       return(newImage);
     }
	
	@Override
	public void load() {
		handler.getAssets().loadShooting();
	}

	public void setTargetX(int targetX) {
		this.targetX = targetX;
	}

	public void setTargetY(int targetY) {
		this.targetY = targetY;
	}
	
	
}

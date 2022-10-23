package buba.main.games;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import buba.main.Handler;
import buba.main.Assets.Animation;
import buba.main.states.State;

public class HangMan extends Game{

	private String[] words;
	
	private int index = -1;
	
	private int missed;
	
	private ArrayList<Character> founded, letters;
	
	private boolean pressed = false, clicked = false;
	
	private int x1, y1, x2, y2;
	
	public HangMan(Handler handler, Animation animation, int index) {
		super(handler, animation, index);
		
		founded = new ArrayList<>();
		letters = new ArrayList<>();
		
		String des = "W tej grze twoim zadaniem jest uzupe³nienie zdania odpowiednim s³owem, tak aby zgadza³o siê ono z danym prawem obywatelskim. Uzupe³niasz s³owo wybieraj¹c pojedyncze litery, za ka¿d¹ b³êdn¹ litere buduje siê czêœæ wisielca.";
		desWords = des.split(" ");
	}

	@Override
	public void tick() {
		if(win) {
			return;
		}
		
		//letters
		
		if(!pressed) {
			if(handler.getMouseManager().isLeftPressed()) {
				pressed = true;
				x1 = handler.getMouseManager().getMouseX();
				y1 = handler.getMouseManager().getMouseY();
			}
		}else {
			if(!handler.getMouseManager().isLeftPressed()) {
				if(!handler.getMouseManager().isLastDragged()) {
					x2 = handler.getMouseManager().getMouseX();
					y2 = handler.getMouseManager().getMouseY();
				}else {
					x2 = handler.getMouseManager().getDraggedX();
					y2 = handler.getMouseManager().getDraggedY();
				}
				clicked = true;
				pressed = false;
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(handler.getAssets().getLoadedImages()[0], 0, 0, handler.getWindowWidth(), handler.getWindowHeight(), null);

		g.drawImage(handler.getAssets().getLoadedImages()[1 + missed], 800, 357, 900, 510, null);
		
		Font myFont = new Font("Serif", Font.BOLD, 50);
		g.setFont(myFont);
		
		int x = 50;
		int y = 400;
		int msgWidth = 1200;
		for(int i = 0; i < words.length; i++) {
			if(x + g.getFontMetrics().stringWidth(words[i] + " ") >= msgWidth) {
				x = 50;
				y += g.getFontMetrics().getHeight();
			}
			
			
			if(i == index) {
				for(char a : words[i].toCharArray()) {
					if(founded.contains(a)) {
						g.drawString(a + "", x, y);
					}else {
						g.drawRect(x, y - g.getFontMetrics().getHeight(), g.getFontMetrics().stringWidth(a + ""), g.getFontMetrics().getHeight());
					}
				
					x += g.getFontMetrics().stringWidth(a + "");
				}
				
				x += g.getFontMetrics().stringWidth(" ");
				
				continue;	
			}
			
			g.drawString(words[i], x, y);
			x += g.getFontMetrics().stringWidth(words[i] + " ");
		}
		
		//letters
		
		int tx = 150, ty = 700, width = 1000, defaultX = 600;
		
		g.drawImage(handler.getAssets().getLoadedImages()[12], tx - 75, ty - g.getFontMetrics().getHeight(), width + 125, (int) (g.getFontMetrics().getHeight() * 2f), null);
		
		int i = 0;
		Iterator<Character> it = letters.iterator();
		while(it.hasNext()){
			char l = it.next();
			
			g.drawString(l + "", tx, ty);
			
			if(clicked) {
				Rectangle bounds = new Rectangle(tx, ty - g.getFontMetrics().getHeight(), g.getFontMetrics().stringWidth(l + ""), g.getFontMetrics().getHeight());
				
				//g.fillRect(tx, ty - (int) (g.getFontMetrics().getHeight() * 0.5f), g.getFontMetrics().stringWidth(l + ""), g.getFontMetrics().getHeight());
				
				if(bounds.contains(x1, y1) && bounds.contains(x2, y2)) {
					clicked = false;
					
					it.remove();
					founded.add(l);
					
					boolean miss = true;
					for(int j = 0; j < words[index].toCharArray().length; j++) {
						if(Character.compare(l, words[index].toCharArray()[j]) == 0) {
							miss = false;
							break;
						}
					}
					
					if(miss) {
						missed++;
						handler.playSound(4);
					}else
						handler.playSound(3);
					
					if(missed > 9) {
						handler.playSound(7);
						win = true;
					}
					
					//check for win
					
					boolean win = true;
					for(char a : words[index].toCharArray()) {
						if(!founded.contains(a)) {
							win = false;
						}
					}
					
					if(win) {
						this.win = true;
						handler.playSound(6);
					}
				}
			}
			
			tx += g.getFontMetrics().stringWidth(l + "  ");
			
			if(tx > defaultX + width) {
				ty += g.getFontMetrics().getHeight() * 2;
				tx = defaultX;
			}
			
			i++;
		}
		
		if(clicked) {
			clicked = false;
			System.out.println("clicked");
		}
		
		if(win)
			win(g);
	}

	@Override
	public void reset() {
		resetWin();
		Random random = new Random();
		words = msg.split(" ");
		
		letters.clear();
		founded.clear();
		
		pressed = false;
		clicked = false;
		missed = 0;
		
		index = -1;

		int longest = 0;
		
		for(int i = 0; i < words.length; i++) {
			if(words[i].toCharArray().length > longest)
				longest = words[i].toCharArray().length;
		}
		
		while(index == -1) {
			int a = random.nextInt(words.length);
			
			if(words[a].toCharArray().length >= longest / 2) {
				index = a;
			}
		}
		
		for(char a : words[index].toCharArray()) {
			boolean add = true;
			for(char b : letters) {
				if(Character.compare(a, b) == 0) {
					add = false;
					break;
				}
			}
			
			if(add)
				letters.add(a);
		}
		
		while(letters.size() < 20) {
			char c = (char)(random.nextInt(26) + 'a');
			
			System.out.println(c);
			
			boolean add = true;
			for(char a : letters) {
				if(Character.compare(a, c) == 0) {
					add = false;
					break;
				}
			}
			
			if(add)
				letters.add(c);
		}
		
		Collections.shuffle(letters);
	}
	
	@Override
	public void load() {
		handler.getAssets().loadHangman();
	}
}

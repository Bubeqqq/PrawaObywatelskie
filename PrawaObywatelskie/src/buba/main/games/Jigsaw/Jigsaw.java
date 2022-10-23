package buba.main.games.Jigsaw;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import buba.main.Handler;
import buba.main.Assets.Animation;
import buba.main.games.Game;
import buba.main.states.State;

public class Jigsaw extends Game{
	
	private Word selectedWord;
	
	private ArrayList<Word> words;
	
	private Rectangle wordsArea;
	
	private Space[] spaces;
	
	private int spaceWidth = 200;

	private final int spaceHeight = 100;

	private final int spaceY = 80;
	
	private boolean update = true;
	
	public Jigsaw(Handler handler, Animation animation, int index) {
		super(handler, animation, index);
		
		String des = "W tej grze twoim zadaniem jest u³o¿enie podanych s³ów w sensowne zdania, tak aby z³o¿y³o siê w jeden sensowny tekst. Gra koñczy siê w momencie w którym uda u³o¿yæ ci siê zdanie.";
		desWords = des.split(" ");
	}

	@Override
	public void tick() {
		if(win) {
			return;
		}
		
		if(handler.getMouseManager().isLeftPressed() && selectedWord == null) {
			for(Space s : spaces) {
				if(s.getWord() == null)
					continue;
				
				if(s.getBounds().contains(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY())) {
					update = true;
					selectedWord = s.getWord();
					s.setWord(null);
					words.add(selectedWord);
					return;
				}
			}
			
			for(Word d : words) {
				if(d.getBounds() == null)
					continue;
				
				if(d.getBounds().contains(handler.getMouseManager().getMouseX(), handler.getMouseManager().getMouseY())) {
					selectedWord = d;
					return;
				}
			}
		}
		if(!handler.getMouseManager().isLeftPressed()){
			if(selectedWord != null) {
				handler.playSound(5);
				
				int x = handler.getMouseManager().getDraggedX();
				int y = handler.getMouseManager().getDraggedY();
				
				for(Space s : spaces) {
					if(s.getWord() != null)
						continue;
					
					if(s.getBounds().contains(x, y)) {
						update = true;
						s.setWord(selectedWord);
						words.remove(selectedWord);
						checkForWin();
						break;
					}
				}
				
				selectedWord.setBounds(null);
			}
			selectedWord = null;
		}
		
		if(selectedWord != null && handler.getMouseManager().isDragged()) {
			selectedWord.setPosition(handler.getMouseManager().getDraggedX(), handler.getMouseManager().getDraggedY());
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(handler.getAssets().getLoadedImages()[0], 0, 0, handler.getWindowWidth(), handler.getWindowHeight(), null);
		
		Font myFont = new Font("Serif", Font.BOLD, 50);
		g.setFont(myFont);
		
		g.drawRect(wordsArea.x, wordsArea.y, wordsArea.width, wordsArea.height);
		
		if(update)
			setSpaces(g);
		
		for(Space s : spaces) {
			s.render(g);
		}
		
		for(Word w : words)
			w.render(g);
		
		if(win)
			win(g);
	}

	@Override
	public void reset() {	
		resetWin();
		String[] w = msg.split(" ");
		words = new ArrayList<>();
		spaces = new Space[w.length];
		
		
		int spaceX = handler.getWindowWidth() / 2 - spaces.length * spaceWidth / 2;
		for(int i = 0; i < spaces.length; i++) {
			spaces[i] = new Space(spaceX + i * spaceWidth, spaceY, spaceWidth, spaceHeight, i, handler);
		}
		
		int width = 1500;
		int height = 500;
		int x = handler.getWindowWidth() / 2 - width / 2;
		int y = handler.getWindowHeight() / 2 - height / 2;
		
		wordsArea = new Rectangle(x, y, width, height);
		
		Random r = new Random();
		
		for(int i = 0; i < w.length; i++) {
			int tx = r.nextInt(width) + x;
			int ty = r.nextInt(height) + y;
			
			words.add(new Word(w[i], tx, ty, i));
		}
	}
	
	private void setSpaces(Graphics g) {
		int width = 0;
		int y = 0, startRow = 0;;
		for(int i = 0; i < spaces.length; i++) {
			if(spaces[i].getWord() != null) {
				width += g.getFontMetrics().stringWidth(spaces[i].getWord().getWord() + " ");
			}else
				width += spaceWidth;
			
			//second row
			
			if(width >= handler.getWindowWidth() - spaceWidth) {
				int x = handler.getWindowWidth() / 2 - width / 2;
				y = 110;
				
				for(int j = 0; j <= i; j++) {
					if(x >= handler.getWindowWidth())
						break;
					
					spaces[j].setPosition(x, spaceY);
					
					if(spaces[j].getWord() != null) {
						spaces[j].setWidth(g.getFontMetrics().stringWidth(spaces[j].getWord().getWord()));
						x += g.getFontMetrics().stringWidth(spaces[j].getWord().getWord() + " ");
					}else {
						spaces[j].setWidth(spaceWidth);
						x += spaceWidth;
					}
					
					startRow = j + 1;
				}
				
				width = 0;
			}
		}
		
		int x = handler.getWindowWidth() / 2 - width / 2;
		
		for(int i = startRow; i < spaces.length; i++) {
			spaces[i].setPosition(x, spaceY + y);
			
			if(spaces[i].getWord() != null) {
				spaces[i].setWidth(g.getFontMetrics().stringWidth(spaces[i].getWord().getWord()));
				x += g.getFontMetrics().stringWidth(spaces[i].getWord().getWord() + " ");
			}else {
				spaces[i].setWidth(spaceWidth);
				x += spaceWidth;
			}
		}
	}
	
	private void checkForWin() {
		boolean win = true;
		
		for(Space s : spaces) {
			if(!s.goodWord()) {
				win = false;
				break;
			}
		}
		
		if(win) {
			this.win = true;
			handler.playSound(6);
		}
	}

	@Override
	public void load() {
		handler.getAssets().loadJigsaw();
	}

}

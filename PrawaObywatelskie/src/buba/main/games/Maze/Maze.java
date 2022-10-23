package buba.main.games.Maze;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import buba.main.Handler;
import buba.main.Assets.Animation;
import buba.main.games.Game;
import buba.main.states.State;

public class Maze extends Game{

	private int[][] map;
	
	private int width = 23, height = 23;
	
	private int size = 100, index = -1, yOffset = 0;
	
	private Player player;
	
	private String[] words;
	private final int tableWidth = 700, tableHeight = 300, tableSpeed = 7;
	
	private ArrayList<Letter> letters, founded, toAdd;
	
	private boolean foundLetter;
	
	private byte tableState = 0;
	
	private long lastTime, timer;
	
	private final byte UP = 0, STAY = 1, DOWN = 2;
	
	private final int delay = 1500;
	
	//road direction
	
	private byte state;
	
	private final byte LEFTROAD = 0, UPROAD = 1;
	
	public Maze(Handler handler, Animation animation, int index) {
		super(handler, animation, index);
	
		letters = new ArrayList<>();
		founded = new ArrayList<>();
		toAdd = new ArrayList<>();
		
		String des = "W tej grze twoim zadaniem jest znalezienie porozrzucanych po labiryncie liter, które razem stworz¹ dokoñcz¹ zdanie. Gra koñczy siê w momencie w ktorym uda ci siê dokoñczyæ zdanie.";
		desWords = des.split(" ");
	}

	@Override
	public void tick() {
		if(win) {
			return;
		}
		
		player.tick();
		
		if(!foundLetter)
			return;
		
		if(tableState == UP) {
			yOffset += tableSpeed;
		}else if(tableState == DOWN) {
			yOffset -= tableSpeed;
		}
		
		if(yOffset - tableHeight >= 0 && tableState == UP) {
			tableState = STAY;
			timer = 0;
			lastTime = System.currentTimeMillis();
		}
		
		if(tableState == STAY) {
			timer += System.currentTimeMillis() - lastTime;
			lastTime = System.currentTimeMillis();
			
			if(timer >= delay)
				tableState = DOWN;
		}
		
		if(tableState == DOWN && yOffset <= 0) {
			System.out.println("off");
			foundLetter = false;
		}
	}

	@Override
	public void render(Graphics g) {
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				g.drawImage(handler.getAssets().getLoadedImages()[map[x][y]], x * size - player.getxOffset(), y * size - player.getyOffset(), size, size, null);
				
				/*if(map[x][y] == 0)
					g.drawImage(handler.getAssets().getLoadedImages()[2], x * size - player.getxOffset(), y * size - player.getyOffset(), size, size, null);
				else
					g.fillRect(x * size - player.getxOffset(), y * size - player.getyOffset(), size, size);*/
			}
		}
		
		player.render(g);
		
		//draw Letters
		
		Font myFont = new Font("Serif", Font.BOLD, 60);
		g.setFont(myFont);
		g.setColor(Color.yellow);
		for(Letter l : letters)
			l.render(g, player.getxOffset(), player.getyOffset());
		
		//draw message
		
		if(!foundLetter)
			return;
		
		myFont = new Font("Serif", Font.BOLD, 40);
		g.setFont(myFont);
		
		g.setColor(Color.white);
		g.drawImage(handler.getAssets().getLoadedImages()[11], handler.getWindowWidth() / 2 - tableWidth / 2, yOffset - tableHeight, tableWidth, tableHeight, null);
	
		g.setColor(Color.black);
		
		int x = handler.getWindowWidth() / 2 - tableWidth / 2 + g.getFontMetrics().stringWidth(" ") * 5;
		int y = yOffset - tableHeight + g.getFontMetrics().getHeight();
		
		for(int i = 0; i < words.length; i++) {
			if(x + g.getFontMetrics().stringWidth(words[i]) >= handler.getWindowWidth() / 2 + tableWidth / 2) {
				x = handler.getWindowWidth() / 2 - tableWidth / 2 + g.getFontMetrics().stringWidth(" ") * 5;
				y += g.getFontMetrics().getHeight();
			}
			
			if(i == index) {
				for(char a : words[index].toCharArray()) {
					boolean found = false;
					for(Letter l : founded) {
						if(Character.compare(a, l.getLetter()) == 0) {
							found = true;
							break;
						}
					}
					
					if(found) {
						g.drawString(a + "", x, y);
						x += g.getFontMetrics().stringWidth(a + "");
					}else {
						g.drawRect(x, y - g.getFontMetrics().getHeight(), g.getFontMetrics().stringWidth(" "), g.getFontMetrics().getHeight());
						x += g.getFontMetrics().stringWidth(" ");
					}
				}
				
				x += g.getFontMetrics().stringWidth(" ");
			}else {
				g.drawString(words[i], x, y);
				x += g.getFontMetrics().stringWidth(words[i] + " ");
			}
		}
		
		if(win)
			win(g);
	}

	@Override
	public void reset() {
		resetWin();
		map = new int[width][height];
		
		handler.getAssets().loadMaze();
		letters.clear();
		founded.clear();
		
		player = new Player(2 * size, 2 * size, size, handler, this);
		
		//get random word
		
		Random random = new Random();
		words = msg.split(" ");
		
		int longest = 0;
		index = -1;
		
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
			for(Letter b : toAdd) {
				if(Character.compare(b.getLetter(), a) == 0) {
					add = false;
					break;
				}
			}
			
			if(add) {
				toAdd.add(new Letter(a, -1, -1, size));
			}
		}
		
		generateMaze();
		
		foundLetter = true;
	}
	
	private void generateMaze() {
		ArrayList<Point> points = new ArrayList<>();
		ArrayList<Point> lettersAt = new ArrayList<>();
		Random r = new Random();
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				map[x][y] = r.nextInt(5) + 6;
			}
		}
		
		for(int y = 2; y < height - 2; y+=2) {
			for(int x = 2; x < width - 2; x+=2) {
				points.add(new Point(x, y));
			}	
		}
		
		//generate maze
		
		Point generator = new Point(2, 2);
		Point old = new Point(2, 2);
		
		while(!points.isEmpty() || !toAdd.isEmpty()) {
			System.out.println(generator.x + " / " + generator.y);
			
			//move generator
			old.x = generator.x;
			old.y = generator.y;
			
			Point move = getRandomMove(r);
			generator.x += move.x;
			generator.y += move.y;
			
			//in map
			if(generator.x < 2)
				generator.x = 2;
			else if(generator.x >= width - 2)
				generator.x = width - 3;
			if(generator.y < 2)
				generator.y = 2;
			else if(generator.y >= height - 2)
				generator.y = height - 3;
			
			//generate letter
			
			if(!toAdd.isEmpty()) {		
				if(r.nextInt(1000) < 1) {
					boolean newSpace = true;
					
					for(Point a : lettersAt) {
						if(a.x == generator.x && a.y == generator.y) {
							newSpace = false;
						}
					}
					
					if(newSpace) {
						Letter a = toAdd.get(0);
						a.setPosition(generator.x * size, generator.y * size);
						toAdd.remove(a);
						letters.add(a);
						lettersAt.add(new Point(generator.x, generator.y));
					}
				}
			}
			
			//delete point
			
			for(Point p : points) {
				if(p.x == generator.y && p.y == generator.x) {
					int road = 0;
					
					switch(state) {
						case UPROAD:
							road = 4;
							break;
						case LEFTROAD:
							road = 5;
							break;
					}
					
					map[(old.x + generator.x) / 2][(old.y + generator.y) / 2] = road;
					map[generator.x][generator.y] = road;
					
					points.remove(p);
					break;
				}
			}
		}
		
	}
	
	private Point getRandomMove(Random r) {	
		int direction = r.nextInt(4);
		
		switch(direction) {
			case 0 :
				state = LEFTROAD;
				return new Point(2, 0);
			case 1 :
				state = LEFTROAD;
				return new Point(-2, 0);
			case 2 :
				state = UPROAD;
				return new Point(0, 2);
			case 3 :
				state = UPROAD;
				return new Point(0, -2);
			default : return new Point(0, 0);
		}
	}
	
	public void foundLetter(Letter l) {
		letters.remove(l);
		founded.add(l);
		foundLetter = true;
		yOffset = 0;
		tableState = UP;
		
		if(letters.isEmpty()) {
			win = true;
			handler.playSound(6);
		}
	}

	public int[][] getMap() {
		return map;
	}

	public ArrayList<Letter> getLetters() {
		return letters;
	}
	
	@Override
	public void load() {
	}
}

package buba.main.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import buba.main.Handler;
import buba.main.Assets.Animation;
import buba.main.games.Game;
import buba.main.games.HangMan;
import buba.main.games.Jigsaw.Jigsaw;
import buba.main.games.Maze.Maze;
import buba.main.games.Shooting.Shooting;
import buba.main.input.Button;

public class SelectState extends State{

	private Game[] games;
	
	private Button[] buttons;
	
	private int offset, lastTouch = -1;
	
	private int previousScreenSize;
	
	private boolean leftPressed = false;
	
	private int width, height;
	
	private boolean clickedButton = false;
	
	private Button back;

	public SelectState(Handler handler) {
		super(handler);
		
		width = handler.getWindowWidth() / 5;
		height = handler.getWindowHeight() / 2;
		
		previousScreenSize = handler.getWindowWidth();
	}

	@Override
	public void tick() {
		for(Button b : buttons) {
			b.tick();
		}
		
		back.tick();
		
		//games animations
		
		for(Game g : games) {
			g.tickAnimation();
			
			if(g.getAnimation() != null)
				g.getAnimation().tick();
		}
		
		//slider
		
		if(previousScreenSize != handler.getWindowWidth()) {
			previousScreenSize = handler.getWindowWidth();
			offset = 0;
			width = handler.getWindowWidth() / 5;
			height = handler.getWindowHeight() / 2;
		}
		
		if(handler.getMouseManager().isLeftPressed() && handler.getMouseManager().isDragged()) {
			if(lastTouch == -1) {
				lastTouch = handler.getMouseManager().getMouseX();
			}else {
				int amount = lastTouch - handler.getMouseManager().getDraggedX();
				
				if(amount + offset <= -handler.getWindowWidth() / 10) {
					offset = -handler.getWindowWidth() / 10;
				}else if(offset + amount + handler.getWindowWidth() >= handler.getWindowWidth() / 5 * games.length * 1.5f) {
					offset = (int) (handler.getWindowWidth() / 5 * games.length * 1.5f - handler.getWindowWidth());
				}else {
					offset += amount;
				}
				
				setButtons();
				lastTouch = handler.getMouseManager().getDraggedX();
			}
		}else {
			lastTouch = -1;
		}
		
		//select games
		
		if(clickedButton) {
			leftPressed = false;
			return;
		}
		
		if(!leftPressed) {
			if(handler.getMouseManager().isLeftPressed())
				leftPressed = true;
		}else {
			if(!handler.getMouseManager().isLeftPressed()) {
				leftPressed = false;
				
				int x, y;
				
				if(!handler.getMouseManager().isLastDragged()) {
					x = handler.getMouseManager().getMouseX();
					y = handler.getMouseManager().getMouseY();
				}else {
					return;

				}
				
				int index = 0;
				for(Game game : games) {
					if(index * width * 1.5f - offset <= handler.getWindowWidth() &&
							index * width * 1.5f - offset + width >= 0) {
						
						Rectangle bounds = new Rectangle((int)(index * width * 1.5f - offset), handler.getWindowHeight() / 2 - height / 2, width, height);
					
						if(bounds.contains(x, y)) {
							handler.playSound(1);
							game.loadMsg();
							GameState state = new GameState(handler, game);
							state.reset();
							State.setCurrentState(state);
							return;
						}
					}
					
					index++;
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {			
		g.drawImage(handler.getAssets().getLoadedImages()[16], 0, 0, handler.getWindowWidth(), handler.getWindowHeight(), null);	
		
		back.render(g);
		
		int index = 0;
		
		for(Game game : games) {
			if(index * width * 1.5f - offset <= handler.getWindowWidth() &&
					index * width * 1.5f - offset + width >= 0)
				game.renderAnimation(g, index, width, height, offset);
			index++;
		}
		
		g.setColor(Color.yellow);
		for(Button b : buttons) {
			b.render(g);
		}
	}
	
	private void setButtons() {
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].moveTo((int)(i * width * 1.5f - offset), (int) (handler.getWindowHeight() / 2 - height / 2));
		}
	}
	
	@Override
	public void load() {				
		games = new Game[4];
		buttons = new Button[games.length];
		
		handler.getAssets().loadSelectState();
		
		back = new Button(100, handler.getWindowHeight() - 300, 300, 300, handler.getAssets().getLoadedImages()[17], handler) {
			@Override
			public void onClick() {
				State.setCurrentState(handler.getLoop().getMenuState());
			}
		};
		
		BufferedImage[] frames = new BufferedImage[4];
		frames[0] = handler.getAssets().getLoadedImages()[0];
		frames[1] = handler.getAssets().getLoadedImages()[1];
		frames[2] = handler.getAssets().getLoadedImages()[2];
		frames[3] = handler.getAssets().getLoadedImages()[3];
		Animation hangMan = new Animation(frames, 250);
		games[0] = new HangMan(handler, hangMan, 0);
		
		
		frames = new BufferedImage[4];
		frames[0] = handler.getAssets().getLoadedImages()[4];
		frames[1] = handler.getAssets().getLoadedImages()[5];
		frames[2] = handler.getAssets().getLoadedImages()[6];
		frames[3] = handler.getAssets().getLoadedImages()[7];
		Animation jigsaw = new Animation(frames, 250);
		games[1] = new Jigsaw(handler, jigsaw, 1);
		
		frames = new BufferedImage[4];
		frames[0] = handler.getAssets().getLoadedImages()[8];
		frames[1] = handler.getAssets().getLoadedImages()[9];
		frames[2] = handler.getAssets().getLoadedImages()[10];
		frames[3] = handler.getAssets().getLoadedImages()[11];
		Animation maze = new Animation(frames, 250);
		games[2] = new Maze(handler, maze, 2);

		frames = new BufferedImage[4];
		frames[0] = handler.getAssets().getLoadedImages()[12];
		frames[1] = handler.getAssets().getLoadedImages()[13];
		frames[2] = handler.getAssets().getLoadedImages()[14];
		frames[3] = handler.getAssets().getLoadedImages()[15];
		Animation shooting = new Animation(frames, 250);
		games[3] = new Shooting(handler, shooting, 3);
		
		for(int i = 0; i < buttons.length; i++) {
			buttons[i] = new Button(0, 0, 80, 80, handler.getAssets().getLoadedImages()[18], handler) {
				@Override
				public void onClick() {
					setClickedButton(true);
					setActive(false);
					
					games[getIndex()].changeDescription();
				}
			};
			buttons[i].setIndex(i);
		}
		
		setButtons();
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	public void setClickedButton(boolean clickedButton) {
		this.clickedButton = clickedButton;
	}

	public Button[] getButtons() {
		return buttons;
	}
}

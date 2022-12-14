package buba.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import buba.main.Assets.Assets;
import buba.main.Sounds.SoundManager;
import buba.main.gfx.Display;
import buba.main.input.MouseManager;
import buba.main.states.MenuState;
import buba.main.states.SelectState;
import buba.main.states.State;

public class Loop implements Runnable{

	private Thread thread;
	
	private boolean running = false;
	
	private Handler handler;
	
	//display
	private final int WIDTH = 1920, HEIGHT = 1080;
	private final String TITLE = "Prawa Obywatelskie";
	private BufferStrategy bs;
	private Graphics g;
	
	private Display display;
	
	//states
	
	private SelectState selectState;
	private State menuState;
	
	//input
	
	private MouseManager mouseManager;
	
	//Assets
	
	private Assets assets;
	
	private SoundManager sound;
	
	//messages
	
	private String[] messages;
	
	public Loop() {
	
	}
	
	public void tick() {
		if(State.getCurrentState() == null)
			return;
		State.getCurrentState().tick();
	}
	
	public void render() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null)
		{
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		g.clearRect(0, 0, WIDTH, HEIGHT);
		
		//Draw
		
		State.getCurrentState().render(g);
		
		//End
		
		bs.show();
		g.dispose();
	}
	
	public void init() {
		display = new Display(TITLE, WIDTH, HEIGHT);
		
		sound = new SoundManager();
		assets = new Assets();
		
		mouseManager = new MouseManager();
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		display.getJFrame().addMouseListener(mouseManager);
		display.getJFrame().addMouseMotionListener(mouseManager);
		
		handler = new Handler(this);
		
		selectState = new SelectState(handler);
		menuState = new MenuState(handler);
		
		State.setCurrentState(menuState);

		loadMsg();
	}
	
	@Override
	public void run() {
		int fps = 60;
		//double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		
		long timePerTick = 1000 / fps;
		
		init();
		
		while(running)
		{
			/*now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			lastTime = now;
			
			if(delta >= 0)
			{
				tick();
				render();
				
				delta--;
			}*/
			
			try {
				Thread.sleep(timePerTick);
				
				tick();
				render();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		stop();
	}
	
	public void startLoop() {
		thread = new Thread(this);
		running = true;
		
		thread.start();
	}
	
	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadMsg() {
		String m = "G??sto???? zaludnienia wynosi 123 os??b/km2\r\n"
				+ "Najd??u??sz?? rzek?? jest Wis??a - 1047 km\r\n"
				+ "Ustr??j polityczny ??? demokracja parlamentarna\r\n"
				+ "Polsk?? zamieszkuje ponad 38 500 000 os??b\r\n"
				+ "Najwy??szym szczytem s?? Rysy - 2499 m n.p.m.\r\n"
				+ "W Polsce jest 908 miast\r\n"
				+ "17 Polak??w zosta??o laureatami nagrody Nobla\r\n"
				+ "A?? 90% os??b uko??czy??o minimum szko???? ??redni??\r\n"
				+ "Maria Curie Sk??odowska, jako jedyna osoba uhonorowana zosta??a nagrod?? Nobla w dw??ch r????nych dziedzinach nauk\r\n"
				+ "Miko??aj Kopernik, jako pierwszy odkry??, ??e Ziemia nie jest centrum wszech??wiata\r\n"
				+ "W Polsce jest najwi??cej w Europie gospodarstw rolnych\r\n"
				+ "Jednym z najwybitniejszych Polak??w by?? Papie?? ??? Jan Pawe?? II\r\n"
				+ "We wsi Pi??tek, 33 km od ??odzi znajduje si?? dok??adny ??rodek Polski\r\n"
				+ "Najstarszym drzewem w Polsce i prawdopodobnie w Europie jest cis w Henrykowie Luba??skim maj??cy oko??o 1500 lat\r\n"
				+ "W miejscowo??ci Tychowo znajduje si?? najwi??kszy w Polsce kamie?? wa????cy oko??o 200 ton i maj??cy 44 metry ??rednicy\r\n"
				+ "G??ry ??wi??tokrzyskie s?? najstarszymi g??rami w Europie\r\n"
				+ "Pustynia B????dowska jest jedyn?? naturaln?? pustyni?? w Europie\r\n"
				+ "prawo do posiadania w??asno??ci oraz prawo do dziedziczenia\r\n"
				+ "swoboda dzia??alno??ci gospodarczej\r\n"
				+ "wolno???? wyboru miejsca pracy i wykonywania zawodu\r\n"
				+ "prawo do bezpiecznych i higienicznych warunk??w pracy\r\n"
				+ "prawo do zabezpieczenia spo??ecznego w razie niezdolno??ci do pracy\r\n"
				+ "prawo do ochrony zdrowia\r\n"
				+ "prawo do bezp??atnej nauki\r\n"
				+ "prawo do ochrony prawnej rodziny i praw dziecka\r\n"
				+ "wolno???? tw??rczo??ci artystycznej i bada?? naukowych oraz og??aszania ich wynik??w\r\n"
				+ "wolno???? nauczania\r\n"
				+ "wolno???? korzystania z d??br kultury\r\n"
				+ "prawo do ??ycia\r\n"
				+ "nietykalno???? i wolno???? osobista\r\n"
				+ "prawo do sprawiedliwego i jawnego procesu\r\n"
				+ "prawo do prywatno??ci\r\n"
				+ "prawo rodzic??w do wychowania dzieci w zgodzie z w??asnymi przekonaniami\r\n"
				+ "prawo do wolno??ci oraz ochrony tajemnicy komunikowania si??\r\n"
				+ "prawo do nienaruszalno??ci mieszkania\r\n"
				+ "wolno???? poruszania si?? po terytorium kraju oraz prawo do wyjazdu za granic??\r\n"
				+ "wolno???? wyra??ania pogl??d??w oraz rozpowszechniania informacji\r\n"
				+ "wolno???? sumienia i religii";
		messages = m.split("\r\n");
	}
	
	public String[] getMessages() {
		return messages;
	}

	public Display getDisplay() {
		return display;
	}
	
	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public SelectState getSelectState() {
		return selectState;
	}

	public State getMenuState() {
		return menuState;
	}

	public Assets getAssets() {
		return assets;
	}

	public SoundManager getSound() {
		return sound;
	}
}

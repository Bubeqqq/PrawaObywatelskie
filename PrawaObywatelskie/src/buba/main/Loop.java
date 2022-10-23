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
		String m = "Gęstość zaludnienia wynosi 123 osób/km2\r\n"
				+ "Najdłuższą rzeką jest Wisła - 1047 km\r\n"
				+ "Ustrój polityczny – demokracja parlamentarna\r\n"
				+ "Polskę zamieszkuje ponad 38 500 000 osób\r\n"
				+ "Najwyższym szczytem są Rysy - 2499 m n.p.m.\r\n"
				+ "W Polsce jest 908 miast\r\n"
				+ "17 Polaków zostało laureatami nagrody Nobla\r\n"
				+ "Aż 90% osób ukończyło minimum szkołę średnią\r\n"
				+ "Maria Curie Skłodowska, jako jedyna osoba uhonorowana została nagrodą Nobla w dwóch różnych dziedzinach nauk\r\n"
				+ "Mikołaj Kopernik, jako pierwszy odkrył, że Ziemia nie jest centrum wszechświata\r\n"
				+ "W Polsce jest najwięcej w Europie gospodarstw rolnych\r\n"
				+ "Jednym z najwybitniejszych Polaków był Papież – Jan Paweł II\r\n"
				+ "We wsi Piątek, 33 km od Łodzi znajduje się dokładny środek Polski\r\n"
				+ "Najstarszym drzewem w Polsce i prawdopodobnie w Europie jest cis w Henrykowie Lubańskim mający około 1500 lat\r\n"
				+ "W miejscowości Tychowo znajduje się największy w Polsce kamień ważący około 200 ton i mający 44 metry średnicy\r\n"
				+ "Góry Świętokrzyskie są najstarszymi górami w Europie\r\n"
				+ "Pustynia Błędowska jest jedyną naturalną pustynią w Europie\r\n"
				+ "prawo do posiadania własności oraz prawo do dziedziczenia\r\n"
				+ "swoboda działalności gospodarczej\r\n"
				+ "wolność wyboru miejsca pracy i wykonywania zawodu\r\n"
				+ "prawo do bezpiecznych i higienicznych warunków pracy\r\n"
				+ "prawo do zabezpieczenia społecznego w razie niezdolności do pracy\r\n"
				+ "prawo do ochrony zdrowia\r\n"
				+ "prawo do bezpłatnej nauki\r\n"
				+ "prawo do ochrony prawnej rodziny i praw dziecka\r\n"
				+ "wolność twórczości artystycznej i badań naukowych oraz ogłaszania ich wyników\r\n"
				+ "wolność nauczania\r\n"
				+ "wolność korzystania z dóbr kultury\r\n"
				+ "prawo do życia\r\n"
				+ "nietykalność i wolność osobista\r\n"
				+ "prawo do sprawiedliwego i jawnego procesu\r\n"
				+ "prawo do prywatności\r\n"
				+ "prawo rodziców do wychowania dzieci w zgodzie z własnymi przekonaniami\r\n"
				+ "prawo do wolności oraz ochrony tajemnicy komunikowania się\r\n"
				+ "prawo do nienaruszalności mieszkania\r\n"
				+ "wolność poruszania się po terytorium kraju oraz prawo do wyjazdu za granicę\r\n"
				+ "wolność wyrażania poglądów oraz rozpowszechniania informacji\r\n"
				+ "wolność sumienia i religii";
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

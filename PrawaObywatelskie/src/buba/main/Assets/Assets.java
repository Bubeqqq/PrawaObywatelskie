package buba.main.Assets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Assets {

	private BufferedImage[] loadedImages;
	
	public void loadSelectState() {
		loadedImages = new BufferedImage[20];
		
		SpriteSheet hangman = new SpriteSheet(ImageLoader.LoadImage("/SelectState/hangMan.png"));
		
		loadedImages[0] = hangman.crop(0, 0, hangman.getSheet().getWidth() / 2, hangman.getSheet().getHeight() / 2);
		loadedImages[1] = hangman.crop(hangman.getSheet().getWidth() / 2, 0, hangman.getSheet().getWidth() / 2, hangman.getSheet().getHeight() / 2);
		loadedImages[2] = hangman.crop(0, hangman.getSheet().getHeight() / 2, hangman.getSheet().getWidth() / 2, hangman.getSheet().getHeight() / 2);
		loadedImages[3] = hangman.crop(hangman.getSheet().getWidth() / 2, hangman.getSheet().getHeight() / 2, hangman.getSheet().getWidth() / 2, hangman.getSheet().getHeight() / 2);

		SpriteSheet zdania = new SpriteSheet(ImageLoader.LoadImage("/SelectState/uoz_zdania.png"));
		
		loadedImages[4] = zdania.crop(0, 0, hangman.getSheet().getWidth() / 2, hangman.getSheet().getHeight() / 2);
		loadedImages[5] = zdania.crop(hangman.getSheet().getWidth() / 2, 0, hangman.getSheet().getWidth() / 2, hangman.getSheet().getHeight() / 2);
		loadedImages[6] = zdania.crop(0, hangman.getSheet().getHeight() / 2, hangman.getSheet().getWidth() / 2, hangman.getSheet().getHeight() / 2);
		loadedImages[7] = zdania.crop(hangman.getSheet().getWidth() / 2, hangman.getSheet().getHeight() / 2, hangman.getSheet().getWidth() / 2, hangman.getSheet().getHeight() / 2);
		
		SpriteSheet maze = new SpriteSheet(ImageLoader.LoadImage("/SelectState/maze.png"));
		
		loadedImages[8] = maze.crop(0, 0, hangman.getSheet().getWidth() / 2, hangman.getSheet().getHeight() / 2);
		loadedImages[9] = maze.crop(hangman.getSheet().getWidth() / 2, 0, hangman.getSheet().getWidth() / 2, hangman.getSheet().getHeight() / 2);
		loadedImages[10] = maze.crop(0, hangman.getSheet().getHeight() / 2, hangman.getSheet().getWidth() / 2, hangman.getSheet().getHeight() / 2);
		loadedImages[11] = maze.crop(hangman.getSheet().getWidth() / 2, hangman.getSheet().getHeight() / 2, hangman.getSheet().getWidth() / 2, hangman.getSheet().getHeight() / 2);
	
		SpriteSheet shooting = new SpriteSheet(ImageLoader.LoadImage("/SelectState/shooting.png"));

		loadedImages[12] = shooting.crop(0, 0, hangman.getSheet().getWidth() / 2, hangman.getSheet().getHeight() / 2);
		loadedImages[13] = shooting.crop(hangman.getSheet().getWidth() / 2, 0, hangman.getSheet().getWidth() / 2, hangman.getSheet().getHeight() / 2);
		loadedImages[14] = shooting.crop(0, hangman.getSheet().getHeight() / 2, hangman.getSheet().getWidth() / 2, hangman.getSheet().getHeight() / 2);
		loadedImages[15] = shooting.crop(hangman.getSheet().getWidth() / 2, hangman.getSheet().getHeight() / 2, hangman.getSheet().getWidth() / 2, hangman.getSheet().getHeight() / 2);
	
		loadedImages[16] = ImageLoader.LoadImage("/SelectState/to_start.jpg");
		loadedImages[17] = ImageLoader.LoadImage("/SelectState/ikona_powrot.png");
		loadedImages[18] = ImageLoader.LoadImage("/SelectState/kolko_informacje.png");
		loadedImages[19] = ImageLoader.LoadImage("/SelectState/table.png");
	}
	
	public void loadHangman() {
		loadedImages = new BufferedImage[13];
		
		loadedImages[0] = ImageLoader.LoadImage("/Hangman/tlo_wisielec.jpg");
		
		for(int i = 1; i < 12; i++) {
			loadedImages[i] = ImageLoader.LoadImage("/Hangman/Hangman/wisielec" + i + ".png");
		}
		
		loadedImages[12] = ImageLoader.LoadImage("/Hangman/ramka_wisielec.png");
	}
	
	public void loadMaze() {
		loadedImages = new BufferedImage[16];
		
		loadedImages[0] = ImageLoader.LoadImage("/Maze/Car/auto.png");
		loadedImages[1] = ImageLoader.LoadImage("/Maze/Car/auto2.png");
		loadedImages[2] = ImageLoader.LoadImage("/Maze/Car/auto3.png");
		loadedImages[3] = ImageLoader.LoadImage("/Maze/Car/auto4.png");
		
		for(int i = 0; i <= 1; i++) {
			loadedImages[i + 4] = ImageLoader.LoadImage("/Maze/droga.jpg");
			Graphics2D g = loadedImages[i + 4].createGraphics();
			g.rotate(Math.toRadians(i * 90), loadedImages[i + 4].getWidth() / 2, loadedImages[i + 4].getHeight() / 2);
			g.drawImage(loadedImages[i + 4], null, 0, 0);
		}
		
		loadedImages[11] = ImageLoader.LoadImage("/Maze/ramka_labirynt.png");
		
		for(int i = 1; i < 6; i++) {
			loadedImages[i + 5] = ImageLoader.LoadImage("/Maze/Buildings/bud" + i + ".jpg");
		}
		
		loadedImages[12] = ImageLoader.LoadImage("/Maze/Arrow/stszauka.png");
		loadedImages[13] = ImageLoader.LoadImage("/Maze/Arrow/stszaukadul.png");
		loadedImages[14] = ImageLoader.LoadImage("/Maze/Arrow/stszaukalewo.png");
		loadedImages[15] = ImageLoader.LoadImage("/Maze/Arrow/stszaukaq.png");
	}
	
	public void loadShooting() {
		loadedImages = new BufferedImage[3];
		loadedImages[0] = ImageLoader.LoadImage("/Shooting/player.png");
		loadedImages[1] = ImageLoader.LoadImage("/Shooting/pocisk.png");
		loadedImages[2] = ImageLoader.LoadImage("/Shooting/tlo_docelu.jpg");
	}

	public void loadJigsaw() {
		loadedImages = new BufferedImage[2];
		loadedImages[0] = ImageLoader.LoadImage("/Jigsaw/uloz_zdania_tlo.jpg");
		loadedImages[1] = ImageLoader.LoadImage("/Jigsaw/magnes_do_ukladania.png");
	}
	
	public BufferedImage[] getLoadedImages() {
		return loadedImages;
	}
	
	public void loadMenu() {
		loadedImages = new BufferedImage[5];
		
		loadedImages[0] = ImageLoader.LoadImage("/Menu/to_menu.jpg");
		loadedImages[1] = ImageLoader.LoadImage("/Menu/play.png");
		loadedImages[2] = ImageLoader.LoadImage("/Menu/tytul.png");
		loadedImages[3] = ImageLoader.LoadImage("/Menu/dzwiekoff.png");
		loadedImages[4] = ImageLoader.LoadImage("/Menu/dzwiekon.png");
	}
}

package main;

import states.Playing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import states.GameStates;
import states.Menu;

public class Game implements Runnable {
	
	public final static float SCALE = 1.0f;
	
	// values tbd
	public final static int GAME_WIDTH = (int)(1280*Game.SCALE);
	public final static int GAME_HEIGHT = (int)(800*Game.SCALE);
	
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 120;
	
	private Playing playing;
	private Menu menu;
	
	// Main Game Constructor
	public Game() {
		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		startGameLoop();

	}
	
	private void initClasses() {
		menu = new Menu(this);
		playing = new Playing(this);
	}
	
	
	//Begins main Loop on a seperate thread
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void update() {
		switch(GameStates.state) {
		case MENU:
			menu.update();
			break;
		case PLAYING:
			playing.update();
			break;
		default:
			break;
		}
	}
	
	public void render(Graphics g, BufferedImage img) {
		switch(GameStates.state) {
		case MENU:
			menu.draw(g);
			break;
		case PLAYING:
			playing.draw(g, img);
			break;
		default:
			break;
		}
	}

	@Override
	public void run() {
		//FPS Counter - [Don't change, will condense this next commit]
		double timePerFrame = 1000000000.0/FPS_SET; //A billion s
		long lastFrame = System.nanoTime();
		long now = System.nanoTime();
		int frames = 0;
		long lastCheck = System.currentTimeMillis();
		while(true) {
			
			now = System.nanoTime();
			if(System.nanoTime() - lastFrame >= timePerFrame) {
				
				gamePanel.repaint();
				lastFrame = System.nanoTime();
				frames++;
			}
			if (System.currentTimeMillis() - lastCheck >=1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
	}
	
	public Playing getPlaying() {
		return playing;
	}
	
	public Menu getMenu() {
		return menu;
	}
}

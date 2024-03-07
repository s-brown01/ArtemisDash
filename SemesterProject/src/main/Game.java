
package main;

import states.Playing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.Player;
import states.GameStates;
import states.Menu;
import states.Overworld;

public class Game implements Runnable {

    public final static float SCALE = 1.0f;

    // values tbd
    public final static int GAME_WIDTH = (int) (1280 * Game.SCALE);
    public final static int GAME_HEIGHT = (int) (800 * Game.SCALE);

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Playing playing;
    private Menu menu;
    private Overworld overworld;
    private Player player;

    // Main Game Constructor
    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        gameWindow = new GameWindow(gamePanel);
        startGameLoop();

    }
    /**
     * Initializes each game state to be used when called
     */
    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
        overworld = new Overworld(this);
        player = new Player(100,100,100,100);

    }

    /**
     * Begins the main loop on a seperate thread
     */
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Calls for updates based on what state the game is in
     */
    public void update() {
        switch (GameStates.state) {
        case MENU:
            menu.update();
            break;
        case OVERWORLD:
            overworld.update();
            break;
        case PLAYING:
            playing.update();
            break;
        default:
            break;
        }
    }

    /**
     * Sets the currently drawn screen based on what state 
     * the game is in
     * @param g
     */
    public void render(Graphics g) {
        switch (GameStates.state) {
        case MENU:
            menu.draw(g);
            break;
        case OVERWORLD:
            overworld.draw(g);
            break;
        case PLAYING:
            playing.draw(g);
            break;
        default:
            break;
        }
    }

    /**
     * Handles the logical aspects of the game, such as 
     * Updates and Frames per second
     */
    @Override
    public void run() {
        // FPS Counter - [Don't change, will condense this next commit]
        double timePerFrame = 1000000000.0 / FPS_SET; // A billion seconds
        double timePerUpdate = 1000000000.0 / UPS_SET;//

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0; // deltaFrames

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Menu getMenu() {
        return menu;
    }

    public Overworld getOverworld() {
        return overworld;
    }
    public Player getPlayer() {
        return player;
    }
	
    public void windowFocusLost() {
    	playing.resetDirBooleans();
		System.out.println("CLICKED OUT OF WINDOW - windowFocusLost()");
	}
}

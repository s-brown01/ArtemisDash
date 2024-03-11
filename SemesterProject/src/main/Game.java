
package main;

import states.Playing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import levels.LevelManager;
import states.GameStates;
import states.Menu;
import states.Overworld;

public class Game implements Runnable {

    public final static int TILES_DEFAULT_SIZE = 45;
    public final static float SCALE = 1.0f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Playing playing;
    private Menu menu;
    private Overworld overworld;
    // private Player player;
    private LevelManager levelManager;

    /**
     * Main Game Constructor
     */
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
        // player = new Player(100, 100, 100, 100);
        levelManager = new LevelManager(this);

    }

    /**
     * <<<<<<< HEAD Begins the main loop on a separate thread Done to dedicate a specific
     * thread to free up logical traffic ======= Begins the main loop on a separate thread
     * Done to dedicate a specific thread to free up logical traffic >>>>>>> main
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
            levelManager.update();
            break;
        case OVERWORLD:
            overworld.update();
            levelManager.update();
            break;
        case PLAYING:
            playing.update();
            levelManager.update();
            break;
        default:
            break;
        }
    }

    /**
     * Sets the currently drawn screen based on what state the game is in
     * 
     * @param g
     */
    public void render(Graphics g) {
        switch (GameStates.state) {
        case MENU:
            menu.draw(g);
//TESTING:             levelManager.draw(g);
            break;
        case OVERWORLD:
            overworld.draw(g);
//TESTING:             levelManager.draw(g);
            break;
        case PLAYING:
            levelManager.draw(g);
            playing.draw(g);
            break;
        default:
            break;
        }
    }

    /**
     * <<<<<<< HEAD Handles the update aspects of the game, such as updates to logical
     * processes and frames per second ======= Handles the update aspects of the game, such as
     * updates to logical processes and frames per second >>>>>>> main
     */
    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS_SET; // A billion seconds
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaUpdates = 0;
        double deltaFrames = 0; // deltaFrames

        while (true) {
            long currentTime = System.nanoTime();

            deltaUpdates += (currentTime - previousTime) / timePerUpdate;
            deltaFrames += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaUpdates >= 1.0) {
                update();
                updates++;
                deltaUpdates--;
            }

            if (deltaFrames >= 1.0) {
                gamePanel.repaint();
                frames++;
                deltaFrames--;
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

    /**
<<<<<<< HEAD
     * Action to take when the window focus is lost, Be it due to misclick, OS update, or
     * otherwise
     */
    public void windowFocusLost() {
        playing.resetDirBooleans();
        System.out.println("CLICKED OUT OF WINDOW - windowFocusLost()");
=======
     * Action to take when the window focus is lost, Be it due to misclick, OS
     * update, or otherwise
     */

    public void windowFocusLost() {
	playing.resetDirBooleans();
	System.out.println("CLICKED OUT OF WINDOW - windowFocusLost()");
>>>>>>> main
    }
}

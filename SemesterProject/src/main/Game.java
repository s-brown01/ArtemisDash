package main;

import java.awt.Graphics;

import entities.Player;
import states.*;
import levels.LevelManager;

/**
 * Game Class
 * 
 * @author johnbotonakis and Sean-Paul Brown This class handle most logic pertaining to
 *         the game, including updates, FPS, level scale, and tile amount on screen.
 */
public class Game implements Runnable {
    // States and Entities
    private LevelManager levelManager;
    private Playing playing;
    private Menu menu;
    private Overworld overworld;

    // Windows and Panels
    private GameWindow gameWindow;
    private GamePanel gamePanel;

    // Updates and Frame Logic
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    // Game Scale and tiles
    public final static int TILES_DEFAULT_SIZE = 32; // 32 x 32 tile size
    public final static float SCALE = 1.75f; // How much should everything get scaled by? KEEP THIS ROUND
    public final static int TILES_IN_WIDTH = 26; // How many tiles width-wise should be drawn?
    public final static int TILES_IN_HEIGHT = 14; // How many tiles height-wise should be drawn?
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);// 32 *1.75 = 56
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH; // 56 * 26 = 1456
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT; // 56 * 14 = 448
    public final static float GAME_BUFFER = (20 / 1.75f * Game.SCALE);

    /**
     * Main Game Constructor
     */
    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        gameWindow = new GameWindow(gamePanel);
        startGame();
    }

    /**
     * Begins the main loop on a separate thread Done to dedicate a specific thread to free up
     * logical traffic
     */
    private void startGame() {
        gameThread = new Thread(this);
        gameThread.start();// needs to be LAST
    }

    /**
     * Initializes each game state to be used when called
     */
    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
        overworld = new Overworld(this);
    }

    /**
     * Handles the update aspects of the game, such as updates to logical processes and frames
     * per second
     */
    @Override
    public void run() {
        // How long each frame will last; 1 billion nano seconds = 1 second
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;
        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;

        double deltaUpdates = 0;
        double deltaFrames = 0;
        long lastCheck = System.currentTimeMillis();

        while (true) {
            long currentTime = System.nanoTime();
            deltaUpdates += (currentTime - previousTime) / timePerUpdate;
            deltaFrames += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaUpdates >= 1) {
                updateGameState();
                updates++;
                deltaUpdates--;
            }

            if (deltaFrames >= 1) {
                gamePanel.repaint();
                frames++;
                deltaFrames--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
//                System.out.println("FPS: " + frames + " | UPDATES: " + updates); //TESTING: Ensure FPS and UPS are working properly
                frames = 0;
                updates = 0;
            }
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
            break;
        case PLAYING:
            playing.draw(g);
            break;
        case OVERWORLD:
            overworld.draw(g);
            break;
        case OPTIONS:
            break;
        case QUIT:
            System.exit(-1);
            break;
        }
    }

    /**
     * Updates the game state
     */
    public void updateGameState() {
        switch (GameStates.state) {
        case MENU:
            menu.update();
            break;
        case PLAYING:
            playing.update();
            break;
        case OVERWORLD:
            overworld.update();
            break;
        case OPTIONS:
            break;
        case QUIT:
            System.exit(-1);
        }
    }

    /**
     * When window focus is lost, stop the player immediately
     */
    public void windowLost() {
        playing.getPlayer().resetDirBools();
    }

    // Getters and setters
    public Player getPlayer() {
        return playing.getPlayer();
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Overworld getOverworld() {
        return overworld;
    }
}

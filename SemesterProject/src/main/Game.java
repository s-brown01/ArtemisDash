
package main;

import states.Playing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entities.Player;
import levels.LevelManager;
import states.GameStates;
import states.Menu;
import states.Overworld;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Playing playing;
    private Menu menu;
    private Overworld overworld;

    public final static int TILES_DEFAULT_SIZE = 45;
    public final static float SCALE = 1.0f;
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    /**
     * Main Game Constructor
     */
    public Game() {
        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();

        startGameLoop();

    }

    /**
     * Initializes each game state to be used when called
     */
    private void initClasses() {

        menu = new Menu(this);
        overworld = new Overworld(this);
        playing = new Playing(this);
    }

    /**
     * Begins the main loop on a separate thread Done to dedicate a specific thread to free up
     * logical traffic
     */
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    //Updates the game logic based on the current game state
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
     * Sets the currently drawn screen based on what state the game is in
     * 
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
     * Handles the update aspects of the game, such as updates to logical processes and frames
     * per second
     */
    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

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

    /**
     * Action to take when the window focus is lost, Be it due to misclick, OS update, or
     * otherwise
     */
    public void windowFocusLost() {
        if (GameStates.state == GameStates.PLAYING)
            playing.getPlayer().resetDirBooleans();
    }
}

package main;

import java.awt.Graphics;

import gameaudio.AudioPlayer;
import states.GameStates;
import states.Menu;
import states.Instructions;
import states.Overworld;
import states.Playing;

/**
 * This class handle most logic pertaining to the game, including updates, FPS, level
 * scale, and tile amount on screen.
 * 
 * @author John Botonakis and Sean-Paul Brown
 */
public class Game implements Runnable {
    /*
     * States
     */
    /**
     * This is the Playing class that will show all of the levels 
     */
    private Playing playing;
    /**
     * This Menu class will be the main menu of the game
     */
    private Menu menu;
    /**
     * This Overworld class will show all of the levels available for selection in the game
     */
    private Overworld overworld;
    /**
     * This is the AudioPlayer that will play all of the audio in every state of the game
     */
    private AudioPlayer audioPlayer;
    /**
     * This will show the options menu
     */
    private Instructions instructions;

    /**
     * The GamePanel where to draw the Game
     */
    private GamePanel gamePanel;

    /*
     * Updates and Frame Logic
     */
    /**
     * This is Thread that the main game will be running on
     */
    private Thread gameThread;
    /**
     * This is how many Frames Per Second there will be in game, how often the draw function is called.
     */
    private final int FPS_SET = 120;
    /**
     * This is how many Updates Per Second there will be in game, how often the update function is called.
     */
    private final int UPS_SET = 200;
    /**
     *  This is the amount of nanoseconds in 1 second: 1 Billion nanoseconds. This is used in the run method instead of the real number so it is easier to read
     */
    private final double NANOSECONDS_IN_SEC = 1000000000.0;

    /*
     * All variables specific to the game: Game Scale and tiles and buffer
     */
    /**
     * How tall and wide each tile is (they should be squares) in pixels
     */
    public final static int TILES_DEFAULT_SIZE = 32;
    /**
     * This allows the game to be scaled to different screen sizes
     */
    public final static float SCALE = 1.75f;
    /**
     * This is the amount of tiles wide the game is, how many tiles should be drawn
     * horizontally
     */
    public final static int TILES_IN_WIDTH = 26;
    /**
     * This is the amount of tiles tall the game is, how many tiles should be drawn vertically
     */
    public final static int TILES_IN_HEIGHT = 14;
    /**
     * The size of the tiles scaled with the Game
     */
    public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    /**
     * The amount of pixels wide that the game is (already scaled)
     */
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    /**
     * The amount of pixels tall that the game is (already scaled)
     */
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
    /**
     * the amount of pixels there are before "falling" out of the world.
     */
    public final static float PIT_DEPTH = GAME_HEIGHT - 10 * SCALE;

    /**
     * The constructor for this class, once this is called then the game will start running.
     */
    public Game() {
        initClasses();
        gamePanel = new GamePanel(this);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        new GameWindow(gamePanel);
    }

    /**
     * This needs to be called in order for the Game to be started.
     * 
     * <BR>
     * Begins the main loop on a separate thread Done to dedicate a specific thread to free up
     * logical traffic
     */
    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();// needs to be LAST
    }

    /**
     * Initializes each of the states in a specific order.
     */
    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
        // overworld needs to be created AFTER the playing class since it uses the playing's
        // levelManager
        instructions = new Instructions(this);
        audioPlayer = new AudioPlayer();
        overworld = new Overworld(this);
    }

    /**
     * Handles the update aspects of the game, such as updates to logical processes and frames
     * per second
     */
    @Override
    public void run() {
        // How long each frame/update should take in nanoseconds
        double timePerFrame = NANOSECONDS_IN_SEC / FPS_SET;
        double timePerUpdate = NANOSECONDS_IN_SEC / UPS_SET;
        long previousTime = System.nanoTime();

        // this is the time that it has been the last update
        double deltaUpdates = 0;
        // this is the time that it has been the last frame
        double deltaFrames = 0;

        // while the game is running, so this should be an infinite loop
        while (true) {
            // get the current time in nano seconds
            long currentTime = System.nanoTime();
            // add the time since the last check to the delta's
            deltaUpdates += (currentTime - previousTime) / timePerUpdate;
            deltaFrames += (currentTime - previousTime) / timePerFrame;
            // this time is now the last checked time
            previousTime = currentTime;

            // if the delta update time is >= 1, then update the game
            if (deltaUpdates >= 1) {
                updateGameState();
                // only subtract 1 from the updates, so if it is above 1, that extra time is accounted for
                // in the next update
                deltaUpdates--;
            }

            // if the delta frame time is >= 1, then repaint the game
            if (deltaFrames >= 1) {
                gamePanel.repaint();
                // only subtract 1 from the frame, so if it is above 1, that extra time is accounted for
                // in the next frame
                deltaFrames--;
            }

        }
    }

    /**
     * Sets the currently drawn screen based on what state the game is in (from
     * GameStates.state)
     * 
     * @param g - the Graphics where to draw the screens
     */
    public void render(Graphics g) {
        // draw whatever the current GameStates is
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
        case INSTRUCTIONS:
            instructions.draw(g);
            break;
        // if quitting, then exit the system
        case QUIT:
            System.exit(-1);
            break;
        }
    }

    /**
     * Updates the current game state based on the GameStates.state
     */
    public void updateGameState() {
        // update whatever the current GameState is
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
        case INSTRUCTIONS:
            instructions.update();
            break;
        case QUIT:
            // if quitting, then exit the system
            System.exit(-1);
            break;
        }
    }

    /**
     * This function handles when the Game loses system-focus. It will pause the game when
     * Playing.
     */
    public void windowLost() {
        // only reset play bools if on the playing
        if (GameStates.state == GameStates.PLAYING) {
            playing.getPlayer().resetDirBools();
            playing.setPaused(true);
        }
    }

    /**
     * Getter for the Menu GameState
     * 
     * @return the Menu state of the Game
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * Getter for the Playing GameState
     * 
     * @return the Playing state of the game
     */
    public Playing getPlaying() {
        return playing;
    }

    /**
     * Getter for the Overworld GameState
     * 
     * @return the Overworld state of the game
     */
    public Overworld getOverworld() {
        return overworld;
    }

    /**
     * Getter for the Options GameState
     * 
     * @return the Options state of the game
     */
    public Instructions getInstructions() {
        return instructions;
    }

    /**
     * Getter for the AudioPlayer, class responsible for playing audio
     * 
     * @return the AudioPlayer object
     */
    public AudioPlayer getAudioPlayer() {
        return audioPlayer;

    }

}

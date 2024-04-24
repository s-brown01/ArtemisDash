package states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import entities.EnemyManager;
import entities.Player;
import gameaudio.AudioPlayer;
import levels.LevelManager;
import main.Game;
import projectiles.ProjectileManager;
import ui.DeathOverlay;
import ui.DemoOverlay;
import ui.HUD;
import ui.PauseOverlay;
import ui.WinOverlay;
import utils.Constants.BackgroundStates;
import static utils.Constants.PlayerStates.IMAGE_WIDTH;
import static utils.Constants.PlayerStates.IMAGE_HEIGHT;
import utils.LoadSave;

/**
 * This is a child class of State that implements the StateMethods interface. This class
 * handles the core game loop of completing levels, it represents how a level will be
 * played by the user.
 * 
 * @author John Botonakis and Sean-Paul Brown
 */
public class Playing extends State implements StateMethods {

    // will keep track if the pause menu/ death overlay should be up or not
    /**
     * This will keep track of if the pause overlay should show
     */
    private boolean paused, 
    /**
     * This will keep track of if the levelComplete overlay should show
     */
    levelComplete, 
    /**
     * This will keep track of if the gameOver overlay should show
     */
    gameOver,
    /**
     * This will keep track of if the demo screen should be showing (true to show).
     */
    demoOver;
    
    /**
     * The Player entity that travels between levels
     */
    private Player player;
    /**
     * The Heads Up Display (HUD) that will be shown
     */
    private HUD hud;

    /**
     * The overlay for when the game is paused
     */
    private final PauseOverlay pauseOverlay = new PauseOverlay(game);
    /**
     * The manager that will load/handle each Level
     */
    private final LevelManager levelManager = new LevelManager(game);
    /**
     * The manager that will load/handle each enemy and if the level has been completed (every enemy killed)
     */
    private final EnemyManager enemyManager = new EnemyManager(this);
    /**
     * The manager that will load/handle each projectile and if they have collided with other Entities
     */
    private final ProjectileManager projManager = new ProjectileManager(this);
    /**
     * The overlay for when the Player is killed
     */
    private final DeathOverlay deathOverlay = new DeathOverlay(this);
    /**
     * The overlay for when the level is completed
     */
    private final WinOverlay winOverlay = new WinOverlay(this);
    /**
     * The overlay for when all 5 Demo levels are completed
     */
    private final DemoOverlay demoOverlay = new DemoOverlay(this);
    /**
     * the player's score
     */
    private int score;

    // Level Expansion vars
    /**
     * X-Offset being added to and subtracted from to render the level itself.
     */
    private int xLevelOffset;

    /**
     * 50% of the screen is rendered, used as the left border for screen rendering.
     */
    private int borderLeft = (int) (0.5 * Game.GAME_WIDTH);

    /**
     * 50% of the screen is hidden, used as the right border for screen rendering.
     */
    private int borderRight = (int) (0.5 * Game.GAME_WIDTH);

    /**
     * The length of the level in tiles.
     */
    private int levelTilesWide = levelManager.getCurrentLevel().getLevelData()[0].length;

    /**
     * The maximum tiles offset that can occur from screen scrolling.
     */
    private int maxTileOffset = levelTilesWide - Game.TILES_IN_WIDTH;

    /**
     * The maximum pixels offset that can occur from screen scrolling.
     */
    private int maxXOffset = maxTileOffset * Game.TILES_SIZE;

    /**
     * Y-Offset being added to and subtracted from to render the level itself.
     */
    private int yLevelOffset;

    /**
     * 50% of the screen is rendered, used as the top border for screen rendering.
     */
    private int borderTop = (int) (0.5 * Game.GAME_HEIGHT);

    /**
     * 50% of the screen is hidden, used as the bottom border for screen rendering.
     */
    private int borderBottom = (int) (0.5 * Game.GAME_HEIGHT);

    /**
     * The height of the level in tiles.
     */
    private int levelTilesHigh = levelManager.getCurrentLevel().getLevelData().length;

    /**
     * The maximum tiles offset that can occur from screen scrolling in the vertical direction.
     */
    private int maxYTileOffset = levelTilesHigh - Game.TILES_IN_HEIGHT;

    /**
     * The maximum pixels offset that can occur from screen scrolling in the vertical direction.
     */
    private int maxYOffset = maxYTileOffset * Game.TILES_SIZE;

    /**
     * the static image in the background
     */
    private BufferedImage backgroundimg, 
    /**
     * The background of they mist/clouds that slowly move by
     */
    background_myst_img, 
    /**
     * the rocks that scroll by as the Player moves
     */
    background_rocks;
    /**
     * all positions of myst background asset
     */
    private int[] mystPos;
    /**
     * A Random that will generate the mist positions
     */
    private Random rnd = new Random();

    /**
     * Runs the logic once the game state has switched to PLAYING Loads in the enemies,
     * backgrounds, and player
     * 
     * @param game - Game
     */
    public Playing(Game game) {
        super(game);
        initBackgroundAssets();
    }

    /**
     * This function will load a specific level based on the index given into the parameters
     * 
     * @param nextLevelIndex - the next level to be loaded in, index is the same as stage
     *                       number
     */
    public void nextLevel(int nextLevelIndex) {
        levelManager.setCurrentLevel(nextLevelIndex);
        loadCurrentLevel();
    }

    /**
     * Load in all of the enemies and other items in from the current Level. It will reset all
     * booleans to get the Level ready to play for the user
     */
    private void loadCurrentLevel() {
        player = new Player(200, 300, (int) (IMAGE_WIDTH * Game.SCALE), (int) (IMAGE_HEIGHT * Game.SCALE), this);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        projManager.reset();
        hud = new HUD(this);
        this.score = 0;
        levelComplete = levelManager.getCurrentLevel().getCompleted();
    }

    /**
     * Initialize the assets that compose the background for the given level Will dynamically
     * switch to another set of assets depending on the world
     */
    private void initBackgroundAssets() {
        // Myst positioning array
        mystPos = new int[8];
        for (int i = 0; i < mystPos.length; i++) {
            mystPos[i] = (int) (70 * Game.SCALE) + rnd.nextInt((int) (150 * Game.SCALE));
        }
        // Load in background images
        backgroundimg = LoadSave.getSpriteSheet(LoadSave.WORLD1_BG);
        background_myst_img = LoadSave.getSpriteSheet(LoadSave.WORLD1_BG_MYST);
        background_rocks = LoadSave.getSpriteSheet(LoadSave.WORLD1_BG_ROCKS);
    }

    /**
     * Every tick, this function updates the game by invoking the similarly named update
     * command on each entity either directly with player.update, or through a manager such as
     * enemyManager.update This function also controls the screen scroller
     */
    @Override
    public void update() {
        // update the playing screen depending on what state the it is in
        if (demoOver) {
            demoOverlay.update();
        } else if (levelComplete) {
        // here is where we do anything when the level is completed
            winOverlay.update();
            return;
        // If the game is paused,
        // update pause overlay
        } else if (paused) {
            pauseOverlay.update();
            return;
            // If the player is kill,
            // update death overlay
        } else if (gameOver) {
            deathOverlay.update();
            return;
            // Otherwise, the game is still being played,
            // so update everything else
        } else {
            player.update(xLevelOffset);
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            projManager.update(levelManager.getCurrentLevel().getLevelData());
            screenScroller();
            hud.updateHUD();
        }
    }

    /**
     * Pushes the screen once the player entity gets beyond a certain percentage of the
     * currently drawn screen
     */
    private void screenScroller() {
        int playerX = (int) player.getHitbox().x;
        int diffX = playerX - xLevelOffset;

        if (diffX > borderRight) {
            xLevelOffset += diffX - borderRight;
        } else if (diffX < borderLeft) {
            xLevelOffset += diffX - borderLeft;
        }

        if (xLevelOffset > maxXOffset) {
            xLevelOffset = maxXOffset;
        } else if (xLevelOffset < 0) {
            xLevelOffset = 0;
        }

        // Y-Position Vars
        int playerY = (int) player.getHitbox().y;
        int diffY = playerY - yLevelOffset;
        if (diffY > borderTop) {
            yLevelOffset += diffY - borderTop;
        } else if (diffY < borderBottom) {
            yLevelOffset += diffY - borderBottom;
        }

        if (yLevelOffset > maxYOffset) {
            yLevelOffset = maxYOffset;
        } else if (yLevelOffset < 0) {
            yLevelOffset = 0;
        }

    }

    /**
     * Draws everything that is intended to be visible while in a level/playing the game
     * 
     * @param g - the Graphics where to draw the screen
     */
    @Override
    public void draw(Graphics g) {
        // if the demo is over, only draw the demo screen nothing else 
        if (demoOver) {
            demoOverlay.draw(g);
            return;
        }
        
        
        // draw background first so everything else sits on it
        drawBackground(g);
        // if it is paused, only draw the background and the pauseOverlay.
        if (paused) {
            pauseOverlay.draw(g);
            // return so it doesn't draw anything else
            return;
        } else if (gameOver) {
            deathOverlay.draw(g);
            deathOverlay.update();
        } else if (levelComplete) {
            winOverlay.draw(g);
            winOverlay.update();
        } else {
            // if not paused, draw everything beneath this.
            levelManager.draw(g, xLevelOffset);
            hud.draw(g);
            enemyManager.draw(g, xLevelOffset);
            projManager.draw(g, xLevelOffset);
            player.renderPlayer(g);
        }

    }

    /**
     * Draws all assets for the background, including adding paralax affect to entities
     * 
     * @param g - Graphics
     */
    private void drawBackground(Graphics g) {
        g.drawImage(backgroundimg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        for (int i = 0; i < mystPos.length; i++) {
            g.drawImage(background_myst_img, BackgroundStates.BGMYST_WIDTH * i - (int) (xLevelOffset * 0.7), mystPos[i],
                    BackgroundStates.BGMYST_WIDTH, BackgroundStates.BGMYST_HEIGHT, null);
        }
        for (int i = 0; i < 4; i++) {
            g.drawImage(background_rocks, i * BackgroundStates.BGROCKS_WIDTH, 0,
                    Game.GAME_WIDTH - (int) (xLevelOffset * 0.3), Game.GAME_HEIGHT, null);

        }

    }

    /**
     * When window focus is lost for whatever reason, this resets the player input, to allow
     * the player to pick up where they were before interruption.
     */
    public void windowFocusLost() {
        player.resetDirBools();
    }

    /**
     * Getter for player entity
     * 
     * @return - Returns the current "player" entity
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Getter for the ProjectileManager
     * 
     * @return - the ProjectileManager for the game
     */
    public ProjectileManager getProjectileManager() {
        return projManager;
    }

    /**
     * create a new arrow at a specific point in the screen, along with the slope that the
     * arrow will take
     * 
     * @param x     - the x coordinate of the new Arrow
     * @param y     - the y coordinate of the new Arrow
     * @param slope - the slope/path that the arrow will take
     * @param left  - true if the arrow is moving left, false is moving right
     * 
     */
    public void addPlayerArrow(float x, float y, float slope, boolean left) {
        projManager.newArrow(x, y, slope, left);
    }

    /**
     * Sets the levelComplete to true and sets the current Level's complete to true
     */
    public void completeLevel() {
        this.levelComplete = true;
        game.getAudioPlayer().lvlCompleted();
        levelManager.getCurrentLevel().setCompleted(true);
        if (!levelManager.unhideNextLevels()) {
            demoOver = true;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // if the mouse is moved, store the point that it moved to and keep drawing.
        // this does not work if you check that it is mouse button 1 was moved.
        player.setNextAttack(e.getPoint());
        player.setDrawArrowPath(true);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (demoOver) {
            demoOverlay.mousePressed(e);
        }
        // if paused, only send the mouse input into the paused overlay
        if (paused) {
            pauseOverlay.mousePressed(e);
            return;
        }
        if (gameOver) {
            deathOverlay.mousePressed(e);
        }
        if (levelComplete) {
            winOverlay.mousePressed(e);
        }
        // if mouse button 1 is pressed, store that point and draw the arrow path to that point
        if (e.getButton() == MouseEvent.BUTTON1) {
            player.setNextAttack(e.getPoint());
            player.setDrawArrowPath(true);
        }
    }

    /**
     * This determines how the Playing class and all components interact with the mouse is
     * released
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (demoOver) {
            demoOverlay.mouseReleased(e);
        }
        // if paused, only send the mouse input into the paused overlay
        if (paused) {
            pauseOverlay.mouseReleased(e);
            return;
        }
        else if (gameOver) {
            deathOverlay.mouseReleased(e);
        }
        else if(levelComplete) {
            winOverlay.mouseReleased(e);
        }
        // if mouse button 1 is released, then try to shoot an arrow and stop drawing the path
        else if (e.getButton() == MouseEvent.BUTTON1) {
            player.setDrawArrowPath(false);
            player.shoot(e.getPoint());
            game.getAudioPlayer().playEffect(AudioPlayer.FIRE);
        }

    }

    /**
     * This determines how the Playing class will interact with the mouse moving
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (demoOver) {
            demoOverlay.mouseMoved(e);
        }
        if (paused) {
            pauseOverlay.mouseMoved(e);
        }
        if (gameOver) {
            deathOverlay.mouseMoved(e);
        }
        if (levelComplete) {
            winOverlay.mouseMoved(e);
        }

    }

    /**
     * THIS FUNCTION IS UNUSED
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // unused using press and release instead
    }

    /**
     * Depending on the key pressed, the Player Entity will react in different ways.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_A:
            player.setLeft(true);
            break;
        case KeyEvent.VK_D:
            player.setRight(true);
            break;
        case KeyEvent.VK_SPACE:
            player.setJump(true);
            player.incJumpCount();
            break;
        case KeyEvent.VK_P:
            paused = !paused;
            break;
        case KeyEvent.VK_SHIFT:
            player.setDash(true);
            break;
        case KeyEvent.VK_BACK_SPACE:
            GameStates.state = GameStates.OVERWORLD;
            break;
        case KeyEvent.VK_H:
            demoOver = true;
            break;
//Debugging tool: Press 9 to increment the score by 9
//        case KeyEvent.VK_9:
//            updateScore(9);
//            break;

        }
    }

    /**
     * Once a key is released, the Player Entity will react in different ways
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_A:
            player.setLeft(false);
            break;
        case KeyEvent.VK_D:
            player.setRight(false);
            break;
        case KeyEvent.VK_SPACE:
            player.setJump(false);
            game.getAudioPlayer().playEffect(AudioPlayer.JUMP);

            break;
        }
    }

    /**
     * Setter for paused boolean (determines if the screen should be paused or not)
     * 
     * @param paused - true if the screen should be paused, false if not
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    /**
     * Setter for Game Over boolean (determines if the screen should display Death Overlay or
     * not)
     * 
     * @param gameOver - true if the screen should display Death Overlay, false if not
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
        if (gameOver == true) {
            //Display demo screen
        }
    }

    /**
     * Getter for the current score
     * 
     * @return the current score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Setter for the current score - adds the values of placed in the method to the current
     * value
     * 
     * @param scoreval - the value to be added to the score, can be positive or negative
     */
    public void updateScore(int scoreval) {
        this.score += scoreval;
    }

    /**
     * Getter for the levelManager
     * 
     * @return the levelManager
     */
    public LevelManager getLevelManager() {
        return levelManager;
    }

    /**
     * Getter for the Enemy Manager
     * 
     * @return the enemyManager
     */
    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    /**
     * Resets all entities to their original values
     */
    public void resetAll() {
        gameOver = false;
        score = 0;
        paused = false;
        levelComplete = false;
        levelManager.getCurrentLevel().setCompleted(false);
        player.resetDirBools();
        enemyManager.resetAllEnemies();
    }

    /**
     * Let's the playing state know the Player Entity died.
     */
    public void playerDied() {
        gameOver = true;
    }
}
package states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import projectiles.ProjectileManager;
import ui.HUD;
import ui.PauseOverlay;
import utils.Constants.BackgroundStates;
import utils.LoadSave;

/**
 * Playing Class
 * 
 * @author johnbotonakis This class handles the core game loop of completing levels
 */
public class Playing extends State implements StateMethods {

    // will keep track if the pause menu should be up or not
    private boolean paused = false, levelComplete = false;
    private Player player;
    private HUD hud;
    private PauseOverlay pauseOverlay;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ProjectileManager projManager;
    private int score;

    // Level Expansion vars
    private int xLevelOffset;// X-Offset being added to and subtracted from to render the level itself
    private int borderLeft = (int) (0.5 * Game.GAME_WIDTH);// 50% of the screen is rendered
    private int borderRight = (int) (0.5 * Game.GAME_WIDTH);// 50% of the screen is hidden
    private int levelTilesWide = LoadSave.getLevelData()[0].length; //
    private int maxTileOffset = levelTilesWide - Game.TILES_IN_WIDTH; //
    private int maxXOffset = maxTileOffset * Game.TILES_SIZE; //

    // Y Expansion Vars for longer levels
    private int yLevelOffset;//Y-Offset being added to and subtracted from to render the level itself
    private int borderTop = (int)(0.5 * Game.GAME_HEIGHT);//50% of the screen is rendered
    private int borderBottom = (int)(0.5 * Game.GAME_HEIGHT);//50% of the screen is hidden
    private int levelTilesHigh = LoadSave.getLevelData().length; //
    private int maxYTileOffset = levelTilesHigh - Game.TILES_IN_HEIGHT; //
    private int maxYOffset = maxYTileOffset * Game.TILES_SIZE; //

    private BufferedImage backgroundimg, background_myst_img, background_rocks;
    private int[] mystPos;// Position of myst background asset
    private Random rnd = new Random();

    /**
     * Runs the logic once the game state has switched to PLAYING Loads in the enemies,
     * backgrounds, and player
     * 
     * @param game - Game
     */
    public Playing(Game game) {
        super(game);
        initClasses();
        initBackgroundAssets();
    }

    /**
     * Initializes the classes as a function instead of calling upon them individually Level
     * Manager, Enemy Manager, and Player entity are all loaded here
     */
    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        projManager = new ProjectileManager(this);

    }

    public void nextLevel(int nextLevelIndex) {
        levelManager.setCurrentLevel(nextLevelIndex);
        loadCurrentLevel();
    }

    private void loadCurrentLevel() {
        player = new Player(200, 480, (int) (55 * Game.SCALE), (int) (65 * Game.SCALE), this);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        pauseOverlay = new PauseOverlay();
        hud = new HUD(this);
        this.score = 0;

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
        // if the level is complete, don't update anything
        if (levelComplete) {
            // here is where we do anything when the level is completed
            GameStates.state = GameStates.OVERWORLD;
            return;
        }

        if (paused) {
            // update pause overlay here
            pauseOverlay.update();
            return;
        }

        // if this is paused, don't update all the paused stuff but not the rest
        levelManager.update();
        player.update();
        enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
        projManager.update();
        screenScroller();
        hud.updateHUD();
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
        int playerY = (int)player.getHitbox().y;
        int diffY = playerY - yLevelOffset;
        if(diffY > borderTop) {
            yLevelOffset +=diffY - borderTop;
        } else if (diffY < borderBottom) {
            yLevelOffset += diffY - borderBottom;
        }
        
        if(yLevelOffset > maxYOffset) {
            yLevelOffset = maxYOffset;
        }else if (yLevelOffset < 0) {
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
        // draw background first so everything else sits on it
        drawBackground(g);
        levelManager.draw(g, xLevelOffset);
        hud.draw(g);
        enemyManager.draw(g, xLevelOffset);
        projManager.draw(g, xLevelOffset);
        player.renderPlayer(g, xLevelOffset);
        if (paused) {
            pauseOverlay.draw(g);
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
     * If the user clicks the mouse button, the Player Entity will try to shoot an arrow
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            player.shoot(e);
        }

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
            break;
        case KeyEvent.VK_P:
            paused = !paused;
            break;
        case KeyEvent.VK_SHIFT:
            player.setDash(true);
            break;
        case KeyEvent.VK_BACK_SPACE:
            GameStates.state = GameStates.MENU;
            break;
        case KeyEvent.VK_9:
            updateScore(9);
            break;
        // Testing text-based cutscenes
//        case KeyEvent.VK_0:
//            draw = true;
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
            player.setJumps();
            break;
        case KeyEvent.VK_K:
            player.kill();
            break;
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
     * @param left  - true if the arrow is moving left, false is right
     * 
     */
    public void addPlayerArrow(float x, float y, float slope, boolean left) {
        projManager.newArrow(x, y, slope, left);
    }

    /**
     * Sets the levelComplete to true
     */
    public void completeLevel() {
        this.levelComplete = true;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseDragged(e);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused) {
            pauseOverlay.mousePressed(e);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseReleased(e);
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseMoved(e);
        }

    }

    public int getScore() {
        return this.score;

    }

    public void updateScore(int scoreval) {
        this.score += scoreval;
    }
}

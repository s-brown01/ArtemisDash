package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import states.Playing;
import utils.LoadSave;

/**
 * The HUD or Heads Up Display will always be visible during gameplay to inform the player
 * of their score, their current health, and the enemies remaining
 * 
 * @author John Botonakis
 */
public class HUD {
    /**
     * Buffered Image to represent the character portrait
     */
    private BufferedImage portrait;
    /**
     * Buffered Image to represent the HUD background
     */
    private BufferedImage hudbg;
    /**
     * Buffered Image to represent Hearts
     */
    private BufferedImage hearts;
    /**
     * X-Position of the Portrait Sprite
     */
    private final int xPos = 100;
    /**
     * Y-Position of the Portrait Sprite
     */
    private final int yPos = 15;
    /**
     * Width of the Portrait Sprite
     */
    private final int width = 80;
    /**
     * Height of the Portrait Sprite
     */
    private final int height = 80;
    /**
     * Game state that the HUD will be drawn over
     */
    private final Playing playing;
    /**
     * Integer value representing player health
     */
    private int playerHealth;
    /**
     * Integer value representing player score
     */
    private int playerScore;
    /**
     * Integer value representing the amount of enemies remaining
     */
    private int enemiesRemaining;
    /**
     * The font used for the HUD as well as other parts of the game
     */
    private Font hudFont;

    /**
     * Constructor class to instantiate the elements that make up the HUD
     * 
     * @param playing - Playing Game state
     */
    public HUD(Playing playing) {
        this.playing = playing;
        this.playerScore = playing.getScore();
        this.enemiesRemaining = playing.getEnemyManager().getEnemiesLeft();
        loadAssets();
    }

    /**
     * Loads in the image assets for the HUD entity
     */
    private void loadAssets() {
        portrait = LoadSave.getSpriteSheet(LoadSave.PLAYER_PORTRAIT);
        hudbg = LoadSave.getSpriteSheet(LoadSave.HUDBG);
        hearts = LoadSave.getSpriteSheet(LoadSave.HEART); // Get assets for hearts

        // Font Initialization
        this.hudFont = LoadSave.loadFont(LoadSave.FONT, 25);
    }

    /**
     * Handles any functionality that requires an update, such as lives, score count, and
     * health
     */
    public void updateHUD() {
        // Check to ensure that Playing is active.
        // Letting it run once without it active will simply return nothing,
        // running it while active will have the intended behavior
        if (this.playing == null) {
            return;
        } else {
            updateHealth();
            updateScore();
            updateEnemiesLeft();
        }

    }

    /**
     * Draws every HUD element to the screen starting from left most element to right most
     * element
     * 
     * @param g - the Graphics where to draw all of the elements.
     */
    public void draw(Graphics g) {
        // Score Positioning Vars
        // X-Position of the Score Title
        int scoreXPos = xPos - 50;
        // Y-Position of the Score Title
        int scoreYPos = yPos * 3;
        // X-Position of the Score Count
        int playerScoreX = xPos - 30;
        // Y-Position of the Score Count
        int playerScoreY = yPos + 75;

        // Enemies Remaining Vars
        // X-Position of the Lives Title
        int remainXPos = xPos * 5;
        // Y-Position of the Lives Title
        int remainYPos = yPos * 3;
        // X-Position of the Lives Count
        int enemyRemainingX = xPos * 5;
        // Y-Position of the Lives Count
        int enemyRemainingY = yPos + 75;

        // X-Position of the Player health represented in hearts
        int heartsXpos = xPos + 80;
        // X-Position of multiple hearts
        int multipleHearts = 80;

        g.drawImage(hudbg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(portrait, Game.GAME_WIDTH - xPos, yPos, width, height, null);

        for (int i = 0; i < updateHealth(); i++) {
            g.drawImage(hearts, Game.GAME_WIDTH - heartsXpos - (i * multipleHearts), yPos, width, height, null);
        }

        g.setColor(Color.BLACK);
        g.setFont(hudFont);

        g.drawString("Score ", scoreXPos, scoreYPos);
        g.drawString(String.valueOf(playerScore), playerScoreX, playerScoreY);

        g.drawString("Enemies Left ", remainXPos, remainYPos);
        g.drawString(String.valueOf(enemiesRemaining), enemyRemainingX, enemyRemainingY);
    }

    /**
     * Function to continuously check that the health is up to date 
     * @return playerHealth - Returns the current value of the Player's Health
     */
    public int updateHealth() {
        playerHealth = playing.getPlayer().getHealth();
        return playerHealth;

    }

    /**
     * Function to keep the HUD element tied to player score up to date 
     */
    public void updateScore() {
        this.playerScore = playing.getScore();
    }

    /**
     * Function to keep the HUD element of Enemies Remaining tied to the dash up to date
     */
    private void updateEnemiesLeft() {
        this.enemiesRemaining = playing.getEnemyManager().getEnemiesLeft();        
    }

}

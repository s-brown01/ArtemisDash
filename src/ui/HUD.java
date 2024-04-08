
package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import states.Playing;
import utils.LoadSave;

/**
 * HUD Class
 * 
 * @author johnbotonakis
 * @description The HUD or Heads Up Display will always be visible during gameplay to
 *              inform the player of their score, their current health, and their lives
 *              remaining
 */
public class HUD {
    private BufferedImage portrait, hudbg, hearts, charge;
    private final int xPos = 100;
    private final int yPos = 15;
    private final int width = 80;
    private final int height = 80;
    private Playing playing;
    private int playerHealth, playerScore, playerLives;
    private Font hudFont;

    public HUD(Playing playing) {
        this.playing = playing;
        this.playerScore = playing.getScore();
        this.playerLives = playing.getPlayer().getLives();
        loadAssets();
    }

    /**
     * Loads in the image assets for the HUD entity
     */
    private void loadAssets() {
        portrait = LoadSave.getSpriteSheet(LoadSave.PLAYER_PORTRAIT);
        hudbg = LoadSave.getSpriteSheet(LoadSave.HUDBG);
//        hearts = LoadSave.getSpriteSheet(LoadSave.HUDBG); //Get assets for hearts
//        charge = LoadSave.getSpriteSheet(LoadSave.HUDBG); //Get assets for Dash charge

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
        // while running it while active, will have the intended behavior
        if (this.playing == null) {
            return;
        } else {
            updateHealth();
            updateScore();
        }

    }

    public void draw(Graphics g) {
        // Score Positioning Vars
        int scoreXPos = xPos - 50;
        int scoreYPos = yPos * 3;
        int playerScoreX = xPos - 30;
        int playerScoreY = yPos + 75;

        // Lives Positioning Vars
        int livesXPos = xPos * 5;
        int livesYPos = yPos * 3;
        int playerLivesX = xPos * 5;
        int playerLivesY = yPos + 75;

        g.drawImage(hudbg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(portrait, Game.GAME_WIDTH - xPos, yPos, width, height, null);

        g.setColor(Color.BLACK);
        g.setFont(hudFont);

        g.drawString("Score ", scoreXPos, scoreYPos);
        g.drawString(String.valueOf(playerScore), playerScoreX, playerScoreY);

        g.drawString("Lives ", livesXPos, livesYPos);
        g.drawString(String.valueOf(playerLives), playerLivesX, playerLivesY);

        g.drawString("Charge", livesXPos + 400, livesYPos);
    }

    /**
     * Function to continuously check that the health is up to date with what the actual value
     * is
     */
    public void updateHealth() {
        this.playerHealth = playing.getPlayer().getHealth();
        if (playerHealth == 2) {
            // Draw two full hearts
        } else if (playerHealth == 1) {
            // Draw one full heart and one dead heart
        } else {
            // Draw two dead hearts
        }

    }

    public void updateScore() {
        this.playerScore = playing.getScore();
    }

    public void updateLives() {
        this.playerLives = playing.getPlayer().getLives();
    }

    public void updateCharge() {

    }

    // For use later with "cut scenes"
//    public void delay(String s, long delay, Graphics g) {
//        for ( int i= 0; i < s.length(); i++) { 
//              // for loop delays individual String characters
//
//            System.out.print(s.charAt(i));
//            g.setColor(Color.CYAN);
//            g.setFont(hudFont);
//            g.drawChars(s.toCharArray(), 0, 10, xPos + 20, yPos * 4);
//            try {
//                Thread.sleep(delay);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } //time is in milliseconds
//        }
//        System.out.println(""); // this is the space in between lines
//    }

}

/**
 * HUD Class
 * @author johnbotonakis
 * The HUD or Heads Up Display will always be visible during gameplay to inform the player of
 * their score, their current health, and their lives remaining
 */
package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import states.Playing;
import utils.LoadSave;


public class HUD {
    private BufferedImage portrait;
    private final int xPos, yPos;
    private final int width, height;
    private final int scoreYPos, livesXPos;
    private Playing playing;
    private int playerHealth, playerScore, playerLives;
    private Font hudFont;

    public HUD(Playing playing) {
        this.xPos = 100;
        this.yPos = 25;
        this.width = 80;
        this.height = 80;
        this.scoreYPos = yPos * 2;
        this.livesXPos = xPos * 5;

        this.playing = playing;
        this.playerScore = playing.getScore();
        this.playerLives = playing.getPlayer().getLives();
        loadPortrait();
        this.hudFont = LoadSave.loadFont(LoadSave.FONT, 25);
    }

    /**
     * Loads in the Portrait for the player entity
     */
    private void loadPortrait() {
        portrait = LoadSave.getSpriteSheet(LoadSave.PLAYER_PORTRAIT);
    }

    /**
     * Handles any functionality that requires an update, such as lives, score count, and health
     */
    public void update() {
        if (this.playing == null) {
            System.out.println("Playing is null RIP");
            return;
        } else {
            updateHealth();
            updateScore();
        }

    }

    public void draw(Graphics g) {
        g.setColor(new Color(150, 150, 150, 150));
        g.fillRect(0, 20, Game.GAME_WIDTH, Game.GAME_HEIGHT / 9);
        g.drawImage(portrait, Game.GAME_WIDTH - xPos, yPos, width, height, null);

        g.setColor(Color.BLUE);
        g.setFont(hudFont);
        g.drawString("Score ", xPos, scoreYPos);
        g.drawString(String.valueOf(playerScore), xPos + 20, yPos * 4);
        g.drawString("Lives ", livesXPos, scoreYPos);
        g.drawString(String.valueOf(playerLives), xPos * 5, yPos * 4);
//        delay("Hello World!",100); 
    }
    
    /**
     * Function to continuously check that the health is up to date with what the actual value is
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
    
    //For use later with "cut scenes"
//    public void delay(String s, long delay) {
//        for ( int i= 0; i < s.length(); i++) { 
//              // for loop delays individual String characters
//
//            System.out.print(s.charAt(i));
//            try {
//                Thread.sleep(delay);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } //time is in milliseconds
//        }
//        System.out.println(""); // this is the space in between lines
//    }


}

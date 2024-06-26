package ui;

import static utils.Constants.EndButtons.ENDBUTTON_SIZE;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import states.GameStates;
import states.Playing;
import utils.LoadSave;

/**
 * This class handles the level complete overlay, which shows when the player kills every
 * enemy in a level
 * 
 * @author John Botonakis
 */
public class WinOverlay {
    /**
     * The EndButton that will return the player to the menu
     */
    private EndButton returnToMenu;
    /**
     * The EndButton that will continue playing - return player to overworld
     */
    private EndButton continuePlay;

    /**
     * Buffered Image for button display
     */
    private BufferedImage img;
    /**
     * Image X-positioning variable
     */
    private int imgX;
    /**
     * Image Y-positioning variable
     */
    private int imgY;
    /**
     * Image width variable
     */
    private int imgW;
    /**
     * Image height variable
     */
    private int imgH;
    /**
     * The playing that is holding/controlling this overlay
     */
    private Playing playing;

    /**
     * Initializes the Pause Overlay by creating and loading in sprite for the menu and its
     * buttons
     * 
     * @param playing - the playing that is holding (will be controlling) this overlay
     * 
     */
    public WinOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        createButtons();
    }

    /**
     * Loads in a background based on the specified image name Background width, height,
     * X-Position and Y-Position are also set here
     */
    private void loadBackground() {
        img = LoadSave.getSpriteSheet(LoadSave.WINSCREEN);
        imgW = (int) (img.getWidth() * Game.SCALE);
        imgH = (int) (img.getHeight() * Game.SCALE);
        imgX = Game.GAME_WIDTH / 2 - imgW / 2;
        imgY = (int) (100 * Game.SCALE);

    }

    /**
     * Updates the buttons based on the users actions. Things such as hover and mouse click
     * affect the button sprite state
     */
    public void update() {
        returnToMenu.update();
        continuePlay.update();
    }

    /**
     * Draws everything that is intended to be visible, to the screen
     * 
     * @param g - Graphics
     */
    public void draw(Graphics g) {
        // background
        g.drawImage(img, imgX, imgY, imgW, imgH, null);

        // buttons
        returnToMenu.draw(g);
        continuePlay.draw(g);
    }

    /**
     * Creates the buttons for use only on the death overlay Scale, X position, Y position and
     * offsets are set here
     */
    private void createButtons() {
        // Button Positioning
        int menuX = (int) (350 * Game.SCALE);
        int playX = (int) (450 * Game.SCALE);
        int yPos = (int) (205 * Game.SCALE);

        continuePlay = new EndButton(playX, yPos, ENDBUTTON_SIZE, ENDBUTTON_SIZE, 0);
        returnToMenu = new EndButton(menuX, yPos, ENDBUTTON_SIZE, ENDBUTTON_SIZE, 2);
        returnToMenu.resetBools();
        continuePlay.resetBools();
    }

    /**
     * Specifies behavior when the mouse is pressed signifying the start of a complete click.
     * 
     * @param e - Mouse pressed event
     */
    public void mousePressed(MouseEvent e) {
        if (isIn(e, returnToMenu)) {
            returnToMenu.setMousePressed(true);
        } else if (isIn(e, continuePlay)) {
            continuePlay.setMousePressed(true);
        }
    }

    /**
     * Executes a specific action based on the mouse movement In this case, changing the
     * sprite to the "hover" sprite
     * 
     * @param e - Mouse movement event
     */
    public void mouseMoved(MouseEvent e) {
        continuePlay.setMouseOver(false);
        returnToMenu.setMouseOver(false);

        if (isIn(e, returnToMenu)) {
            returnToMenu.setMouseOver(true);
        } else if (isIn(e, continuePlay)) {
            continuePlay.setMouseOver(true);
        }
    }

    /**
     * Specifies behavior when the mouse button is released, signifying the end of a complete
     * click
     * 
     * @param e - Mouse released event
     */
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, returnToMenu)) {
            System.out.println("IN BOUNDS MENU");
            if (returnToMenu.isMousePressed()) {
                GameStates.state = GameStates.MENU;
                playing.resetAll();
            }
        } else if (isIn(e, continuePlay)) {
            if (continuePlay.isMousePressed()) {
                playing.resetAll();
                GameStates.state = GameStates.OVERWORLD;
            }
        }

        returnToMenu.resetBools();
        continuePlay.resetBools();
    }

    /**
     * Checks to see if the mouse cursor is within the bounds of a menu button
     * 
     * @param e - Mouse movement event
     * @param b - Button bounding box
     * @return - Returns true if the mous is within the confines of the buttons bounding box
     */
    private boolean isIn(MouseEvent e, EndButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

}

package ui;

import static utils.Constants.EndButtons.ENDBUTTON;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utils.LoadSave;

/**
 * This class is responsible for handling the creation of, and interaction with, buttons
 * bound within the Pause Overlay
 * 
 * @author John Botonakis
 */
public class EndButtons extends PauseButton {
    private BufferedImage[] buttonImgs;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;

    /**
     * Creates a button for use ONLY in the Death Overlay
     * 
     * @param x      - X-Position placement of the created button
     * @param y      - Y-Position placement of the created button
     * @param width  - Width of the sprite
     * @param height - Height of the sprite
     */
    public EndButtons(int x, int y, int width, int height, int rowindex) {
        super(x, y, width, height);
        this.rowIndex = rowindex;
        loadDeathImages();
    }

    /**
     * Loads in the images to build the buttons from Uses utils.Constants.ButtonStates to
     * populate size variables
     */
    private void loadDeathImages() {
        BufferedImage temp = LoadSave.getSpriteSheet(LoadSave.ENDBUTTONS);
        buttonImgs = new BufferedImage[3];
        for (int i = 0; i < buttonImgs.length; i++) {
            buttonImgs[i] = temp.getSubimage(i * ENDBUTTON, rowIndex * ENDBUTTON, ENDBUTTON, ENDBUTTON);
        }
    }

    /**
     * Handles any updates that are to be processed by the sound button object, such as
     * changing its appearance when hovered over or clicked
     */
    public void update() {
        index = 0;
        if (mouseOver) {
            index = 1;
        }
        if (mousePressed) {
            index = 2;
        }
    }

    /**
     * Draws everything to the screen using Graphics
     * 
     * @param g - Graphics
     */
    public void draw(Graphics g) {
        g.drawImage(buttonImgs[index], x, y, ENDBUTTON, ENDBUTTON, null);

    }

    /**
     * Resets all boolean values associated with this Sound button object
     */
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    /**
     * getter for mouseOver
     * 
     * @return mouseOver - the current value of mouseOver
     */
    public boolean isMouseOver() {
        return mouseOver;
    }

    /**
     * setter for mouseOver
     * 
     * @param mouseOver - the new value of mouseOver
     */
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    /**
     * getter for mousePressed
     * 
     * @return mousePressed - the current value of mousePressed
     */
    public boolean isMousePressed() {
        return mousePressed;
    }

    /**
     * setter for mousePressed
     * 
     * @param mousePressed - the new value of mouseOver
     */
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}

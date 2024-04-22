package ui;

import static utils.Constants.EndButtons.ENDBUTTON_DEFAULT;
import static utils.Constants.EndButtons.ENDBUTTON_SIZE;

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
    /**
     * The different images for the end button (default/hover/click images)
     */
    private BufferedImage[] buttonImgs;
    /**
     * The index of the image to draw in the buttonImgs array (0-default, 1-hover, 2-pressed)
     */
    private int index;
    /**
     * This boolean will represent if the mouse is hovering over the button without clicking
     */
    private boolean mouseOver, 
    /**
     * This boolean represents if the mouse has clicked down on the button
     */
    mousePressed;

    /**
     * Creates a button for use ONLY in the Death Overlay
     * 
     * @param x         - X-Position placement of the created button
     * @param y         - Y-Position placement of the created button
     * @param width     - Width of the sprite
     * @param height    - Height of the sprite
     * @param rowindex  - The row of of the image to get in the sprite sheet
     */
    public EndButtons(int x, int y, int width, int height, int rowindex) {
        super(x, y, width, height);
        loadDeathImages(rowindex);
    }

    /**
     * Loads in the images to build the buttons from Uses utils.Constants.ButtonStates to
     * populate size variables
     * 
     * @param rowIndex - the rows of sprites to load in from the ENDBUTTONS sheet
     */
    private void loadDeathImages(int rowIndex) {
        BufferedImage temp = LoadSave.getSpriteSheet(LoadSave.ENDBUTTONS);
        buttonImgs = new BufferedImage[3];
        for (int i = 0; i < buttonImgs.length; i++) {
            buttonImgs[i] = temp.getSubimage(i * ENDBUTTON_DEFAULT, rowIndex * ENDBUTTON_DEFAULT, ENDBUTTON_DEFAULT, ENDBUTTON_DEFAULT);
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
        g.drawImage(buttonImgs[index], x, y, ENDBUTTON_SIZE, ENDBUTTON_SIZE, null);

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

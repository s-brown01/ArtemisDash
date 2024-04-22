package ui;

import static utils.Constants.ButtonStates.SOUNDSIZE_DEFAULT;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utils.LoadSave;

/**
 * This class is responsible for handling the creation of, and interactions with, the
 * Sound button bound within the Pause Overlay
 * 
 * @author John Botonakis
 */
public class SoundButton extends PauseButton {

    /**
     * All images that can be used
     */
    private BufferedImage[][] soundimgs;
    /**
     * Represents if the mouse is hovering over the button
     */
    private boolean mouseOver,
            /**
             * Represents if the mouse is pressed on the button
             */
            mousePressed;
    /**
     * if the sound for this button should be muted or not
     */
    private boolean muted;
    /**
     * the row that relates to the sprites images
     */
    private int rowIndex,
            /**
             * the column that relates to the sprites images
             */
            colIndex;

    /**
     * Creates a button for use ONLY in the pause menu
     * 
     * @param x      - X-Position of the sprite
     * @param y      - Y-Position of
     * @param width  - Width of Sprite
     * @param height - Height of Sprite
     */
    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadSoundImages();
    }

    /**
     * Loads in the images to build the buttons from Uses utils.Constants.ButtonStates to
     * populate size variables
     */
    private void loadSoundImages() {
        BufferedImage temp = LoadSave.getSpriteSheet(LoadSave.PAUSE_SOUND_BUTTONS);
        soundimgs = new BufferedImage[2][3];
        for (int j = 0; j < soundimgs.length; j++) {
            for (int i = 0; i < soundimgs[j].length; i++) {
                soundimgs[j][i] = temp.getSubimage(i * SOUNDSIZE_DEFAULT, j * SOUNDSIZE_DEFAULT, SOUNDSIZE_DEFAULT,
                        SOUNDSIZE_DEFAULT);
            }
        }

    }

    /**
     * Handles any updates that are to be processed by the sound button object, such as
     * changing its appearance when hovered over or clicked
     */
    public void update() {
        if (muted) {
            rowIndex = 1;
        } else {
            rowIndex = 0;
        }

        colIndex = 0;
        if (mouseOver) {
            colIndex = 1;
        }

        if (mousePressed) {
            colIndex = 2;
        }
    }

    /**
     * Draws everything to the screen using Graphics
     * 
     * @param g - Graphics
     */
    public void draw(Graphics g) {
        g.drawImage(soundimgs[rowIndex][colIndex], x, y, width, height, null);
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

    /**
     * Getter for the muted boolean
     * 
     * @return the current value of muted
     */
    public boolean isMuted() {
        return muted;
    }

    /**
     * Setter for the muted boolean
     * 
     * @param muted - new value of muted
     */
    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}

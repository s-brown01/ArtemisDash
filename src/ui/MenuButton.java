package ui;

import static utils.Constants.ButtonStates.B_HEIGHT;
import static utils.Constants.ButtonStates.B_HEIGHT_DEFAULT;
import static utils.Constants.ButtonStates.B_WIDTH;
import static utils.Constants.ButtonStates.B_WIDTH_DEFAULT;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import states.GameStates;
import utils.LoadSave;

/**
 * This class represents 1 Button on the Menu GameState. A button will be linked to
 * different GameStates so that the Game can change states based on what button is pushed.
 * 
 * @author John Botonakis
 */
public class MenuButton {
    /**
     * This is the index for the PLAY button sprites
     */
    public static final int PLAY_INDEX = 0;
    /**
     * This is the index for the INSTRUCTIONS button sprites
     */
    public static final int INSTRUCTIONS_INDEX = 1;
    /**
     * This is the index for the QUIT button sprites
     */
    public static final int QUIT_INDEX = 2;
    /**
     * This is the index for the MENU button sprites
     */
    public static final int MENU_INDEX = 3;

    /**
     * The left coordinate in pixels
     */
    private int xpos;
    /**
     * The top coordinate in pixels
     */
    private int ypos;
    /**
     * This correlates to the image state to draw (default/hover/pressed)
     */
    private int index;

    /**
     * Since x is the left coordinate, 1/2 of the width means that the button can be centered
     * on the x-coordinate
     */
    private int XOffsetCenter = B_WIDTH / 2;
    /**
     * The GameState to switch to
     */
    private GameStates state;
    /**
     * The images to draw that contain all of the images for the index property
     */
    private BufferedImage[] imgs;
    /**
     * Represents if the mouse is hovering over the button
     */
    private boolean mouseOver,
            /**
             * Represents if the mouse is pressed while on this button
             */
            mousePressed;
    /**
     * The bounds that determine where the user can click on a button
     */
    private Rectangle bounds;

    /**
     * Generates a menu button object
     * 
     * @param xpos     - X-Position on the Screen
     * @param ypos     - Y-Position on the screen
     * @param rowIndex - Row index to determine which button to draw
     * @param state    - Determines what state of the game the button is linked to. Once hit,
     *                 this game state will load in.
     */
    public MenuButton(int xpos, int ypos, int rowIndex, GameStates state) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.state = state;
        loadImgs(rowIndex);
        initBounds();
    }

    /**
     * Initializes a rectangle around the button to determine if mouse is intersecting within.
     */
    private void initBounds() {
        bounds = new Rectangle(xpos - XOffsetCenter, ypos, B_WIDTH, B_HEIGHT);

    }

    /**
     * Loads in image to represent the button from a specified sprite sheet
     * 
     * @param rowIndex - Row index to determine which button to draw.
     */
    private void loadImgs(int rowIndex) {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.getSpriteSheet(LoadSave.MENU_BUTTONS);
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT,
                    B_HEIGHT_DEFAULT);
        }
    }

    /**
     * Draws the button to the screen with the specified parameters
     * 
     * @param g - the Graphics where to draw the MenuButton
     */
    public void draw(Graphics g) {
        g.drawImage(imgs[index], xpos - XOffsetCenter, ypos, B_WIDTH, B_HEIGHT, null);
    }

    /**
     * Sets behavior when this instance of the button is interacted with, be it mouse or
     * keyboard input.
     */
    public void update() {
        // the first index means nothing is on it
        index = 0;
        // the second index is highlighted (mouse hovering)
        if (mouseOver) {
            index = 1;
        }
        // the third index shows when the mouse is pressed on the button
        if (mousePressed) {
            index = 2;
        }
    }

    /**
     * reset all booleans in the mouse (mouseOver and mousePressed)
     */
    public void resetButton() {
        mouseOver = false;
        mousePressed = false;
    }

    /**
     * Set the GameState to whatever value was selected by the user, that value is stored in
     * the menuButton as "state"
     */
    public void applyGamestate() {
        GameStates.state = this.state;
    }

    /**
     * Getter for mouseOver
     * 
     * @return the current value of mouseOver
     */
    public boolean isMouseOver() {
        return mouseOver;
    }

    /**
     * Setter for mouseOver
     * 
     * @param mouseOver - new value of mouseOver
     */
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    /**
     * Getter for mousePressed
     * 
     * @return the current value of mousePressed
     */
    public boolean isMousePressed() {
        return mousePressed;
    }

    /**
     * Setter for mousePressed
     * 
     * @param mousePressed - the new of mousePressed
     */
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    /**
     * Getter for the bounds of the button
     * 
     * @return the Rectangle that is the 'hitbox' of the button
     */
    public Rectangle getBounds() { // Returns rectangle around menu button
        return bounds;
    }

}

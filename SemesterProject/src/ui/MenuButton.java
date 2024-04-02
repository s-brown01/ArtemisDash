/**
 * Menu Button Class
 * @author johnbotonakis
 */
package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import states.GameStates;
import utils.LoadSave;
import static utils.Constants.ButtonStates.*;

public class MenuButton {
    private int xpos, ypos, rowIndex, index;
    private int XOffsetCenter = B_WIDTH / 2;
    private GameStates state;
    private BufferedImage[] imgs;
    private boolean mouseOver, mousePressed;
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
        this.rowIndex = rowIndex;
        this.state = state;
        loadImgs();
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
     */
    private void loadImgs() {
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
     * @param g
     */
    public void draw(Graphics g) {
        g.drawImage(imgs[index], xpos - XOffsetCenter, ypos, B_WIDTH, B_HEIGHT, null);
    }

    /**
     * Sets behavior when this instance of the button is interacted with, be it mouse or
     * keyboard input.
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

    public void resetButtons() {
        mouseOver = false;
        mousePressed = false;
    }

    public void applyGamestate() {
        GameStates.state = state;
    }

    /**
     * Getters and Setters
     * 
     * @return
     */
    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Rectangle getBounds() { // Returns rectangle around menu button
        return bounds;
    }
}

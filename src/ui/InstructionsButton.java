package ui;

import static utils.Constants.ButtonStates.B_HEIGHT;
import static utils.Constants.ButtonStates.B_HEIGHT_DEFAULT;
import static utils.Constants.ButtonStates.B_WIDTH;
import static utils.Constants.ButtonStates.B_WIDTH_DEFAULT;
import static utils.Constants.EndButtons.ENDBUTTON_SIZE;
import static utils.Constants.OverworldButtonConstants.DEFAULT;
import static utils.Constants.OverworldButtonConstants.DEFAULT_CLICKED;
import static utils.Constants.OverworldButtonConstants.DEFAULT_HIGHLIGHT;
import static utils.Constants.OverworldButtonConstants.OUTLINE;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import states.GameStates;
import utils.LoadSave;

/**
 * This class represents 1 Button on the Instructions GameState. A button will be linked to
 * different GameStates so that the Game can change states based on what button is pushed.
 * 
 * @author Sean-Paul Brown
 */
public class InstructionsButton extends PauseButton {
    /**
     * The words to display on the button
     */
    private String title;
    /**
     * The GameState to switch to when the button is selected
     */
    private GameStates state;
    /**
     * Represents if the mouse is hovering over the button
     */
    private boolean mouseOver, 
    /**
     * Represents if the mouse is pressed while on this button
     */
    mousePressed;
    /**
     * This is the color that the button will be on screen
     */
    private Color color;
    
    /**
     * This is the constructor for 1 Instructions  Button
     * @param title     - the string to be displayed on screen
     * @param x         - the left coordinate
     * @param y         - the top coordinate
     * @param state     - The game state to draw onto
     */
    public InstructionsButton(String title, int x, int y, GameStates state) {
        super(x, y, (int)(B_WIDTH * .75), (int)(B_HEIGHT * .75));
        this.title = title;
        this.state = state;
    }

    /**
     * Draws the button to the screen with the specified parameters
     * 
     * @param g - the Graphics where to draw the InstructionsButton
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(color);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        g.setColor(OUTLINE);
        // setting the stroke to 2.0 pixels, so it is drawn as a thicker line around the rectangle
        g2d.setStroke(new BasicStroke(3f));
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        g.setColor(Color.WHITE);
        g.setFont(LoadSave.loadFont(LoadSave.FONT, 30));
        g.drawString(title, bounds.x + bounds.width / 5, bounds.y + bounds.height / 4 * 3);
    }

    /**
     * Sets behavior when this instance of the button is interacted with, be it mouse or
     * keyboard input.
     */
    public void update() {
        color = DEFAULT;
        if (mouseOver) {
            color = DEFAULT_HIGHLIGHT;
        }
        if (mousePressed) {
            color = DEFAULT_CLICKED;
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
     * set the GameState to whatever value was selected by the user, that value is stored in
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

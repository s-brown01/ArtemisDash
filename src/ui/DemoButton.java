package ui;

import static utils.Constants.OverworldButtonConstants.DEFAULT;
import static utils.Constants.OverworldButtonConstants.DEFAULT_CLICKED;
import static utils.Constants.OverworldButtonConstants.DEFAULT_HIGHLIGHT;
import static utils.Constants.OverworldButtonConstants.OUTLINE;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * This class is responsible for handling the creation of, and interaction with, buttons
 * bound within the DemoOverlay
 * 
 * @author Sean-Paul Brown
 */
public class DemoButton extends PauseButton {
    /**
     * This will be the text that is shown to the screen
     */
    private final String title;
    /**
     * This boolean will represent if the mouse is hovering over the button without clicking
     */
    private boolean mouseOver,
            /**
             * This boolean represents if the mouse has clicked down on the button
             */
            mousePressed;

    /**
     * This is the color that the button will be when drawn
     */
    private Color color;

    /**
     * This is the constructor for 1 Demo Button
     * 
     * @param title  - the string to be displayed on screen
     * @param x      - the left coordinate
     * @param y      - the top coordinate
     * @param width  - how wide to make the button
     * @param height - how tall to make the button
     */
    public DemoButton(String title, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.title = title;
    }

    /**
     * Draws everything to the screen using Graphics for this specfic button
     * 
     * @param g - Graphics
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(OUTLINE);
        // setting the stroke to 2.0 pixels, so it is drawn as a thicker line around the rectangle
        g2d.setStroke(new BasicStroke(3f));
        g.drawRect(x, y, width, height);
        g.setColor(Color.WHITE);
        g.drawString(title, x + width / 4, y + height / 4 * 3);

    }

    /**
     * Handles any updates that are to be processed by the demo button, such as changing its
     * appearance when hovered over or clicked
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

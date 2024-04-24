package ui;

import static utils.Constants.OverworldButtonConstants.COMPLETED;
import static utils.Constants.OverworldButtonConstants.COMPLETED_CLICKED;
import static utils.Constants.OverworldButtonConstants.COMPLETED_HIGHLIGHT;
import static utils.Constants.OverworldButtonConstants.DEFAULT;
import static utils.Constants.OverworldButtonConstants.DEFAULT_CLICKED;
import static utils.Constants.OverworldButtonConstants.DEFAULT_HIGHLIGHT;
import static utils.Constants.OverworldButtonConstants.HIDDEN;
import static utils.Constants.OverworldButtonConstants.HIDDEN_HIGHLIGHT;
import static utils.Constants.OverworldButtonConstants.OUTLINE;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * This class represents how a "level"/button is displayed on the Overworld GameState.
 * This class relies on the utils.Constants.OverworldButtonConstants class. This
 * represents a single level/button *
 * 
 * @author Sean-Paul Brown
 */
public class OverworldButton {
    /**
     * The name of a specific level
     */
    private final String levelName;
    /**
     * The world number of a level
     */
    private final int worldNumber;
    /**
     * the stage number of a level. This should be the same index as the index of the level
     * that this button correlates to in the LevelManager levels array/
     */
    private final int stageNumber;
    /**
     * Represents if this level has been completed
     */
    private boolean completed = false;
    /**
     * Represents if the level is hidden from the user or not
     */
    private boolean hidden = true;
    /**
     * if the mouse is currently hovering over this button
     */
    private boolean mouseOver = false;
    /**
     * if the mouse is currently pressed on this button
     */
    private boolean mousePressed = false;
    /**
     * The color to draw this button on the menu
     */
    private Color color;
    /**
     * This is the boundary of the button: where the mouse can click on it
     */
    private Rectangle bounds;

    /**
     * The constructor for this class
     * 
     * @param x           - the x coordinate of the button; left-side of the button
     * @param y           - the y coordinate of the button; top-side of the button
     * @param levelName   - the name of the level to be played
     * @param worldNumber - the number of the world that the level is located in
     * @param stageNumber - the stage number of the level
     */
    public OverworldButton(int x, int y, String levelName, int worldNumber, int stageNumber) {
        this.bounds = new Rectangle(x, y, 25, 25);
        this.levelName = levelName;
        this.worldNumber = worldNumber; // specific worlds
        this.stageNumber = stageNumber; // this will be incremented for all levels
    }

    /**
     * This will update the OverworldButton, mainly how the colors are displayed. It uses the
     * hidden/completed and mouseOver/mousePressed
     */
    public void update() {
        // check if the level is hidden
        if (hidden) {
            color = HIDDEN;
            if (mouseOver) {
                color = HIDDEN_HIGHLIGHT;
            }
            // if hidden, only use these colors
            return;
        } 
        // check if it is completed
        if (completed) {
            color = COMPLETED;
            if (mouseOver)
                color = COMPLETED_HIGHLIGHT;
            if (mousePressed)
                color = COMPLETED_CLICKED;
        } else {
            // if not completed, use the DEFAULT color
            color = DEFAULT;
            if (mouseOver)
                color = DEFAULT_HIGHLIGHT;
            if (mousePressed)
                color = DEFAULT_CLICKED;
        }
    }

    /**
     * Draws the button to the graphics g given into the method.
     * 
     * @param g - the Graphics where to draw the button
     */
    public void draw(Graphics g) {
        // Graphics2D allows us to use setStroke and other methods.
        // Casting here doesn't change anything
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(color);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        g.setColor(OUTLINE);
        // setting the stroke to 2.0 pixels, so it is drawn as a thicker line around the rectangle
        g2d.setStroke(new BasicStroke(2.0f));
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    /**
     * get a formatted version of the level name, world number, and stage number Example:
     * "World 1, Stage 1 - The Beginning"
     * 
     * @return - a formatted String of the level info
     */
    public String toString() {
        if (hidden) {
            return "?????";
        } else {
            return "WORLD " + worldNumber + "- STAGE " + stageNumber + ":\n" + levelName;
        }
    }

    /**
     * Get a boolean if the level is hidden or not
     * 
     * @return hidden the current value of hidden
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Get a boolean if the level is completed or not
     * 
     * @return completed the current value of completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Setter for hidden
     * 
     * @param hidden - the next value of hidden
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * setter for completed
     * 
     * @param completed - the next value of completed
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
        
    }

    /**
     * return the button's bounds/hitbox
     * 
     * @return the button's rectangle
     */
    public Rectangle getBounds() {
        return bounds;
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
     * setter for mousePressed
     * 
     * @param mousePressed - the new value of mouseOver
     */
    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    /**
     * Reset every boolean, including hidden/completed
     */
    public void resetBools() {
        completed = false;
        hidden = true;
        mouseOver = false;
        mousePressed = false;
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
     * getter for mouseOver
     * 
     * @return mouseOver - the current value of mouseOver
     */
    public boolean isMouseOver() {
        return mouseOver;
    }

    /**
     * getter for the stageNumber
     * 
     * @return stage number - the stage number associated with this button
     */
    public int getStageNumber() {
        return stageNumber;
    }
}

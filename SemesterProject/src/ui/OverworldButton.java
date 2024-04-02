package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import static utils.Constants.OverworldButtonConstants.*;

/**
 * OverworldButton.java
 * @author Sean-Paul Brown
 * This class represents how a "level"/button is displayed on the Overworld GameState. This class relies on the utils.Constants.OverworldButtonConstants class. This represents a single level/button
 */
public class OverworldButton {
    // this class will keep track of...
    private final String levelName;
    private final int worldNumber, stageNumber;
    private boolean completed = false, mouseOver = false, mousePressed = false, hidden = true;
    private Color color;
    private Rectangle bounds;

    public OverworldButton(int x, int y, String levelName, int worldNumber, int stageNumber) {
        this.bounds = new Rectangle(x, y, 25, 25);
        this.levelName = levelName;
        this.worldNumber = worldNumber; // specific worlds
        this.stageNumber = stageNumber; // this will be incremented for all levels
    }

    public OverworldButton(int x, int y) {
        this.bounds = new Rectangle(x, y, BUTTON_SIZE, BUTTON_SIZE);
        this.levelName = "Level Name";
        this.worldNumber = 1; // specific worlds
        this.stageNumber = 1; // this will be incremented for all levels
    }

    /**
     * 
     */
    public void update() {
        if (hidden) {
            color = HIDDEN;
            if (mouseOver)
                color = HIDDEN_HIGHLIGHT;
        } else if (completed) {
            color = COMPLETED;
            if (mouseOver)
                color = COMPLETED_HIGHLIGHT;
            if (mousePressed)
                color = COMPLETED_CLICKED;
        } else {
            color = DEFAULT;
            if (mouseOver)
                color = DEFAULT_HIGHLIGHT;
            if (mousePressed)
                color = DEFAULT_CLICKED;
        }
    }

    /**
     * 
     * @param g
     */
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(color);
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
        g.setColor(OUTLINE);
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
        return "World " + worldNumber + ", Stage " + stageNumber + " - " + levelName;
    }

    /**
     * Get a boolean if the level is hidden or not
     * 
     * @return
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Get a boolean if the level is completed or not
     * 
     * @return
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Setter for hidden
     * 
     * @param hidden
     */
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * setter for completed
     * 
     * @param completed
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
     * @param mouseOver
     */
    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    /**
     * setter for mousePressed
     * 
     * @param mousePressed
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
     * @return
     */
    public boolean isMousePressed() {
        return mousePressed;
    }

    /**
     * getter for mouseOver
     * 
     * @return
     */
    public boolean isMouseOver() {
        return mouseOver;
    }
    
    /**
     * @return
     */
    public int getStageNumber() {
        return stageNumber;
    }

}

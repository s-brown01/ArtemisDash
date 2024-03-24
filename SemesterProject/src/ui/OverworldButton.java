package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class OverworldButton {
    // this class will keep track of...
    private final String levelName;
    private final int worldNumber, stageNumber;
    private boolean completed = false, mouseOver = false, mousePressed = false, hidden = true;
    private Color color;
    private Color[][] colors = new Color[][] {{new Color(0,0,0)}, {new Color(0, 0, 100), new Color(0, 0, 150), new Color(0,0,200)}, {new Color(0,100,0), new Color(0,150,0), new Color(0,200,0)}};
    private Rectangle bounds;
    
    public OverworldButton(int x, int y, String levelName, int worldNumber, int stageNumber) {
        this.bounds = new Rectangle(x, y, 25, 25);
        this.levelName = levelName;
        this.worldNumber = worldNumber; // specific worlds
        this.stageNumber = stageNumber; // this will be incremented for all levels
    }
    
    public OverworldButton(int x, int y){
        this.bounds = new Rectangle(x, y, 25, 25);
        this.levelName = "Level Name";
        this.worldNumber = 1; // specific worlds
        this.stageNumber = 1; // this will be incremented for all levels
    }
    
    /**
     * 
     */
    public void update() {
        if (hidden) {
            color = colors[0][0];
        }
        else if (completed) {
            color = colors[1][0];
            if (mouseOver)
                color = colors[1][1];
            if (mousePressed)
                color = colors[1][2];
        } else {
            color = colors[2][0];
            if (mouseOver)
                color = colors[2][1];
            if (mousePressed)
                color = colors[2][2];     
        }
    }

    /**
     * 
     * @param g
     */
    public void draw(Graphics g) {
        // TODO - work on this logic
        g.setColor(color);
        
        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
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
    
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * @param mouseOver 
     */
    public void setMouseOver(boolean mouseOver) {        
        this.mouseOver = mouseOver;
    }
    
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
     * @return
     */
    public boolean isMousePressed() {
        return mousePressed;
    }
    
    public boolean isMouseOver() {
        return mouseOver;
    }



}

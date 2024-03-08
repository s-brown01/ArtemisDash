package ui;

import java.awt.Color;
import java.awt.Graphics;

public class OverworldButton {
    // this class will keep track of...
    private final String levelName;
    private final int worldNumber, stageNumber;
    private boolean completed = false;
    private boolean hidden = true;
    private int x, y;

    public OverworldButton(int x, int y) {
        this.x = x;
        this.y = y;
        levelName = "Level Name";
        worldNumber = 1; // specific worlds
        stageNumber = 2; // this will be incremented for all levels
    }

    public void draw(Graphics g) {
        if (hidden) {
            g.setColor(Color.BLACK);
            g.fillRect(x, y, 25, 25);
        } else if (completed) {
            g.setColor(Color.YELLOW);
            g.fillRect(x, y, 25, 25);
        } else {
            g.setColor(Color.RED);
            g.fillRect(x, y, 25, 25);
        }
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

}

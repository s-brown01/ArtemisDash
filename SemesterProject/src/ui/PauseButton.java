
package ui;

import java.awt.Rectangle;

/**
 * Pause Button Class
 * @author johnbotonakis
 * @description This class is responsible for handling the creation of, and interaction with, buttons bound within the Pause Overlay
 */
public class PauseButton {
    protected int x, y, width, height;
    protected Rectangle bounds;

    /**
     * Creates a button for use ONLY in the pause menu
     * 
     * @param x      - X-Position placement of the created button
     * @param y      - Y-Position placement of the created button
     * @param width  - Width of the sprite
     * @param height - Height of the sprite
     */
    public PauseButton(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        createBounds();
    }

    /**
     * Generates clickable bounds to allow the button to be interacted with
     */
    private void createBounds() {
        bounds = new Rectangle(x, y, width, height);
    }

    /**
     * Getters and setters
     * 
     * @return
     */
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}

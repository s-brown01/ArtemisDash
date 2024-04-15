
package ui;

import java.awt.Rectangle;

/**
 * This class is responsible for handling the creation of, and interaction with, buttons
 * bound within the Pause Overlay
 * 
 * @author John Botonakis and Sean-Paul Brown
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
     * Getter for the bounds of this buttons
     * 
     * @return the rectangle that contains the hitbox of this button
     */
    public Rectangle getBounds() {
        return bounds;
    }

}

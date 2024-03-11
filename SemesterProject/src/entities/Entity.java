
package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public abstract class Entity {
    /**
     * Parameters that every instance of entity will inherit Multiplying these by SCALE means
     * it will remain proportionally the same on screen Protected variables mean that only
     * instances of this class can use
     */
    protected final static float GRAVITY = 0.004f * Game.SCALE;
    protected final static int ANISPEED = 25;
    protected float walk_speed = 1.5f * Game.SCALE;
    protected float x, y, width, height;
    protected final Rectangle2D.Float hitbox;

    /**
     * Constructor class for every entity instance
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        hitbox = new Rectangle2D.Float(x, y, width, height);

    }

    abstract protected void update();

    abstract protected void draw(Graphics g);

    /**
     * Draws the hitbox around the player's sprite
     * 
     * @param g
     * @param xlvlOffset The offset needed for the level collision
     */
    protected void drawHitbox(Graphics g, int xlvlOffset) {
        g.setColor(Color.RED);
        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    /**
     * @returns hitbox object for collision detection
     */
    public Rectangle2D getHitbox() {
        return hitbox;
    }

}

/**
 * Entity.java
 * @author Sean-Paul Brown
 * @date 03/15/2024
 * @description
 * This class is an abstract class for all types of moving/interacting objects in the game. 
 * Players, enemies, and bosses will all be children (of different generations) of this class. 
 * Each entity will need an x and y coordinate, along with a width and height.
 */
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
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected int state; // this determines what "mode" the entity is in
    protected int aniIndex, aniTick, aniSpeed = 25;
    protected int currentHealth, maxHealth;

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

    }

    abstract public void update();

    abstract public void draw(Graphics g);

    // Initalize hitbox here
    protected void initHitbox(float x, float y, float width, float height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);
    }

    // Updates hitbox by giving it new X + Y
    protected void updateHitbox() {
        x = (int) x;
        y = (int) y;
    }

    /**
     * Draws the hitbox around the player's sprite. This is for debugging the hitbox
     * 
     * @param g
     * @param xlvlOffset The offset needed for the level collision
     */
    protected void drawHitbox(Graphics g) {
        g.setColor(Color.PINK);
        g.drawRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    /**
     * @returns hitbox object for collision detection
     */
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    /**
     * @return the state that the entity is in
     */
    public int getState() {
        return state;
    }

    /**
     * @return the animation index
     */
    public int getAniIndex() {
        return aniIndex;
    }

}

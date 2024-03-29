package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

/**
 * Entity.java
 * Entity Abstract Class
 * @author johnbotonakis and Sean-Paul Brown
 * This abstract Entity class shares functionality across all player and enemy objects. Things that can move on either axis, 
 * attack, and have some degree of "intelligence" start their classes from this blueprint. 
 */
public abstract class Entity {
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected Rectangle2D.Float attackbox;

    protected int state; // this determines what "mode" the entity is in
    protected int aniIndex, aniTick, aniSpeed = utils.Constants.ANISPEED;
    protected int currentHealth, maxHealth;

    protected boolean inAir;
    protected float gravity = utils.Constants.GRAVITY; // How quickly the entity falls to earth
    protected float airSpeed = 0f; // How quickly the player moves in the air

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
        this.height = height;
        this.width = width;
    }

    /**
     * Draws the hitbox around the player's sprite. This is for debugging the hitbox
     * 
     * /** Draws a hitbox around the entity for debugging purposes
     * 
     * @param g
     */
    protected void drawHitbox(Graphics g) {
        // For debugging hitbox
        g.setColor(Color.PINK);
        g.fillRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    protected void drawAttackbox(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect((int) attackbox.x, (int) attackbox.y, (int) attackbox.width, (int) attackbox.height);
    }

    /**
     * Initializes the hitbox
     * 
     * @param x      - X-Position on screen
     * @param y      - Y-Position on screen
     * @param width  - How wide the box is
     * @param height - How tall the box is
     */
    protected void initHitbox(float x, float y, int width, int height) {
        hitbox = new Rectangle2D.Float(x, y, width, height);

    }

    /**
     * Returns the hitbox of a given entity
     * 
     * @return - hitbox
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

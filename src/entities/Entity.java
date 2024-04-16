package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

/**
 * This abstract Entity class shares functionality across all player and enemy objects.
 * Things that can move on either axis, attack, and have some degree of "intelligence"
 * start their classes from this blueprint. *
 * 
 * @author John Botonakis and Sean-Paul Brown
 * 
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
     * @param x      - X-Position to be placed within the game world
     * @param y      - Y-Position to be placed within the game world
     * @param width  - The width of the entity sprite
     * @param height - The height of the entity sprite
     */

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    /**
     * Draws the hitbox around the caller entities sprite. This is for debugging the hitbox
     * 
     * @param g - Graphics to be called and drawn from
     */
    protected void drawHitbox(Graphics g) {
        // For debugging hitbox
        g.setColor(Color.PINK);
        g.fillRect((int) hitbox.x, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    /**
     * Draws the attack hitbox around the caller entity. This is for debugging attacks
     * 
     * @param g - Graphics to be called and drawn from
     */
    protected void drawAttackbox(Graphics g, int xLevelOffset) {
        g.setColor(Color.RED);
        g.drawRect((int) attackbox.x - xLevelOffset, (int) attackbox.y, (int) attackbox.width, (int) attackbox.height);
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

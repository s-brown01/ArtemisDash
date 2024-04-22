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
    /**
     * The left coordinate of the Entity
     */
    protected float x,
            /**
             * The top coordinate of the Entity
             */
            y;
    /**
     * How wide the Entity is
     */
    protected int width,
            /**
             * how tall the Entity is
             */
            height;
    /**
     * The area that represents where the Entity can interact with objects/environment/other
     * Entities. <BR>
     * The game uses the hitbox to determine if there is contact with other items
     */
    protected Rectangle2D.Float hitbox;
    /**
     * This is the area that represets where the Entity's attack would hit. It is the area
     * that is attacked when the Entity attacks.
     */
    protected Rectangle2D.Float attackbox;

    /**
     * This determines what "mode" the Entity is in. Based on the Constants (specific for each
     * implementation of Entity)
     */
    protected int state;
    /**
     * This represents what animation index to draw to the screen.
     */
    protected int aniIndex,
            /**
             * This is a counter that keeps track of how many updates have passed since the last frame
             * change
             */
            aniTick,
            /**
             * This keeps track of how often the animations change. The higher the animation, the less
             * often it changes.
             */
            aniSpeed = utils.Constants.ANISPEED;
    /**
     * This is how much health the Entity has at a given times
     */
    protected int currentHealth,
            /**
             * This is how much health an Entity could have at any time.
             */
            maxHealth;

    /**
     * This boolean represents in the Entity is in the air and if gravity should be impacting
     * them
     */
    protected boolean inAir = true;
    /**
     * How quickly the entity falls to earth, this will never change
     */
    protected float gravity = utils.Constants.GRAVITY;
    /**
     * How quickly the player is moving in the air at a given time
     */
    protected float airSpeed = 0f;

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
     * @param g            - Graphics to be called and drawn from
     * @param xLevelOffset - The amount of horizontal offset from screenscrolling
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

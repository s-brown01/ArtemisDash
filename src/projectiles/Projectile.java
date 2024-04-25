package projectiles;

import static utils.Constants.ProjectileConstants.getProjHeight;
import static utils.Constants.ProjectileConstants.getProjSpeed;
import static utils.Constants.ProjectileConstants.getProjWidth;

import java.awt.geom.Rectangle2D;

/**
 * This class will provide the framework for how all Projectiles in game and the basis for
 * how they should move. Made with similar structure to Entity abstract class.
 * 
 * @author Sean-Paul Brown
 */
public abstract class Projectile {
    /**
     * the movement equation of every projectile is based on the general linear form: mx + b =
     * y, where b is y(0)
     */
    protected final float slope;
    /**
     * the initial left coordinate of the projectile
     */
    protected float x,
            /**
             * the initial top coordinate of the projectile
             */
            y;
    /**
     * The hitbox of the projectile (where it can hit other in-game objects)
     */
    protected final Rectangle2D.Float hitbox;
    /**
     * the current animation index to draw
     */
    protected int aniIndex = 0,
            /**
             * A counter that represents when it is time to change animation frames
             */
            aniTick = 0;
    /**
     * how often the frames switch. A higher number means the less often frames are changed
     */
    protected int aniSpeed = utils.Constants.ANISPEED;
    /**
     * The horizontal speed of the Projectiles
     */
    protected final float SPEED;

    /**
     * This lets the arrow face left or right, so it can face the direction of travel
     */
    protected final float FLIP_X;
    /**
     * This will draw the width one direction or the opposite. This lets the arrow face left
     * or right, so it can face the direction of travel
     */
    protected final int FLIP_W;

    /** active is used to determine if this Projectile should be 'alive' in the Game */
    protected boolean active = true;
    /**
     * collision is used to determine if this Projectile is currently colliding with anything
     */
    protected boolean colliding = false;

    /**
     * This is the constructor of the Projectile. It relies on the ProjectileConstants static
     * class in utils.Constants
     * 
     * @param x        - the X-coordinate of the Projectile
     * @param y        - the Y-coordinate of the Projectile
     * @param slope    - the slope of the Projectile, what path it will follow
     * @param projType - the type of Projectile it is, based on ProjectileConstants
     * @param left     - if the Arrow is moving towards the left, this should be true; if
     *                 right, false
     */
    public Projectile(float x, float y, float slope, int projType, boolean left) {
        this.x = x;
        this.y = y;
        this.slope = slope;
        // get the specific height and width depending on the type given
        hitbox = new Rectangle2D.Float(this.x, this.y, getProjWidth(projType), getProjHeight(projType));

        // if it is moving left, it should have a negative sleep
        if (left) {
            this.SPEED = -1 * calculateHorizontalSpeed(getProjSpeed(projType), slope);
            this.FLIP_X = hitbox.width;
            this.FLIP_W = -1;
        }
        // if the arrow is moving right, it should have a positive speed
        else {
            this.SPEED = calculateHorizontalSpeed(getProjSpeed(projType), slope);
            this.FLIP_X = 0;
            this.FLIP_W = 1;
        }
    }

    /**
     * A getter for the hitbox
     * 
     * @return - the Player's hitbox
     */
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    /**
     * This is a helper method to calculate the horizontal speed of a projectile for the
     * diagonal speed to be consistent with ARROW_SPEED no matter the slope. <br>
     * This was calculated from using the pythagorean theorem (a^2 +b^2 = c^2). For
     * Projectiles, it is (xSpeed^2 + ySpeed = slopeSpeed, where ySpeed = xSpeed*slope).
     * 
     * @param slopeSpeed - the desired diagonal speed for the projectiles
     * @param slope      - the slope of the projectile
     * @return - the horizontal speed for the projectile to have a consistent speed
     */
    protected float calculateHorizontalSpeed(float slopeSpeed, float slope) {
        // xSpeed = square root of (slopeSpeed^2 / (1+slope^2)
        return (float) Math.sqrt((slopeSpeed * slopeSpeed) / (1 + (slope * slope)));
    }

    /**
     * Getter for the animation index
     * 
     * @return the current animation index
     */
    public int getAniIndex() {
        return aniIndex;
    }

    /**
     * Getter for the active boolean
     * 
     * @return the current value of active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Getter for the colliding boolean
     * 
     * @return the current value of colliding
     */
    public boolean isColliding() {
        return colliding;
    }

    /**
     * Getter for the horizontal speed of the Projectile
     * 
     * @return the SPEED of the Projectile
     */
    public float getSpeed() {
        return SPEED;
    }

}

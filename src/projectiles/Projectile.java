package projectiles;

import static utils.Constants.ProjectileConstants.*;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;

/**
 * Player.java
 * 
 * @author Sean-Paul Brown
 * @date 03/15/2024
 * @description: This class will provide the framework for how all Projectiles in game and
 *               the basis for how they should move. Made with similar structure to Entity
 *               abstract class.
 */
public abstract class Projectile {
    /**
     * the movement equation of every projectile is based on the general linear form: mx + b =
     * y, where b is y(0)
     */
    protected final float slope;
    protected float x, y;
//    protected int width, height;
    protected final Rectangle2D.Float hitbox;
    protected int aniIndex = 0, aniTick = 0;
    protected int aniSpeed = utils.Constants.ANISPEED;
    protected BufferedImage[] animations;

    /** active is used to determine if this Projectile should be 'alive' in the Game */
    protected boolean active = true;
    /** collision is used to determine if this Projectile is currently colliding with anything */
    protected boolean colliding = false;

    /**
     * This is the constructor of the Projectile. It relies on the ProjectileConstants static
     * class in utils.Constants
     * 
     * @param x        - the X-coordinate of the Projectile
     * @param y        - the Y-coordinate of the Projectile
     * @param slope    - the slope of the Projectile, what path it will follow
     * @param projType - the type of Projectile it is, based on ProjectileConstants
     */
    public Projectile(float x, float y, float slope, int projType) {
        this.x = x;
        this.y = y;
        this.slope = slope;
        // get the specific height and width depending on the type given
        hitbox = new Rectangle2D.Float(this.x, this.y, getProjWidth(projType), getProjHeight(projType));
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
     * diagonal speed to be consistent with ARROW_SPEED no matter the slope. This was
     * calculated from using the pythagorean theorem (a^2 +b^2 = c^2). For Projectiles, it is
     * (xSpeed^2 + ySpeed = slopeSpeed, where ySpeed = xSpeed*slope).
     * 
     * @param slopeSpeed - the desired diagonal speed for the projectiles
     * @param slope      - the slope of the projectile
     * @return - the horizontal speed for the proctile to have a consistent speed
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
    
    public boolean isActive() {
        return active;
    }
    
    public boolean isColliding() {
        return colliding;
    }

}

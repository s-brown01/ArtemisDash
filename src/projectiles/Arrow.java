package projectiles;

import static utils.Constants.ProjectileConstants.ARROW_SPEED;

import java.awt.Color;
import java.awt.Graphics;

import static utils.Constants.ProjectileConstants.*;

/**
 * Arrow.java
 * 
 * @author Sean-Paul Brown
 * @date Mar 26, 2024
 * @description This is a child class of Projectile. It represents an Arrow that the
 *              Player will shoot. It cannot detect collisions but will update it's
 *              movement when update is called.
 */
public class Arrow extends Projectile {

    private final float SPEED;

    /**
     * @param x     - the x coordinate of the left of the hitbox
     * @param y     - the y coordinate of the top of the hitbox
     * @param slope - the slope of the arrow: a negative slope will make the arrow rise,
     *              positive falls
     * @param left  - if the Arrow is moving towards the left, this should be true, if right,
     *              false
     */
    public Arrow(float x, float y, float slope, boolean left) {
        super(x, y, slope, ARROW);
        // if it is moving left, it should have a positive slope
        if (left) {
            this.SPEED = calculateHorizontalSpeed(ARROW_SPEED, slope);
        }
        // if the arrow is moving right, it should be the reversed slope
        else {
            this.SPEED = -1 * calculateHorizontalSpeed(ARROW_SPEED, slope);

        }
    }

    /**
     * This method will update the behavior of the Arrow, like updating movement
     * 
     */
    public void update() {
        // update movement
        this.hitbox.x += SPEED;
        this.hitbox.y += SPEED * slope;
    }

    /**
     * This method will draw the specific Arrow to the Graphics inputted into the function
     * 
     * @param g            - the Graphics where to draw stuff
     * @param xLevelOffset - the int representing the screenscrolling
     */
    public void draw(Graphics g, int xLevelOffset) {
        g.setColor(Color.cyan);
        g.drawRect((int) (hitbox.x - xLevelOffset), (int) hitbox.y, (int) (hitbox.width), (int) (hitbox.height));
    }

    public float getSpeed() {
        return SPEED;
    }

}

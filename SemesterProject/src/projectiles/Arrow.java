package projectiles;

import static utils.Constants.ProjectileConstants.ARROW_SPEED;

import java.awt.Color;
import java.awt.Graphics;

import static utils.Constants.ProjectileConstants.*;
/**
 * Arrow.java
 * 
 * @author
 * @date Mar 26, 2024
 * @description
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
        if (left) {
            SPEED = ARROW_SPEED;
        } else {
            SPEED = ARROW_SPEED * -1;
        }
    }

    /**
     * This method will update the behavior of the Arrow, calling methods like
     * updateMovement().
     * 
     */
    public void update() {
//        int[][] lvlData, Player player
        // TODO Fill in method
        updateMovement();
    }

    /**
     * This will update the movement of a projectile
     */
    private void updateMovement() {
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

}

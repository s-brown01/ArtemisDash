package projectiles;

import static utils.Constants.ProjectileConstants.ARROW;
import static utils.Constants.ProjectileConstants.ARROW_DRAW_OFFSET_X;
import static utils.Constants.ProjectileConstants.ARROW_DRAW_OFFSET_Y;
import static utils.Constants.ProjectileConstants.ARROW_WIDTH;
import static utils.Constants.ProjectileConstants.ARROW_HEIGHT;
import static utils.Constants.ProjectileConstants.getProjSpeed;


import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * This is a child class of Projectile. It represents an Arrow that the Player will shoot.
 * It cannot detect collisions but will update it's movement when update is called.
 * 
 * @author Sean-Paul Brown
 */
public class Arrow extends Projectile {

    /**
     * There are a total of 5 sprite maximum for all arrow animations
     */
    private static final int MAX_SPRITES = 5;

    /**
     * @param x     - the x coordinate of the left of the hitbox
     * @param y     - the y coordinate of the top of the hitbox
     * @param slope - the slope of the arrow: a negative slope will make the arrow rise,
     *              positive falls
     * @param left  - if the Arrow is moving towards the left, this should be true, if right,
     *              false
     */
    public Arrow(float x, float y, float slope, boolean left) {
        super(x, y, slope, ARROW, left);       
    }

    /**
     * This method will update the behavior of the Arrow, like updating movement
     * 
     */
    public void update() {
        // updating the animation
        updateAnimation();
        // update movement
        this.hitbox.x += SPEED;
        this.hitbox.y += SPEED * slope;
    }

    /**
     * Method will deal with any animation logic
     */
    private void updateAnimation() {
        // add 1 to the animation tick and check if it is time to change frame
        aniTick++;
        // if NOT time, don't to anything and end early
        if (aniTick < aniSpeed) {
            return;
        }
        // if reaches this point, it is time to change frame
        aniTick = 0;
        aniIndex++;
        // check if it is done with the animation
        // if it it NOT done, do nothing and end early
        if (aniIndex < MAX_SPRITES) {
            return;
        }
        // if reaches this point then it is end of index and restart
        aniIndex = 0;
    }

    /**
     * This method will draw the specific Arrow to the Graphics inputted into the function
     * 
     * @param g            - the Graphics where to draw stuff
     * @param xLevelOffset - the int representing the screenscrolling
     * @param img          - the Image to draw
     */
    public void draw(Graphics g, int xLevelOffset, BufferedImage img) {
        g.drawImage(img, (int) (hitbox.x - xLevelOffset - ARROW_DRAW_OFFSET_X + FLIP_X), (int) hitbox.y - ARROW_DRAW_OFFSET_Y, (int) (ARROW_WIDTH) * FLIP_W,
                (int) (ARROW_HEIGHT), null); 
    }

    /**
     * Getter for the horizontal speed of the Arrow
     * 
     * @return the SPEED of the arrow
     */
    public float getSpeed() {
        return SPEED;
    }

}

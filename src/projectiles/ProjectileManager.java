
package projectiles;

import static utils.Constants.ProjectileConstants.ARROW_DAMAGE;
import static utils.HelperMethods.canMoveHere;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import entities.Enemy;
import main.Game;
import states.Playing;
import utils.LoadSave;

/**
 * This class will be a manager/factory for the Playing GameState. This allows all of the
 * moving/collision logic to be stored outside of the zPlaying state
 * 
 * @author Sean-Paul Brown
 */
public class ProjectileManager {
    /**
     * all of the images that the arrows will use
     */
    private final BufferedImage[] arrowImgs;
    /**
     * a list containing all active Arrows
     */
    private final List<Arrow> arrowList;

    /**
     * This is the constructor
     * 
     * @param playing The playing GameState that is holding this class.
     */
    public ProjectileManager(Playing playing) {
        // arrowList can be any type of List, LinkedList was chosen first
        this.arrowList = new LinkedList<>();
        this.arrowImgs = LoadSave.getArrowImgs();
    }

    /**
     * This method updates each Projectile and checks their end-case (when they should
     * de-spawn)
     * 
     * @param levelData - the current Level represented as a 2D int array
     */
    public void update(int[][] levelData) {
        // the following for loop is from StackOverflow and ChatGPT
        // Create an iterator to safely iterate over the arrowList
        final Iterator<Arrow> arrowIter = arrowList.iterator();
        // Iterate over each arrow in the arrowList
        while (arrowIter.hasNext()) {
            // Get the next arrow from the iterator
            Arrow a = arrowIter.next();
            // update the arrow
            a.update();
            // check for collision
            if (collisionCheck(a.getHitbox(), levelData, a.getSpeed(), a.getSpeed())) {
                // if an Arrow collides with something it is destroyed
                arrowIter.remove();
            }
        }
    }

    /**
     * Checks to see if the Projectile placed into the equation collides with the level or
     * exceeds the bounds
     * 
     * @param hitbox    - the hitbox of the Projectile to check
     * @param levelData - the current Level represented as a 2D int array
     * @param xSpeed    - the horizontal speed of the Projectile
     * @param ySpeed    - the vertical speed of the Projectile
     * 
     * @return true if the arrow collides with anything, false if not.
     */
    private boolean collisionCheck(Rectangle2D.Float hitbox, int[][] levelData, float xSpeed, float ySpeed) {
        // bounds check
        // checking if going below 0
        if (hitbox.getX() < 0 || hitbox.getY() < 0) {
            return true;
        }
        // checking if the hitbox other end of the hitbox is outside of the game.
        // check the length and height of the level, must convert the array length to tiles.
        if (hitbox.getX() + hitbox.getHeight() > levelData[0].length * Game.TILES_SIZE
                || hitbox.getY() + hitbox.getHeight() > levelData.length * Game.TILES_SIZE) {
            return true;
        }

        // checking that the arrow can move to the tile that is xSpeed away
        if (!canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            return true;
        }

        // checking that the arrow can move to the tile that is ySpeed away
        if (!canMoveHere(hitbox.x, hitbox.y + ySpeed, hitbox.width, hitbox.height, levelData)) {
            return true;
        }

        // if it has passed everything, nothing is collided with so return false
        return false;
    }

    /**
     * This method will draw every projectile on screen.
     * 
     * @param g            - the Graphics of where to draw the arrows
     * @param xLevelOffset - the offset from the screenscrolling horizontally
     */
    public void draw(Graphics g, int xLevelOffset) {
        // draw all arrows
        for (Arrow a : arrowList) {
            if (a.isColliding()) {
                a.draw(g, xLevelOffset, arrowImgs[a.getAniIndex()]);
            } else {
                a.draw(g, xLevelOffset, arrowImgs[a.getAniIndex()]);
            }
        }
    }

    /**
     * Check if a certain Enemy (doesn't matter the type) collides with any projectile in the
     * ProjectileMnager.
     * 
     * @param e - the specific Enemy to check.
     * @return ture if the enemy was hit by an arrow, false if the enemy wasn't.
     */
    public boolean checkEnemyHit(Enemy e) {
        // check if the enemy is currently hurting so that they can't get hit over and over
        if (e.isHurting()) {
            return false;
        }
        // check every Arrow on the screen
        // Create an iterator to safely iterate over the arrowList
        final Iterator<Arrow> arrowIter = arrowList.iterator();
        // Iterate over each arrow in the arrowList
        while (arrowIter.hasNext()) {
            // Get the next arrow from the iterator
            Arrow a = arrowIter.next();
            if (e.getHitbox().intersects(a.getHitbox())) {
                // if it does intersect, hurt them
                e.hurt(ARROW_DAMAGE);

                // the arrow breaks on contact with enemy and should be removed from screen
                arrowList.remove(a);
                // return because the arrow can only hit 1 enemy at a time
                return true;
            }
        }
        return false;
    }

    /**
     * This will create a new arrow in the arrowList here, so it can the arrow can be managed
     * 
     * @param x     - the x coordinate of the new Arrow, will be left-bounds of hitbox
     * @param y     - the y coordinate of the new Arrow, will be top-bounds of hitbox
     * @param slope - the slope/path that the arrow will take
     * @param left  - a boolean determining if the Arrow is moving left (true if left, false
     *              if right)
     */
    public void newArrow(float x, float y, float slope, boolean left) {
        arrowList.add(new Arrow(x, y, slope, left));
    }

    /**
     * This method deletes all Projectiles stored in the manager. Should be used when
     * starting/restarting a level.
     */
    public void reset() {
        this.arrowList.clear();
    }

}

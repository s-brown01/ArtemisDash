
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

/**
 * ProjectileManager.java
 * 
 * @author Sean-Paul Brown
 * @date Mar 28, 2024
 * @description This class will be a projectile manager/factory for the Playing GameState.
 *              This allows all of the moving/collision logic to be stored outside of the
 *              Playing state
 */
public class ProjectileManager {
    private final Playing playing;
    private BufferedImage[][] arrowImgs;
    private final List<Arrow> arrowList;

    /**
     * This is the constructor
     * 
     * @param playing The playing GameState that is holding this class.
     */
    public ProjectileManager(Playing playing) {
        this.playing = playing;
        // arrowList can be any type of List, LinkedList was chosen first
        this.arrowList = new LinkedList<>();
        // loadImgs();
    }

    /**
     * This function will load all of the images to be used for projectiles
     */
    private void loadImgs() {
        // load all Arrow images here
//        final BufferedImage arrowSprite = LoadSave.getSpriteSheet("TEST");
        // the first dimension is how many types of arrows. The second is the maximum number of
        // sprites
        arrowImgs = new BufferedImage[1][4];

        // this loop will fill in the arrowImgs array
        for (int i = 0; i < arrowImgs.length; i++) {
            for (int j = 0; j < arrowImgs[i].length; i++) {
                // grab all of the sprites from that row. width and height should be determined on the
                // arrow size
//                arrowImgs[i][j] = arrowSprite.getSubimage(1, 1, 1, 11);
            }
        }
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
            // TODO: CHANGE THE GETSPEED HERE
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
     * @param p         - the Projectile to check collision with
     * @param levelData - the current Level represented as a 2D int array
     * 
     * @return true if the arrow collides with anything, false if not.
     */
    private boolean collisionCheck(Rectangle2D.Float hitbox, int[][] levelData, float xSpeed, float ySpeed) {
        // bounds check
        // checking if going below 0
        if (hitbox.getX() < 0 || hitbox.getY() < 0) {
            System.out.println("NOT Passed collision check - a");
            return true;
        }
        // checking if the hitbox other end of the hitbox is outside of the game.
        // check the length and height of the level, must convert the array length to tiles.
        if (hitbox.getX() + hitbox.getHeight() > levelData[0].length * Game.TILES_SIZE
                || hitbox.getY() + hitbox.getHeight() > levelData.length * Game.TILES_SIZE) {
            System.out.println("NOT Passed collision check - b");
            return true;
        }

        // checking that the arrow can move to the tile that is xSpeed away
        if (!canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            System.out.println("NOT Passed collision check - c");

            return true;
        }

        // checking that the arrow can move to the tile that is ySpeed away
        if (!canMoveHere(hitbox.x, hitbox.y + ySpeed, hitbox.width, hitbox.height, levelData)) {
            System.out.println("NOT Passed collision check - d");
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
            a.draw(g, xLevelOffset);
        }
    }

    /**
     * Check if a certain Enemy (doesn't matter the type) collides with any projectile in the
     * ProjectileMnager.
     * 
     * @param e - the specific Enemy to check.
     */
    public void checkEnemyHit(Enemy e) {
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
                return;
            }
        }
    }

    /**
     * This will create a new arrow in the arrowList here, so it can the arrow can be managed
     * 
     * @param x     - the x coordinate of the new Arrow, will be left-bounds of hitbox
     * @param y     - the y coordinate of the new Arrow, will be top-bounds of hitbox
     * @param slope - the slope/path that the arrow will take
     * @param left  - a boolean determining if the Arrow is moving left (true if left, false
     *              is right)
     */
    public void newArrow(float x, float y, float slope, boolean left) {
        arrowList.add(new Arrow(x, y, slope, left));
    }

}

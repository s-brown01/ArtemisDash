/**
 * Arrow.java
 * @author
 * @date Mar 26, 2024
 * @description
 */
package projectiles;

import java.awt.Graphics;

import entities.Player;

/**
 *
 */
public class Arrow extends Projectile {

    /**
     * @param x
     * @param y
     * @param projectileType
     * @param slope
     */
    public Arrow(float x, float y, int projectileType, int slope) {
        super(x, y, projectileType, slope);
        // TODO Auto-generated constructor stub
    }

    public void update(int[][] lvlData, Player player) {
        // TODO Fill in method
        updateMovement();
    }

    /**
     * 
     */
    private void updateMovement() {
        // TODO this logic
        // check if colliding with a wall or Entity
        
    }

    public void draw(Graphics g) {
        // TODO Fill in method
    }

}

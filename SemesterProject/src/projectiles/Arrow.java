package projectiles;

import java.awt.Color;
import java.awt.Graphics;

import entities.Player;

import static utils.Constants.ProjectileConstants.ARROW_SPEED;

/**
 * Arrow.java
 * 
 * @author
 * @date Mar 26, 2024
 * @description
 */
public class Arrow extends Projectile {

    /**
     * @param x
     * @param y
     * @param projectileType
     * @param slope          A negative slope will make the projectile rise, positive will
     *                       make it fallz
     */
    public Arrow(float x, float y, float slope) {
        super(x, y, slope);
        // TODO Auto-generated constructor stub
    }

    public void update() {
//        int[][] lvlData, Player player
        // TODO Fill in method
        updateMovement();
    }

    /**
     * 
     */
    private void updateMovement() {
        // TODO this logic
        this.hitbox.x += ARROW_SPEED;
        this.hitbox.y += ARROW_SPEED * slope;

    }

    public void draw(Graphics g, int xLevelOffset) {
        g.setColor(Color.cyan);
        g.drawRect((int) (hitbox.x - xLevelOffset), (int) hitbox.y, (int) (hitbox.width), (int) (hitbox.height));
    }

}

/**
 * Player.java
 * @author Sean-Paul Brown
 * @date 03/15/2024
 * @description:
 * This class will provide the framework for how all Projectiles in the game move. 
 * Projectile extends the Entity abstract class. 
 */
package entities;

import java.awt.Graphics;

public abstract class Projectile extends Entity {

    public Projectile(float x, float y, int width, int height) {
        super(x, y, width, height);
        // TODO Auto-generated constructor stub
    }

    public void update() {
        // TODO Fill in method
    }
    
    public void draw(Graphics g) {
        // TODO Fill in method
    }

}

/**
 * Entity Abstract Class
 * @author johnbotonakis
 * This abstract Entity class shares functionality across all player and enemy objects. Things that can move on either axis, 
 * attack, and have some degree of "intelligence" start their classes from this blueprint. 
 * 
 */
package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

//Class you cannot create an instance of, ONLY EXTEND
public abstract class Entity {
    protected float x,y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    
    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }
    /**
     * Draws a hitbox around the entity for debugging purposes
     * @param g
     */
    protected void drawHitbox(Graphics g) {
        //For debugging hitbox
        g.setColor(Color.RED);
        g.drawRect((int)hitbox.x, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
    }
    
    /**
     * Initializes the hitbox
     * @param x - X-Position on screen
     * @param y - Y-Position on screen
     * @param width - How wide the box is
     * @param height - How tall the box is
     */
    protected void initHitbox(float x, float y, float width, float height) {
        hitbox = new Rectangle2D.Float(x,y, width, height);
        
    }
    
//    protected void updateHitbox() {
//        hitbox.x = (int) x;
//        hitbox.y = (int)y;
//    }
    
    /**
     * Returns the hitbox of a given entity
     * @return - hitbox
     */
    public Rectangle2D getHitbox() {
        return hitbox;
    }

    public void update() {
        // TODO Auto-generated method stub
        
    }
}

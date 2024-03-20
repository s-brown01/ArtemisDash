/**
 * Entity.java
 * Entity Abstract Class
 * @author johnbotonakis and Sean-Paul Brown
 * This abstract Entity class shares functionality across all player and enemy objects. Things that can move on either axis, 
 * attack, and have some degree of "intelligence" start their classes from this blueprint. 
 * 
 */
package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import main.Game;

//Class you cannot create an instance of, ONLY EXTEND
public abstract class Entity {
    protected float x,y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected int state; // this determines what "mode" the entity is in
    protected int aniIndex, aniTick, aniSpeed = 25;
    protected int currentHealth, maxHealth;
    
    
    protected boolean inAir;
    protected float gravity = 0.04f * Game.SCALE; // How quickly the player falls to earth
    protected float airSpeed = 0f; // How quickly the player moves in the air


    /**
     * Constructor class for every entity instance
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     */

    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

//    abstract public void update();

//    abstract public void draw(Graphics g);

    // Updates hitbox by giving it new X + Y
//    protected void updateHitbox() {
//        x = (int) x;
//        y = (int) y;
//    }

    /**
     * Draws the hitbox around the player's sprite. This is for debugging the hitbox
     * 
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
    
    /**
     * Returns the hitbox of a given entity
     * @return - hitbox
     */
    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    /**
     * @return the state that the entity is in
     */
    public int getState() {
        return state;
    }

    /**
     * @return the animation index
     */
    public int getAniIndex() {
        return aniIndex;
    }

}

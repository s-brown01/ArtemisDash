package entities;

import static utils.Constants.BossConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * This will be an abstract class that provides a framework for all bosses in the game.
 * This class allows the bosses to have similar behaviors, both in code and possibly in
 * gameplay. It allows the EnemyManager to work well with this and Enemy.
 * 
 * @author Sean-Paul Brown
 * 
 */
public abstract class Boss extends Entity {
    protected int maxHealth;
    protected int currentHealth;
    protected BufferedImage[][] animations;
    protected int flipX, flipW;
    protected boolean firstUpdate = true;

    /**
     * This is the constructor for a Boss Entity
     * 
     * @param x         the horizontal coordinate, on the left side of the hitbox
     * @param y         the vertical coordinate, on the top of the hitbox
     * @param width     the width of the hitbox
     * @param height    the height of the hitbox
     * @param boss_type the type of boss that it will be
     */
    public Boss(float x, float y, int width, int height, int boss_type) {
        super(x, y, width, height);
        maxHealth = getMaxHealth(boss_type);
        this.currentHealth = maxHealth;
    }

    /**
     * This method will be used to draw the Boss Entity
     * 
     * @param g             the Graphics where to draw the Boss
     * @param xLevelOffset  the horizontal offset from screen scrolling
     */
    abstract void draw(Graphics g, int xLevelOffset);

    /**
     * This method will be used to update the Boss Entity
     * 
     * @param levelData     the Current Level's Data in a 2D int array
     */
    abstract void update(int[][] levelData);

}

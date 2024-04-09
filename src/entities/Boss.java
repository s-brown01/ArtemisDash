package entities;

import static utils.Constants.BossConstants.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Boss.java
 * 
 * @author Sean-Paul Brown
 * @date 03/15/2024
 * @description This will be an abstract class that provides a framework for all bosses in
 *              the game. This class allows the bosses to have similar behaviors, both in
 *              code and possibly in gameplay. It allows the EnemyManager to work well
 *              with this and Enemy.
 */
public abstract class Boss extends Entity {
    // test

    protected int maxHealth;
    protected int currentHealth;
    protected int boss_type;
    protected int[] phases; // just a possible way we could set up phases (if we want those)
    private BufferedImage[][] animations;

    /**
     * This is the constructor for a Boss Entity
     * 
     * @param x         - the horizontal coordinate, on the left side of the hitbox
     * @param y         - the vertical coordinate, on the top of the hitbox
     * @param width     - the width of the hitbox
     * @param height    - the height of the hitbox
     * @param boss_type - the type of boss that it will be
     */
    public Boss(float x, float y, int width, int height, int boss_type) {
        super(x, y, width, height);
        this.boss_type = boss_type;
        maxHealth = getMaxHealth(boss_type);
        this.currentHealth = maxHealth;
    }

    public void draw(Graphics g) {

    }

    public void update() {

    }

}

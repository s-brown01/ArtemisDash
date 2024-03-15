/**
 * Enemy.java
 * @author: Sean-Paul Brown
 * @date: 03/15/2024
 * @desc: 
 * This is an abstract class that provides the framework for all Enemy objects in the game. 
 * Individual types of enemies should extend this class, but this should never be a specific enemy. 
 * This will help all Enemies have the same behavior and make it easier to create/implement the EnemyManager.
 */
package entities;

import main.Game;
import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;
import static utils.Constants.PlayerConstants.getAnimationLength;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Enemy extends Entity {

    protected boolean patrolling, attacking; //
    protected boolean active = true; // keeps track of if enemy is "alive" to the program.
    protected boolean firstUpdate = true;
    protected int enemy_type;

    /*
     * These are temporary variables to define how far the enemy can see and how fast the
     * enemy will walk. Both are open to change, just numbers made up by Sean.
     * 
     * Also, enemy_speed can be changed back to walk_speed later if we want It was just a way
     * to show that the enemy_speed will be different than (but maybe related to?) player
     * speed
     * 
     * Attack distance can also change if we want. For now it is just 1 tile.
     * 
     */
//    protected float eyeSight = walk_speed * 5;
//    protected float enemy_speed = walk_speed / 2;
    protected float attackDistance = Game.TILES_SIZE;
    protected int walkDirection = LEFT;

    public Enemy(float x, float y, int width, int height, int enemy_type) {
        super(x, y, width, height);
        this.enemy_type = enemy_type;
        this.maxHealth = getMaxHealth(enemy_type);
        this.currentHealth = maxHealth;
        this.state = IDLE;
    }

    /**
     * This will determine how the enemies move and which direction
     */
    protected void move() {
        // TODO: fill out this method and how enemies move
    }

    /**
     * This will update what tick the enemy is on, and then will change the frame the enemy is
     * on. If the end index has been reached it will restart.
     */
    protected void updateAniTick() {
        // TODO: fill out this method (how each update changes animation)
        // this is only filled out for walking
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= 13) {
                aniIndex = 0;
                // attacking = false allows us to not let the enemy attack over and over
                attacking = false;
            }
        }
    }

    /**
     * This function will subtract what ever damage is inputed into this method from the
     * enemies total health. If the enemy reaches 0 health, it will deactivate and die.
     * 
     * @param damageTaken - the amount of damage taken by this enemy
     */
    public void hurt(int damageTaken) {
        currentHealth -= damageTaken;
        if (currentHealth <= 0) {
            active = false;
            state = DEAD;
        }
    }

    public boolean isActive() {
        return active;
    }

    /**
     * This function will have no purpose. The EnemyManager will draw all Enemies since it is
     * better for memory to have imgs stored there.
     * 
     * USE ENEMY MANAGER DRAW INSTEAD
     */
    @Override
    public void draw(Graphics g) {
        // EMPTY FUNCTION
        // NO ENEMY SHOULD DRAW ITSELF
    }

}

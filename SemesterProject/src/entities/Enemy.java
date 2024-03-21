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
import static utils.Constants.GRAVITY;
import static utils.HelperMethods.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Enemy extends Entity {

    protected boolean patrolling, attacking; //
    protected boolean active = true; // keeps track of if enemy is "alive" to the program.
    protected boolean firstUpdate = true;
    protected int enemy_type;
    protected int tileY;    

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
    protected float eyeSight = attackDistance * 5;
    protected int walkDirection = LEFT;

    public Enemy(float x, float y, int width, int height, int enemy_type) {
        super(x, y, width, height);
        this.enemy_type = enemy_type;
        this.maxHealth = getMaxHealth(enemy_type);
        this.currentHealth = maxHealth;
//        this.state = IDLE;
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
        // this is only filled out for walking
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getSpriteAmount(enemy_type, state)) {
                aniIndex = 0;
                // attacking = false allows us to not let the enemy attack over and over
                attacking = false;
                
                switch(state){
                // only do 1 attack at a time & leave attack animation
                case ATTACK, HIT -> startNewState(IDLE);
                case DEAD -> active = false;
                }
            }
        }
    }
    
    /**
     * This should be used to change the enemy state, instead of assigning it.
     * This will reset the ticks/index
     * @param newState
     */
    protected void startNewState(int newState) {
        // TODO - add validity check
        this.state = newState;
        aniIndex = 0;
        aniTick = 0;
        
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
//            state = DEAD;
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
    public void draw(Graphics g) {
        // EMPTY FUNCTION
        // NO ENEMY SHOULD DRAW ITSELF
    }
    
    
    /**
     * @param player
     */
    protected void checkHit(Player player) {
        // TODO Auto-generated method stub
        
    }

    /**
     * Check if the player is within attack range
     * @param player
     * @return true the player is, either side
     */
    protected boolean isInAttackRange(Player player) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * Turn the sprite/walking direction
     * @param player
     */
    protected void turnTowardsPlayer(Player player) {
        // TODO Auto-generated method stub
        
    }

    /**
     * Check if the player is within eyesight and clear line of sight
     * @param lvlData
     * @param player
     * @return returns true if there is a clear line of sight to the player and within sight
     */
    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        // enemies cannot see different y-values
        
        return false;
    }

    /**
     * @param lvlData
     */
    protected void updateInAir(int[][] lvlData) {
        // check if can fall
        if (canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += airSpeed;
//            hitbox.x += 1;
            airSpeed += GRAVITY;
        } else {
            // if can't fall
            inAir = false;
//            airSpeed = 0;
            hitbox.y = getYPosRoof(hitbox, airSpeed, 0) + hitbox.height;
            tileY = (int)(hitbox.y / Game.TILES_SIZE);
        }
        
    }

    /**
     * This is the first update, should only run once when it is the first update.
     * Since enemies can't jump, it checks if they spawned in the air.
     */
    protected void firstUpdateCheck(int[][] lvlData) {
        // TODO fill this out
        firstUpdate = false;
        // check on ground
        if (!gravity(hitbox, lvlData))
            inAir = true;
        return;
    }

}

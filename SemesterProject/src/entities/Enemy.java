package entities;

import main.Game;
import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;

import java.awt.image.BufferedImage;

public abstract class Enemy extends Entity {

    protected boolean patrolling, attacking; //
    protected boolean active = true; // keeps track of if enemy is "alive" to the program.
    protected boolean firstUpdate = true;
    protected int currentHealth, maxHealth, enemy_type;
    private BufferedImage[][] animations;

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

    protected void move() {
        // TODO: fill out this method and how enemies move
    }

    protected void updateAniTick() {
        // TODO: fill out this method (how each update changes animation)
    }

    public void hurt(int damageTaken) {
        currentHealth -= damageTaken;
        if (currentHealth <= 0) {
            active = false;
            state = DEAD;
        }
    }

}

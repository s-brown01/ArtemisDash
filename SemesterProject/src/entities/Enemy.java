package entities;

import main.Game;
import static utils.Constants.Directions.*;

public abstract class Enemy extends Entity {

    protected boolean patrolling, attacking; // 
    protected boolean active = true; // keeps track of if enemy is "alive" to the program.
    protected int enemyType; // keep track of what enemy it is
    protected boolean firstUpdate = true;
    /*
     * These are temporary variables to define how far the enemy can see and how fast the
     * enemy will walk. Both are open to change, just numbers made up by Sean. 
     * 
     * Also, enemy_speed can be changed back to walk_speed later if we want It was just a way to
     * show that the enemy_speed will be different than (but maybe related to?) player speed
     * 
     * Attack distance can also change if we want. For now it is just 1 tile.
     * 
     */
    protected float eyeSight = walk_speed * 5;
    protected float enemy_speed = walk_speed / 2;
    protected float attackDistance = Game.TILES_SIZE;
    protected int walkDirection = LEFT;

    public Enemy(float x, float y, int width, int height) {
        super(x, y, width, height);
    }
    
    protected void move() {
        // TODO: fill out this method and how enemies move
    }
    
    protected void updateAniTick() {
        // TODO: fill out this method (how each update changes animation)   
    }
    
    

}

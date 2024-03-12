package entities;

public abstract class Enemy extends Entity {

    protected boolean patrolling, attacking;
    /*
     * These are temporary variables to define how far the enemy can see and how fast the
     * enemy will walk. Both are open to change, just numbers made up by Sean. Also,
     * enemy_speed can be changed back to walk_speed later if we want It was just a way to
     * show that the enemy_speed will be different than (but maybe related to?) player speed
     * 
     */
    protected float eyeSight = walk_speed * 5;
    protected float enemy_speed = walk_speed / 2;

    public Enemy(float x, float y, int width, int height) {
        super(x, y, width, height);
    }

}

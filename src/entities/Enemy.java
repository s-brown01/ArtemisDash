package entities;

import main.Game;
import states.Playing;

import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;
import static utils.Constants.GRAVITY;
import static utils.HelperMethods.*;

import java.awt.Graphics;

/**
 * Enemy.java
 * 
 * @author Sean-Paul Brown
 * @date 03/15/2024
 * @description This is an abstract class that provides the framework for all Enemy
 *              objects in the game. Individual types of enemies should extend this class,
 *              but this should never be a specific enemy. This will help all Enemies have
 *              the same behavior and make it easier to create/implement the EnemyManager.
 */
public abstract class Enemy extends Entity {

    protected boolean patrolling, attacking; //
    protected boolean active = true; // keeps track of if enemy is "alive" to the program.
    protected boolean firstUpdate = true;
    protected int enemy_type;
    protected int tileY;
    protected boolean attackChecked;
    protected float walkSpeed = 0.80f;
    // The offset is 55 when game scale is 1.75, so divide to make it work for all scales
    protected float hitboxYOffset = (55 / 1.75f) * Game.SCALE;
    protected int score = 0;
    protected boolean killed = false;

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
    protected float attackDistance;
    protected float eyeSight;
    protected int walkDirection = LEFT;

    /**
     * Initializes an Enemy instance
     * 
     * @param x          - the x-coordinate of the Enemy, will be left side of hitbox
     * @param y          - the y-coorindate of the Enemy, will be the top side of hitboc
     * @param width      - the width of the hitbox
     * @param height     - the height of the hitbox
     * @param enemy_type - the type of enemy, based of utils.Constants.EnemyConstants
     */
    public Enemy(float x, float y, int width, int height, int enemy_type) {
        super(x, y, width, height);
        this.enemy_type = enemy_type;
        this.maxHealth = getMaxHealth(enemy_type);
        this.currentHealth = maxHealth;
    }

    /**
     * This will update what tick the enemy is on, and then will change the frame the enemy is
     * on. If the end index has been reached it will restart.
     */
    protected void updateAniTick() {
        // increment the animation tick
        aniTick++;
        // if the tick is equal to the speed, then it is time to change frame
        if (aniTick >= aniSpeed) {
            // reset the tick and add 1 to the frame
            aniTick = 0;
            aniIndex++;
            // check if that is the last frame in the, reset the index and state
            if (aniIndex >= getSpriteAmount(enemy_type, state)) {
                aniIndex = 0;
                // attacking = false allows us to not let the enemy attack over and over
                // once they attack, they go back to idle
                attacking = false;

                switch (state) {
                // only do 1 attack at a time & leave attack animation
                case ATTACK, HIT -> startNewState(IDLE);
                case DEAD -> active = false;
                }
            }
        }
    }

    /**
     * This should be used to change the enemy state, instead of assigning it. This will reset
     * the ticks/index
     * 
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
     * entity total health. If the enemy reaches 0 health, it will deactivate and die.
     * 
     * @param damageTaken - the amount of damage taken by this entity
     */
    public void hurt(int damageTaken) {
        currentHealth -= damageTaken;
        if (currentHealth <= 0) {
            active = false;
            killed = true;
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
     * This will check if this enemy's attack box intersects with the players hitbox. If it
     * does, the attack connected and player takes damage.
     * 
     * @param player
     */
    public void checkHit(Player player) {
        // TODO - one hit should not immediately kill player
        if (attackbox.intersects(player.getHitbox()))
            player.kill();
        attackChecked = true;
    }

    /**
     * Turn the sprite/walking direction
     * 
     * @param player
     */
    protected void turnTowardsPlayer(Player player) {
        // if the player's x is less than enemy's, they must be to the left
        if (player.getHitbox().x < hitbox.x)
            walkDirection = LEFT;
        else
            // if not left, than right
            walkDirection = RIGHT;

    }

    /**
     * Check if the player is within eyesight and clear line of sight. Players must be on same
     * tile to be seen.
     * 
     * @param lvlData - the data from the level as a 2D int array
     * @param player  - the main Player
     * @return returns true if there is a clear line of sight to the player and within sight
     */
    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        // enemies cannot see different y-values
        final int playerYTile = (int) (player.getHitbox().y / Game.TILES_SIZE);
        // check height/y-tile
        if (playerYTile != tileY)
            return false;
        // check that the player is within their eyesight
        if (!isPlayerInSightRange(player))
            return false;
        // check if the line of sight to the player is clear
        if (!isSightClear(lvlData, hitbox, player.getHitbox(), tileY))
            return false;
        // same height & in eyesight & clear l.o.s.
        // enemy can see player
        return true;
    }

    /**
     * Check that the absolute value of the distance is within the eyeSight of the enemy.
     * 
     * @param player - the Player in the game
     * @return true if the player is within the enemy's eyesight
     */
    private boolean isPlayerInSightRange(Player player) {
        // using absolute value means that we don't care which side player is on
        final int distance = (int) (Math.abs(player.hitbox.x - hitbox.x));
        return distance <= eyeSight;
    }

    /**
     * Check if the player is within attack range
     * 
     * @param player
     * @return true the player is, either side
     */
    protected boolean isInAttackRange(Player player) {
        // using absolute value means that we don't care which side player is on
        final int distance = (int) (Math.abs(player.hitbox.x - hitbox.x));
        return distance <= attackDistance;
    }

    /**
     * Update how the enemy will behave in the air/landing
     * 
     * @param lvlData
     */
    protected void updateInAir(int[][] lvlData) {
        // check if can fall
        if (canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += airSpeed;
            airSpeed += GRAVITY;
        } else {
            // if can't fall
            inAir = false;
            hitbox.y = getYPosRoof(hitbox, airSpeed, hitboxYOffset);
            tileY = (int) (hitbox.y / Game.TILES_SIZE);
        }

    }

    /**
     * This is the first update, should only run once when it is the first update. Since
     * enemies can't jump, it checks if they spawned in the air.
     */
    protected void firstUpdateCheck(int[][] lvlData) {
        firstUpdate = false;
        // check on ground
        if (!floorCheck(hitbox, lvlData))
            inAir = true;
    }

    /**
     * This method will switch the walk direction horizontally. If they enemy is moving right,
     * then they will turn left. If left, then turn right
     */
    protected void switchWalkDirection() {
        // if left currently, move right
        if (walkDirection == LEFT)
            walkDirection = RIGHT;
        else
            // if they're not going left, they are now
            walkDirection = LEFT;
    }

    /**
     * This method determines how the Enemy will move and how fast they will.
     * 
     * @param lvlData - the current Level's data as a 2D int array
     */
    protected void move(int[][] lvlData) {
        // xSpeed will store where the enemy is moving and how fast
        float xSpeed = 0;
        // left is a negative number, right is a positive number
        if (walkDirection == LEFT) {
            xSpeed -= walkSpeed;
        } else {
            xSpeed += walkSpeed;
        }

        // if the Enemy can move to the tile that is xSpeed away AND they can walk on that tile,
        // move there.
        if (canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)
                && isTileWalkable(hitbox, xSpeed, lvlData)) {
            // moving the hitbox will move the enemy
            hitbox.x += xSpeed;
            // return after this so it doesn't switch direction
            return;
        }

        // if the enemy can't move to the next tile OR it isn't walkable, switch their direction.
        switchWalkDirection();

    }

    /**
     * This function will return the X-position of the entity based on which direction it is
     * facing. This will allow the Enemies to be drawn facing both directions.
     * 
     * @return width of the Enemy if walking left, 0 if facing right;
     */
    public int xFlipped() {
        if (walkDirection == LEFT) {
            return width;
        } else {
            return 0;
        }
    }

    /**
     * This method will return a scalar value to multiply the drawn width by, which depends on
     * which direction the Enemy is facing. This allows the Enemy to be drawn correctly either
     * direction.
     * 
     * @return -1 if facing left, 1 if facing right
     */
    public int widthFlipped() {
        if (walkDirection == LEFT) {
            return -1;
        } else {
            return 1;
        }
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getScore() {
        return score;
    }

    public boolean getKilled() {
        return killed;
    }

}

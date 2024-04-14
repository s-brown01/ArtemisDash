package entities;

import main.Game;

import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;
import static utils.Constants.GRAVITY;
import static utils.HelperMethods.*;

import java.awt.Graphics;

/**
 * This is an abstract class that provides the framework for all Enemy objects in the
 * game. Individual types of enemies should extend this class, but this should never be a
 * specific enemy. This will help all Enemies have the same behavior and make it easier to
 * create/implement the EnemyManager.
 * 
 * @author Sean-Paul Brown
 */
public abstract class Enemy extends Entity {

    /** Keeps track of if this Enemy is currently attacking the player*/
    protected boolean attacking; 
    protected boolean active = true; // keeps track of if enemy is "alive" to the Game.
    /**
     * The firstUpdate keeps track of if the enemy has been updated at all. On the first update, the enemy should check if it is in the air. After the first update, the Enemy should never re-enter the air.
     */
    protected boolean firstUpdate = true; 
    /**
     * The type of Enemy it is, based on the EnemyConstants
     */
    protected int enemy_type;
    /**
     * The vertical (y-axis) tile that the Enemy is on
     */
    protected int tileY;
    /**
     * used to keep track of if a single attack has been checked for collision with the player
     */
    protected boolean attackChecked;
    /** 
     * the movement speed of the enemy
     */
    protected float walkSpeed = 0.80f;
    /**
     * The image offset from top side of image to top side of hitbox.
     * 
     * The offset is 55 when game scale is 1.75, so divide to make it work for all scales
     */
    protected float hitboxYOffset = (55 / 1.75f) * Game.SCALE;
    protected int score = 0;
    protected boolean killed = false;

    /**
     * the variables that keep track of how the player sees and attacks the palyer
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
     * @param newState - the state to be changed too
     */
    protected void startNewState(int newState) {
        // reset the animation tick and index so it start from the beginning of the animation
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
        // take the damage from the arrow
        currentHealth -= damageTaken;
        // if the health is equal to (or less than) 0, the enemy is dead
        if (currentHealth <= 0) {
            killed = true;
            startNewState(DEAD);
        }
    }

    /**
     * Getter for the active boolean
     * @return the current value of active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * This function will have no purpose. The EnemyManager will draw all Enemies since it is
     * better for memory to have imgs stored there.
     * 
     * USE ENEMY MANAGER DRAW INSTEAD
     * @param g     - the graphics where to draw the Enemy
     */
    public void draw(Graphics g) {
        // EMPTY FUNCTION
        // NO ENEMY SHOULD DRAW ITSELF
    }

    /**
     * This will check if this enemy's attack box intersects with the players hitbox. If it
     * does, the attack connected and player takes damage.
     * 
     * @param player    - the Player entity
     */
    public void checkHit(Player player) {
        // if the attackbox intersects with the Player's hitbox then the attack hit the player
        if (attackbox.intersects(player.getHitbox())) {
            player.hurt();
        }
        // once the attack is checked, it shouldn't be checked again
        attackChecked = true;
    }

    /**
     * Turn the sprite/walking direction
     * 
     * @param player    - the Player entity
     */
    protected void turnTowardsPlayer(Player player) {
        // if the player's x is less than enemy's, they must be to the left
        if (player.getHitbox().x < hitbox.x) {
            walkDirection = LEFT;
        }
        else {
            // if not left, than right
            walkDirection = RIGHT;
        }
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
        if (playerYTile != tileY) {
            return false;
        }
        // check that the player is within their eyesight
        if (!isPlayerInSightRange(player)) {
            return false;
        }
        // check if the line of sight to the player is clear
        if (!isSightClear(lvlData, hitbox, player.getHitbox(), tileY)) {
            return false;
        }
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
        // using absolute value since it doesn't matter which side the player is on
        final int distance = (int) (Math.abs(player.hitbox.x - hitbox.x));
        return distance <= eyeSight;
    }

    /**
     * Check if the player is within attack range
     * 
     * @param player    - the Player entity
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
     * @param lvlData - the current Level's data as a 2D int array
     */
    protected void updateInAir(int[][] lvlData) {
        // check if can there is room underneath to walk. If there is, then fall
        if (canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += airSpeed;
            airSpeed += GRAVITY;
        } else {
            // if there is nothing underneath, land on the tile exactly
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
        // if this happens, the first update has happened
        firstUpdate = false;
        // check if the Enemy is on the floor.
        // if it isn't, then they are in the air
        if (!floorCheck(hitbox, lvlData))
            inAir = true;
    }

    /**
     * This method will switch the walk direction horizontally. If they enemy is moving right,
     * then they will turn left. If left, then turn right
     */
    protected void switchWalkDirection() {
        // if left currently, move right
        if (walkDirection == LEFT) {
            walkDirection = RIGHT;
        }
        else {
            // if they're not going left, they are now
            walkDirection = LEFT;
        }
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
        // if the enemy is walking to the left, then the draw offset should be the width
        if (walkDirection == LEFT) {
            return width;
        } else {
            // if the enemy is walking to the left, then the x to draw is the same as hitbox
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
        // if the enemy is walking to the left, then the flipWidth is the opposite sign so it is drawn backwards
        if (walkDirection == LEFT) {
            return -1;
        } else {
            // if the enemy is walking to the right, then the flipWidth is the normal sign
            return 1;
        }
    }

    /**
     * Getter for the killed boolean
     * @return the current value of killed
     */
    public boolean isKilled() {
        return killed;
    }
    
    /**
     * Resets enemy behavior
     */
    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        active = true;
        airSpeed = 0;
    }
}

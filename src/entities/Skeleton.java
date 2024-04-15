package entities;

import static utils.Constants.EnemyConstants.*;

import java.awt.geom.Rectangle2D;

/**
 * Skeleton is a child-class of Enemy. It is a specific instance of Enemy, that will use
 * the skeleton constants and implement all behaviors/updates here.
 * 
 * @author Sean-Paul Brown
 */
public class Skeleton extends Enemy {

    /**
     * This is the frame where the skeleton swings their axe, so it is the frame that should
     * be checked for hitting the player
     */
    private static final int ATTACK_FRAME = 8;

    /**
     * This is the constructor for a single Skeleton
     * 
     * @param x      - the initial x-coordinate of the Skeleton
     * @param y      - the initial y-cooridinate of the Skeleton
     * @param width  - the width of the Skeleton
     * @param height - the height of the Skeleton
     */
    public Skeleton(float x, float y, int width, int height) {
        super(x, y, width, height, SKELETON);
        initHitbox(x, y, SKELETON_HITBOX_WIDTH, SKELETON_HITBOX_HEIGHT);
        startNewState(IDLE);
        // attack box will be a long rectangle that extends out from the hitbox of the Skeleton
        attackbox = new Rectangle2D.Float(hitbox.x, hitbox.y, SKELETON_HITBOX_WIDTH * 3, SKELETON_HITBOX_HEIGHT);
        attackDistance = hitbox.width;
        eyeSight = attackDistance * 5;
        this.score = 100;
    }

    /**
     * This will update where the Skeleton is, what action they are doing, and their
     * animation.
     * 
     * @param lvlData - A 2D int array of all of the data in the level
     * @param player  - the Player entity of the Game
     */
    public void update(int[][] lvlData, Player player) {
        updateBehavior(lvlData, player);
        updateAniTick();
        updateAttackbox();
    }

    /**
     * This is a helper function to update the behavior of the Skeleton based on it's
     * booleans.
     * 
     * @param lvlData - the current Level's data as a 2D array
     * @param player  - the Player Entity
     */
    private void updateBehavior(int[][] lvlData, Player player) {
        // check if is the first update
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }
        // then check if they are in the air
        if (inAir) {
            updateInAir(lvlData);
            // returning here instead of "else" statement
            return;
        }

        // if the enemy is not in the air then update their behavior based on their current state
        switch (state) {
        // if IDLE, start moving
        case (IDLE):
            startNewState(RUNNING);
            break;
        // if moving, check if they can see, move to, and attack the player
        case (RUNNING):
            // turn, attack, then move
            // if the Enemy can see player
            if (canSeePlayer(lvlData, player)) {
                // then turn towards the turn towards player
                turnTowardsPlayer(player);
                // if the Player is in attack range
                if (isInAttackRange(player)) {
                    // start attacking the player
                    startNewState(ATTACK);
                }
            }
            move(lvlData);
            break;
        // if attacking, update the attackbox and check the attack
        case (ATTACK):
            // change where the attackbox is located
            updateAttackbox();
            // check if first update with attack (i.e. first frame)
            if (aniIndex == 0) {
                attackChecked = false;
            }
            // if the attack hasn't been checked yet and it is on the attack frame
            if (!attackChecked && aniIndex == ATTACK_FRAME) {
                checkHit(player);
            }
            break;
        // if they are hit just update normally (since Skeletons are a 1 hit then they die)
        case (HIT):
            break;
        }

    }

    /**
     * Make sure the attack box is in line with the hitbox/sprite
     */
    private void updateAttackbox() {
        attackbox.x = hitbox.x - hitbox.width;
        attackbox.y = hitbox.y;
    }

}

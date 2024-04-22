package entities;

import static utils.Constants.EnemyConstants.ATTACK;
import static utils.Constants.EnemyConstants.HIT;
import static utils.Constants.EnemyConstants.IDLE;
import static utils.Constants.EnemyConstants.RUNNING;
import static utils.Constants.EnemyConstants.SKELETON_KING;
import static utils.Constants.EnemyConstants.SKELETON_KING_HITBOX_HEIGHT;
import static utils.Constants.EnemyConstants.SKELETON_KING_HITBOX_WIDTH;

import java.awt.geom.Rectangle2D;

/**
 * Skeleton King is a child-class of Enemy. It is a specific instance of Enemy, that will
 * use the skeleton king constants and implement all behaviors/updates here.
 * 
 * @author Sean-Paul Brown
 */
public class SkeletonKing extends Enemy {

    /**
     * This is the frame when the skeleton kings swings its axe, so it is the frame that
     * should be checked for hitting the player
     */
    private static final int ATTACK_FRAME = 8;

    /**
     * This is the constructor of the Skeleton King
     * 
     * @param x      - the initial x-coordinate of the Skeleton King
     * @param y      - the initial y-cooridinate of the Skeleton King
     * @param width  - the width of the Skeleton King
     * @param height - the height of the Skeleton King
     */
    public SkeletonKing(float x, float y, int width, int height) {
        super(x, y, width, height, SKELETON_KING);
        initHitbox(x, y, SKELETON_KING_HITBOX_WIDTH, SKELETON_KING_HITBOX_HEIGHT);
        startNewState(IDLE);
        attackbox = new Rectangle2D.Float(hitbox.x, hitbox.y, SKELETON_KING_HITBOX_WIDTH * 3,
                SKELETON_KING_HITBOX_HEIGHT);
        attackDistance = hitbox.width * 1.5f;
        eyeSight = attackDistance * 10;
        this.score = 1000;
    }

    /**
     * This will update where the Skeleton King is, what action they are doing, and their
     * animation.
     * 
     * @param lvlData - A 2D int array of all of the data in the level
     * @param player  - the Player entity in the Game
     */
    public void update(int[][] lvlData, Player player) {
        updateBehavior(lvlData, player);
        updateAniTick();
        updateAttackbox();
    }

    /**
     * This is a helper function to update the behavior of the Skeleton King based on it's
     * booleans.
     * 
     * @param lvlData - the current Level's data as a 2D array
     * @param player  - the Player Entity
     */
    private void updateBehavior(int[][] lvlData, Player player) {
        // THIS IS THE SAME STRUCTURE AS SKELETON
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }

        if (inAir) {
            updateInAir(lvlData);
            return;
        }

        switch (state) {
        case (IDLE):
            startNewState(RUNNING);
            break;
        case (RUNNING):
            // since hurting prevents the enemy from taking additional damage, moving it here gives
            // them some invincibility over a couple updates
            hurting = false;
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
        case (ATTACK):
            turnTowardsPlayer(player);
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

            /*
             * Check to make sure that when attacking, if the player hasn't moved, king doesn't move.
             * This checks if the player is within the attackbox and the buffer. If the player is,
             * then the skeleton king shouldn't move.
             */
            final int xBuffer = 15;
            if (this.attackbox.x + xBuffer < player.getHitbox().x
                    && this.attackbox.x + attackbox.width - xBuffer > player.getHitbox().x + player.getHitbox().width) {
                break;
            }

            move(lvlData);
            break;
        case (HIT):
            break;
        }
    }

}
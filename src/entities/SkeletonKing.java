package entities;

import static utils.Constants.EnemyConstants.*;
import static utils.HelperMethods.getYPosRoof;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public class SkeletonKing extends Enemy {

    /**
     * This is the frame where the skeleton swings their axe, so it is the frame that should
     * be checked for hitting the player
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
            // turn towards where the player is so the attack "follows" them
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
            move(lvlData);
            break;
        case (HIT):
            break;
        }
    }

    public void drawHitbox(Graphics g, int xLevelOffset) {
        g.setColor(Color.red);
        g.drawRect((int) hitbox.x - xLevelOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

}

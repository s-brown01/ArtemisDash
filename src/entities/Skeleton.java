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
     * @param player
     */
    public void update(int[][] lvlData, Player player) {
        updateBehavior(lvlData, player);
        updateAniTick();
        updateAttackbox();
    }

    /**
     * 
     * @param lvlData
     * @param player
     */
    private void updateBehavior(int[][] lvlData, Player player) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);

        if (inAir)
            updateInAir(lvlData);
        else {
            switch (state) {
            case (IDLE):
                startNewState(RUNNING);
                break;
            case (RUNNING):
                // turn, attack, then move
                // if can see player
                if (canSeePlayer(lvlData, player)) {

                    // turn towards player
                    turnTowardsPlayer(player);
                    // if in attack range
                    if (isInAttackRange(player))
                        startNewState(ATTACK);
                }
                move(lvlData);
                break;
            case (ATTACK):
                updateAttackbox();
                // check if first update with attack
                if (aniIndex == 0)
                    attackChecked = false;
                if (!attackChecked && aniIndex == 8)
                    checkHit(player);
                break;
            case (HIT):
                break;
            }
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

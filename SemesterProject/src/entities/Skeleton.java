/**
 * Skeleton.java
 * @author Sean-Paul Brown
 * @date 03/15/2024
 * @description:
 * Skeleton is a child-class of Enemy. It is a specific instance of Enemy, 
 * that will use the skeleton constants and implement all behaviors/updates here.
 * 
 */
package entities;

import static utils.Constants.EnemyConstants.*;

import java.awt.geom.Rectangle2D;

import main.Game;

public class Skeleton extends Enemy {

    public Skeleton(float x, float y, int width, int height) {
        super(x, y, width, height, SKELETON);
        initHitbox(x, y, SKELETON_HITBOX_WIDTH, SKELETON_HITBOX_HEIGHT);
        startNewState(IDLE);
        attackbox = new Rectangle2D.Float(hitbox.x, hitbox.y, SKELETON_HITBOX_WIDTH, SKELETON_HITBOX_HEIGHT);
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
                    System.out.println("SEEING PLAYER");

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
                if (!attackChecked && aniIndex == 3)
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
        // TODO Auto-generated method stub

    }

}

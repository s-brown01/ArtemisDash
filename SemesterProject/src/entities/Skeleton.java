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

import java.awt.Graphics;

import utils.HelperMethods;

import static utils.Constants.EnemyConstants.*;
import static utils.HelperMethods.*;

public class Skeleton extends Enemy {

    public Skeleton(float x, float y, int width, int height) {
        super(x, y, width, height, SKELETON);
        initHitbox(x, y, width, height);
        // TODO Auto-generated constructor stub
    }

    /**
     * This will update where the Skeleton is, what action they are doing, and their
     * animation.
     * 
     * @param lvlData - A 2D int array of all of the data in the level
     */
    public void update(int[][] lvlData) {
        if (firstUpdate)
            firstUpdateRun(lvlData);
        updateAniTick();
    }

    /**
     * This is the first update, should only run once when it is the first update.
     * Since enemies can't jump, it checks if they spawned in the air.
     */
    private void firstUpdateRun(int[][] lvlData) {
        // TODO fill this out
        firstUpdate = false;
        // check on ground
        if (!gravity(hitbox, lvlData)) {
            inAir = true;
            
        }
    }

}

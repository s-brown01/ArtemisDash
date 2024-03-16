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
import static utils.Constants.EnemyConstants.*;

public class Skeleton extends Enemy {

    public Skeleton(float x, float y, int width, int height) {
        super(x, y, width, height, SKELETON);
        initHitbox(x, y, width, height);
        // TODO Auto-generated constructor stub
    }

    /**
     * This will update where the Skeleton is, what action they are doing, and their
     * animation.
     */
    @Override
    public void update() {
        updateAniTick();
    }

}

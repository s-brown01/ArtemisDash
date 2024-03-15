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
     * This will update where the Skeleton is, what action they are doing, and their animation.
     */
    @Override
    public void update() {
        updateAniTick();
    }



}

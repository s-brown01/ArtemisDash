package entities;

import java.awt.Graphics;
import static utils.Constants.EnemyConstants.*;

public class Skeleton extends Enemy {

    public Skeleton(float x, float y, int width, int height) {
        super(x, y, width, height, SKELETON);
        initHitbox(x, y, width, height);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void update() {
        updateAniTick();
    }

    @Override
    public void draw(Graphics g) {
        // Individual enemies should not be able to draw themselves as it is better for the memory to have the enemy manager draw everything
    }



}

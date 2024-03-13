package entities;

import java.awt.Graphics;
import static utils.Constants.EnemyConstants.*;

public class Skeleton extends Enemy {

    public Skeleton(float x, float y, int width, int height) {
        super(x, y, width, height, SKELETON);
        loadAnimations();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @Override
    public void draw(Graphics g) {
        // TODO Auto-generated method stub

    }

    private void loadAnimations() {
        // TODO Auto-generated method stub
        
    }

    public int getAniIndex() {
        // TODO Auto-generated method stub
        return 0;
    }



}

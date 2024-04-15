package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.Constants;
import utils.LoadSave;

import static utils.Constants.GRAVITY;
import static utils.Constants.BossConstants.*;
import static utils.Constants.EnemyConstants.SKELETON_WIDTH_DEFAULT;
import static utils.HelperMethods.*;
import static utils.Constants.EnemyConstants.SKELETON_HEIGHT_DEFAULT;
/**
 * This class is a concrete implementation of the Boss superclass. This represents a Skeleton King in the game.
 * @author Sean-Paul Brown
 *
 */
public class SkeletonKing extends Boss {
    
    private final int attackDmg;

    public SkeletonKing(float x, float y, int width, int height) {
        super(x, y, width, height, SKELETON_KING);
        this.attackDmg = getAttackDamage(SKELETON_KING);
        initHitbox(x, y, SKELETON_KING_HITBOX_WIDTH, SKELETON_KING_HITBOX_HEIGHT);
        loadAnimations();
        faceRight();
    }

    /**
     * this is a helper method that will load all of the animations from the Skeleton sprite sheet into the animations array
     */
    private void loadAnimations() {
        // this is the same loop as the EnemyManager loadImgs method for the Skeletons, 
        // since the Skeleton King uses the same animations as the Skeletons
        animations = new BufferedImage[6][18];
        final BufferedImage spriteImg = LoadSave.getSpriteSheet(LoadSave.SKELETON_SPRITES);
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = spriteImg.getSubimage(i * SKELETON_WIDTH_DEFAULT, j * SKELETON_HEIGHT_DEFAULT,
                        SKELETON_WIDTH_DEFAULT, SKELETON_HEIGHT_DEFAULT);
            }
        }
        
    }

    /**
     * this will draw the Skeleton King to the Graphics given into the method
     * 
     * @param g             the Graphics where to draw the Boss
     * @param xLevelOffset  the horizontal offset from screen scrolling
     */
    @Override
    public void draw(Graphics g, int xLevelOffset) {
        g.drawImage(animations[getState()][getAniIndex()],
                (int) (hitbox.x - SKELETON_KING_DRAW_OFFSET_X - xLevelOffset + flipX),
                (int) (hitbox.y - SKELETON_KING_DRAW_OFFSET_Y), (int) (SKELETON_KING_WIDTH * flipW),
                (int) (SKELETON_KING_HEIGHT), null);    
    }

    @Override
    public void update(int[][] levelData) {
        updateBehavior(levelData);
        updateAniTick();
    }
    
    private void updateBehavior(int[][] levelData) {
        if (firstUpdate) {
            firstUpdateCheck(levelData);
        }
        
        if (inAir) {
            updateInAir(levelData);
            return;
        }   
        
        switch (state) {
        // if IDLE, start moving
        case (IDLE):
            startNewState(RUNNING);
            break;
        // if moving, check if they can see, move to, and attack the player
        case (RUNNING):
            // turn, attack, then move
            move(levelData);
            startNewState(ATTACK);
            break;
        case (ATTACK):
            break;
        // if they are hit just update normally (since Skeletons are a 1 hit then they die)
        case (HIT):
            break;
        }
    }

    private void move(int[][] levelData) {
        // TODO Auto-generated method stub
        
    }

    private void startNewState(int newState) {
        this.state = newState;
        aniIndex = 0;
        aniTick = 0;        
    }

    private void updateInAir(int[][] levelData) {
        // check if can there is room underneath to walk. If there is, then fall
        if (canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
            hitbox.y += airSpeed;
            airSpeed += GRAVITY;
        } else {
            // if there is nothing underneath, land on the tile exactly
            inAir = false;
            hitbox.y = getYPosRoof(hitbox, airSpeed, SKELETON_KING_DRAW_OFFSET_Y);
        }        
    }

    private void firstUpdateCheck(int[][] levelData) {
        // if this happens, the first update has happened
        firstUpdate = false;
        // if it isn't, then they are in the air
        if (!floorCheck(hitbox, levelData))
            inAir = true;        
    }

    private void updateAniTick() {
        aniTick++;
        // check to see if it is time to increase the animation frame
        if (aniTick >= Constants.ANISPEED) {
            aniTick = 0;
            aniIndex++;
            // check to see if the animation is completed
            if (aniIndex >= getSpriteAmount(state, SKELETON_KING)) {
                // once it is complete, reset all of the variables such at attacking/hurting
                aniIndex = 0;
            }
        }        
    }

    /**
     * This is a helper function to "flip" the sprite so that it is facing towards the right
     * side of the screen.
     */
    private void faceRight() {
        flipX = 0;
        flipW = 1;
    }

    /**
     * This is a helper function to "flip" the sprite so that it is facing towards the left
     * side of the screen.
     */
    private void faceLeft() {
        flipX = width;
        flipW = -1;
    }

}

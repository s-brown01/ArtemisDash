
package entities;

import static utils.Constants.PlayerConstants.*;
import static utils.HelperMethods.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.HelperMethods;
import utils.LoadSave;

public class Player extends Entity {

    private static int playerCount = 0;

    private static boolean singletonCheck() {
        return playerCount <= 1;
    }

    /**
     * Parameters the player will inherit from the Entity abstract class
     */
    private BufferedImage[][] animations;
    private int aniIndex, aniTick, aniSpeed = 25;
    private int player_action = IDLE;
    private boolean left, right, up, down, moving, attacking = false;
    private float playerSpeed = 2.0f;
    private int[][] lvlData;
    private float xDrawOffset =0;// Change 21?
    private float yDrawOffset = 4;// Change 4?

    // For jumping and gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;
    private boolean jump;

    //Player Constructor
    public Player(float xPosition, float yPosition, int width, int height) {
        super(xPosition, yPosition, width, height);
        if (!singletonCheck())
            throw new IllegalStateException("Another player has already been created");
        playerCount++;
        loadAnimations();
        initHitbox(xPosition, yPosition, 55*Game.SCALE, 65*Game.SCALE);
    }

    /**
     * Updates the position, animation tick, and row of sprites
     */
    public void update() {
        updatePos();
        updateAniTick();
        setAnimation();

    }

    /**
     * Draws the sprite along with it's hitbox to the screen
     */
    public void draw(Graphics g) {
        // 100 px offset from top left corner, 70px offset from top
        // Width is ~64, Height ~60
        // Doubled X and Y size
        g.drawImage(animations[player_action][aniIndex], (int) (hitbox.x), (int) (hitbox.y),
                width, height, null);
        drawHitbox(g);
    }
    
    //Updates Animation by progressing to the next frame,
    //given the player's current action
    private void updateAniTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getAnimationLength(player_action)) {
                aniIndex = 0;
                attacking = false;
            }
        }
    }

    //Starts the animation based on the player's action
    private void setAnimation() {
        int start_state = player_action;
        if (moving) {
            player_action = RUNNING;
        } else {
            player_action = IDLE;
        }

        if (inAir) {
            if (airSpeed < 0) {
                player_action = JUMP_START;
            } else {
                player_action = FALLING;
            }
        }
        if (attacking) {
            player_action = ATTACK;
        }
        // checking if the state has changed
        // if it has changed, we need to restart animation
        if (start_state != player_action)
            resetAni();
    }

    //Resets animation related values back to 0
    //Called when out of frames, but needs to keep animating
    private void resetAni() {
        aniIndex = 0;
        aniTick = 0;
    }

    /**
     * Updates position of hitbox as well as player sprite Handles things such as pressing
     * Left and Right simultaneously
     */
    private void updatePos() {
        moving = false;

        if (jump)
            jump();
        if (!left && !right && !inAir)
            return;

        float xSpeed = 0;

        if (left)
            xSpeed -= playerSpeed;
        if (right)
            xSpeed += playerSpeed;

        if (!inAir)
            if (!isOnFloor(hitbox, lvlData))
                inAir = true;

        if (inAir) {
            if (canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = getYPosRoof(hitbox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }

        } else
            updateXPos(xSpeed);
        moving = true;
    }


    //Jump logic 
    private void jump() {
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;

    }

    //Landing logic
    private void resetInAir() {
        inAir = false;
        airSpeed = 0;

    }

    //Update the horizontal positioning based on if you are able to move forward
    private void updateXPos(float xSpeed) {
        if (canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = getXPosWall(hitbox, xSpeed);
        }

    }

    /**
     * Loads ALL animations from a sprite sheet into a 2D array The length of this array, will
     * be the length of the longest animation
     */
    private void loadAnimations() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);
        // change 13 and 20 to constants at top of class
        animations = new BufferedImage[13][20]; // 12 is the amount of frames in the idle animation
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations.length; i++) {
                // The first 7 rows have a smaller bounding size as opposed to the next 3
                // Investigate dynamic resizing of bounding box + sprites
                if (j < 10)
                    animations[j][i] = img.getSubimage(i * 55, j * 65, 55, 65); // i* sprite WIDTH

            }
            // TESTING: img.getSubimage(WIDTH of sprite, Y position, Width of selection,
            // Height of selection)
        }
    }

    //Loads in level data for use with updating X Positioning
    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!isOnFloor(hitbox, lvlData))
            inAir = true;

    }

    //Ignores player's inputs setting them all to false
    //Useful when control has to be taken away from them
    public void resetDirBooleans() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

   
    //Set attacking stage to properly update animations
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

}
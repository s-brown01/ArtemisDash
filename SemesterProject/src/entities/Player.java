/**
 * Player.java
 * Player Class
 * @author johnbotonakis and Sean-Paul Brown
 * This player class will hold every variable and funciton relating to the active player's inputs and outputs. 
 * 
 */
package entities;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.PlayerConstants.getSpriteAmt;
import static utils.HelperMethods.*;
import static utils.Constants.GRAVITY;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import states.Playing;
import utils.Constants;
import utils.LoadSave;

public class Player extends Entity {
    // player_count and playerCountCheck will make sure there is only 1 player
    private static int player_count = 0;

    private static boolean singletonCheck() {
        return player_count < 1;
    }

    private final Playing playing;
    private BufferedImage[][] animations;
    private int player_action = IDLE;
    private boolean moving, attacking, killed, dashing = false;
    private boolean left, up, right, down, jump;
    private float playerSpeed = 1.25f * Game.SCALE;
    private int jumps = 0;// Counts the number of jumps allowed to the player; Resets back to 0.

    private int[][] levelData;
    private float xDrawOffset = 20 * Game.SCALE; // Calculated X-Positional offset for drawing Sprite
    private float yDrawOffset = 20 * Game.SCALE; // Calculated Y - Positional offset for drawing Sprite
    private float hitboxCorrectionWidth = 20 * Game.SCALE; // Wraps the generic hitbox tighter around the player's width
    private float hitboxCorrectionHeight = 45 * Game.SCALE; // Wraps the generic hitbox tighter around the player's
                                                            // height
    private float hitboxOffset = (55 / 1.75f) * Game.SCALE;// Calculated Y-Positional change offset for jumping/falling

    private boolean attackChecked = false; // this will keep track if a current has already been checked, so 1 attack
                                           // doesn't count as multiple

    /**
     * Jumping and Gravity variables
     */
    private float jumpSpeed = -2.75f * Game.SCALE; // How high the player can jump
    private float fallCollisionSpeed = 0.5f * Game.SCALE; // How quickly the player falls after a collision

    /**
     * Constructor for the player class
     * 
     * @param x       - X-Position on the screen
     * @param y       - Y-Position on the screen
     * @param width   - Width of Sprite
     * @param height  - Height of Sprite
     * @param playing - the Playing GameState
     */
    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        // Singleton check
        if (!Player.singletonCheck())
            throw new IllegalStateException("Only 1 Player can ever be created at a time");
        loadAni();
        initHitbox((int) x, (int) y, (int) (hitboxCorrectionWidth), (int) (hitboxCorrectionHeight));
        this.state = IDLE;

    }

    /**
     * Handles updates for Position, Animation Tick, and Setting Animations
     */
    public void update() {
        updatePos();
        updateAniTick();
//        if (attacking)
//            checkAttack();
        setAnimation();
    }

    /**
     * Renders the player, along with hitbox
     * 
     * @param g - Graphics
     * @see java.awt.Graphics @
     */
    public void renderPlayer(Graphics g) {
        g.drawImage(animations[player_action][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset),
                width, height, null);
    }

    /**
     * Creates an animation library to store every animation from the loaded in sprite sheet
     */
    private void loadAni() {

        BufferedImage img = LoadSave.getSpriteSheet(LoadSave.PLAYER_SPRITES);

        animations = new BufferedImage[10][20]; // 10 animations; longest animation is 20 frames long

        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * 55, j * 65, 55, 65);
            }
        }

    }

    /**
     * Load in level data as a 2D array to continuously check for collision
     * 
     * @param lvlData
     */
    public void loadLvlData(int[][] lvlData) {
        this.levelData = lvlData;
    }

    /**
     * Check if the Players attack. If the attack has already been checked, don't check it
     */
    public void checkAttack(Playing p, MouseEvent e) {
        // PLEASE
        // REWORK THIS
        // ITS SO BAD
        // the attack should only be checked once on 6th frame (index 5)
        if (attackChecked) {
            System.out.println("NO SHOT - attackChecked");
            return;
        }
        if (aniIndex != 5) {
            System.out.println("NO SHOT - aniIndex");
            return;
        }
        System.out.println("SHOT");
        attackChecked = true;
        final float xDiff = e.getX() - hitbox.x + utils.Constants.PlayerConstants.SHOT_OFFSET_X;
        final float yDiff = e.getY() - hitbox.y + utils.Constants.PlayerConstants.SHOT_OFFSET_Y;
        p.addPlayerArrow(hitbox.x + utils.Constants.PlayerConstants.SHOT_OFFSET_X,
                hitbox.y + +utils.Constants.PlayerConstants.SHOT_OFFSET_Y, xDiff / yDiff);
    }

    /**
     * Increments an animation index to simulate fluid movement by drawing the next image in
     * the sprite sheet
     */
    private void updateAniTick() {
        aniTick++;
        if (aniTick >= Constants.ANISPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getSpriteAmt(player_action)) {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }

    /**
     * This is a multi-faceted function, allowing the position of the player to be updated
     * whenever an input is passed through. Standard key inputs are A,D, and Spacebar
     * controlling left, right, and jump movement respectively. Further in, this funciton also
     * calculates what happens when coming down from a jump, falling off a ledge, or any other
     * movement.
     */
    private void updatePos() {
        moving = false;
        if (jump) {
            jump();
        }

        if (dashing) {
            dash();
        }
        // check if holding both left and right or holding neither
        if (!inAir)
            if ((!left && !right) || (right && left))
                return;

        // checking that the player isn't too far on the bottom of the screen
        if (hitbox.y + hitbox.height >= Game.GAME_HEIGHT - Game.GAME_BUFFER) {
            kill();
        }

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
        }
        if (right) {
            xSpeed += playerSpeed;
        }
        
        // Checks if the player wanted to be in the air
        if (!inAir) 
            // And if he is not supposed to be, and there is no gravity Player is now considered in the air. (i.e walking off a ledge)
            // 
            if (!floorCheck(hitbox, levelData))
                inAir = true; 

        if (inAir) {
            if (canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitbox.y = getYPosRoof(hitbox, airSpeed, hitboxOffset);

                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallCollisionSpeed;
                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }
        moving = true;
    }

    /**
     * Handles event where the jump button, Space bar, is pressed
     */
    private void jump() {
        //Explain Jump Offset
        final float JUMP_OFFSET = 0.01f;
        if (jump && jumps <= 1) {
            inAir = true;
            airSpeed = jumpSpeed + JUMP_OFFSET; 
            if (hitbox.y < 170) {
                System.out.println("Hit ceiling, too high");
            }
        }

        if (jumps > 2) {
            return;
        }

    }

    /**
     * Handles event where the dash button (Shift) is pressed
     */
    private void dash() {
        if (dashing) {
            while (canMoveHere(hitbox.x, hitbox.y, hitbox.width, hitbox.height, levelData)) {
                hitbox.x += playerSpeed + 2f;
                break;
            }
            if (!canMoveHere(hitbox.x, hitbox.y, hitbox.width + 5, hitbox.height, levelData)) {
                for (int i = 0; i < 5; i++) {// idea is to have a parabola but going backwards? hard to make
                    hitbox.x -= i * 2;
                    hitbox.y -= i * i;

                }
//                if (the hitbox is over a certain threshold ABOVE the floor), inAir is TRUE
                inAir = true;
                airSpeed = 0;
                gravity = GRAVITY;
                dashing = false;

            }
        }
    }

    /**
     * Reset the variables that determine ability to jump
     */
    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
        gravity = GRAVITY;
        setJump(false);
        jumps = 0;
    }

    /**
     * Updates X-Position of player after hitbox detects collision
     * 
     * @param xSpeed
     */
    private void updateXPos(float xSpeed) {
        if (canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = getXPosWall(hitbox, xSpeed);
            if (player_action == DASH) {

            }
        }

    }

    /**
     * Sets the animation based on if player is moving
     */
    private void setAnimation() {

        int startAni = player_action;

        if (moving) {
            player_action = RUNNING;
        } else {
            player_action = IDLE;
        }

        if (attacking) {
            player_action = DRAW;
        }

        if (jump && inAir) {// If spacebar is held and you're in the air, hold the jumping animation
            player_action = JUMPSTART;
        }

        if (!jump && inAir) {// If spacebar is not held and in the air, begin the falling animation
            player_action = JUMPEND;
        }

        if (killed) {
            player_action = DIE;
        }
        if (dashing) {
            player_action = DASH;
        }

        if (startAni != player_action) {
            resetAniTick();
        }

    }

    /**
     * Resets animation to ensure that the animation is rendered timely
     */
    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;

    }

    /**
     * Resets player inputs in event window looses focus
     */
    public void resetDirBools() {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    /**
     * Mainly for testing, this function assigns a "kill" state if the player's health is too
     * low, OR kill was evoked via key binds
     */
    public void kill() {
        if (killed)
            killed = false;
        else {
            killed = true;
        }
    }

    /**
     * Getters and setters
     * 
     * @return
     */
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

    public void setAttack(boolean attack) {
        attacking = attack;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
        // When jump is released, implement gravity a bit more
        if (!jump) {
            gravity = gravity + 0.01f;
        }

    }

    public boolean getInAir() {
        return inAir;
    }

    public void setDash(boolean dashing) {
        this.dashing = dashing;
    }

    public void setJumps() {
        this.jumps++;// Set this to only increment ONCE
    }

}

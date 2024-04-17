package entities;

import static utils.Constants.PlayerStates.*;
import static utils.Constants.PlayerStates.getSpriteAmt;
import static utils.HelperMethods.*;
import static utils.Constants.GRAVITY;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import states.Playing;
import utils.Constants;
import utils.LoadSave;

/**
 * This player class will hold every variable and funciton relating to the active player's
 * inputs and outputs.
 * 
 * @author John Botonakis and Sean-Paul Brown
 */
public class Player extends Entity {

    /**
     * this is the maximum number of jumps that can occur mid-air. Setting this to 2 means
     * that there is a 'double jump' mechanic.
     */
    public static final int MAX_JUMPS = 2;
    /**
     * This is the Playing class that the Player is currently in
     */
    private final Playing playing;
    /**
     * This all of the animations that the player will do. The first index maps to the
     * PlayerConstants actions, and the second index is the a specifc frame
     */
    private BufferedImage[][] animations;
    /**
     * The current Level's level data, represented as a 2D int array
     */
    private int[][] levelData;

    // Player Actions
    private int player_action = IDLE;
    private boolean moving, attacking, killed, hurting, dash = false;
    private boolean left, right, jump;
    private float playerSpeed = 1.25f * Game.SCALE;
    private int playerLives = 3;
    /** flipX and flipY are for having the player able to flip the sprite left and right */
    private int flipX = 0, flipW = 1;
    /** xLevelOffset represents how far the level has scrolled */
    private int xLevelOffset = 0;

    // Hitbox Vars
    /**
     * Calculated X-Positional offset for drawing Sprite
     */
    private float xDrawOffset = 20 * Game.SCALE;
    /**
     * Calculated Y - Positional offset for drawing Sprite
     */
    private float yDrawOffset = 20 * Game.SCALE;
    /**
     * Wraps the generic hitbox tighter around the player's width
     */
    private float hitboxCorrectionWidth = 20 * Game.SCALE;
    /**
     * Wraps the generic hitbox tighter around the player's
     */
    private float hitboxCorrectionHeight = 45 * Game.SCALE;
    /**
     * Calculated Y-Positional change offset for jumping/falling
     */
    private float hitboxOffset = (55 / 1.75f) * Game.SCALE;
    /**
     * this will keep track if a current has already been checked, so 1 attack doesn't count
     * as multiple
     */
    private boolean attackChecked = false;

    /** Keeps track of where the next attack will go so that specific angles can be done */
    private Point nextAttack;
    /** Draws the path the arrow will take */
    private boolean drawArrowPath = false;

    // Jumping and Gravity variables
    /**
     * How high the player can jump
     */
    private float jumpSpeed = -2.75f * Game.SCALE;
    /**
     * How quickly the player falls after a collision
     */
    private float fallCollisionSpeed = 0.5f * Game.SCALE;
    /**
     * Counts the number of jumps allowed to the player; Resets back to 0
     */
    private int jumps = 0;

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
        currentHealth = STARTING_HEALTH;
        loadAni();
        initHitbox((int) x, (int) y, (int) (hitboxCorrectionWidth), (int) (hitboxCorrectionHeight));
        this.state = IDLE;

    }

    /**
     * Handles updates for Position, Animation Tick, and Setting Animations
     * 
     * @param xLevelOffset - how far the screen offset is from scrolling
     */
    public void update(int xLevelOffset) {
        // if dead, only update animations 
        if (killed) {
            updateAniTick();
            setAnimation();
            return;
        }

        this.xLevelOffset = xLevelOffset;
        updatePos();
        updateAniTick();
        if (attacking) {
            checkAttack();
        }
        setAnimation();
    }

    /**
     * Renders the player, along with hitbox
     * 
     * @param g - Graphics where to draw the player
     */
    public void renderPlayer(Graphics g) {
        // Add int yLevelOffset to input vars and to YHitbox
        Graphics2D g2D = (Graphics2D) g;
        g2D.drawImage(animations[player_action][aniIndex], (int) (hitbox.x - xDrawOffset) - xLevelOffset + flipX,
                (int) (hitbox.y - yDrawOffset), width * flipW, height, null);

        // drawing the dashed line to show the path of the arrow
        if (drawArrowPath && nextAttack != null) {
            g2D.setColor(Color.CYAN);
            g2D.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f,
                    new float[] { 5.0f, 5.0f }, 0.0f));
            g2D.drawLine((int) (hitbox.x + SHOT_OFFSET_X) - xLevelOffset, (int) (hitbox.y + SHOT_OFFSET_Y),
                    (int) (nextAttack.getX()), (int) (nextAttack.getY()));
        }
    }

    /**
     * Creates an animation library to store every animation from the loaded in sprite sheet
     */
    private void loadAni() {
        // get the image that contains all of the sprites
        BufferedImage img = LoadSave.getSpriteSheet(LoadSave.PLAYER_SPRITES);

        // 10 total animations; longest animation is 20 frames long
        animations = new BufferedImage[10][20];

        // divide up the image into each frame
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * IMAGE_WIDTH, j * IMAGE_HEIGHT, IMAGE_WIDTH, IMAGE_HEIGHT);
            }
        }

    }

    /**
     * Load in level data as a 2D array to continuously check for collision
     * 
     * @param lvlData - The data that
     */
    public void loadLvlData(int[][] lvlData) {
        this.levelData = lvlData;
    }

    /**
     * Increments an animation index to simulate fluid movement by drawing the next image in
     * the sprite sheet
     */
    private void updateAniTick() {
        aniTick++;
        // check to see if it is time to increase the animation frame
        if (aniTick >= Constants.ANISPEED) {
            aniTick = 0;
            aniIndex++;
            // check to see if the animation is completed
            if (aniIndex >= getSpriteAmt(player_action)) {
                // once it is complete, reset all of the variables such at attacking/hurting
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
                hurting = false;
                // at the very end of the death animation, let the Playing State know the player died
                if (killed) {
                    playing.playerDied();
                }
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

        // before moving, make sure that they are not outside of the world
        worldBoundsCheck();

        // if the user is jumping, try to jump
        if (jump) {
            jump();
        }
        // if the user is dashing, try to dash
        if (dash) {
            dash();
        }

        // check if holding both left and right or holding neither
        if (!inAir) {
            if ((!left && !right) || (right && left)) {
                return;
            }
        }

        float xSpeed = 0;

        // if the player is moving left...
        if (left) {
            // the speed is negative, then face to the left
            xSpeed -= playerSpeed;
            faceLeft();
        }
        // if the player is moving right...
        if (right) {
            // the speed is positive, then face to the right
            xSpeed += playerSpeed;
            faceRight();

        }

        // Checks if the player wanted to be in the air
        if (!inAir) {
            // And if he is not supposed to be, and there is no gravity Player is now considered in
            // the air. (i.e walking off a ledge)
            //
            if (!floorCheck(hitbox, levelData)) {
                inAir = true;
            }
        }

        // if the Player is in the air...
        if (inAir) {
            // if the Player has room underneath or above them to move then then fall
            if (canMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
                // move down by the airspeed then have the speed increase by the Gravity
                hitbox.y += airSpeed;
                airSpeed += gravity;
                // check to see if the use is having the Player move horizontally too
            } else {
                // if cannot move vertically, get the y-position at the tile
                hitbox.y = getYPosRoof(hitbox, airSpeed, hitboxOffset);

                // check to see if it was falling or rising
                if (airSpeed > 0) {
                    // if it was falling they are now on the ground so reset actions
                    resetInAir();
                } else {
                    // if they were rising set the speed to the collision speed
                    airSpeed = fallCollisionSpeed;
                }
            }
        }
        // always update the horizontal (x) position
        updateXPos(xSpeed);
        moving = true;
    }

    /**
     * This is a helper method that checks if the player has gone past the Game's Height. If
     * the player has, then they are killed.
     */
    private void worldBoundsCheck() {
        // this is a buffer when checking game height so there is a little room for error
        final int buffer = 3;

        // checking that the player isn't too far on the bottom of the screen
        if (hitbox.y + hitbox.height >= Game.GAME_HEIGHT - buffer) {
            // if they died then have them fall out of the world and die
            kill();
            hitbox.y += hitbox.height * 1.5;
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

    /**
     * Handles event where the jump button, Space bar, is pressed
     */
    private void jump() {
        // This is the offset to be added to the jump speed to facillitate a double jump
        final float JUMP_OFFSET = 0.02f;
        if (jump && jumps <= MAX_JUMPS) {
            inAir = true;
            airSpeed = jumpSpeed + JUMP_OFFSET;
            if (hitbox.y < 170) {
                System.err.println("Hit ceiling, too high");
            }
        }

        if (jumps > MAX_JUMPS) {
            return;
        }
        jump = false;
    }

    /**
     * Handles event where the dash button (Shift) is pressed
     */
    private void dash() {
        // if not dashing, return
        if (!dash) {
            return;
        }
        // if dashing...
        // check they can move to the next step, then they should move
        while (canMoveHere(hitbox.x, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            // multiply by the flip width, so that dashing can go both directions
            hitbox.x += (playerSpeed + 2f) * flipW;
            break;
        }
        if (!canMoveHere(hitbox.x, hitbox.y, hitbox.width + 5, hitbox.height, levelData)) {
            for (int i = 0; i < 5; i++) {// idea is to have a parabola but going backwards? hard to make
                hitbox.x -= i * 2;
                hitbox.y -= i * i;

            }
            // if (the hitbox is over a certain threshold ABOVE the floor), inAir is TRUE
            inAir = true;
            airSpeed = 0;
            gravity = GRAVITY;
            dash = false;

        }
    }

    /**
     * This will start the Player attacking. If this is called then
     * 
     * @param e - the information about what the mouse is currently doing
     */
    public void shoot(MouseEvent e) {
        // checking validation
        if (attacking || killed) {
            // dont attack again while attacking
            return;
        }
        // if they aren't already attacking, they are now
        nextAttack = e.getPoint();
        attacking = true;
    }

    /**
     * Check if the Players attack. If the attack has already been checked, don't check it
     */
    private void checkAttack() {
        // this is the horizontal (x) difference between where the next arrow will go and where
        // the Player is
        final float xDiff = (float) ((nextAttack.getX()) - (hitbox.x + SHOT_OFFSET_X - xLevelOffset));

        // turn the Player to face where the arrow will fire
        if (xDiff < 0) {
            faceLeft();
        } else {
            faceRight();
        }

        final int attackAniIndex = 5;
        if (attackChecked) {
            return;
        }
        // the attack should only be checked once on 6th frame (index 5)
        if (aniIndex != attackAniIndex) {
            return;
        }

        attackChecked = true;
        // the vertical (y) difference between the next attack and Player
        final float yDiff = (float) (nextAttack.getY() - (hitbox.y + SHOT_OFFSET_Y));
        // the path/slope for the arrow to travel
        final float slope = yDiff / xDiff;

        /*
         * the arrow should spawn near the Player, but there will be a slight offset so that the
         * arrow matches up with the bow
         */
        playing.addPlayerArrow(hitbox.x + SHOT_OFFSET_X * flipW, hitbox.y + SHOT_OFFSET_Y, slope, xDiff < 0);

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
     * @param xSpeed - how fast the player is moving
     */
    private void updateXPos(float xSpeed) {
        if (canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = getXPosWall(hitbox, xSpeed);

        }

    }

    /**
     * Sets the animation based on how Player is moving and what state the Player is in
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
            player_action = FALL;
        }
        if (hurting) {
            player_action = DAMAGE;
        }
        if (dash) {
            player_action = DASH;
        }
        if (killed) {
            player_action = DIE;
        }

        if (startAni != player_action) {
            resetAniTick();
            // this makes it so that when the player is attacking it starts on the 2nd frame.
            // starting on the second frame makes the animation a little quicker/smoother.
            if (attacking) {
                aniIndex = 1;
            }
        }

    }

    /**
     * Changes the current health value of the player
     * 
     * @param value - The updated health value to change to
     */
    public void changeHealth(int value) {
        currentHealth += value;

        if (currentHealth <= 0)
            currentHealth = 0;
        else if (currentHealth >= maxHealth)
            currentHealth = maxHealth;
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
        jump = false;
        dash = false;
    }

    /**
     * Mainly for testing, this function assigns a "kill" state if the player's health is too
     * low, OR kill was evoked via key binds
     */
    public void kill() {
        killed = true;
    }

    /**
     * Getter for left
     * 
     * @return the current value of left
     */
    public boolean isLeft() {
        return left;
    }

    /**
     * Setter for left
     * 
     * @param left - true if the player is moving left. False if not.
     */
    public void setLeft(boolean left) {
        this.left = left;
    }

    /**
     * Getter for right
     * 
     * @return the current value of right
     */
    public boolean isRight() {
        return right;
    }

    /**
     * Setter for right
     * 
     * @param right - true if the player is moving right. False if not.
     */
    public void setRight(boolean right) {
        this.right = right;
    }

    /**
     * Setter for jump
     * 
     * @param jump - true if the player is jumping. False if not.
     */
    public void setJump(boolean jump) {
        this.jump = jump;
        // When jump is released, implement gravity a bit more
        if (!jump) {
            gravity = gravity + 0.01f;
        }
    }

    /**
     * Increases the amount of jumps by 1
     */
    public void incJumpCount() {
        this.jumps++;
    }

    /**
     * Setter for dashing
     * 
     * @param dashing - true if the player is dashing. False if not.
     */
    public void setDash(boolean dashing) {
        // If already dashing and you press the button again,
        // stop dashing
        if (this.dash == true && dashing == true) {
            this.dash = false;
        } else {
            this.dash = dashing;
        }
    }

    /**
     * Getter for the players current health
     * 
     * @return the current health of the player
     */
    public int getHealth() {
        return currentHealth;
    }

    /**
     * Getter for the amount of lives the player has
     * 
     * @return the current amount of lives left
     */
    public int getLives() {
        return playerLives;
    }

    /**
     * Setter for the nextAttack Point and will switch where the player is facing based on
     * where the mouse is
     * 
     * @param p the Point where the next attack will be located
     */
    public void setNextAttack(Point p) {
        nextAttack = p;
        // checking that if the next attack's x-coordinate if less than the where the player's,
        // then the player should face towards the left
        if (nextAttack.getX() < (hitbox.x - xLevelOffset)) {
            faceLeft();
        } else {
            // if it is the same of greater than the player's x-coordinate, then face right
            faceRight();
        }
    }

    /**
     * Setter for drawArrowPath. If this is true, then a line will be drawn from the Player to
     * nextAttack Point
     * 
     * @param drawArrowPath - true to draw the line, false to not
     */
    public void setDrawArrowPath(boolean drawArrowPath) {
        this.drawArrowPath = drawArrowPath;
    }

    /**
     * This method will give damage to the player and check if they have died.
     */
    public void hurt() {
        // if you are already taking damage you can't get hit a second time
        if (hurting) {
            return;
        }
        // take 1 damage
        currentHealth--;
        hurting = true;
        if (currentHealth <= 0) {
            kill();
        }
    }

    /**
     * Getter for the inAir boolean
     * 
     * @return the current value of inAir
     */
    public boolean isInAir() {
        return inAir;
    }

    /**
     * Getter for the killed boolean
     * 
     * @return the current value of killed
     */
    public boolean isKilled() {
        return killed;
    }

    /**
     * Geter for the hurting boolean
     * 
     * @return the current value of hurting
     */
    public boolean isHurting() {
        return hurting;
    }
}

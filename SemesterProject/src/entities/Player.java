/**
 * Player.java
 * Player Class
 * @author johnbotonakis and Sean-Paul Brown
 * This player class will hold every variable and funciton relating to the active player's inputs and outputs. 
 * 
 */
package entities;

import static utils.Constants.Directions.*;
import static utils.Constants.PlayerConstants.*;
import static utils.Constants.PlayerConstants.getSpriteAmt;
import static utils.HelperMethods.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;
import states.Playing;
import utils.LoadSave;

public class Player extends Entity {
    // player_count and playerCountCheck will make sure there is only 1 player
    private static int player_count = 0;
    private static boolean singletonCheck() {
        return player_count < 1;
    }

    private final Playing playing;
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 10; // 120 framespersecond / 12 idle frames = 10
    private int player_action = IDLE;
    private boolean moving, attacking,killed = false;
    private boolean left, up, right, down, jump;
    private float playerSpeed = 2.75f;
    
    
    private int[][] levelData;
    private float xDrawOffset = 20 * Game.SCALE; // Calculated X-Positional offset for drawing Sprite
    private float yDrawOffset = 20 * Game.SCALE; // Calculated Y - Positional offset for drawing Sprite
    private float hitboxCorrectionWidth = 20 * Game.SCALE; // Wraps the generic hitbox tighter around the player's width
    private float hitboxCorrectionHeight = 45 * Game.SCALE; // Wraps the generic hitbox tighter around the player's
                                                            // height
    private float hitboxOffset = 30 * Game.SCALE;//Calculated Y-Positional change offset for jumping/falling

    /**
     * Jumping and Gravity variables
     */
    private float jumpSpeed = -2.25f * Game.SCALE; // How high the player can jump
    private float fallCollisionSpeed = 0.5f * Game.SCALE; // How quickly the player falls after a collision
    
    /**
     * Constructor for the player class
     * 
     * @param x      - X-Position on the screen
     * @param y      - Y-Position on the screen
     * @param width  - Width of Sprite
     * @param height - Height of Sprite
     * @param playing - the Playing GameState 
     */
    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        // Singleton check
        if (!Player.singletonCheck())
            throw new IllegalStateException("Only 1 Player can ever be created at a time");
        loadAni();
        initHitbox(x, y, hitboxCorrectionWidth, hitboxCorrectionHeight);
        this.state = IDLE;

    }

    /**
     * Handles updates for Position, Animation Tick, and Setting Animations
     */
    public void update() {
        updatePos();
        updateAniTick();
        setAnimation();
    }

    /**
     * Renders the player, along with hitbox
     * 
     * @param g - Graphics
     */
    public void renderPlayer(Graphics g) {
        drawHitbox(g);
        g.drawImage(animations[player_action][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset),
                width, height, null);
    }
    
//    public void draw(Graphics g) {
//        //TODO - naming
//        renderPlayer(g);
//    }

    /**
     * Creates an animation library to store every animation from the loaded in
     * sprite sheet
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
     * Increments index to simulate animation by drawing the next image in the
     * sprite sheet
     */
    private void updateAniTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getSpriteAmt(player_action)) {
                aniIndex = 0;
                attacking = false;
            }
        }
    }

    /**
     * Updates player positioning based on the inputs
     */
    private void updatePos() {
        moving = false;
        
        if(jump) {
            jump();
        }
        // check if holding both left and right or holding neither
        if (!inAir)
            if ((!left && ! right) || (right && left))
                return;

        float xSpeed = 0;

        if (left) {
            xSpeed -= playerSpeed;
        }
        if (right) {
            xSpeed += playerSpeed;
        }

        if (!inAir)
            if (!gravity(hitbox, levelData))
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
        } else
            updateXPos(xSpeed);
        moving = true;
    }

    /**
     * Handles what happens when jump is pressed, in the air or on the ground
     */
    private void jump() {
        if(inAir) {//Edit later for Cyote time
            
            return;
        }
        
        inAir = true;
        airSpeed = jumpSpeed;
    }

    /**
     * Reset the variables that determine jumping
     */
    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
        gravity = 0.04f * Game.SCALE;

    }

    /**
     * Updates X-Position of player after hitbox detects collision
     * @param xSpeed
     */
    private void updateXPos(float xSpeed) {
        if (canMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            hitbox.x += xSpeed;
        } else {
            hitbox.x = getXPosWall(hitbox, xSpeed);
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
        
        if(jump && inAir) {//If spacebar is held and you're in the air, hold the jumping animation
            player_action = JUMPSTART;
        }
        
        if(!jump && inAir) {//If spacebar is not held and in the air, begin the falling animation
            player_action = JUMPEND;
        }
        
        if(killed) {
            player_action = DIE;
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
        //When jump is released, implement gravity a bit more
        if (!jump) {
            gravity = gravity +0.11f;
        }
    }
    
    public boolean getInAir() {
        return inAir;
    }

    public void kill() {
        if (killed)
        killed = false;
        else {
            killed = true;
        }
    }

        
}

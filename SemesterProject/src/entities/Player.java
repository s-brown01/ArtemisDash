/**
 * Player.java
 *
 * @author - Sean-Paul Brown
 * @date - 3/8/2024
 * @desc - This is a singleton 
 */
package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import utils.LoadSave;

import static utils.Constants.PlayerConstants.*;

public class Player extends Entity {
    
    private static int playerCount = 0;
    private static boolean singletonCheck() {
        return playerCount <= 1;
    }

	/**
	 * Parameters the player will inherit from the Entity abstract class
	 */
    private BufferedImage[][] animations;
    private int aniIndex, aniTick;

    // private int aniSpeed = 120 / 8; // 120 frames per second / 10 animations per second
    private int player_action = IDLE;
    private boolean left, right, up, down, moving = false, attacking = false;
    private BufferedImage img;

    private int maxHealth = 2;
    private int currentHealth = maxHealth;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        if (!singletonCheck())
            throw new IllegalStateException("Another player has already been created");
        playerCount++;
        
        importImg();
        loadAnimations();
    }

    /**
     * Updates the position, animation tick, and row of sprites
     */
    @Override
    public void update() {
        updateAniTick();
        updatePos();
        setAnimation();

    }
    
    /**
     * Draws the sprite along with it's hitbox to the screen 
     */
    @Override
    public void draw(Graphics g) {
        drawHitbox(g, 0);
        // 100 px offset from top left corner, 70px offset from top
        // Width is ~64, Height ~60
        // Doubled X and Y size
        g.drawImage(animations[player_action][aniIndex], (int) hitbox.x, (int) hitbox.y,
                            (int) width, (int) height, null);
    }

    /**
     * Reads in sprite sheet to be cut up into smaller animation frames
     */
    public void importImg() {
        final String image = "Artemis_Finished.png";
        InputStream is = getClass().getClassLoader().getResourceAsStream(image);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            System.out.println("NULL");
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                System.out.println("CLOSE ERROR");
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads ALL animations from a sprite sheet into a 2D array
     * The length of this array, will be the length of the longest animation
     */
    private void loadAnimations() {
    	BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
        animations = new BufferedImage[13][20]; // 12 is the amount of frames in the idle animation
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations.length; i++) {
            	if (j <7)
                animations[j][i] = img.getSubimage(i * 60, j * 45, 55, 45); // i* sprite WIDTH
            	else{//Animation #7 is the start of new boxes. Investigate dynamic resizing of bounding box + sprites
            		animations[j][i] = img.getSubimage(i * 55, j*45, 55, 65); // i* sprite WIDTH
            	}

            }
            // Test Idle Animation: img.getSubimage(i*60, 0, 60, 45); Set BufferedImage[12]
            // Test Run Animation: img.getSubimage(i*60, 45, 60, 45); Set BufferedImage[10]
            // Test Jump Animation: img.getSubimage(i*60, 90, 60, 45);Set BufferedImage[11]
            // Test Land Animation: img.getSubimage(i*60, 135, 60, 45);Set BufferedImage[3]
            // Test Death Animation: img.getSubimage(i*60, 180, 60, 45);Set BufferedImage[20]

            // img.getSubimage(WIDTH of sprite, Y position, Width of selection, Height of selection)
        }
    }

    /**
     * Updates position of hitbox as well as player sprite
     * Handles things such as pressing Left and Right simultaneously
     */
    private void updatePos() {
        moving = false;

        // if holding down left+right...
        if (left && right) {
            // if holding down just L+R or all 4 keys, don't move
            if ((up && down) || (!up && !down))
                return;
        }
        if (left) {
            hitbox.x -= 5;
            moving = true;
        }
        if (right) {
            hitbox.x += 5;
            moving = true;
        }
        if (up) {
            hitbox.y -= 5;
            moving = true;
        }
        if (down) {
            hitbox.y += 5;
            moving = true;
        }
    }

    /**
     * Keeps track of the player movement by setting a True or False value
     * Will be used for "Coyote Time", shooting, among other Player related movement options
     * @param moving
     */
    public void setMoving(boolean moving) {
    	this.moving = moving;
        if (moving == false) {
        	this.setRight(false);
    		this.setLeft(false);
    		this.setUp(false);
    		this.setDown(false);
        }
    }

	public void setAttack(boolean attacking) {
	    	this.attacking = attacking;
	    }
 
    /**
     * Sets the current animation sprite, based on the player movement
     */
    private void setAnimation() {
        int start_state = player_action;
        if (moving) {
            player_action = RUNNING;
        } else {
            player_action = IDLE;
        }
        
        if (attacking) {
        	player_action = ATTACK;
        }
        // checking if the state has changed
        // if it has changed, we need to restart animation
        if (start_state != player_action)
            resetAni();
    }

    /**
     * Resets animation timers and indexes to repeat animation 
     * once end of sprite list has been reached
     */
    private void resetAni() {
        aniIndex = 0;
        aniTick = 0;
    }

    /**
     * Updates the animation index to move the animation forward 
     */
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

    public int getHealth() {
        return currentHealth;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }
    

}

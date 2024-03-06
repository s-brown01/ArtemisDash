
package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import static utils.Constants.PlayerConstants.*;

public class Player extends Entity {

    private BufferedImage[][] animations;
    private int aniIndex, aniTick;
    // private float xDelta = 100, yDelta = 100;
    // private int aniSpeed = 120 / 8; // 120 frames per second / 10 animations per
    // second
    private int player_action = IDLE;
    private boolean left, right, up, down, moving = false;
    private BufferedImage img;

    private int maxHealth = 2;
    private int currentHealth = maxHealth;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        // TODO Auto-generated constructor stub

        importImg();
        loadAnimations();

    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        updateAniTick();
        updatePos();
        setAnimation();

    }

    @Override
    public void draw(Graphics g) {
        drawHitbox(g, 0);
        // 100 px offset from top left corner, 70px offset from top
        // Width is ~64, Height ~60
        // Doubled X and Y size
        g.drawImage(animations[player_action][aniIndex], (int) hitbox.x, (int) hitbox.y,
                            (int) width, (int) height, null);
    }

    // READS IN THE SPRITE SHEET TO BE USED AND CUT UP INTO SMALLER FRAMES
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

    // LOADS IN ANIMATIONS USING A 2D ARRAY OF THE LENGTH OF THE ANIMATIONS THEMSELVES
    private void loadAnimations() {
        animations = new BufferedImage[12][12]; // 12 is the amount of frames in the idle animation
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations.length; i++) {
                animations[j][i] = img.getSubimage(i * 60, j * 45, 60, 45); // i* sprite WIDTH

            }
            // Test Idle Animation: img.getSubimage(i*60, 0, 60, 45); Set BufferedImage[12]
            // Test Run Animation: img.getSubimage(i*60, 45, 60, 45); Set BufferedImage[10]
            // Test Jump Animation: img.getSubimage(i*60, 90, 60, 45);Set BufferedImage[11]
            // Test Land Animation: img.getSubimage(i*60, 135, 60, 45);Set BufferedImage[3]
            // Test Death Animation: img.getSubimage(i*60, 180, 60, 45);Set BufferedImage[20]

            // img.getSubimage(WIDTH of sprite, Y position, Width of selection, Height of
            // selection)
        }
    }

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

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private void setAnimation() {
        int start_state = player_action;

        if (moving) {
            player_action = RUNNING;
        } else {
            player_action = IDLE;
        }

        // checking if the state has changed
        // if it has changed, we need to restart animation
        if (start_state != player_action)
            resetAni();
    }

    private void resetAni() {
        aniIndex = 0;
        aniTick = 0;
    }

    private void updateAniTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getAnimationLength(player_action)) {
                aniIndex = 0;
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

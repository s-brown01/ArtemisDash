package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import levels.Level;
import main.Game;
import states.Playing;
import utils.LoadSave;
import static utils.Constants.EnemyConstants.*;

/**
 * EnemyManager.java
 * @author Sean-Paul Brown
 * @date 03/15/2024
 * @description 
 * EnemyManager will handle all enemies in each level.
 * This means that instead of Playing storing and handling every Enemy, they can be dealt with here.
 * This includes checking for updating, drawing, checking for getting hit, and more.
 * Storing all images here will be less memory intensive then every single Enemy-object storing their photos.
 */
public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] skeletonAnis;
    private List<Skeleton> skeletonList = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadImgs();
        // temp code
        skeletonList.add(
                new Skeleton((600 / 1.75f) * Game.SCALE, (200 / 1.75f) * Game.SCALE, SKELETON_WIDTH, SKELETON_HEIGHT));
        skeletonList.add(
                new Skeleton((1300 / 1.75f) * Game.SCALE, (200 / 1.75f) * Game.SCALE, SKELETON_WIDTH, SKELETON_HEIGHT));
    }

    /**
     * Load enemies from a specific level and add them into the appropriate lists
     * 
     * @param level - what level to load enemies in from
     */
    public void loadEnemies(Level level) {
        // skeletonList = level.getSkeletons();
    }

    /**
     * Load all images for specific enemies. It is easier to load/save here than individually
     * by Object.
     * 
     * This relies on the LoadSave.getSpriteAtlas to return a BufferedImage of an atlas
     */
    private void loadImgs() {
        // SKELETONS
        skeletonAnis = new BufferedImage[6][18];
        final BufferedImage img = LoadSave.getSpriteSheet(LoadSave.SKELETON_SPRITES);
        for (int j = 0; j < skeletonAnis.length; j++) {
            for (int i = 0; i < skeletonAnis[j].length; i++) {
                skeletonAnis[j][i] = img.getSubimage(i * SKELETON_WIDTH_DEFAULT, j * SKELETON_HEIGHT_DEFAULT,
                        SKELETON_WIDTH_DEFAULT, SKELETON_HEIGHT_DEFAULT);
            }
        }
    }

    /**
     * This will draw all of the enemies in each List, this should only be used AFTER
     * LoadEnemies has been used to fill in all of the lists. If that hasn't been used then
     * errors will happen.
     * 
     * @param g - the Graphics where to draw
     */
    public void draw(Graphics g) {
        for (Skeleton s : skeletonList) {
            // if the skeleton isn't active, skip it
            if (!s.isActive())
                continue;
            s.drawHitbox(g);
            s.drawAttackbox(g);
            g.setColor(Color.CYAN);
            g.drawLine((int) s.getHitbox().x, (int) s.getHitbox().y, (int) (s.getHitbox().x + s.eyeSight),
                    (int) s.getHitbox().y);
            g.setColor(Color.black);
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 13));
            g.drawString("" + s.getCurrentHealth(), (int)(s.getHitbox().x-15), (int)(s.getHitbox().y-15));
            g.drawImage(skeletonAnis[s.getState()][s.getAniIndex()],
                    (int) (s.getHitbox().x - SKELETON_DRAW_OFFSET_X + s.xFlipped()),
                    (int) (s.getHitbox().y - SKELETON_DRAW_OFFSET_Y), (int) (SKELETON_WIDTH * s.widthFlipped()),
                    (int) (SKELETON_HEIGHT), null);
        }
    }

    /**
     * This will update all enemies in the lists in a level, this should only be used AFTER
     * LoadEnemies has been used to fill in all of the lists.
     * 
     * It loops through all enemy Lists and will call update on each individual enemy.
     * 
     * @param lvlData
     * @param player
     */
    public void update(int[][] lvlData, Player player) {
        for (Skeleton s : skeletonList) {
            // if the skeleton isn't active, skip it
            if (!s.isActive())
                continue;
            s.update(lvlData, player);
        }
    }

}

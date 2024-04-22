package entities;

import static utils.Constants.EnemyConstants.SKELETON_DRAW_OFFSET_X;
import static utils.Constants.EnemyConstants.SKELETON_DRAW_OFFSET_Y;
import static utils.Constants.EnemyConstants.SKELETON_HEIGHT;
import static utils.Constants.EnemyConstants.SKELETON_HEIGHT_DEFAULT;
import static utils.Constants.EnemyConstants.SKELETON_KING_DRAW_OFFSET_X;
import static utils.Constants.EnemyConstants.SKELETON_KING_DRAW_OFFSET_Y;
import static utils.Constants.EnemyConstants.SKELETON_KING_HEIGHT;
import static utils.Constants.EnemyConstants.SKELETON_KING_WIDTH;
import static utils.Constants.EnemyConstants.SKELETON_WIDTH;
import static utils.Constants.EnemyConstants.SKELETON_WIDTH_DEFAULT;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import levels.Level;
import states.Playing;
import utils.LoadSave;

/**
 * EnemyManager will handle all enemies in each level. This means that instead of Playing
 * storing and handling every Enemy, they can be dealt with here. This includes checking
 * for updating, drawing, checking for getting hit, and more. Storing all images here will
 * be less memory intensive then every single Enemy-object storing their photos.
 * 
 * @author Sean-Paul Brown
 */
public class EnemyManager {

    /**
     * The playing class that is holding this class and managing the levels
     */
    private final Playing playing;
    /**
     * All of the skeleton's animations. First index is the state that the enemy is in, the
     * second is the specific frame of that state.
     */
    private BufferedImage[][] skeletonAnis;
    /**
     * All of the skeleton king's animations. First index is the state that the enemy is in,
     * the second is the specific frame of that state.
     */
    private BufferedImage[][] skelKingAnis;
    /**
     * A list containing all of the Skeletons in a certain Level. <BR>
     * This does not have to be an array list, any type of List would work in the program.
     */
    private final List<Skeleton> skeletonList = new ArrayList<>();
    /**
     * A list containing all of the Skeleton Kings in a certain Level. <BR>
     * This does not have to be an array list, any type of List would work in the program.
     */
    private final List<SkeletonKing> kingList = new ArrayList<>();

    /**
     * This class manages every entity instance that is currently loaded into the game
     * 
     * @param playing - The game state to which the entities will be loaded onto
     */
    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadImgs();
    }

    /**
     * Load enemies from a specific level and add them into the appropriate lists
     * 
     * @param level - what level to load enemies in from
     */
    public void loadEnemies(Level level) {
        skeletonList.addAll(level.getSkeletons());
        kingList.addAll(level.getSkeletonKings());
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
        final BufferedImage skelImg = LoadSave.getSpriteSheet(LoadSave.SKELETON_SPRITES);
        for (int j = 0; j < skeletonAnis.length; j++) {
            for (int i = 0; i < skeletonAnis[j].length; i++) {
                skeletonAnis[j][i] = skelImg.getSubimage(i * SKELETON_WIDTH_DEFAULT, j * SKELETON_HEIGHT_DEFAULT,
                        SKELETON_WIDTH_DEFAULT, SKELETON_HEIGHT_DEFAULT);
            }
        }
        // SKELETON KINGS
        skelKingAnis = new BufferedImage[6][18];
        final BufferedImage skelKingImg = LoadSave.getSpriteSheet(LoadSave.SKELETON_KING_SPRITES);
        for (int j = 0; j < skelKingAnis.length; j++) {
            for (int i = 0; i < skelKingAnis[j].length; i++) {
                skelKingAnis[j][i] = skelKingImg.getSubimage(i * SKELETON_WIDTH_DEFAULT, j * SKELETON_HEIGHT_DEFAULT,
                        SKELETON_WIDTH_DEFAULT, SKELETON_HEIGHT_DEFAULT);
            }
        }
    }

    /**
     * This will draw all of the enemies in each List, this should only be used AFTER
     * LoadEnemies has been used to fill in all of the lists. If that hasn't been used then
     * errors will happen.
     * 
     * @param g            - the Graphics where to draw
     * @param xLevelOffset - the horizontal offset from screen scrolling
     */
    public void draw(Graphics g, int xLevelOffset) {
        // go through every enemy in the Lists
        for (Skeleton s : skeletonList) {
            // if the skeleton isn't active, skip it
            if (!s.isActive()) {
                continue;
            }
            // this draws the current skeleton with their state and animation at the skeleton hitbox
            g.drawImage(skeletonAnis[s.getState()][s.getAniIndex()],
                    (int) (s.getHitbox().x - SKELETON_DRAW_OFFSET_X - xLevelOffset + s.xFlipped()),
                    (int) (s.getHitbox().y - SKELETON_DRAW_OFFSET_Y), (int) (SKELETON_WIDTH * s.widthFlipped()),
                    (int) (SKELETON_HEIGHT), null);
        }

        for (SkeletonKing sk : kingList) {
            // if the skeleton isn't active, skip it
            if (!sk.isActive()) {
                continue;
            }
            // this draws the current skeleton with their state and animation at the skeleton hitbox
            g.drawImage(skelKingAnis[sk.getState()][sk.getAniIndex()],
                    (int) (sk.getHitbox().x - SKELETON_KING_DRAW_OFFSET_X - xLevelOffset + sk.xFlipped()),
                    (int) (sk.getHitbox().y - SKELETON_KING_DRAW_OFFSET_Y),
                    (int) (SKELETON_KING_WIDTH * sk.widthFlipped()), (int) (SKELETON_KING_HEIGHT), null);
        }
    }

    /**
     * This will update all enemies in the lists in a level, this should only be used AFTER
     * LoadEnemies has been used to fill in all of the lists.
     * 
     * It loops through all enemy Lists and will call update on each individual enemy.
     * 
     * @param lvlData - the current level's data, stored as a 2D int array
     * @param player  - the current Player that the user is using
     */
    public void update(int[][] lvlData, Player player) {
        // this boolean will keep track of if every enemy has died, defaulted to true
        boolean allEnemiesKilled = true;
        for (Skeleton s : skeletonList) {
            // update every skeleton in the list
            s.update(lvlData, player);

            // if the skeleton isn't active, skip it
            if (!s.isActive()) {
                continue;
            }

            // if any skeleton is active, all enemies have not been killed.
            // this if statement only sets the allEnemiesKilled boolean once: short circuit
            if (allEnemiesKilled) {
                allEnemiesKilled = false;
            }

            // if the skeleton is killed, they cannot be hit over and over agian.
            // if the player has hit a Skeleton then the Skeleton died and the Player should get
            // points
            if (!s.isKilled() && playing.getProjectileManager().checkEnemyHit(s)) {
                playing.updateScore(s.score);
            }
        }

        for (SkeletonKing sk : kingList) {
            sk.update(lvlData, player);

            if (!sk.isActive()) {
                continue;
            }

            if (allEnemiesKilled) {
                allEnemiesKilled = false;
            }

            if (!sk.isKilled() && playing.getProjectileManager().checkEnemyHit(sk)) {
                playing.updateScore(sk.score);
            }
        }

        // if every enemy is dead/inactive, the level is complete
        if (allEnemiesKilled && !skeletonList.isEmpty()) {
            playing.updateScore(2000);
            playing.completeLevel();
        }
    }

    /**
     * This will reset all of the enemies stored in this manager
     */
    public void resetAllEnemies() {
        skeletonList.clear();
        kingList.clear();
    }

    /**
     * Getter for the List of Skeletons
     * 
     * @return the List containing all Skeletons that were loaded in
     */
    public List<Skeleton> getSkeletons() {
        return skeletonList;
    }

    /**
     * Getter for the List of Skeleton Kings
     * 
     * @return the List containing all Skeleton Kings that were loaded in
     */
    public List<SkeletonKing> getSkeletonKings() {
        return kingList;
    }
}

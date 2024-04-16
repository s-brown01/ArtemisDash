package levels;

import static utils.Constants.EnemyConstants.SKELETON_HEIGHT;
import static utils.Constants.EnemyConstants.SKELETON_WIDTH;
import static utils.Constants.EnemyConstants.SKELETON_KING_HEIGHT;
import static utils.Constants.EnemyConstants.SKELETON_KING_WIDTH;

import java.util.ArrayList;
import java.util.List;

import entities.Skeleton;
import entities.SkeletonKing;
import main.Game;

/**
 * Every object instantiated from this class will be a level that the user can play
 * throughout. This holds onto information such as level data and sprite indices to create
 * a level.
 * 
 * @author johnbotonakis and Sean-Paul Brown
 */
public class Level {

    private int[][] lvlData;
    private Boolean completed = false;
    private Boolean hidden = true;

    /**
     * Constructor for a Level
     * 
     * @param lvlData - the levelData that represents the level as a 2D int array
     */
    public Level(int[][] lvlData) {
        this.lvlData = lvlData;
    }

    /**
     * Getter for a specific index in the levelData array
     * 
     * @param x - the x index (second array index)
     * @param y - the y index (first array index)
     * @return the int for the index determined by the parameters
     */
    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    /**
     * Getter for the Level's data
     * 
     * @return the 2D int Array representing the level
     */
    public int[][] getLevelData() {
        return lvlData;
    }

    /**
     * Getter for the completed boolean (to determine if the level has been completed)
     * 
     * @return the current value of completed
     */
    public Boolean getCompleted() {
        return completed;
    }

    /**
     * Setter for the completed boolean (to determine if the level has been completed)
     * 
     * @param completed - true if the level has been 100%'d, false if not
     */
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    /**
     * Getter for the hidden boolean (to determine if the level is hidden from the user)
     * 
     * @return the current value of hidden
     */
    public Boolean getHidden() {
        return hidden;
    }

    /**
     * Setter for the hidden boolean (to determine if the level has been completed)
     * 
     * @param hidden - true if the level is hidden from the player, false if the player can
     *               play/see it
     */
    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    /**
     * Return a List of all the Skeletons in this level, it is currently stored as an
     * ArrayList.
     * 
     * @return a List storing all Skeletons in this Level
     */
    public List<Skeleton> getSkeletons() {
        final List<Skeleton> skeletonList = new ArrayList<>();
        // temp code
        skeletonList.add(
                new Skeleton((600 / 1.75f) * Game.SCALE, (200 / 1.75f) * Game.SCALE, SKELETON_WIDTH, SKELETON_HEIGHT));
        skeletonList.add(
                new Skeleton((1300 / 1.75f) * Game.SCALE, (200 / 1.75f) * Game.SCALE, SKELETON_WIDTH, SKELETON_HEIGHT));

        return skeletonList;
    }

    public List<SkeletonKing> getSkeletonKings() {
        final List<SkeletonKing> skelKingList = new ArrayList<>();
        skelKingList.add(new SkeletonKing((1500 / 1.75f) * Game.SCALE, (425 / 1.75f) * Game.SCALE, SKELETON_KING_WIDTH,
                SKELETON_KING_HEIGHT));
        return skelKingList;
    }

}

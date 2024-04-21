package levels;

import static utils.Constants.EnemyConstants.SKELETON_HEIGHT;
import static utils.Constants.EnemyConstants.SKELETON_KING_HEIGHT;
import static utils.Constants.EnemyConstants.SKELETON_KING_WIDTH;
import static utils.Constants.EnemyConstants.SKELETON_WIDTH;

import java.util.ArrayList;
import java.util.List;

import entities.Skeleton;
import entities.SkeletonKing;
import main.Game;
import utils.LoadSave;

/**
 * Every object instantiated from this class will be a level that the user can play
 * throughout. This holds onto information such as level data and sprite indices to create
 * a level.
 * 
 * @author johnbotonakis and Sean-Paul Brown
 */
public class Level {

    /**
     * the levelData that represents the environment of the level as a 2D int array
     */
    private int[][] lvlData;
    private String fileName;
    private Boolean completed = false;
    private Boolean hidden = true;

    /**
     * Constructor for a Level
     * 
     * @param levelFileName - the file containing the RGB map of the Level
     */
    public Level(String levelFileName) {
        this.fileName = levelFileName;
        this.lvlData = LoadSave.getLevelData(levelFileName);
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
//        return LoadSave.getSkeletons(fileName);
        List<Skeleton> skels = new ArrayList<>();
        skels.add(new Skeleton(1200, 300, SKELETON_WIDTH, SKELETON_HEIGHT));
        skels.add(new Skeleton(600, 300, SKELETON_WIDTH, SKELETON_HEIGHT));
        return skels;
    }

    /**
     * Return a List of all the Skeleton Kings in this level, it is currently stored as an
     * ArrayList.
     * 
     * @return a List storing all Skeleton Kings in this Level
     */
    public List<SkeletonKing> getSkeletonKings() {
//        return LoadSave.getSkeletonKings(fileName);
        List<SkeletonKing> skels = new ArrayList<>();
        skels.add(new SkeletonKing(2200, 300, SKELETON_KING_WIDTH, SKELETON_KING_HEIGHT));
        return skels;
    }

    /**
     * This allows two different levels to be compared. It compares the level data from each
     * Level to see if they are the same dimensions and same numbers.
     * 
     * @param otherLevel - the other level to for this one to be compared to
     * @return true if the two levels are equivalent, false if not
     */
    public boolean equals(Level otherLevel) {
        int[][] otherLevelData = otherLevel.getLevelData();
        // check that both dimensions are the same
        if (this.lvlData.length != otherLevelData.length) {
            return false;
        }
        if (this.lvlData[0].length != otherLevelData[0].length) {
            return false;
        }
        // make sure that every index is the same
        for (int i = 0; i < lvlData.length; i++) {
            for (int j = 0; j < lvlData[i].length; j++) {
                if (lvlData[i][j] != otherLevelData[i][j]) {
                    return false;
                }
            }
        }
        // if passed all tests above, they are the same
        return true;

    }

}

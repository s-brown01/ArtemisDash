package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;

/**
 * This class will handle things such as importing level sprites, drawing the level to the
 * screen, and keep track of the current level played
 * 
 * @author johnbotonakis and Sean-Paul Brown
 */
public class LevelManager {
    private BufferedImage[] levelSprite;
    private Level[] levels = new Level[15]; // 15 total levels
    private Level currentLevel;
    private int currentLevelIndex;

    /**
     * Instantiates a manager for created level objects
     * 
     * @param game - The Game object to which the level will be drawn onto
     */
    public LevelManager(Game game) {
        importLevelSprites();
        loadLevels();
        // initially the current level is the first index
        this.currentLevelIndex = 0;
        this.currentLevel = levels[currentLevelIndex];
    }

    /**
     * Load in the levels arrays with template levels.
     */
    private void loadLevels() {
        for (int i = 0; i < levels.length; i++) {
            levels[i] = new Level(LoadSave.LEVEL1_DATA);
        }
    }

    /**
     * Imports the sprites to build a level
     */
    private void importLevelSprites() {
        BufferedImage img = LoadSave.getSpriteSheet(LoadSave.WORLD1_SPRITES);
        levelSprite = new BufferedImage[48]; // 12 sprites wide * 4 sprites tall = 48
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }

    }

    /**
     * Draws the level to the screen with the provided sprite sheet
     * 
     * @param g            - the Graphics to draw on
     * @param xLevelOffset - the current xOffset of the game from screen-scrolling
     *                     horizontally
     */
    public void draw(Graphics g, int xLevelOffset) {
        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < currentLevel.getLevelData()[0].length; i++) {
                int index = currentLevel.getSpriteIndex(i, j);
                // index 11 is a transparent tile, might as well not draw it
                if (index != 11)
                    g.drawImage(levelSprite[index], i * Game.TILES_SIZE - xLevelOffset, Game.TILES_SIZE * j,
                            Game.TILES_SIZE, Game.TILES_SIZE, null);

            }

        }
    }

    /**
     * Setter for the current level. The new current level will be the index of the levelIndex
     * parameter.
     * 
     * @param levelIndex - the index of the new current Level, it is mapped to the stage
     *                   number
     */
    public void setCurrentLevel(int levelIndex) {
        this.currentLevelIndex = levelIndex;
        this.currentLevel = levels[currentLevelIndex];
    }

    /**
     * Returns the current level
     * 
     * @return - The currently played level
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }

    /**
     * Getter for a specific index in the array of levels. They should be mapped to the stage
     * number
     * 
     * @param levelIndex - the specific index to get
     * @return if can, the Level at the level index; if it is out of bounds, then return null
     */
    public Level getLevelAtIndex(int levelIndex) {
        // sanity check
        if (levelIndex < 0 || levelIndex >= levels.length) {
            return null;
        }
        return levels[levelIndex];
    }

    /**
     * Set hidden to false on certain levels based on the current Level. This lets the Player
     * 'progress' through the different Levels
     */
    public void unhideNextLevels() {
        // depending what the current level is, it should unhide different levels
        // for now just unlock the next level
        switch (currentLevelIndex) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
        case 11:
        case 12:
        case 13:
        case 14:
            unhideLevel(currentLevelIndex + 1);
        case 15:
            return;
        }
    }

    /**
     * Set the hidden boolean to false for the specific Level at the index in the parameters
     * 
     * @param index - the index for the Level to set hidden false
     */
    private void unhideLevel(int index) {
        levels[index].setHidden(false);
    }

    /**
     * Getter for the entire array of Levels
     * 
     * @return the array containing all 15 levels
     */
    public Level[] getLevels() {
        return levels;
    }

    /**
     * Getter for a current level's index
     * 
     * @return the current value of the current level index
     */
    public int getCurrentLevelIndex() {
        return currentLevelIndex;
    }
}

package drivers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import levels.Level;
import levels.LevelManager;
import utils.LoadSave;

/**
 * This is a tester for all of the classes inside of the levels package. It tests the
 * Level and LevelManager class with the test function.
 * 
 * @author Sean-Paul Brown
 *
 */
public class LevelsDriver implements DriverInterface {

    @Override
    public boolean test() {
        boolean allSuccess = true;
        if (!testLevel()) {
            allSuccess = false;
        }
        if (!testLevelManager()) {
            allSuccess = false;
        }
        return allSuccess;
    }

    /**
     * This tests the methods of the Level class
     * 
     * @return true if all of the tests were passed. False if at least 1 was failed.
     */
    private boolean testLevel() {
        boolean allSuccess = true;

        // Test creating a Level instance
        final Level level = new Level(LoadSave.LEVEL1_DATA);

        // Test getting sprite index
        if (level.getSpriteIndex(0, 0) != 13 || level.getSpriteIndex(2, 2) != 25) {
            printLevelError("Failed getSpriteIndex");
            allSuccess = false;
        }

        // Test initial completed value - should be false initially
        if (level.getCompleted()) {
            printLevelError("Failed getCompleted");
            allSuccess = false;
        }

        // Test setting and getting completed status
        level.setCompleted(true);
        if (!level.getCompleted()) {
            printLevelError("Failed setCompleted");
            allSuccess = false;
        }

        // Test initial hidden value - should be true initially
        if (!level.getHidden()) {
            printLevelError("Failed getHidden");
            allSuccess = false;
        }

        // Test setting and getting hidden status
        level.setHidden(false);
        if (level.getHidden()) {
            printLevelError("Failed setHidden");
            allSuccess = false;
        }

        // Test getSkeletons method, should be 2 skeletons
        if (level.getSkeletons().size() != 6) {
            printLevelError(level.getSkeletons().size() + "Failed getSkeletons");
            allSuccess = false;
        }

        // Test getSkeletonKings method, should be 1 skeleton king
        if (level.getSkeletonKings().size() != 0) {
            printLevelError(level.getSkeletonKings().size() + "failed getSkeletonKings");
            allSuccess = false;
        }

        // create 2 new levels to test the equals method
        // same level data as the inital level
        final Level sameLevel = new Level(LoadSave.LEVEL1_DATA);
        // different level data than the 2 above
        final Level differentLevel = new Level(LoadSave.DEFAULT_LEVEL);

        // these two levels should be the same
        if (!level.equals(sameLevel)) {
            printLevelError("Failed valid equals");
            allSuccess = false;
        }

        // these two levels should not be the same
        if (level.equals(differentLevel)) {
            printLevelError("Failed invalid equals");
            allSuccess = false;
        }

        return allSuccess;
    }

    /**
     * This tests the methods of the LevelManager class
     * 
     * @return true if all of the tests were passed. False if at least 1 was failed.
     */
    private boolean testLevelManager() {
        boolean allSuccess = true;

        // Create a LevelManager instance
        // create it without the Game so that a game doesn't start
        LevelManager testLM = new LevelManager(null);
        Level[] allLevels = testLM.getLevels();

        // Test getting levels (should be exactly 5)
        if (allLevels.length != 5) {
            printLevelManagerError("Failed getting/creating levels");
            allSuccess = false;
        }

        // Test getCurrentLevel method
        if (!testLM.getCurrentLevel().equals(allLevels[0])) {
            printLevelManagerError("Failed valid getCurrentLevel");
            allSuccess = false;
        }

        // Test setCurrentLevel method
        testLM.setCurrentLevel(1);
        if (!testLM.getCurrentLevel().equals(allLevels[1])) {
            printLevelManagerError("Failed setCurrentLevel, after setting");
            allSuccess = false;
        }

        // Test getLevelAtIndex method
        if (!testLM.getLevelAtIndex(4).equals(allLevels[4])) {
            printLevelManagerError("Failed getLevelAtIndex");
            allSuccess = false;
        }

        // Test draw method
        BufferedImage mockImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics g = mockImage.getGraphics();
        // make sure there is not error when drawing an image
        // Assuming no xLevelOffset for simplicity
        testLM.draw(g, 0);

        // Test unhideNextLevel
        testLM.unhideNextLevels();
        // since current level is 1, then 2 should now be unhidden
        if (testLM.getLevelAtIndex(2).getHidden()) {
            printLevelManagerError("Failed to unhide levels");
            allSuccess = false;
        }

        return allSuccess;
    }

    /**
     * This prints off a message in the format to make all level errors look the same
     * 
     * @param message - the error message to be printed
     */
    private void printLevelError(String message) {
        System.err.println("\tLEVEL - " + message);
    }

    /**
     * This prints off a message in the format to make all level manager errors look the same
     * 
     * @param message - the error message to be printed
     */
    private void printLevelManagerError(String message) {
        System.err.println("\tLEVEL MANAGER - " + message);
    }
}

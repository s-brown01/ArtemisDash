package drivers;

import entities.*;
import levels.Level;
import main.Game;
import utils.LoadSave;
import static utils.Constants.*;

/**
 * This will test all classes in the Entity package
 * 
 * @author Sean-Paul Brown
 */
public class EntityDriver implements DriverInterface {

    /**
     * This method will be the main method of this class. It will test each class in the
     * entities package.
     */
    @Override
    public boolean test() {
        boolean allSuccess = true;
        // test the Enemies
        if (!testEnemy()) {
            allSuccess = false;
        }
        return allSuccess;
    }

    /**
     * This will test the all instances of Enemies and return the result the tests. Cannot
     * test Enemy itself since it is an abstract class.
     * 
     * @return true if all instances passed the tests, false if not
     */
    private boolean testEnemy() {
        boolean allSuccess = true;
        // create a Level to have the skeleton and player interact with.
        // it uses the Level 1 Data.
        Level testLvl = new Level(LoadSave.getLevelData());
        int[][] lvlData = testLvl.getLevelData();

        // create a Skeleton at a point that is above the ground so they are in air. 4 tiles on x
        // and y is in the air.
        final int skelX = 3 * Game.TILES_SIZE;
        final int skelY = 4 * Game.TILES_SIZE;
        final int skelWidth = 100;
        final int skelHeight = 100;
        Skeleton testSkel = new Skeleton(skelX, skelY, skelWidth, skelHeight);

        // testing hitbox coordinates
        if (testSkel.getHitbox().x != skelX || testSkel.getHitbox().y != skelY) {
            printEnemyError("failed getting hitbox coords");
            allSuccess = false;
        }
        // testing hitbox dimensions
        if (testSkel.getHitbox().width != EnemyConstants.SKELETON_HITBOX_WIDTH
                || testSkel.getHitbox().height != EnemyConstants.SKELETON_HITBOX_HEIGHT) {
            printEnemyError("failed getting hitbox dimensions");
            allSuccess = false;
        }

        // testing getting state, should be defaulted to IDLE
        if (testSkel.getState() != EnemyConstants.IDLE) {
            printEnemyError("Failed getting state");
            allSuccess = false;
        }

        // testing getting the animation index (since no updates, it should be 0th index)
        if (testSkel.getAniIndex() != 0) {
            printEnemyError("Failed getting animation index");
            allSuccess = false;
        }

        // testing Active, it should be true since the enemy just spawned in
        if (!testSkel.isActive()) {
            printEnemyError("Failed getting active, no updates");
            allSuccess = false;
        }

        // testing getting the walkSpeed
        if (testSkel.getWalkSpeed() != 0.8f) {
            printEnemyError("Failed to get walkSpeed");
            allSuccess = false;
        }

        // testing getting the walk direction, should be LEFT default
        if (testSkel.getWalkDirection() != Directions.LEFT) {
            printEnemyError("Failed getting walking direction, no updates");
            allSuccess = false;
        }

        // initially facing left means width x is the width
        if (testSkel.xFlipped() != skelWidth) {
            printEnemyError("Failed getting xFlipped, no updates");
            allSuccess = false;
        }

        // initially facing left means width flip is -1
        if (testSkel.widthFlipped() != -1) {
            printEnemyError("Failed getting widthFlipped, no updates");
            allSuccess = false;

        }

        // Skeleton should not be killed when initially created
        if (testSkel.isKilled()) {
            printEnemyError("Failed isKilled, no updates");
            allSuccess = false;
        }

        // Skeleton should not be inAir when initially created
        if (testSkel.isInAir()) {
            printEnemyError("Failed inAir, no updates");
            allSuccess = false;
        }

        // create a player to the bottom right of the test Skeleton
        Player testPlayer = new Player(skelX + 1 * Game.TILES_SIZE, skelY + 2 * Game.TILES_SIZE, skelWidth, skelHeight,
                null);

        // the first update has different behavior than the rest
        testSkel.update(lvlData, testPlayer);

        // the skeleton should be in the air after the first update
        if (!testSkel.isInAir()) {
            printEnemyError("Failed inAir, first update");
            allSuccess = false;
        }

        // the first update the skeleton should still be in IDLE
        if (testSkel.getState() != EnemyConstants.IDLE) {
            printEnemyError("Failed getting state, first update");
            allSuccess = false;
        }

        // an update buffer to make sure that the loop isn't infinite. This should be a high
        // number because updates happen slowly.
        final int buffer = 100;
        int counter = 0;
        // make the Skeleton drop to the floor
        while (testSkel.isInAir()) {
            testSkel.update(lvlData, testPlayer);
            counter++;
            // make sure
            if (counter >= buffer) {
                printEnemyError("Took too long to fall");
                allSuccess = false;
                break;
            }
        }

        // after falling, the enemy will be IDLE until 1 more update
        if (testSkel.getState() != EnemyConstants.IDLE) {
            printEnemyError("Failed getting state, updated");
            allSuccess = false;
        }

        // update once more after falling has been completed
        testSkel.update(lvlData, testPlayer);

        // the skeleton ishould not be in the air any longer
        if (testSkel.isInAir()) {
            printEnemyError("Failed inAir, updated");
            allSuccess = false;
        }

        // once they skeleton is on the ground and updated, then they will start moving
        if (testSkel.getState() != EnemyConstants.RUNNING) {
            printEnemyError("Failed getting state, updated");
            allSuccess = false;
        }

        // since the skeleton just switch states, the index should be at 0
        if (testSkel.getAniIndex() != 0) {
            printEnemyError("Failed getting animation index");
            allSuccess = false;
        }

        // testing getting x-coordinate after falling, should not have changed
        if (testSkel.getHitbox().x != skelX) {
            printEnemyError("failed getting hitbox coords, updated");
            allSuccess = false;
        }

        // testing hitbox dimensions after falling, should not have changed
        if (testSkel.getHitbox().width != EnemyConstants.SKELETON_HITBOX_WIDTH
                || testSkel.getHitbox().height != EnemyConstants.SKELETON_HITBOX_HEIGHT) {
            printEnemyError("failed getting hitbox dimensions, updated");
            allSuccess = false;
        }

        while (testPlayer.isInAir()) {
            testPlayer.update(0);
        }
        testPlayer.update(0);
        System.out.println((testSkel.getHitbox().y + testSkel.getHitbox().height) + "  "
                + (testPlayer.getHitbox().y + testPlayer.getHitbox().height));

        return allSuccess;
    }

    /**
     * This is used to style the messages that are printed if any tests failed in the
     * testEnemy Method
     * 
     * @param message the message to be printed
     */
    private void printEnemyError(String message) {
        System.err.println("\tENEMY - " + message);
    }

}

package drivers;

import entities.*;
import levels.Level;
import main.Game;
import utils.Constants.EnemyConstants;
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

        // an update buffer to make sure that the while loops are not infinite. This should be a
        // high
        // number because updates happen slowly.
        final int loopBuffer = 500;

        // create a Level to have the skeleton and player interact with.
        // it uses the Level 1 Data.
        Level testLvl = new Level(LoadSave.getLevelData(LoadSave.DEFAULT_LEVEL));
        int[][] lvlData = testLvl.getLevelData();

        // create a Skeleton at a point that is above the ground so they are in air. 4 tiles on x
        // and y is in the air.
        final int skelX = 3 * Game.TILES_SIZE;
        final int skelY = 4 * Game.TILES_SIZE;
        final int skelKingX = skelX - Game.TILES_SIZE / 2;
        final int skelWidth = 100;
        final int skelHeight = 100;
        Skeleton testSkel = new Skeleton(skelX, skelY, skelWidth, skelHeight);
        SkeletonKing testSkelKing = new SkeletonKing(skelKingX, skelY, skelWidth, skelHeight);

        // testing hitbox coordinates
        if (testSkel.getHitbox().x != skelX || testSkel.getHitbox().y != skelY) {
            printEnemyError("failed getting hitbox coords");
            allSuccess = false;
        }

        // skeleton king coordinates
        if (testSkelKing.getHitbox().x != skelKingX || testSkelKing.getHitbox().y != skelY) {
            printEnemyError("failed getting hitbox coords, king");
            allSuccess = false;
        }

        // testing hitbox dimensions
        if (testSkel.getHitbox().width != EnemyConstants.SKELETON_HITBOX_WIDTH
                || testSkel.getHitbox().height != EnemyConstants.SKELETON_HITBOX_HEIGHT) {
            printEnemyError("failed getting hitbox dimensions");
            allSuccess = false;
        }

        // skeleton king dimensions
        if (testSkelKing.getHitbox().width != EnemyConstants.SKELETON_KING_HITBOX_WIDTH
                || testSkelKing.getHitbox().height != EnemyConstants.SKELETON_KING_HITBOX_HEIGHT) {
            printEnemyError("failed getting hitbox dimensions, king");
            allSuccess = false;
        }
        // testing getting state, should be defaulted to IDLE
        if (testSkel.getState() != EnemyConstants.IDLE || testSkelKing.getState() != EnemyConstants.IDLE) {
            printEnemyError("Failed getting state");
            allSuccess = false;
        }

        // testing getting the animation index (since no updates, it should be 0th index)
        if (testSkel.getAniIndex() != 0 || testSkelKing.getAniIndex() != 0) {
            printEnemyError("Failed getting animation index");
            allSuccess = false;
        }

        // testing Active, it should be true since the enemy just spawned in
        if (!testSkel.isActive() || !testSkelKing.isActive()) {
            printEnemyError("Failed getting active, no updates");
            allSuccess = false;
        }

        // testing getting the walkSpeed
        if (testSkel.getWalkSpeed() != EnemyConstants.getWalkSpeed(EnemyConstants.SKELETON)) {
            printEnemyError("Failed to get walkSpeed");
            allSuccess = false;
        }

        // skeleton king walk speed
        if (testSkelKing.getWalkSpeed() != EnemyConstants.getWalkSpeed(EnemyConstants.SKELETON_KING)) {
            printEnemyError("Failed to get walkSpeed, king");
            allSuccess = false;
        }

        // testing getting the attack walk speed
        if (testSkel.getAttackWalkSpeed() != EnemyConstants.getAttackWalkSpeed(EnemyConstants.SKELETON)) {
            printEnemyError("Failed to get attackWalkSpeed");
            allSuccess = false;
        }

        // skeleton king attack walk speed
        if (testSkelKing.getAttackWalkSpeed() != EnemyConstants.getAttackWalkSpeed(EnemyConstants.SKELETON_KING)) {
            printEnemyError("Failed to get attackWalkSpeed, king");
            allSuccess = false;
        }

        // testing getting the walk direction, should be LEFT default
        if (testSkel.getWalkDirection() != Directions.LEFT) {
            printEnemyError("Failed getting walking direction, no updates");
            allSuccess = false;
        }

        // skeleton king initial direction
        if (testSkelKing.getWalkDirection() != Directions.LEFT) {
            printEnemyError("Failed getting walking direction, no updates");
            allSuccess = false;
        }

        // initially facing left means width x is the width
        if (testSkel.xFlipped() != skelWidth || testSkelKing.xFlipped() != skelWidth) {
            printEnemyError("Failed getting xFlipped, no updates");
            allSuccess = false;
        }

        // initially facing left means width flip is -1
        if (testSkel.widthFlipped() != -1 || testSkelKing.widthFlipped() != -1) {
            printEnemyError("Failed getting widthFlipped, no updates");
            allSuccess = false;

        }

        // Skeleton should not be killed when initially created
        if (testSkel.isKilled() || testSkelKing.isKilled()) {
            printEnemyError("Failed isKilled, no updates");
            allSuccess = false;
        }

        // Skeleton should be inAir when initially created
        if (!testSkel.isInAir() || !testSkelKing.isInAir()) {
            printEnemyError("Failed inAir, no updates");
            allSuccess = false;
        }

        /*
         * create a player to the right of the test Skeleton. The y-coordinate doesn't matter
         * (since the player will fall to the ground); however, the x-coordinate should be within
         * the Enemy's attack range
         */
        Player testPlayer = new Player(skelX + 25, skelY, skelWidth, skelHeight, null);
        testPlayer.loadLvlData(lvlData);

        // the first update has different behavior than the rest
        testSkel.update(lvlData, testPlayer);
        testSkelKing.update(lvlData, testPlayer);

        // the skeleton should be in the air after the first update
        if (!testSkel.isInAir() || !testSkelKing.isInAir()) {
            printEnemyError("Failed inAir, first update");
            allSuccess = false;
        }

        // the first update the skeleton should still be in IDLE
        if (testSkel.getState() != EnemyConstants.IDLE || testSkelKing.getState() != EnemyConstants.IDLE) {
            printEnemyError("Failed getting state, first update");
            allSuccess = false;
        }

        int counter = 0;
        // make the Skeleton drop to the floor
        while (testSkel.isInAir()) {
            testSkel.update(lvlData, testPlayer);
            counter++;
            // make sure it's not an infinite loop
            if (counter >= loopBuffer) {
                printEnemyError("Took too long to fall");
                allSuccess = false;
                break;
            }
        }

        counter = 0;
        // make the Skeleton king drop to the floor
        while (testSkelKing.isInAir()) {
            testSkelKing.update(lvlData, testPlayer);
            counter++;
            // make sure it's not an infinite loop
            if (counter >= loopBuffer) {
                printEnemyError("Took too long to fall");
                allSuccess = false;
                break;
            }
        }

        // after falling, the enemy will be IDLE until 1 more update
        if (testSkel.getState() != EnemyConstants.IDLE || testSkelKing.getState() != EnemyConstants.IDLE) {
            printEnemyError("Failed getting state, updated");
            allSuccess = false;
        }

        // testing getting x-coordinate after falling, should not have changed
        if (testSkel.getHitbox().x != skelX || testSkelKing.getHitbox().x != skelKingX) {
            printEnemyError("failed getting hitbox coords after falling");
            allSuccess = false;
        }

        // update once more after falling has been completed
        testSkel.update(lvlData, testPlayer);
        testSkelKing.update(lvlData, testPlayer);

        // the skeleton should not be in the air any longer
        if (testSkel.isInAir() || testSkelKing.isInAir()) {
            printEnemyError("Failed inAir, updated");
            allSuccess = false;
        }

        // once they skeleton is on the ground and updated, then they will start moving
        if (testSkel.getState() != EnemyConstants.RUNNING || testSkelKing.getState() != EnemyConstants.RUNNING) {
            printEnemyError("Failed getting state, updated");
            allSuccess = false;
        }

        // since the skeleton just switch states, the index should be at 0
        if (testSkel.getAniIndex() != 0 || testSkelKing.getAniIndex() != 0) {
            printEnemyError("Failed getting animation index");
            allSuccess = false;
        }

        // testing getting x-coordinate after falling, should not have changed
        if (testSkel.getHitbox().x != skelX || testSkelKing.getHitbox().x != skelKingX) {
            printEnemyError("failed getting hitbox coords, after post-fall update");
            allSuccess = false;
        }

        // testing hitbox dimensions after falling, should not have changed
        if (testSkel.getHitbox().width != EnemyConstants.SKELETON_HITBOX_WIDTH
                || testSkel.getHitbox().height != EnemyConstants.SKELETON_HITBOX_HEIGHT) {
            printEnemyError("failed getting hitbox dimensions, updated");
            allSuccess = false;
        }

        // skeleton king dimensions
        if (testSkelKing.getHitbox().width != EnemyConstants.SKELETON_KING_HITBOX_WIDTH
                || testSkelKing.getHitbox().height != EnemyConstants.SKELETON_KING_HITBOX_HEIGHT) {
            printEnemyError("failed getting hitbox dimensions, updated - king");
            allSuccess = false;
        }

        // update 1 time to make the Player register they are in the air
        testPlayer.update(0);
        // bring the player to the ground too
        while (testPlayer.isInAir()) {
            testPlayer.update(0);
        }
        // update after landing
        testPlayer.update(0);

        // check they are both resting on the same tile (should be the y + height, since y is the
        // top of the hitbox)
        if ((testSkel.getHitbox().y + testSkel.getHitbox().height) != (testPlayer.getHitbox().y
                + testPlayer.getHitbox().height)) {
            printEnemyError("Not resting on same tile");
            allSuccess = false;
        }

        // skeleton king and player tile height
        if ((testSkelKing.getHitbox().y + testSkelKing.getHitbox().height) != (testPlayer.getHitbox().y
                + testPlayer.getHitbox().height)) {
            printEnemyError("Not resting on same tile - king");
            allSuccess = false;
        }

        // checking that the testPlayer is still on the right of the testSkeleton
        if (testSkel.getHitbox().x - testPlayer.getHitbox().x >= 0) {
            printEnemyError("Player is no longer on the right");
            allSuccess = false;
        }

        // skeleton king with player to the right
        if (testSkelKing.getHitbox().x - testPlayer.getHitbox().x >= 0) {
            printEnemyError("Player is no longer on the right");
            allSuccess = false;
        }

        // updating the skeleton so it sees the player, and since it is attack range, it now be in
        // the attacking state
        testSkel.update(lvlData, testPlayer);
        testSkelKing.update(lvlData, testPlayer);
        // make sure the skeleton is in attack
        if (testSkel.getState() != EnemyConstants.ATTACK) {
            printEnemyError("Is not in attack state");
            allSuccess = false;
        }

        // skeleton king in attack
        if (testSkelKing.getState() != EnemyConstants.ATTACK) {
            printEnemyError("Is not in attack state");
            allSuccess = false;
        }

        // test that the enemy turned towards the Player in the update
        if (testSkel.getWalkDirection() != Directions.RIGHT) {
            printEnemyError("Failed to turn towards player");
            allSuccess = false;
        }

        // skeleton king direction
        if (testSkelKing.getWalkDirection() != Directions.RIGHT) {
            printEnemyError("Failed to turn towards player");
            allSuccess = false;
        }

        // check the xFlipped and wFlipped now that the direction has changed
        if (testSkel.xFlipped() != 0 && testSkel.widthFlipped() != 1) {
            printEnemyError("Failed xFlipped or widthFlipped after changing direction");
            allSuccess = false;
        }

        // skeleton king flipping
        if (testSkelKing.xFlipped() != 0 && testSkelKing.widthFlipped() != 1) {
            printEnemyError("Failed xFlipped or widthFlipped after changing direction");
            allSuccess = false;
        }

        // updating it with player insight means that it should have moved to the right
        if (skelX + testSkel.getWalkSpeed() * testSkel.widthFlipped() != testSkel.getHitbox().x) {
            printEnemyError("Failed moving towards the player");
            allSuccess = false;
        }

        // skeleton king movement
        if (skelKingX + testSkelKing.getWalkSpeed() * testSkelKing.widthFlipped() != testSkelKing.getHitbox().x) {
            printEnemyError("Failed moving towards the player");
            allSuccess = false;
        }

        final int startingPlayerHealth = testPlayer.getHealth();
        // once the enemy finishes attacking the Player it will go back to IDLE
        counter = 0;
        while (testSkel.getState() != EnemyConstants.IDLE) {
            testSkel.update(lvlData, testPlayer);
            counter++;
            // make sure that it is only in the attack or idle states
            if (!(testSkel.getState() == EnemyConstants.ATTACK || testSkel.getState() == EnemyConstants.IDLE)) {
                printEnemyError("Failed to return to IDLE from ATTACK");
                allSuccess = false;
                break;
            }
            // check that it isn't an infinite loop
            if (counter >= loopBuffer) {
                printEnemyError("Failed attacking in a reasonable time");
                allSuccess = false;
                break;
            }
        }

        // testing that the enemy hit the player
        if (testPlayer.getHealth() != startingPlayerHealth - 1) {
            printEnemyError("Failed to register first hit on player");
            allSuccess = false;
        }

        // reset the players' hurting boolean so that the second attack can register
        while (testPlayer.isHurting()) {
            testPlayer.update(0);
        }

        // once the enemy finishes attacking the Player it will go back to IDLE
        counter = 0;
        while (testSkelKing.getState() != EnemyConstants.IDLE) {
            testSkelKing.update(lvlData, testPlayer);
            counter++;
            // make sure that it is only in the attack or idle states
            if (!(testSkelKing.getState() == EnemyConstants.ATTACK || testSkelKing.getState() == EnemyConstants.IDLE)) {
                printEnemyError("Failed to return to IDLE from ATTACK");
                allSuccess = false;
                break;
            }
            // check that it isn't an infinite loop
            if (counter >= loopBuffer) {
                printEnemyError("Failed attacking in a reasonable time");
                allSuccess = false;
                break;
            }
        }

        // checking the second hit on the player
        if (testPlayer.getHealth() != startingPlayerHealth - 2) {
            printEnemyError("Failed to register second hit on player");
            allSuccess = false;
        }

        // make sure that it reset the index after attacking
        if (testSkel.getAniIndex() != 0 && testSkelKing.getAniIndex() != 0) {
            printEnemyError("Failed to reset aniIndex after attacking");
            allSuccess = false;
        }

        // kill the skeleton (doing the max health damage guarentees a kill)
        testSkel.hurt(EnemyConstants.getMaxHealth(EnemyConstants.SKELETON));
        testSkelKing.hurt(EnemyConstants.getMaxHealth(EnemyConstants.SKELETON_KING));

        // it should be dead now
        if (!testSkel.isKilled() || !testSkelKing.isKilled()) {
            printEnemyError("Failed to kill skeleton");
            allSuccess = false;
        }

        // it should not immediately be deactivated
        if (!testSkel.isActive() || !testSkelKing.isActive()) {
            printEnemyError("Deactivated enemy too fast");
            allSuccess = false;
        }

        // should be in the DEAD state and the aniIndex should be restarted
        if (testSkel.getState() != EnemyConstants.DEAD || testSkel.getAniIndex() != 0) {
            printEnemyError("Failed to change to the DEAD state");
            allSuccess = false;
        }

        if (testSkelKing.getState() != EnemyConstants.DEAD || testSkelKing.getAniIndex() != 0) {
            printEnemyError("Failed to change to the DEAD state");
            allSuccess = false;
        }

        counter = 0;
        while (testSkel.isActive()) {
            testSkel.update(lvlData, testPlayer);
            counter++;
            // make sure that the state is always DEAD
            if (testSkel.getState() != EnemyConstants.DEAD) {
                printEnemyError("Changed states while dying");
                allSuccess = false;
            }
            // check that it isn't an infinite loop
            if (counter >= loopBuffer) {
                printEnemyError("Failed dying in a reasonable time");
                allSuccess = false;
                break;
            }
        }

        counter = 0;
        while (testSkelKing.isActive()) {
            testSkelKing.update(lvlData, testPlayer);
            counter++;
            // make sure that the state is always DEAD
            if (testSkelKing.getState() != EnemyConstants.DEAD) {
                printEnemyError("Changed states while dying");
                allSuccess = false;
            }
            // check that it isn't an infinite loop
            if (counter >= loopBuffer) {
                printEnemyError("Failed dying in a reasonable time");
                allSuccess = false;
                break;
            }
        }

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

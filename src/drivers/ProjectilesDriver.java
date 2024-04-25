package drivers;

import projectiles.Arrow;

import static utils.Constants.ProjectileConstants.*;

/**
 * This will test all class in the projectile package
 * 
 * @author Sean-Paul Brown
 *
 */
public class ProjectilesDriver implements DriverInterface {

    @Override
    public boolean test() {
        boolean allSuccess = true;
        if (!testArrow()) {
            allSuccess = false;
        }
        return allSuccess;
    }

    /**
     * This will test the methods of the Arrow class
     * @return ture if Arrrow passed all tests, false if not
     */
    private boolean testArrow() {
        boolean allSuccess = true;
        // arrow is starting at 0,0 moving diagonally to the right-down at 1 pixel per update
        final int initX = 0;
        final int initY = 0;
        final int slope = 1;
        Arrow testArrow = new Arrow(initX, initY, 1, false);
        if (!testArrow.isActive()) {
            allSuccess = false;
            printArrowError("Failed initial active test");
        }
        if (testArrow.getAniIndex() != 0) {
            allSuccess = false;
            printArrowError("Failed inital aniIndex test");
        }
        if (testArrow.isColliding()) {
            allSuccess = false;
            printArrowError("Failed initial colliding test");
        }
        final float horizSpeed = (float) Math.sqrt((getProjSpeed(ARROW) * (getProjSpeed(ARROW)) / (1 + (slope * slope))));
        if (testArrow.getSpeed() != horizSpeed) {
            allSuccess = false;
            printArrowError("Failed Calculating speed");
        }
        testArrow.update();
        if (testArrow.getHitbox().x != (initX + horizSpeed)) {
            allSuccess = false;
            printArrowError("Failed horizontal movement");
        }
        if (testArrow.getHitbox().y != (initY + horizSpeed * slope)) {
            allSuccess = false;
            printArrowError("Failed vertical movement");
        }
        
        return allSuccess;
    }
    
    /**
     * This prints out an error format for arrow tests
     * 
     * @param message - the message to be printed
     */
    private void printArrowError(String message) {
        System.err.println("\tARROW - " + message);
    }

}

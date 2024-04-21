package drivers;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import main.Game;
import states.Playing;
import utils.Constants.PlayerStates;

public class InputsDriver implements DriverInterface {

    @Override
    public boolean test() {
        boolean allSuccess = true;
        if (!testKeyboard()) {
            allSuccess = false;
        }
        if (!testMouse()) {
            allSuccess = false;
        }
        return allSuccess;
    }

    /**
     * This tests the all of the inputs on a keyboard if they are handled properly
     * 
     * @return true if all tests are passed, false if not
     */
    private boolean testMouse() {
        boolean allSuccess = true;
        Playing testPlaying = new Playing(null);
        Player testPlayer = testPlaying.getPlayer();
        if (testPlayer.isAttacking()) {
            printMouseError("Attacking after initializing");
            allSuccess = false;
        }

        // create a mouseEvent to the left of the player
        MouseEvent pressLeft = new MouseEvent(null, MouseEvent.MOUSE_PRESSED, System.currentTimeMillis(), 0,
                (int) (testPlayer.getHitbox().x - 40), (int) (testPlayer.getHitbox().y - 40), 1, false);
        testPlaying.mousePressed(pressLeft);
        // make sure not attacking...yet
        if (testPlayer.isAttacking()) {
            printMouseError("Should not be in attacking immediately after clicking");
            allSuccess = false;
        }
        // make sure it is the same Point
        if (!testPlayer.getNextAttack().equals(pressLeft.getPoint())) {
            printMouseError("Not attacking in the same mouse as the mouse event");
            allSuccess = false;
        }
        // the shot was to left, the player should be facing left
        if (!(testPlayer.getFlipX() == (int) (PlayerStates.IMAGE_WIDTH * Game.SCALE) && testPlayer.getFlipW() == -1)) {
            printMouseError("Failed to face left and set FlipX or FlipW");
            allSuccess = false;
        }

        // drag the "mouse" to the right of the player
        MouseEvent dragRight = new MouseEvent(null, MouseEvent.MOUSE_DRAGGED, System.currentTimeMillis(), 0,
                (int) (testPlayer.getHitbox().x + 40), (int) (testPlayer.getHitbox().y + 40), 1, false);
        testPlaying.mouseDragged(dragRight);
        // still not attacking yet
        if (testPlayer.isAttacking()) {
            printMouseError("Should not be in attacking after dragging");
            allSuccess = false;
        }
        // make sure it is the same Point as drafRight
        if (!testPlayer.getNextAttack().equals(dragRight.getPoint())) {
            printMouseError("Not attacking in the same mouse as the mouse event");
            allSuccess = false;
        }
        // the dragRight was to right, the player should be facing right
        if (!(testPlayer.getFlipX() == 0 && testPlayer.getFlipW() == 1)) {
            printMouseError("Failed to face right and set FlipX or FlipW");
            allSuccess = false;
        }

        // create a release event at a different point that the other two
        MouseEvent release = new MouseEvent(null, MouseEvent.MOUSE_RELEASED, System.currentTimeMillis(), 0,
                (int) (testPlayer.getHitbox().x + 50), (int) (testPlayer.getHitbox().y - 100), 1, false);
        testPlaying.mouseReleased(release);
        // now is attacking
        if (!testPlayer.isAttacking()) {
            printMouseError("Should be attacking after releasing mouse");
            allSuccess = false;
        }

        // make sure it is the same Point as release
        if (!testPlayer.getNextAttack().equals(release.getPoint())) {
            printMouseError("Not attacking in the same mouse as the mouse event");
            allSuccess = false;
        }
        // should still be facing right
        if (!(testPlayer.getFlipX() == 0 && testPlayer.getFlipW() == 1)) {
            printMouseError("Failed to face right and set FlipX or FlipW");
            allSuccess = false;
        }

        return allSuccess;
    }

    /**
     * This tests the all of the inputs on a keyboard if they are handled properly
     * 
     * @return true if all tests are passed, false if not
     */
    private boolean testKeyboard() {
        boolean allSuccess = true;
        Playing testPlaying = new Playing(null);
        Player testPlayer = testPlaying.getPlayer();
        if (testPlayer.isLeft() || testPlayer.isRight()) {
            printKeyError("Failed initial test");
            allSuccess = false;
        }

        // create 2 keyEvent that simulates pushing A and D
        KeyEvent aPress = new KeyEvent(null, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, '\0');
        KeyEvent dPress = new KeyEvent(null, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, '\0');
        testPlaying.keyPressed(aPress);
        testPlaying.keyPressed(dPress);
        if (!testPlayer.isLeft() || !testPlayer.isRight()) {
            printKeyError("Failed press test");
            allSuccess = false;
        }

        // releasing a random key (not A or D), picked T arbitrarily
        KeyEvent tRelease = new KeyEvent(null, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_T,
                '\0');
        testPlaying.keyPressed(tRelease);
        if (!testPlayer.isLeft() || !testPlayer.isRight()) {
            printKeyError("Failed invalid release test");
            allSuccess = false;
        }

        KeyEvent aRelease = new KeyEvent(null, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_A,
                '\0');
        KeyEvent dRelease = new KeyEvent(null, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_D,
                '\0');
        testPlaying.keyPressed(aRelease);
        testPlaying.keyPressed(dRelease);
        if (testPlayer.isLeft() || testPlayer.isRight()) {
            printKeyError("Failed valid release test");
            allSuccess = false;
        }

        return allSuccess;
    }

    /**
     * This prints out an error format for keyboard tests
     * 
     * @param message - the message to be printed
     */
    private void printKeyError(String message) {
        System.err.println("KEYBOARD - " + message);
    }

    /**
     * This prints out an error format for mouse tests
     * 
     * @param message - the message to be printed
     */
    private void printMouseError(String message) {
        System.err.println("KEYBOARD - " + message);
    }
}

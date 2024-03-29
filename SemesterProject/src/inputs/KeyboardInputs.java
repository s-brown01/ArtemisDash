/**
 * Keyboard Inputs
 * @author johnbotonakis
 * This class will convert the key events from the user to actions on the screen, 
 * by passing in each event to the proper game state.
 */
package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;
import states.GameStates;

public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameStates.state) {
        // deal with logic in the individual classes
        case MENU:
            gamePanel.getGame().getMenu().keyPressed(e);
            break;
        case OVERWORLD:
            gamePanel.getGame().getOverworld().keyPressed(e);
            break;
        case PLAYING:
            gamePanel.getGame().getPlaying().keyPressed(e);
            break;
        default:
            break;
        }

    }

    /**
     * Calls actions specified by each game state, as each state handles similar button inputs
     * differently
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameStates.state) {
        // this allows us to deal with case-switch logic per class instead of all at once
        case MENU:
            gamePanel.getGame().getMenu().keyReleased(e);
            break;
        case OVERWORLD:
            gamePanel.getGame().getOverworld().keyReleased(e);
            break;
        case PLAYING:
            gamePanel.getGame().getPlaying().keyReleased(e);
            break;
        default:
            break;

        }

    }

}

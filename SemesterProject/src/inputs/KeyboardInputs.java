
package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;
import states.GameStates;
import static utils.Constants.Directions.*;

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
     * Calls actions specified by each game state, as each state
     * handles similar button inputs differently
     */
    @Override
    public void keyReleased(KeyEvent e) {
        /*
         * case switch inspired by Kaarin Gaming on youtube and his free platformer tutorial
         */
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

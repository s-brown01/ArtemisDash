package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.GamePanel;
import states.GameStates;

/**
 * This class will convert the mouse events from the user to actions on the screen, by
 * passing in each event to the proper game state.
 * 
 * @author John Botonakis and Sean-Paul Brown
 */
public class MouseInputs implements MouseListener, MouseMotionListener {
    /**
     * The game panel that all of the inputs will be coming through
     */
    private GamePanel gamePanel;

    /**
     * This is the constructor for a MouseInputs class
     * 
     * @param gamePanel - the gamePanel that will be receiving the mouse inputs
     */
    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Calls actions specified by each game state, as each state handles similar mouse drag
     * inputs differently
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameStates.state) {
        case MENU:
            gamePanel.getGame().getMenu().mouseDragged(e);
            break;
        case INSTRUCTIONS:
            gamePanel.getGame().getInstructions().mouseDragged(e);
            break;
        case OVERWORLD:
            gamePanel.getGame().getOverworld().mouseDragged(e);
            break;
        case PLAYING:
            gamePanel.getGame().getPlaying().mouseDragged(e);
            break;
        default:
            break;

        }
    }

    /**
     * Calls actions specified by each game state, as each state handles similar mouse moved
     * inputs differently
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameStates.state) {
        case MENU:
            gamePanel.getGame().getMenu().mouseMoved(e);
            break;
        case INSTRUCTIONS:
            gamePanel.getGame().getInstructions().mouseMoved(e);
            break;
        case OVERWORLD:
            gamePanel.getGame().getOverworld().mouseMoved(e);
            break;
        case PLAYING:
            gamePanel.getGame().getPlaying().mouseMoved(e);
            break;
        default:
            break;

        }
    }

    /**
     * Calls actions specified by each game state, as each state handles similar mouse clicked
     * inputs differently
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameStates.state) {
        case MENU:
            gamePanel.getGame().getMenu().mouseClicked(e);
            break;
        case INSTRUCTIONS:
            gamePanel.getGame().getInstructions().mouseClicked(e);
            break;
        case OVERWORLD:
            gamePanel.getGame().getOverworld().mouseClicked(e);
            break;
        case PLAYING:
            gamePanel.getGame().getPlaying().mouseClicked(e);
            break;
        default:
            break;
        }
    }

    /**
     * Calls actions specified by each game state, as each state handles similar mouse pressed
     * inputs differently
     */
    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameStates.state) {
        case MENU:
            gamePanel.getGame().getMenu().mousePressed(e);
            break;
        case INSTRUCTIONS:
            gamePanel.getGame().getInstructions().mousePressed(e);
            break;
        case OVERWORLD:
            gamePanel.getGame().getOverworld().mousePressed(e);
            break;
        case PLAYING:
            gamePanel.getGame().getPlaying().mousePressed(e);
            break;
        default:
            break;

        }
    }

    /**
     * Calls actions specified by each game state, as each state handles similar mouse
     * released inputs differently
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameStates.state) {
        case MENU:
            gamePanel.getGame().getMenu().mouseReleased(e);
            break;
        case INSTRUCTIONS:
            gamePanel.getGame().getInstructions().mouseReleased(e);
            break;
        case OVERWORLD:
            gamePanel.getGame().getOverworld().mouseReleased(e);
            break;
        case PLAYING:
            gamePanel.getGame().getPlaying().mouseReleased(e);
            break;
        default:
            break;

        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // method not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // method not used
    }

}

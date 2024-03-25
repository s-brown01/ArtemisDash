/**
 * Mouse Inputs
 * @author johnbotonakis
 * This class will convert the mouse events from the user to actions on the screen, 
 * by passing in each event to the proper game state.
 */
package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.GamePanel;
import states.GameStates;

public class MouseInputs implements MouseListener, MouseMotionListener {
    private GamePanel gamePanel;

    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameStates.state) {
        case MENU:
            gamePanel.getGame().getMenu().mouseDragged(e);
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

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameStates.state) {
        case MENU:
            gamePanel.getGame().getMenu().mouseMoved(e);
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

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (GameStates.state) {
        case MENU:
            gamePanel.getGame().getMenu().mouseClicked(e);
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

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameStates.state) {
        case MENU:
            gamePanel.getGame().getMenu().mousePressed(e);
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

    @Override
    public void mouseReleased(MouseEvent e) {
        // to be filled in
        switch (GameStates.state) {
        case MENU:
            gamePanel.getGame().getMenu().mouseReleased(e);
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
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}

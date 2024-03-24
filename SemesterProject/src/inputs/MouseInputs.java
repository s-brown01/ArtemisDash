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
        // to be filled in
        switch (GameStates.state) {
        case MENU:
            break;
        case OVERWORLD:
            break;
        case PLAYING:
            break;
        default:
            break;

        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // to be filled in
        switch (GameStates.state) {
        case MENU:
            gamePanel.getGame().getMenu().mouseMoved(e);
            break;
        case OVERWORLD:
            break;
        case PLAYING:
            break;
        default:
            break;

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // to be filled in
        switch (GameStates.state) {
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
        // to be filled in
        switch (GameStates.state) {
        case MENU:
            gamePanel.getGame().getMenu().mousePressed(e);
            break;
        case PLAYING:
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
        case PLAYING:
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

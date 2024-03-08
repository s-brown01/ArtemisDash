
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
    	case MENU:
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
        // to be filled in
        switch (GameStates.state) {
        case MENU:
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

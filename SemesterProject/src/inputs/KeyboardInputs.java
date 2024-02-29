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
		gamePanel.getGame().getPlaying().KeyPressed(e);

		// to be filled in
		switch(GameStates.state) {
		case MENU:
			break;
		case PLAYING:
			// TO BE CHANGED LATER
			// for now deal with it all here
			switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				gamePanel.getGame().getPlaying().KeyReleased(e);
				gamePanel.changeYDelta(-5);
				break;
			case KeyEvent.VK_A:
				gamePanel.changeXDelta(-5);
				 break;
			case KeyEvent.VK_S:
				gamePanel.changeYDelta(5);
				 break;
			case KeyEvent.VK_D:
				gamePanel.changeXDelta(5);
				break;
			}
			break;
		default:
			break;
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// this allows us to deal with case-switch logic per class instead of all at once
		gamePanel.getGame().getPlaying().KeyReleased(e);
		/* 
		 * case switch inspired by Kaarin Gaming on youtube and his free platformer tutorial
		 */
		switch(GameStates.state) {
		case MENU:
			break;
		case PLAYING:
			break;
		default:
			break;
			
		}
		
		
	}

}

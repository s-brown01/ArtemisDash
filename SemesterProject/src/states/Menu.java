package states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;

public class Menu extends State implements StateMethods {
	
	private MenuButton[] buttons;
	private BufferedImage backgroundImg;

	public Menu(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		loadImgs();
		loadButtons();
	}
	
	private void loadImgs() {
		// this will be where we load any images for the menu, like the background or menu screen
		backgroundImg = null;
	}
	
	private void loadButtons() {
		// this is where we create and load MenuButtons
		// THIS IS JUST EXAMPLE CODE
		// CHANGE THIS
		final int amount_of_buttons = 1;
		buttons = new MenuButton[amount_of_buttons];
		buttons[0] = new MenuButton();
	}
	
	private void resetButtons() {
		for (MenuButton mb : buttons) {
			// mb.reset or mb.resetBools? something simialr to this
		}
	}

	@Override
	public void update() {
		// update buttons and any other graphics in the menu
		for (MenuButton mb : buttons) {
			// mb.update()
		}
	}

	@Override
	public void draw(Graphics g) {
		// draw background/buttons/menu
		// g.draw(backgroundImg, 0, 0, gamewidth, gameheight);
		for (MenuButton mb : buttons) {
			// mb.draw(g);
		}	}

	@Override
	public void MouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void MouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void MousePressed(MouseEvent e) {
		// see where the mouse is and if it an in any menu button
		// THIS IS JUST EXAMPLE CODE
		// PLEASE CHANGE
		if (!isInMB(e, buttons[0])) 
			System.out.println("TEST");
	}

	@Override
	// activated when we release the mouse from clicking
	public void MouseReleased(MouseEvent e) {
		// following code from youTube tutorial
		// if it is within a button and we have clicked on that button then use the button
//		for (MenuButton mb : buttons) {
//            if (isInMB(e, mb)) {
//                if (mb.isMousePressed())
//                    mb.applyGamestate();
//                break;
//            }
//        }
		resetButtons();		
	}

	@Override
	public void MouseMoved(MouseEvent e) {
		// when we move the mouse reset mouseOver
		for (MenuButton mb : buttons) {
			// tell the mb button mouse isn't on it
			// this prevents click, drag off button, and release counting as a click on the button
		}
		// now check if the mouse is on any button
		for (MenuButton mb : buttons) {
			if (isInMB(e, mb)) {
				// set mb mouseOver to true
			}
		}
	}

	@Override
	public void KeyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		// decide what to do with keyboard inputs here
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			GameStates.state = GameStates.PLAYING;
	}

	@Override
	public void KeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		// what to do if the key is released
	}

}

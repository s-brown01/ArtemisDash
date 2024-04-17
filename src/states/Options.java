package states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.OptionsButtons;
import utils.LoadSave;

/**
 * Options State class gives functionality to the Options button on the Main Menu
 * 
 * @author John Botonakis
 */
public class Options extends State implements StateMethods {

    private OptionsButtons button;
    private final BufferedImage backgroundImg;

    public Options(Game game) {
        super(game);
        backgroundImg = LoadSave.getSpriteSheet(LoadSave.OPTIONS_SCREEN);
        loadButtons();
    }

    /**
     * Given the input of the user, this function helps update the button appearance
     */
    @Override
    public void update() {
        button.update();
    }

    /**
     * Draw the menu buttons onto the screen
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, backgroundImg.getWidth(), backgroundImg.getHeight(), null);
            button.draw(g);
    }

    /**
     * Loads in the buttons into the "buttons" array to be passed off to render. Links the
     * game state to the button function
     */
    private void loadButtons() {
        button = new OptionsButtons(Game.GAME_WIDTH / 6, (int) (240 * Game.SCALE), 0, GameStates.MENU);
    }

    /**
     * Handles resetting button behavior back to originally defined parameters
     */
    private void resetButtons() {
            button.resetButtons();
    }

    /**
     * Sets behavior of buttons when Mouse 1 is pressed
     */
    @Override
    public void mousePressed(MouseEvent e) {
            if (hoverOverButton(e, button)) {
                button.setMousePressed(true);
            }
        }

    /**
     * Sets behavior of buttons when Mouse 1 is released
     */
    @Override
    public void mouseReleased(MouseEvent e) {
            if (hoverOverButton(e, button)) {
                if (button.isMousePressed()) {
                    button.applyGamestate();
                }
            }
            button.resetButtons();
        }

    @Override
    public void mouseMoved(MouseEvent e) {
            if (hoverOverButton(e, button)) {
                button.setMouseOver(true);
            } else {
                button.setMouseOver(false);
            }
        }

    /**
     * Goes unused as there is no functionality for mouse dragging in the Options Screen
     */
    @Override
    public void mouseDragged(MouseEvent e) {
    }

    /**
     * Goes unused as the "click" encompasses both the press and release of the button, which
     * we have separate functions for each.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    /**
     * Goes unused as there is no functionality for Key events in the Options Screen
     */
    @Override
    public void keyPressed(KeyEvent e) {
    }
    /**
     * Goes unused as there is no functionality for Key events in the Options Screen
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

}

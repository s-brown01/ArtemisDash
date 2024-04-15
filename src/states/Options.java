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

    private OptionsButtons[] buttons = new OptionsButtons[2];
    private final BufferedImage backgroundImg;

    public Options(Game game) {
        super(game);
        backgroundImg = LoadSave.getSpriteSheet(LoadSave.MENU_SCREEN);
        loadButtons();
    }

    /**
     * Given the input of the user, this function helps update the button appearance
     */
    @Override
    public void update() {
        for (OptionsButtons ob : buttons) {
            ob.update();
        }
    }

    /**
     * Draw the menu buttons onto the screen
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, backgroundImg.getWidth(), backgroundImg.getHeight(), null);
        for (OptionsButtons ob : buttons) {
            ob.draw(g);
        }
    }

    /**
     * Loads in the buttons into the "buttons" array to be passed off to render. Links the
     * game state to the button function
     */
    private void loadButtons() {
        buttons[0] = new OptionsButtons(Game.GAME_WIDTH / 6, (int) (200 * Game.SCALE), 0, GameStates.OVERWORLD);
        buttons[1] = new OptionsButtons(Game.GAME_WIDTH / 6, (int) (270 * Game.SCALE), 1, GameStates.OPTIONS);
    }

    /**
     * Handles resetting button behavior back to originally defined parameters
     */
    private void resetButtons() {
        for (OptionsButtons ob : buttons) {
            ob.resetButtons();
        }
    }

    /**
     * Goes unused as there is no functionality for mouse dragging in the Menu
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
     * Sets behavior of buttons when Mouse 1 is pressed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        for (OptionsButtons ob : buttons) {
            if (hoverOverButton(e, ob)) {
                ob.setMousePressed(true);
                break;
            }
        }
    }

    /**
     * Sets behavior of buttons when Mouse 1 is released
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        for (OptionsButtons ob : buttons) {
            if (hoverOverButton(e, ob)) {
                if (ob.isMousePressed()) {
                    ob.applyGamestate();
                    break;
                }
            }
        }
        resetButtons();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (OptionsButtons ob : buttons) {
            if (hoverOverButton(e, ob)) {
                ob.setMouseOver(true);
                break;
            } else {

                ob.setMouseOver(false);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}

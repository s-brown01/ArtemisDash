package states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
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
        backgroundImg = LoadSave.getSpriteSheet(LoadSave.OPTIONS_SCREEN);
        loadButtons();
    }

    /**
     * Given the input of the user, this function helps update the button appearance
     */
    @Override
    public void update() {
        for (OptionsButtons mb : buttons) {
            mb.update();
        }
    }

    /**
     * Draw the menu buttons onto the screen
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, backgroundImg.getWidth(), backgroundImg.getHeight(), null);
        for (OptionsButtons mb : buttons) {
            mb.draw(g);
        }
    }

    /**
     * Loads in the buttons into the "buttons" array to be passed off to render. Links the
     * game state to the button function
     */
    private void loadButtons() {
        buttons[0] = new OptionsButtons(Game.GAME_WIDTH / 6, (int) (200 * Game.SCALE), 0, GameStates.MENU);
        buttons[1] = new OptionsButtons(Game.GAME_WIDTH / 6, (int) (270 * Game.SCALE), 1, GameStates.OPTIONS);
//        button = new OptionsButtons(Game.GAME_WIDTH / 9, (int) (370 * Game.SCALE), 0, GameStates.MENU);
    }

    /**
     * Handles resetting button behavior back to originally defined parameters
     */
    private void resetButtons() {
        for (OptionsButtons mb : buttons) {
            mb.resetButtons();
        }    }

    /**
     * Sets behavior of buttons when Mouse 1 is pressed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        for (OptionsButtons mb : buttons) {
            if (hoverOverButton(e, mb)) {
                mb.setMousePressed(true);
                game.getAudioPlayer().playEffect(4);
                break;
            }
        }
        }

    /**
     * Sets behavior of buttons when Mouse 1 is released
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        for (OptionsButtons mb : buttons) {
            if (hoverOverButton(e, mb)) {
                if (mb.isMousePressed()) {
                    mb.applyGamestate();
                    break;
                }
            }
        }
        resetButtons();
        }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (OptionsButtons mb : buttons) {
            if (hoverOverButton(e, mb)) {
                mb.setMouseOver(true);
            } else {
                mb.setMouseOver(false);
            }
            
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

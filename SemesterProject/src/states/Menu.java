package states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import utils.LoadSave;

/**
 * Menu Class
 * 
 * @author johnbotonakis and Sean-Paul Brown
 * @description This is a child class of State that implements the StateMethods interface.
 *              This represents the Main Menu of the game, it should be the first screen
 *              that the player sees and leads them to the Overworld.
 * 
 */
public class Menu extends State implements StateMethods {

    private MenuButton[] buttons = new MenuButton[3];
    private final BufferedImage backgroundImg;

    public Menu(Game game) {
        super(game);
        backgroundImg = LoadSave.getSpriteSheet(LoadSave.MENU_SCREEN);
        loadButtons();
    }

    /**
     * Given the input of the user, this function helps update the button appearance
     */
    @Override
    public void update() {
        for (MenuButton mb : buttons) {
            mb.update();
        }
    }

    /**
     * Draw the menu buttons onto the screen
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, backgroundImg.getWidth(), backgroundImg.getHeight(), null);
        for (MenuButton mb : buttons) {
            mb.draw(g);
        }
    }

    /**
     * Loads in the buttons into the "buttons" array to be passed off to render. Links the
     * game state to the button function
     */
    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 6, (int) (200 * Game.SCALE), 0, GameStates.OVERWORLD);
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 6, (int) (270 * Game.SCALE), 1, GameStates.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 6, (int) (340 * Game.SCALE), 2, GameStates.QUIT);
    }

    /**
     * Handles resetting button behavior back to originally defined parameters
     */
    private void resetButtons() {
        for (MenuButton mb : buttons) {
            mb.resetButtons();
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
     * Sets behavior of mouse buttons when Mouse 1 is pressed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (hoverOverButton(e, mb)) {
                mb.setMousePressed(true);
                break;
            }
        }
    }

    /**
     * Sets behavior of mouse buttons when Mouse 1 is released
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons) {
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
        for (MenuButton mb : buttons) {
            if (hoverOverButton(e, mb)) {
                mb.setMouseOver(true);
                break;
            } else {

                mb.setMouseOver(false);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        // what to do if the key is released
    }

}

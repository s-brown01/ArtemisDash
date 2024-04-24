package states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import utils.LoadSave;

/**
 * This is a child class of State that implements the StateMethods interface. This
 * represents the Main Menu of the game, it should be the first screen that the player
 * sees and leads them to the Overworld.
 * 
 * @author John Botonakis and Sean-Paul Brown
 */
public class Menu extends State implements StateMethods {

    /**
     * All of the buttons that will displayed on screen
     */
    private MenuButton[] buttons = new MenuButton[3];
    /**
     * The background image that will be displayed with everything else on top
     */
    private final BufferedImage backgroundImg;

    /**
     * The constructor for a main menu
     * 
     * @param game - the game that will hold this Main Menu
     */
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
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 6, (int) (200 * Game.SCALE), MenuButton.PLAY_INDEX, GameStates.OVERWORLD);
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 6, (int) (270 * Game.SCALE), MenuButton.INSTRUCTIONS_INDEX, GameStates.INSTRUCTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 6, (int) (340 * Game.SCALE), MenuButton.QUIT_INDEX, GameStates.QUIT);
    }

    /**
     * Handles resetting button behavior back to originally defined parameters
     */
    private void resetButtons() {
        for (MenuButton mb : buttons) {
            mb.resetButton();
        }
    }

    /**
     * Sets behavior of mouse buttons when Mouse 1 is pressed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isInMenuButton(e, mb)) {
                mb.setMousePressed(true);
                game.getAudioPlayer().playEffect(4);
                break;
            }
        }
    }

    /**
     * Sets the behavior of buttons when Mouse 1 is released
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isInMenuButton(e, mb)) {
                if (mb.isMousePressed()) {
                    mb.applyGamestate();
                    break;
                }
            }
        }
        resetButtons();
    }

    /**
     * Sets the behavior of buttons when Mouse 1 is released
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isInMenuButton(e, mb)) {
                mb.setMouseOver(true);
            } else {
                mb.setMouseOver(false);
            }

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
     * GOES UNUSED but needed to implement StateMethods Properly
     */
    @Override
    public void keyPressed(KeyEvent e) {

    }

    /**
     * GOES UNUSED but needed to implement StateMethods Properly
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

}

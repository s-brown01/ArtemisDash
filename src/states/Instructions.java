package states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import utils.LoadSave;

/**
 * Instructions State class gives functionality to the Instructions button on the Main
 * Menu
 * 
 * @author John Botonakis and Sean-Paul Brown
 */
public class Instructions extends State implements StateMethods {
    /**
     * The background image for this menu
     */
    private final BufferedImage backgroundImg;
    /**
     * This is the button that will link back to the main menu
     */
    private MenuButton menu = new MenuButton(Game.GAME_WIDTH / 4, Game.GAME_HEIGHT / 6 * 5, MenuButton.MENU_INDEX,
            GameStates.MENU);

    /**
     * This is the initializer, it will load in the background image and create the
     * InstructionsButtons
     * 
     * @param game - the Game that created and is managing this Class
     */
    public Instructions(Game game) {
        super(game);
        backgroundImg = LoadSave.getSpriteSheet(LoadSave.INSTRUCTIONS_SCREEN);
    }

    /**
     * Updates the buttons based on the users actions. Things such as hover and mouse click
     * affect the button sprite state
     */
    public void update() {
        menu.update();
    }

    /**
     * Draws everything that is intended to be visible, to the screen
     * 
     * @param g - Graphics
     */
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, backgroundImg.getWidth(), backgroundImg.getHeight(), null);
        menu.draw(g);
    }

    /**
     * Handles resetting button behavior back to originally defined parameters
     */
    private void resetButtons() {
        menu.resetButton();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // method not used
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isInMenuButton(e, menu)) {
            menu.setMousePressed(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // method not used, using pressed and released insteads
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isInMenuButton(e, menu)) {
            if (menu.isMousePressed()) {
                GameStates.state = GameStates.MENU;
            }
        }

        resetButtons();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menu.setMouseOver(false);

        if (isInMenuButton(e, menu)) {
            menu.setMouseOver(true);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // not using key inputs here
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // not using key inputs here
    }

}
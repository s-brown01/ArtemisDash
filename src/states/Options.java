package states;

import static main.Game.SCALE;
import static utils.Constants.ButtonStates.SOUNDSIZE;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.OptionsButton;
import ui.SoundButton;
import utils.LoadSave;

/**
 * Options State class gives functionality to the Options button on the Main Menu
 * 
 * @author John Botonakis
 */
public class Options {

    private final BufferedImage backgroundImg;
    private OptionsButton[] buttons = new OptionsButton[2];
    private Game game;

    public Options(Game game) {
        this.game = game;
        backgroundImg = LoadSave.getSpriteSheet(LoadSave.OPTIONS_SCREEN);
        createButtons();
    }
    
    /**
     * Updates the buttons based on the users actions. Things such as hover and mouse click
     * affect the button sprite state
     */
    public void update() {
        for(OptionsButton ob : buttons) {
            ob.update();
        }
    }
    
    /**
     * Draws everything that is intended to be visible, to the screen
     * 
     * @param g - Graphics
     */
    public void draw(Graphics g) {
        // Buttons
        for(OptionsButton ob : buttons) {
            ob.draw(g);
        }
    }
    
    /**
     * Creates the buttons for use only on the pause menu Scale, X position, Y position and
     * offsets are set here
     */
    public void createButtons() {
        // Had to touch up the overall size so decreased it by 5 in its height
        buttons[0] = new OptionsButton(Game.GAME_WIDTH / 6, (int) (370 * Game.SCALE), 0, GameStates.MENU);
        buttons[1] = new OptionsButton(Game.GAME_WIDTH / 6, (int) (200 * Game.SCALE), 0, GameStates.OVERWORLD);
    }


}
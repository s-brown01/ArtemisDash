package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.InstructionsButton;
import utils.LoadSave;

/**
 * Instructions State class gives functionality to the Instructions button on the Main Menu
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
    private InstructionsButton menu = new InstructionsButton("MENU", Game.GAME_WIDTH/5, Game.GAME_HEIGHT/6*5, GameStates.MENU);
    /**
     * This is the button that will link to the overworld
     */
    private InstructionsButton play = new InstructionsButton("PLAY", Game.GAME_WIDTH/5 * 4, Game.GAME_HEIGHT/6*5, GameStates.OVERWORLD);
    
    /**
     * This is the initializer, it will load in the background image and create the InstructionsButtons
     * 
     * @param game - the Game that created and is managing this Class
     */
    public Instructions(Game game) {
        super(game);
        backgroundImg = LoadSave.getSpriteSheet(LoadSave.MENU_SCREEN);
    }

    /**
     * Updates the buttons based on the users actions. Things such as hover and mouse click
     * affect the button sprite state
     */
    public void update() {
        menu.update();
        play.update();
    }

    /**
     * Draws everything that is intended to be visible, to the screen
     * 
     * @param g - Graphics
     */
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, backgroundImg.getWidth(), backgroundImg.getHeight(), null);
        
        final int key_X = 425;
        final int init_Y = 300;
        final int spacing_Y = 60;
        g.setFont(LoadSave.loadFont(LoadSave.FONT, 25));
        g.setColor(Color.WHITE);
        g.drawString("A - Left      D - right", key_X, init_Y + spacing_Y);
        g.drawString("Space - Jump      Shift - Dash", key_X, init_Y + 2 * spacing_Y);
        g.drawString("Press Mouse - Show Arrow Path", key_X, init_Y + 3 * spacing_Y);
        g.drawString("Release Mouse - Shoot", key_X, init_Y + 4 * spacing_Y);
        g.drawString("P - Pause", key_X, init_Y + 5 * spacing_Y);
        
        // A - left
        // D - right
        // space - jump
        // shift - dash
        // press mouse - show arrow path
        // release mouse - shoot
        // p - pause
        menu.draw(g);
        play.draw(g);
    }

    /**
     * Handles resetting button behavior back to originally defined parameters
     */
    private void resetButtons() {
        menu.resetButton();
        play.resetButton();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // method not used
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isInInstructionsButton(e, menu)) {
            menu.setMousePressed(true);
        } else if (isInInstructionsButton(e, play)) {
            play.setMousePressed(true);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // method not used, using pressed and released insteads
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isInInstructionsButton(e, menu)) {
            if (menu.isMousePressed()) {
                GameStates.state = GameStates.MENU;
            }
        } else if (isInInstructionsButton(e, play)) {
            if (play.isMousePressed()) {
                GameStates.state = GameStates.OVERWORLD;
            }
        }
        
        resetButtons();       
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menu.setMouseOver(false);
        play.setMouseOver(false);

        if (isInInstructionsButton(e, menu)) {
            menu.setMouseOver(true);
        } else if (isInInstructionsButton(e, play)) {
            play.setMouseOver(true);
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
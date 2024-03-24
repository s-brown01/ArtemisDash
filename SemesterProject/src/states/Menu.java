/**
 * Menu Class
 * @author johnbotonakis
 * 
 */

package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;

public class Menu extends State implements StateMethods {

    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundImg;

    public Menu(Game game) {
        super(game);
        loadImgs();
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
        for (MenuButton mb : buttons) {
            mb.draw(g);
        }
    }

    /**
     * Handles loading in of images; In this case, a menu background
     */
    private void loadImgs() {
        backgroundImg = null;
    }

    /**
     * Loads in the buttons into the "buttons" array to be passed off to render.
     * Links the game state to the button function
     */
    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH/2, (int)(150 * Game.SCALE), 0, GameStates.OVERWORLD);
        buttons[1] = new MenuButton(Game.GAME_WIDTH/2, (int)(220 * Game.SCALE), 1, GameStates.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH/2, (int)(290 * Game.SCALE), 2, GameStates.QUIT);
//        buttons[3] = new MenuButton(Game.GAME_WIDTH/2, (int)(150 * Game.SCALE), 0, GameStates.OVERWORLD);
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
     * Goes unused as the "click" encompasses both the press and release of the button, 
     * which we have separate functions for each.
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
            if(hoverOverButton(e,mb)){
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
            }else {
                
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

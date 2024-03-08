
package states;

import java.awt.Color;
import java.awt.Font;
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
        loadImgs();
        loadButtons();
    }
    /**
     * Handles loading in of images; In this case, a menu background
     */
    private void loadImgs() {
        backgroundImg = null;
    }

    /**
     * Will be used to create and load in buttons for the 
     * Menu options
     */
    private void loadButtons() {
         /* THIS IS JUST EXAMPLE CODE
         * CHANGE THIS EVENTUALLY
         */
        final int amount_of_buttons = 1;
        buttons = new MenuButton[amount_of_buttons];
        buttons[0] = new MenuButton();
    }

    /**
     * Will be used to reset and monitor states of buttons  
     * for the Menu options
     */
    private void resetButtons() {
        for (MenuButton mb : buttons) {
            // mb.reset or mb.resetBools? something similar to this
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
        g.setColor(new Color(100, 100, 100, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        g.setColor(new Color(255, 255, 255));
        g.setFont(boldFont);
        g.drawString("MENU", Game.GAME_WIDTH / 2 - 10, Game.GAME_HEIGHT / 2 - 10);
        g.drawString("Press ENTER to continue", Game.GAME_WIDTH / 2 - 65,
                            Game.GAME_HEIGHT / 2 + 20);

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // see where the mouse is and if it an in any menu button
        // THIS IS JUST EXAMPLE CODE
        // PLEASE CHANGE
        if (!isInMB(e, buttons[0]))
            System.out.println("TEST");
    }

    @Override
    // activated when we release the mouse from clicking
    public void mouseReleased(MouseEvent e) {
        // following code from youTube tutorial
        // if it is within a button and we have clicked on that button then use the button
        // for (MenuButton mb : buttons) {
        // if (isInMB(e, mb)) {
        // if (mb.isMousePressed())
        // mb.applyGamestate();
        // break;
        // }
        // }
        resetButtons();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // when we move the mouse reset mouseOver
        for (MenuButton mb : buttons) {
            // tell the mb button mouse isn't on it
            // this prevents click, drag off button, and release counting as a click on the
            // button
        }
        // now check if the mouse is on any button
        for (MenuButton mb : buttons) {
            if (isInMB(e, mb)) {
                // set mb mouseOver to true
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        // decide what to do with keyboard inputs here
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println("SWITCH FROM MENU TO OVERWORLD");
            GameStates.state = GameStates.OVERWORLD;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        // what to do if the key is released
    }

}

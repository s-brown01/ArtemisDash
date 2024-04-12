package states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Defines every method that a state should have when instantiated new
 * 
 * @author John Botonakis and Sean-Paul Brown
 */
public interface StateMethods {

    /**
     * Allows updates for children of caller object, mainly for animations but can be used for
     * more complex things.
     */
    public void update();

    /**
     * Displays the caller object to the screen
     * 
     * @param g - the Graphics where to draw the state
     */
    public void draw(Graphics g);

    /**
     * When the mouse is clicked and moved this function should be called. 
     * @param e - what the mouse is doing
     */
    public void mouseDragged(MouseEvent e);

    /**
     * When the mouse is pressed down this function should be called. 
     * @param e - what the mouse is doing
     */
    public void mousePressed(MouseEvent e);

    /**
     * When the mouse is pressed down and released this function should be called. 
     * @param e - what the mouse is doing
     */
    public void mouseClicked(MouseEvent e);

    /**
     * When the mouse is released this function should be called. 
     * @param e - what the mouse is doing
     */
    public void mouseReleased(MouseEvent e);

    /**
     * When the mouse is moved (not clicked) this function should be called. 
     * @param e - what the mouse is doing
     */
    public void mouseMoved(MouseEvent e);

    /**
     * When the key is pressed down this function should be called. 
     * @param e - information about the key that was pressed
     */
    public void keyPressed(KeyEvent e);
    
    /**
     * When the key is released this function should be called. 
     * @param e - information about the key that was pressed
     */
    public void keyReleased(KeyEvent e);

}

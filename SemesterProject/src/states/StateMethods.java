/**
 * StateMethods Interface
 * @author johnbotonakis
 * Defines every method that a state should have when instantiated new
 */
package states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface StateMethods {

    /**
     * Allows updates for children of caller object, mainly for animations but can be used for more complex things.
     */
    public void update();

    /**
     * Displays the caller object to the screen
     * @param g
     */
    public void draw(Graphics g);

    public void mouseDragged(MouseEvent e);

    public void mousePressed(MouseEvent e);

    public void mouseClicked(MouseEvent e);

    public void mouseReleased(MouseEvent e);

    public void mouseMoved(MouseEvent e);

    public void keyPressed(KeyEvent e);

    public void keyReleased(KeyEvent e);

}

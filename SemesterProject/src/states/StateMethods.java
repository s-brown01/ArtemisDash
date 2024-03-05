package states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface StateMethods {
	
    // every State should update and draw itself    
	public void update();
	public void draw(Graphics g);
	
	// handling mouse events
	public void mouseDragged(MouseEvent e);
	public void mousePressed(MouseEvent e);
	public void mouseClicked(MouseEvent e);
	public void mouseReleased(MouseEvent e);
	public void mouseMoved(MouseEvent e);
	
	// handling keyboard events and inputs
	public void keyPressed(KeyEvent e);
	public void keyReleased(KeyEvent e);
	
	
	

}

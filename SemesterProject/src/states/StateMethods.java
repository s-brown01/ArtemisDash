package states;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface StateMethods {
	
    // every State should update and draw itself    
	public void update();
	public void draw(Graphics g);
	
	// handling mouse events
	public void MouseDragged(MouseEvent e);
	public void MousePressed(MouseEvent e);
	public void MouseClicked(MouseEvent e);
	public void MouseReleased(MouseEvent e);
	public void MouseMoved(MouseEvent e);
	
	// handling keyboard events and inputs
	public void KeyPressed(KeyEvent e);
	public void KeyReleased(KeyEvent e);
	
	
	

}

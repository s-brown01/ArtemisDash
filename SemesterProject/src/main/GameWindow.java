
package main;

import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow {

    private JFrame gameFrame;

    // Game Window Constructor
    public GameWindow(GamePanel gamePanel) {
	gameFrame = new JFrame();

	gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	gameFrame.add(gamePanel);
	gameFrame.pack();
	gameFrame.setResizable(false);
	gameFrame.setLocationRelativeTo(null);
	gameFrame.setVisible(true); // Has to be at bottom

	// if the user clicks onto/away from screen
	gameFrame.addWindowFocusListener(new WindowFocusListener() {
	    // onto screen
	    @Override
	    public void windowGainedFocus(WindowEvent e) {

	    }

	    // away from screen
	    @Override
	    public void windowLostFocus(WindowEvent e) {
		gamePanel.getGame().windowFocusLost();
	    }

	});
    }
}

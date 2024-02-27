package main;

import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow{
	
	private JFrame gameFrame;
	//Game Window Constructor
	public GameWindow(GamePanel gamePanel) {
		gameFrame = new JFrame();
		
		gameFrame.setSize(400,400);
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.add(gamePanel);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setVisible(true); //Has to be at bottom

		jframe.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                // gamePanel.getGame().windowFocusLost();
            }

        });
	}
}

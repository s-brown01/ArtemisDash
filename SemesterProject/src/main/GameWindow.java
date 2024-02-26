package main;

import javax.swing.JFrame;


public class GameWindow{
	
	private JFrame gameFrame;
	//Game Window Constructor
	public GameWindow(GamePanel gamePanel) {
		gameFrame = new JFrame();
		
		gameFrame.setSize(400,400);
		gameFrame.setDefaultCloseOperation(gameFrame.EXIT_ON_CLOSE);
		gameFrame.add(gamePanel);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setVisible(true); //Has to be at bottom
	}
}

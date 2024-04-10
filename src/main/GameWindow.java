package main;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

/**
 * This class will act as the frame holding the painting
 * 
 * @author johnbotonakis and Sean-Paul Brown
 */
//DOUBLE BUFFERING HERE NOWHERE ELSE!
public class GameWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor - initializes the window with which the gamePanel will sit
     * 
     * @param gamePanel - The current GamePanel to be displayed
     */
    public GameWindow(GamePanel gamePanel) {
        super();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// When exiting, close program
        this.add(gamePanel);// Add in the game Panel
        this.pack(); // Fit around the gamePanel
        this.setResizable(false); // Do not resize
        this.setLocationRelativeTo(null); // Center the frame
        this.setVisible(true);

        this.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
            }

            // If window loses focus, stop ALL player inputs
            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowLost();

            }

        });
    }

}

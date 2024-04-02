package main;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

/**
 * Game Window Class
 * @author johnbotonakis and Sean-Paul Brown
 * This class will act as the frame holding the painting
 */
public class GameWindow extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem m1, m2, m3;

    /**
     * Initializes the window with which the gamePanel will sit
     * 
     * @param gamePanel - The current game Panel to be displayed
     */
    public GameWindow(GamePanel gamePanel) {
        super();

        // this is how we add a menuBar to our game
        // so far no Item as any action, clicking them does nothing
        // easy to implement/remove
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        m1 = new JMenuItem("Pause");
        m2 = new JMenuItem("End Level");
        m3 = new JMenuItem("Quit Game");

        menu.add(m1);
        menu.add(m2);
        menu.add(m3);
        menuBar.add(menu);

        this.setJMenuBar(menuBar);

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

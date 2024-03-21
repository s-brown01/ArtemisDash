/**
 * Game Window Class
 * @author johnbotonakis
 * This class will act as the frame holding the painting
 */
package main;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameWindow {

    private JFrame gameFrame;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem m1, m2, m3;

    // Game Window Constructor
    public GameWindow(GamePanel gamePanel) {
        gameFrame = new JFrame();

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

        gameFrame.setJMenuBar(menuBar);

        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// When exiting, close program
        gameFrame.add(gamePanel);// Add in the game Panel
        gameFrame.pack(); // Fit around the gamePanel
        gameFrame.setResizable(false); // Do not resize
        gameFrame.setLocationRelativeTo(null); // Center the frame
        gameFrame.setVisible(true);
        gameFrame.addWindowFocusListener(new WindowFocusListener() {
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

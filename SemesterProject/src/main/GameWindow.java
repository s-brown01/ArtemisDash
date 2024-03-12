
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

        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.add(gamePanel);
        gameFrame.pack();
        gameFrame.setResizable(false);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true); // Has to be at bottom

        // if the user clicks onto/away from screen
        gameFrame.addWindowFocusListener(new WindowFocusListener() {

            @Override
            public void windowGainedFocus(WindowEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }

        });
    }
}

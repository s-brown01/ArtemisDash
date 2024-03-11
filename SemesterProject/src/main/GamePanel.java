
package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {

    private Game game;

    private MouseInputs mouseInputs;
    private String image = "Artemis_Finished.png";
    private BufferedImage img;

    public GamePanel(Game game) {
        // adding a Game to the constructor allows us to access the GameState from the
        // gamePanel
        /*
         * How to create a custom cursor, perhaps for a recticle on mouse to show shots? Toolkit
         * toolkit = Toolkit.getDefaultToolkit(); Image image =
         * toolkit.getImage("icons/handwriting.gif"); Cursor c = toolkit.createCustomCursor(image
         * , new Point(mainPane.getX(), mainPane.getY()), "img"); mainPane.setCursor (c); how to
         * restore to default cursor (good for the menu?): setCursor(Cursor.getDefaultCursor());
         */

        this.game = game;
        mouseInputs = new MouseInputs(this);// Forwards all mouse listener events to input class
        addKeyListener(new KeyboardInputs(this));// Forwards all key listener events to input class

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    /**
     * Sets the size of Game Window and subsequently, Game Panel
     */
    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
    }
    //TBD
    public void updateGame() {

    }

    /**
     * Allows us to render each frame of our game
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);// Uses JPanel's own paint method
        game.render(g);
    }

    public Game getGame() {
        return game;
    }

}

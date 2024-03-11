
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
    	addMouseListener(mouseInputs);
    	addMouseMotionListener(mouseInputs);
    }

    /**
     * READS IN THE SPRITE SHEET TO BE USED AND CUT UP INTO SMALLER FRAMES
     */
    public void importImg() {
        InputStream is = getClass().getClassLoader().getResourceAsStream(image);
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            System.out.println("NULL");
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                System.out.println("NULL");
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets the size of Game Window and subsequently, Game Panel
     */
    private void setPanelSize() {
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
        System.out.println("CURRENT WIDTH: " + GAME_WIDTH + " CURRENT HEIGHT: " + GAME_HEIGHT);
    }

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

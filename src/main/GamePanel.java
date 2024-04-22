package main;

import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

/**
 * This class acts as the painting in the frame
 * 
 * @author John Botonakis and Sean-Paul Brown
 */
public class GamePanel extends JPanel {
    /**
     * This is to help prevent InvalidClassExceptions when serialized objects are deserialized. Suggested by IDE.
     */
    private static final long serialVersionUID = 1L;
    /**
     * This is the game where all of the mouseInputs should be directed to
     */
    private Game game;

    /**
     * Creates a new panel to which the game will be drawn onto
     * 
     * @param game - The Game instance to which the game will be drawn to
     */
    public GamePanel(Game game) {
        /*
         * This is the MouseInputs handler for the Game. Unlike the keyboard listener, this needs to be created outside of the addMouseListener commands because otherwise it would create 2 different inputs 
         */
        MouseInputs mouseInputs = new MouseInputs(this);
        this.game = game;

        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    /**
     * Sets the panel size to wrap around the frame
     */
    private void setPanelSize() {
        // 1280 width / 55 sprite width = ~27 tiles wide; 800 height / 65 sprite height = ~12
        Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
        setPreferredSize(size);
    }

    /**
     * ONLY Takes care of drawing everything via the JPanel Graphics object Easier to use the
     * built in tools rather than make from scratch
     */
    public void paintComponent(Graphics g) {
        // Calls the super class to clean everything and then draw.
        // This also makes the program use DoubleBuffering to load sprites faster.
        super.paintComponent(g);
        game.render(g);
    }

    /**
     * Returns the current game object
     * 
     * @return - Current Game object
     */
    public Game getGame() {
        return game;
    }
}

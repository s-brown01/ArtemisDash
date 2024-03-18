/**
 * Game Panel Class
 * @author johnbotonakis
 * This class acts as the painting in the frame
 */
package main;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static main.Game.GAME_HEIGHT;
import static main.Game.GAME_WIDTH;

public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private Game game;
    
    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
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
        //1280 width / 55 sprite width = ~27 tiles wide; 800 height / 65 sprite height = ~12
        Dimension size = new Dimension(GAME_WIDTH,GAME_HEIGHT);
        setPreferredSize(size);
    }
    
    /**
     * ONLY Updates game Logic
     */
    public void updateGame() {

    }
    
    /**
     * ONLY Takes care of drawing everything via the JPanel Graphics object
     * Easier to use the built in tools rather than make from scratch
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);//Calls the super class to clean everything and then draw
        game.render(g);
    }
   

    public Game getGame() {
        return game;
    }
}

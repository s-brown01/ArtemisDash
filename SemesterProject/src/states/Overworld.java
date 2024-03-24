/**
 * Overworld Class 
 * @author johnbotonakis
 * 
 */
package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;

public class Overworld extends State implements StateMethods {

    private final BufferedImage background;

    public Overworld(Game game) {
        super(game);
        background = LoadSave.getSpriteSheet(LoadSave.OVERWORLD_BG);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(background, 5, 5, Game.GAME_WIDTH-10, Game.GAME_HEIGHT-10, null);
        g.setColor(new Color(175, 175, 200, 100));
        g.setFont(boldFont);
        g.setColor(Color.red);
        g.drawString("Hit ENTER to continue to game", Game.GAME_WIDTH / 2 - 100, 300);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_ENTER:
            GameStates.state = GameStates.PLAYING;
            break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}

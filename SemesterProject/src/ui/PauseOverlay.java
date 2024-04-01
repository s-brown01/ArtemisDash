package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;

public class PauseOverlay {
    private BufferedImage backgroundImage;
    private int bgX, bgY, bgH, bgW;
    
    public PauseOverlay() {
        loadBackground();
    }
    
    private void loadBackground() {
        backgroundImage = LoadSave.getSpriteSheet(LoadSave.PAUSE_MENU);
        bgW = (int)(backgroundImage.getWidth() * Game.SCALE);
        bgH = (int)(backgroundImage.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW /2;
        bgY = 50;
    }

    public void update() {
        
    }
    
    public void draw(Graphics g) {
        g.drawImage(backgroundImage,bgX,bgY,bgW,bgH, null);
    }
    
    

    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }
    
    

}

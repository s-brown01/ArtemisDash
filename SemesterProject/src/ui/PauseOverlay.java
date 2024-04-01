package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static main.Game.*;
import utils.LoadSave;
import static utils.Constants.*;

public class PauseOverlay {
    private BufferedImage backgroundImage;
    private int bgX, bgY, bgH, bgW;
    private SoundButton musicButton,sfxButton;
    
    public PauseOverlay() {
        loadBackground();
        createButtons();
    }
    
    private void loadBackground() {
        backgroundImage = LoadSave.getSpriteSheet(LoadSave.PAUSE_MENU);
        bgW = (int)(backgroundImage.getWidth() * SCALE);
        bgH = (int)(backgroundImage.getHeight() * SCALE);
        bgX = GAME_WIDTH / 2 - bgW /2;
        bgY = (int)(35 * SCALE);
    }

    public void update() {
        musicButton.update();
        sfxButton.update();
        
    }
    
    public void draw(Graphics g) {
        //Background
        g.drawImage(backgroundImage,bgX,bgY,bgW,bgH, null);
        
        //Buttons
        musicButton.draw(g);
        sfxButton.draw(g);
    }
    
    /**
     * Creates the buttons for use on the pause menu
     */
    public void createButtons() {
        int soundX = (int)(400 * SCALE);
        int musicY = (int)(230 * SCALE);
        int sfxY = (int)(186 * SCALE);
        musicButton = new SoundButton(soundX,musicY,SOUNDSIZE,SOUNDSIZE);
        sfxButton = new SoundButton(soundX,sfxY,SOUNDSIZE,SOUNDSIZE);
    }
    

    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void mousePressed(MouseEvent e) {
        if (isIn(e,musicButton)) {
            musicButton.setMousePressed(true);
        }
        else if (isIn(e,sfxButton)) {
            sfxButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (isIn(e,musicButton)) {
            if(musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
            }
        }
        else if (isIn(e,sfxButton)) {
            if(sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
            }
        }
        
        musicButton.resetBools();
        sfxButton.resetBools();

    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        
        if (isIn(e,musicButton)) {
            musicButton.setMouseOver(true);
        }
        else if (isIn(e,sfxButton)) {
            sfxButton.setMouseOver(true);
        }

    }
    
    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(),e.getY());
    }
    

}

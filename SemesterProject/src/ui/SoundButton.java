package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utils.LoadSave;
import static utils.Constants.*;

public class SoundButton extends PauseButton{
    
private BufferedImage[][] soundimgs;
private boolean mouseOver, mousePressed;
private boolean muted;
private int rowIndex, colIndex;

    /**
     * 
     * @param x - X-Position of the sprite
     * @param y - Y-Position of 
     * @param width - Width of Sprite
     * @param height - Height of Sprite
     */
    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadSoundImages();
    }

    /**
     * Loads in the images to build the buttons from
     */
    private void loadSoundImages() {
        BufferedImage temp = LoadSave.getSpriteSheet(LoadSave.PAUSE_SOUND_BUTTONS);
        soundimgs = new BufferedImage[2][3];
        for (int j = 0; j< soundimgs.length;j++) {
            for (int i = 0; i <soundimgs[j].length; i++) {
                soundimgs[j][i] = temp.getSubimage(i * SOUNDSIZE_DEFAULT, j * SOUNDSIZE_DEFAULT, SOUNDSIZE_DEFAULT, SOUNDSIZE_DEFAULT);
            }
        }
        
    }
    
    public void update() {
        if (muted) {
            rowIndex = 1;
        }
        else {
            rowIndex = 0;
        }
        
        colIndex = 0;
        if (mouseOver) {
            colIndex = 1;
        }
        
        if (mousePressed) {
            colIndex = 2;
        }
    }
    
    public void draw(Graphics g) {
        g.drawImage(soundimgs[rowIndex][colIndex],x,y,width,height,null);
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
    
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

}

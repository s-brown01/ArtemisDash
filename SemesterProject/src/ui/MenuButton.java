/**
 * Menu Button Class
 */
package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import states.GameStates;
import utils.LoadSave;
import utils.Constants.UI.Buttons;

public class MenuButton{
    private int xpos,ypos,rowIndex,index;
    private int XOffsetCenter = Buttons.B_WIDTH /2;
    private GameStates state;
    private BufferedImage[] imgs;
    private boolean mouseOver,mousePressed;
    private Rectangle bounds;
    
    public MenuButton(int xpos, int ypos, int rowIndex, GameStates state) {
        this.xpos = xpos;
        this.ypos = ypos;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImgs();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xpos - XOffsetCenter,ypos, Buttons.B_WIDTH, Buttons.B_HEIGHT);
        
    }

    private void loadImgs() {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.getSpriteSheet(LoadSave.MENU_BUTTONS);
        for (int i = 0; i < imgs.length; i++) {
            imgs[i] = temp.getSubimage(i* Buttons.B_WIDTH_DEFAULT, rowIndex * Buttons.B_HEIGHT_DEFAULT, Buttons.B_WIDTH_DEFAULT, Buttons.B_HEIGHT_DEFAULT);
        }
    }
    public void draw(Graphics g) {
        g.drawImage(imgs[index], xpos-XOffsetCenter, ypos, Buttons.B_WIDTH,Buttons.B_HEIGHT, null);
    }
    public void update() {
        index = 0;
        if (mouseOver) {
            index = 1;
        }
        if (mousePressed) {
            index = 2;
        }
    }

    public void resetButtons() {
        mouseOver = false;
        mousePressed = false;
    }
    
    public void applyGamestate() {
        GameStates.state = state;
    }
/**
 * Getters and Setters
 * @return
 */
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

    public Rectangle getBounds() {
        return bounds;
    }
}

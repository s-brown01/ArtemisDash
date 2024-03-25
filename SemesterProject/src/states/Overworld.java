/**
 * Overworld Class 
 * @author johnbotonakis
 * 
 */
package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import static utils.Constants.OverworldButtonConstants.*;

import main.Game;
import ui.OverworldButton;
import utils.LoadSave;

public class Overworld extends State implements StateMethods {

    private final BufferedImage background;
//    private final List<OverworldButton> buttonList = new ArrayList<>();
    private OverworldButton[] buttonArr = new OverworldButton[4]; // there will be 15 levels
    private final Point[] btnLocations = new Point[] {POINT_1, POINT_2, POINT_3, POINT_4, POINT_5, POINT_6, POINT_7, POINT_8, POINT_9, POINT_10, POINT_11, POINT_12, POINT_13, POINT_14, POINT_15};
    private OverworldButton selectedLvl = null;

    public Overworld(Game game) {
        super(game);
        background = LoadSave.getSpriteSheet(LoadSave.OVERWORLD_BG);
        initButtons();
    }

    /**
     * This is where we load each of buttons.
     */
    private void initButtons() {
        for (int i = 0; i < buttonArr.length; i++) {
            buttonArr[i] = new OverworldButton(btnLocations[i].x, btnLocations[i].y, "Level Name", 1, i+1);
        }
        buttonArr[1].setCompleted(true);
        buttonArr[2].setHidden(false);
        buttonArr[1].setHidden(false);
    }

    @Override
    public void update() {
        for (OverworldButton ob : buttonArr) {
            ob.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        // background
        g.drawImage(background, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        
        // set font and color
        g.setFont(boldFont);
        g.setColor(Color.WHITE);
        // instructions for user
        g.drawString("OVERWORLD", Game.GAME_WIDTH / 2 + 100, 25);
        g.drawString("Click anywhere to continue to game", Game.GAME_WIDTH / 2 + 100, 50);
        // selected level, make sure its not null
        if (selectedLvl != null)
            g.drawString(selectedLvl.toString(), Game.GAME_WIDTH / 2 - 150, 25);
        
        for (OverworldButton ob : buttonArr) {
            ob.draw(g);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (OverworldButton ob : buttonArr) {
            if (isInOB(e, ob)) {
                ob.setMousePressed(true);
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // disregard this function, using mousePressed/mouseReleased instead

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (OverworldButton ob : buttonArr) {
            // check if the mouse is in the bounds of the button
            // if the mouse is inbounds AND was pressed on that button, move to that level
            if (isInOB(e, ob) && ob.isMousePressed()) {
                selectedLvl = ob;
                // GameStates.state = GameStates.PLAYING;
            }
        }
        for (OverworldButton ob :buttonArr) {
            ob.setMouseOver(false);
            ob.setMousePressed(false);
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (OverworldButton ob : buttonArr) {
            ob.setMouseOver(false);
        }
        for (OverworldButton ob : buttonArr) {
            if (isInOB(e, ob))
                ob.setMouseOver(true);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
    
    /**
     * This function will determine if the cursor of the mouse is inside the bounds of a button.
     * @param e     - the mouseEvent
     * @param ob    - the OverworldButton to look at
     * @return true if the MouseEvent's x and y are within the buttons bounds
     */
    private boolean isInOB(MouseEvent e, OverworldButton ob) {
        return ob.getBounds().contains(e.getPoint());
    }

}

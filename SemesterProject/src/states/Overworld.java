package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.OverworldButtonConstants.*;

import main.Game;
import ui.OverworldButton;
import utils.LoadSave;

/**
 * Overworld Class 
 * @author johnbotonakis
 * 
 */
public class Overworld extends State implements StateMethods {

    private final BufferedImage background;
    private final Point[] btnLocations = BUTTON_POINT_ARRAY;
    private OverworldButton[] buttonArr = new OverworldButton[btnLocations.length];
    private OverworldButton selectedLvl = null;

    public Overworld(Game game) {
        super(game);
        background = LoadSave.getSpriteSheet(LoadSave.OVERWORLD_BG);
        initButtons();
    }

    /**
     * This is where we load each of buttons. It uses a for loop that runs for the length of
     * buttonArr.length to generate new buttons at the locations of matching indexes in
     * btnLocations
     */
    private void initButtons() {
        for (int i = 0; i < buttonArr.length; i++) {
            buttonArr[i] = new OverworldButton(btnLocations[i].x, btnLocations[i].y, "Level Name", 1, i);
        }
        // this is just to see how the levels look at different stages
        // should be removed by game starting
        buttonArr[0].setHidden(false);
        buttonArr[0].setCompleted(true);
        buttonArr[1].setHidden(false);
    }

    /**
     * This update will make sure that the page can respond to the user's inputs and other
     * events on screen
     */
    @Override
    public void update() {
        for (OverworldButton ob : buttonArr) {
            ob.update();
        }
    }

    /**
     * This method will draw the overworld to the Graphics g inputted into the function. It
     * utilizes the OverworldButton's draw function.
     * 
     * @param g The graphics where to draw the Overworld
     */
    @Override
    public void draw(Graphics g) {
        // background
        g.drawImage(background, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        // set font and color
        g.setFont(boldFont);
        g.setColor(Color.WHITE);
        // instructions for user
        g.drawString("OVERWORLD", Game.GAME_WIDTH / 2 + 100, 25);
        g.drawString("Click a level to start", Game.GAME_WIDTH / 2 + 100, 50);
        // selected level, make sure its not null
        if (selectedLvl != null)
            g.drawString(selectedLvl.toString(), Game.GAME_WIDTH / 2 - 150, 25);

        for (OverworldButton ob : buttonArr) {
            ob.draw(g);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // ignore this method, not using it
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
                applyState(ob);
            }
        }
        for (OverworldButton ob : buttonArr) {
            ob.setMouseOver(false);
            ob.setMousePressed(false);
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (OverworldButton ob : buttonArr) {
            ob.setMouseOver(false);
            selectedLvl = null;
        }
        for (OverworldButton ob : buttonArr) {
            if (isInOB(e, ob)) {
                ob.setMouseOver(true);
                selectedLvl = ob;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // ignore this method, not using key bindings in OverWorld
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // ignore this method, not using key bindings in OverWorld

    }

    /**
     * This function will determine if the cursor of the mouse is inside the bounds of a
     * button.
     * 
     * @param e  - the mouseEvent
     * @param ob - the OverworldButton to look at
     * @return true if the MouseEvent's x and y are within the buttons bounds
     */
    private boolean isInOB(MouseEvent e, OverworldButton ob) {
        return ob.getBounds().contains(e.getPoint());
    }
    
    private void applyState(OverworldButton ob) {
        game.getPlaying().nextLevel(ob.getStageNumber());
        GameStates.state = GameStates.PLAYING;
    }

}

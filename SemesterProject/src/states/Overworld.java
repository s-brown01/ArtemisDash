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

import main.Game;
import ui.OverworldButton;
import utils.LoadSave;

public class Overworld extends State implements StateMethods {

    private final BufferedImage background;
//    private final List<OverworldButton> buttonList = new ArrayList<>();
    private OverworldButton[] buttonArr = new OverworldButton[3]; // there will be 15 levels
    private final Point[] btnPointsArr = new Point[] {new Point(100, 100), new Point(150, 150), new Point (200, 200)};

    public Overworld(Game game) {
        super(game);
        background = LoadSave.getSpriteSheet(LoadSave.OVERWORLD_BG);
        initButtons();
    }

    /**
     * This is where we load each of buttons.
     */
    private void initButtons() {
        for (int i = 0; i < 3; i++) {
            buttonArr[i] = new OverworldButton(btnPointsArr[i].x, btnPointsArr[i].y);
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
        g.drawImage(background, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.setColor(new Color(175, 175, 200, 100));
        g.setFont(boldFont);
//        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
        g.setColor(Color.WHITE);
        g.drawString("OVERWORLD", Game.GAME_WIDTH / 2 - 30, 25);
        g.drawString("Click anywhere to continue to game", Game.GAME_WIDTH / 2 - 100, 50);
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
        System.out.println("PRESSED");
        for (OverworldButton ob : buttonArr) {
            if (isInOB(e, ob)) {
                ob.setMousePressed(true);
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        if (e.getButton() == MouseEvent.BUTTON1) {
//            GameStates.state = GameStates.PLAYING;
//        }
        // disregard this function, using mousePressed/mouseReleased instead

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (OverworldButton ob : buttonArr) {
            // check if the mouse is in the bounds of the button
            // if the mouse is inbounds AND was pressed on that button, move to that level
            if (isInOB(e, ob) && ob.isMousePressed()) {
                GameStates.state = GameStates.PLAYING;
            }
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.println("MouseMoved");
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

package states;

import static utils.Constants.OverworldButtonConstants.BUTTON_POINT_ARRAY;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gameaudio.AudioPlayer;
import levels.Level;
import levels.LevelManager;
import main.Game;
import ui.OverworldButton;
import utils.LoadSave;

/**
 * This is a child class of State that implements the StateMethods interface. This
 * represents the overworld of the game. This screen will allow the player to move a
 * specific level and show the user's current progress in the game.
 * 
 * @author John Botonakis and Sean-Paul Brown
 */
public class Overworld extends State implements StateMethods {
    /**
     * The image that will be dispalyed in the background of the overworld menu
     */
    private final BufferedImage background;
    /**
     * The array of button locations that will be displayed on screen, it is the same array as
     * the Constants.OverworldButtonConstants.BUTTON_POINT_ARRAY.
     */
    private final Point[] btnLocations = BUTTON_POINT_ARRAY;
    /**
     * The array of OverworldButtons that will be displayed on screen that match to the
     * locations array
     */
    private OverworldButton[] buttonArr = new OverworldButton[btnLocations.length];
    /**
     * The level that was selected by the user
     */
    private OverworldButton selectedLvl = null;
    /**
     * All of the level names correlate to the buttonArr
     */
    private String[] lvlNames = { "DARRING DESCENT", "LAVA LAKE", "BURNING BADLANDS", "ROCKY RELIEF", "KILL THE KING" };
    /**
     * This should be the same LevelManager as the Game's Playing class. This allows it to
     * influence the current levels and what will be loaded next.
     */
    private final LevelManager levelManager;
    /**
     * This boolean keeps track of if the state has changed at all. Default it to true
     */
    private boolean changed = true;
    /**
     * The font to use in the overworld.
     */
    private Font owFont;

    /**
     * This is the constructor for a Overworld menu. This can only be called after the Game
     * has a Playing class created
     * 
     * @param game - the Game handling this menu
     */
    public Overworld(Game game) {
        super(game);
        background = LoadSave.getSpriteSheet(LoadSave.OVERWORLD_BG);
        // Font Initialization
        this.owFont = LoadSave.loadFont(LoadSave.FONT, 35);
        initButtons();
        this.levelManager = game.getPlaying().getLevelManager();
    }

    /**
     * This is where we load each of buttons. It uses a for loop that runs for the length of
     * buttonArr.length to generate new buttons at the locations of matching indexes in
     * btnLocations
     */
    private void initButtons() {
        for (int i = 0; i < buttonArr.length; i++) {
            buttonArr[i] = new OverworldButton(btnLocations[i].x, btnLocations[i].y, lvlNames[i], 1, i);
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
        // only update the Level booleans if the state has changed, only need to update it once
        // per change.
        // this should hopefully help with performance as it is not checked every update
        updateLevelBooleans();
        for (OverworldButton ob : buttonArr) {
            ob.update();
        }
    }

    /**
     * This method will update all of the booleans for each Overworld button for the Level
     * that represents them in LevelMaanger
     */
    private void updateLevelBooleans() {
        // for each button, get the Level that is matched to that button. Then set the
        // completed/hidden to the booleans from that Level
        for (OverworldButton ob : buttonArr) {
            final Level currLevel = levelManager.getLevelAtIndex(ob.getStageNumber());
            ob.setCompleted(currLevel.getCompleted());
            ob.setHidden(currLevel.getHidden());
        }
        // the first level should not be hidden no matter what.
        // completed can either be true or false, so that doesn't change from above
        levelManager.getLevelAtIndex(0).setHidden(false);
        buttonArr[0].setHidden(levelManager.getLevelAtIndex(0).getHidden());

    }

    /**
     * This method will draw the overworld to the Graphics g inputted into the function. It
     * utilizes the OverworldButton's draw function.
     * 
     * @param g The graphics where to draw the Overworld
     */
    @Override
    public void draw(Graphics g) {
        // Specified positions for titles
        final int owTitleXPos = 115;
        final int owTitleYPos = 150;
        final int owsubTitleXPos = 25;
        final int owsubTitleYPos = 250;
        final int levelTitleX = 1050;
        final int levelTitleY = 100;
        final int backXPOs = 500;
        final int backYPos = Game.GAME_HEIGHT - 50;

        // background
        g.drawImage(background, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        // set font and color
        g.setFont(owFont);
        g.setColor(Color.WHITE);

        // instructions for user
        g.drawString("OVERWORLD", owTitleXPos, owTitleYPos);
        g.drawString("CLICK A LEVEL TO START", owsubTitleXPos, owsubTitleYPos);

        g.setFont(LoadSave.loadFont(LoadSave.FONT, 20));
        g.drawString("Press Backspace to return to main menu", backXPOs, backYPos);

        // selected level, make sure its not null
        if (selectedLvl != null)
            drawString(g, selectedLvl.toString(), levelTitleX, levelTitleY);

        // Draws the buttons to the screen
        for (OverworldButton ob : buttonArr) {
            ob.draw(g);
        }
    }

    /**
     * This is called when the mouse is pressed and moved; ignored in this class
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        // ignore this method, not using it
    }

    /**
     * This deals with that happens when the mouse is pressed down. It checks if the mouse is
     * over any button, and if so then sets the mousePressed of that button to true.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // regardless of what mouse button was pressed, check if the mouse is over any of the
        // buttons.
        for (OverworldButton ob : buttonArr) {
            // if the mouse is in a button, set that button to true
            if (isInOB(e, ob)) {
                game.getAudioPlayer().playEffect(AudioPlayer.BTN_CONFIRM);
                ob.setMousePressed(true);
                // return because it will only be over 1 button at a time
                return;
            }
        }
    }

    /**
     * When the mouse is released, this method should be called. It checks if the mouse in on
     * a button and it is was pressed on that button.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // check if the mouse is in the bounds of the button
        for (OverworldButton ob : buttonArr) {
            // if the mouse is inbounds AND was pressed on that button, apply that button
            if (isInOB(e, ob) && ob.isMousePressed()) {
                applyState(ob);
            }
        }
        // if it wasn't over any button, reset everything
        for (OverworldButton ob : buttonArr) {
            ob.setMouseOver(false);
            ob.setMousePressed(false);
        }

    }

    /**
     * When the mouse is moved (pressed or not), this method should be called. It checks where
     * the mouse is and sets the Button's mouseOver boolean (if appropriate)
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        // reset every mouseOver variable so it defaults to false
        for (OverworldButton ob : buttonArr) {
            ob.setMouseOver(false);
            selectedLvl = null;
        }
        // since everything is false, check if the mouse is over any of the buttons
        for (OverworldButton ob : buttonArr) {
            if (isInOB(e, ob)) {
                ob.setMouseOver(true);
                selectedLvl = ob;
            }
        }
    }

    /**
     * This handles what happens when a key is pressed; Ignored in this class
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_BACK_SPACE:
            GameStates.state = GameStates.MENU;
            break;
        }
    }

    /**
     * This handles what happens when a key is released; Ignored in this class
     */
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

    /**
     * If the level is not hidden, then set the next level and apply the GameState PLAYING
     * 
     * @param ob - the OverworldButton that was selected (it's stage number will be the level
     *           to be loaded)
     * 
     */
    private void applyState(OverworldButton ob) {
        // if the level is hidden, the user can't play it
        if (ob.isHidden()) {
            return;
        }
        // if not hidden, then they can play it.
        // load the next level in Playing and also change the changed boolean to true
        game.getPlaying().nextLevel(ob.getStageNumber());
        changed = true;
        GameStates.state = GameStates.PLAYING;
        // Set the music to the playing music
        game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getCurrentLevelIndex());
    }

    /**
     * Draw string is a formatter for the displayed level names in the Overworld, such that
     * symbols like \n are recognized when drawn to the screen
     * 
     * @param g    - Graphics object to draw to screen
     * @param text - The text to be formatted and passed in
     * @param x    - The intended X-Position
     * @param y    - The intended Y-Position
     */
    private void drawString(Graphics g, String text, int x, int y) {
        for (String line : text.split("\n"))
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
    }
    
    /**
     * THIS FUNCTION IS UNUSED
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

}

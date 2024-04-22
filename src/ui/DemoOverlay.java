package ui;

import static utils.Constants.EndButtons.ENDBUTTON_SIZE;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gameaudio.AudioPlayer;
import main.Game;
import states.GameStates;
import states.Playing;
import utils.LoadSave;

/**
 * This class handles the DemoOverlay when the use reaches the end of the demo levels
 * 
 * @author Sean-Paul Brown
 */
public class DemoOverlay {

    /**
     * The main background image of the overlay
     */
    private BufferedImage img;
    /**
     * The left co-ordinate of the overlay
     */
    private int imgX,
            /**
             * The top co-ordinate of the overlay
             */
            imgY,
            /**
             * The width of the overlay
             */
            imgW,
            /**
             * The heght of the overlay
             */
            imgH;
    /**
     * An EndButton that connects to the QUIT GameState
     */
    private DemoButton quit;
    /**
     * The Playing object that will be displaying the overlay
     */
    private Playing playing;

    /**
     * Initializes the Demo Overlay by creating and loading in sprite for the menu and its
     * buttons
     * 
     * @param playing - the Playing object that will be displaying the overlay
     */
    public DemoOverlay(Playing playing) {
        this.playing = playing;
        loadBackground();
        createButtons();
    }

    /**
     * Updates the buttons based on the users actions. Things such as hover and mouse click
     * affect the button sprite state
     */
    public void update() {
        quit.update();
    }

    /**
     * Draws everything that is intended to be visible, to the screen
     * 
     * @param g - Graphics
     */
    public void draw(Graphics g) {
        // background
        g.drawImage(img, imgX, imgY, imgW, imgH, null);
        g.setFont(LoadSave.loadFont(LoadSave.FONT, 25));
        g.setColor(Color.WHITE);
        g.drawString("Thanks for playing the Artemis Dash Demo!", imgW / 5, imgH / 2);

        // buttons
        quit.draw(g);
    }

    /**
     * Loads in a background based on the specified image name Background width, height,
     * X-Position and Y-Position are also set here
     */
    private void loadBackground() {
        img = LoadSave.getSpriteSheet(LoadSave.MENU_SCREEN);
        // create the image as big and wide and the GamePanel
        imgW = Game.GAME_WIDTH;
        imgH = Game.GAME_HEIGHT;
        imgX = 0;
        imgY = 0;

    }

    /**
     * Creates the buttons for use only on the death overlay Scale, X position, Y position and
     * offsets are set here
     */
    private void createButtons() {
        // Button Positioning
        // the button will be centered about 2 third down the screen
        final int quitX = imgW / 2 - ENDBUTTON_SIZE;
        final int yPos = imgH / 3 * 2;

        quit = new DemoButton("QUIT", quitX, yPos, ENDBUTTON_SIZE * 2, ENDBUTTON_SIZE);
    }

    /**
     * Specifies behavior when the mouse is pressed signifying the start of a complete click.
     * 
     * @param e - Mouse pressed event
     */
    public void mousePressed(MouseEvent e) {
        if (isIn(e, quit)) {
            playing.getGame().getAudioPlayer().playEffect(AudioPlayer.BTN_CONFIRM);
            quit.setMousePressed(true);
        }
    }

    /**
     * Executes a specific action based on the mouse movement In this case, changing the
     * sprite to the "hover" sprite
     * 
     * @param e - Mouse movement event
     */
    public void mouseMoved(MouseEvent e) {
        quit.setMouseOver(false);

        if (isIn(e, quit)) {
            quit.setMouseOver(true);
        }
    }

    /**
     * Specifies behavior when the mouse button is released, signifying the end of a complete
     * click
     * 
     * @param e - Mouse released event
     */
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, quit)) {
            if (quit.isMousePressed()) {
                GameStates.state = GameStates.QUIT;
                playing.resetAll();
            }
        }

        quit.resetBools();
    }

    /**
     * Checks to see if the mouse cursor is within the bounds of a end button
     * 
     * @param e - Mouse movement event
     * @param b - Button bounding box
     * @return - Returns true if the mouse is within the confines of the buttons bounding box
     */
    private boolean isIn(MouseEvent e, DemoButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

}

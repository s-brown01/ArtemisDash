package ui;

import static main.Game.GAME_WIDTH;
import static main.Game.SCALE;
import static utils.Constants.ButtonStates.SOUNDSIZE;
import static utils.LoadSave.PAUSE_MENU;
import static utils.LoadSave.getSpriteSheet;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;

/**
 * This class handles the pause overlay when the user hits the pause button during
 * gameplay
 * 
 * @author John Botonakis
 */
public class PauseOverlay {
    /**
     * The background image of the overlay
     */
    private BufferedImage backgroundImage;
    /**
     * the left coordinate of the overlay
     */
    private int bgX,
            /**
             * the top coordinate of the overlay
             */
            bgY,
            /**
             * the height of the overlay
             */
            bgH,
            /**
             * the width of the overlay
             */
            bgW;
    /**
     * The sound button that will mute or unmute music
     */
    private SoundButton musicButton,
            /**
             * The Sound Button that will mute or unmute sound effects
             */
            sfxButton;
    /**
     * The main game class that has is using the audio players
     */
    private Game game;

    /**
     * Initializes the Pause Overlay by creating and loading in sprite for the menu and its
     * buttons
     * 
     * @param game - the game that is containing the playing class that holds this
     */
    public PauseOverlay(Game game) {
        this.game = game;
        loadBackground();
        createButtons();
    }

    /**
     * Loads in a background based on the specified image name Background width, height,
     * X-Position and Y-Position are also set here
     */
    private void loadBackground() {
        backgroundImage = getSpriteSheet(PAUSE_MENU);
        bgW = (int) (backgroundImage.getWidth() * SCALE);
        bgH = (int) (backgroundImage.getHeight() * SCALE);
        bgX = GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (35 * SCALE);
    }

    /**
     * Updates the buttons based on the users actions. Things such as hover and mouse click
     * affect the button sprite state
     */
    public void update() {
        musicButton.update();
        sfxButton.update();

    }

    /**
     * Draws everything that is intended to be visible, to the screen
     * 
     * @param g - Graphics
     */
    public void draw(Graphics g) {
        // Background
        g.drawImage(backgroundImage, bgX, bgY, bgW, bgH, null);

        // Buttons
        musicButton.draw(g);
        sfxButton.draw(g);
    }

    /**
     * Creates the buttons for use only on the pause menu Scale, X position, Y position and
     * offsets are set here
     */
    public void createButtons() {
        // Had to touch up the overall size so decreased it by 5 in its height
        int sizeOffset = 5;
        int soundX = (int) (400 * SCALE);
        int musicY = (int) (275 * SCALE);
        int sfxY = (int) (186 * SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUNDSIZE, SOUNDSIZE - sizeOffset);
        sfxButton = new SoundButton(soundX, sfxY, SOUNDSIZE, SOUNDSIZE - sizeOffset);
    }

    /**
     * Specifies behavior when the mouse is pressed signifying the start of a complete click.
     * 
     * @param e - Mouse pressed event
     */
    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMousePressed(true);
        }
    }

    /**
     * Specifies behavior when the mouse button is released, signifying the end of a complete
     * click
     * 
     * @param e - Mouse released event
     */
    public void mouseReleased(MouseEvent e) {
        // If the mouse is within the bounds of a button,
        // and if the button has already been pressed
        // flip the current button state (toggle it on or off)
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
                game.getAudioPlayer().toggleSongMute();

            }
        }
        // Similar design here, only for the SFX button
        else if (isIn(e, sfxButton)) {
            if (sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
                game.getAudioPlayer().toggleEffectMute();
            }
        }

        musicButton.resetBools();
        sfxButton.resetBools();

    }

    /**
     * Executes a specific action based on the mouse movement In this case, changing the
     * sprite to the "hover" sprite
     * 
     * @param e - Mouse movement event
     */
    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);

        if (isIn(e, musicButton)) {
            musicButton.setMouseOver(true);
        } else if (isIn(e, sfxButton)) {
            sfxButton.setMouseOver(true);
        }

    }

    /**
     * Checks to see if the mouse cursor is within the bounds of a menu button
     * 
     * @param e - Mouse movement event
     * @param b - Button bounding box
     * @return - Returns true if the mous is within the confines of the buttons bounding box
     */
    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

}

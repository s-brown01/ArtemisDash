package utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * This class is focused on loading in sprite data, level data, and building levels. All
 * file names will be stored here. The different functions of this class will be to load
 * in the different types of data.
 * 
 * @author John Botonakis and Sean-Paul Brown
 */
public class LoadSave {
    /**
     * Where the font file is located
     */
    public static final String FONT = "res/HUDElements/HudFont.ttf"; // Mercy Christole is the Font
    /**
     * Where the hud background image is located
     */
    public static final String HUDBG = "/HUDElements/HUDbg.png";
    /**
     * Where the player's portait image is located
     */
    public static final String PLAYER_PORTRAIT = "/HUDElements/temp_artemis.png";

    /**
     * Where the player health HEART representation is located
     */
    public static final String HEART = "/HUDElements/heart.png";

    /**
     * the file containing all of the player's sprites/animations
     */
    public static final String PLAYER_SPRITES = "/EntitySpritesheets/Artemis_Finished.png";
    /**
     * the file containing all of the skeleton's sprites/animations
     */
    public static final String SKELETON_SPRITES = "/EntitySpritesheets/Skeleton.png";
    /**
     * the file containing all of the blue projectile sprites/animations
     */
    public static final String BLUE_PROJECTILE = "/EntitySpritesheets/Blue_Projectiles.png";

    /**
     * the Overworld background image
     */
    public static final String OVERWORLD_BG = "/overworld1.png";

    /**
     * the pause menu background
     */
    public static final String PAUSE_MENU = "/PauseElements/pause_menu.png";
    /**
     * the pause sound button images
     */
    public static final String PAUSE_SOUND_BUTTONS = "/PauseElements/pause_sound_buttons.png";
    /**
     * the pause button images
     */
    public static final String PAUSE_BUTTONS = "/PauseElements/pause_buttons.png";

    /**
     * the main menu button images
     */
    public static final String MENU_BUTTONS = "/TitleScreenElements/TitleButtons.png";
    /**
     * the main menu's background
     */
    public static final String MENU_SCREEN = "/TitleScreenElements/TitleScreen1.png";

    /**
     * the sprite sheet for all of the tiles in world 1
     */
    public static final String WORLD1_SPRITES = "/World1/World1Sprites.png";
    /**
     * the background for world 1
     */
    public static final String WORLD1_BG = "/World1/World1bg.png";
    /**
     * the background mist for world 1
     */
    public static final String WORLD1_BG_MYST = "/World1/World1bg_myst.png";
    /**
     * the background rocks for world 1
     */
    public static final String WORLD1_BG_ROCKS = "/World1/World1_rocks.png";

    /**
     * the RGB values for Level 1s
     */
    public static final String LEVEL1_DATA = "/level_one_data_long.png";

    /**
     * the tile sprites for all of world 2
     */
    public static final String WORLD2_SPRITES = "/World2/World2Sprites.png";
    /**
     * the tile sprites for all of world 3
     */
    public static final String WORLD3_SPRITES = "/World3/World3Sprites.png";

    /**
     * The buttons relating to the Level Complete screen as well as Death Screen
     */
    public static final String ENDBUTTONS = "/PauseElements/endButtons.png";

    /**
     * The assets for the death screen overlay
     */
    public static final String DEATHSCREEN = "/death_screen.png";

    /**
     * Returns the specified sprite atlas for use in drawing the correct image to the screen
     * 
     * @param filename - The name of the sprite sheet that is requested
     * @return - The specified image as a variable
     */
    public static BufferedImage getSpriteSheet(String filename) {
        BufferedImage img = null;

        try (InputStream is = LoadSave.class.getResourceAsStream(filename)) {
            img = ImageIO.read(is);
        } catch (IOException e) {
            System.out.println("NULL");
        }
        return img;
    }

    /**
     * Uses RGB values to generate a tiled level. Red Value sets the tile Green Value sets the
     * enemy Blue value sets the object
     * 
     * @return - A 2D array that is representative of tiles making up the level
     */
    public static int[][] getLevelData() {
        BufferedImage img = getSpriteSheet(LEVEL1_DATA);
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48)
                    value = 0;
                lvlData[j][i] = value;
            }
        return lvlData;

    }

    /**
     * Loads a custom font from a file. Re-Used from Botonakis_Project2
     *
     * @param path The path to the font file.
     * @param size The size of the font to be loaded.
     * @return The loaded Font object.
     */
    public static Font loadFont(String path, float size) {
        Font customFont = null;
        try {
            // Load the font file
            File fontFile = new File(path);
            // if the file cannot be read, handle it here
            if (!fontFile.canRead()) {
                System.err.println("WARNING: Font file not accessible.");
                // a default Font in case the file can't be read
                return new Font("Lucida Handwriting", Font.BOLD, 20);
            }
            // otherwise, load in the Font
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(size);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return customFont;
    }

    /**
     * This returns an array of the sprites that will be used for Player's Arrows.
     * 
     * @return an array of BufferedImages containing all the sprites
     */
    public static BufferedImage[] getArrowImgs() {
        final BufferedImage allBlueSprites = getSpriteSheet(BLUE_PROJECTILE);
//        if (allBlueSprites == null) {
//            System.exit(0);
//        }
        // these are from the sprite sheet, the specifc coordinates of what images to use
        final int[] arrowImgX = { 2, 18, 34, 50, 66 };
        final int arrowImgY = 277;
        BufferedImage[] arrowSprites = new BufferedImage[arrowImgX.length];

        // get the sprite img at each point above with the Arrow's width and height
        for (int i = 0; i < arrowImgX.length; i++) {
            arrowSprites[i] = allBlueSprites.getSubimage(arrowImgX[i], arrowImgY,
                    Constants.ProjectileConstants.ARROW_IMG_WIDTH, Constants.ProjectileConstants.ARROW_IMG_HEIGHT);
        }
        return arrowSprites;
    }

}
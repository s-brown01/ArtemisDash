package utils;

import static utils.Constants.EnemyConstants.SKELETON_HITBOX_HEIGHT;
import static utils.Constants.EnemyConstants.SKELETON_HITBOX_WIDTH;
import static utils.Constants.EnemyConstants.SKELETON_KING_HITBOX_HEIGHT;
import static utils.Constants.EnemyConstants.SKELETON_KING_HITBOX_WIDTH;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import entities.Skeleton;
import entities.SkeletonKing;
import main.Game;

/**
 * This class is focused on loading in sprite data, level data, and building levels. All
 * file names will be stored here. The different functions of this class will be to load
 * in the different types of data.
 * 
 * @author John Botonakis and Sean-Paul Brown
 */
public class LoadSave {
    // HUD ASSETS
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

    // ENTITY SPRITE SHEETS
    /**
     * The file containing all of the player's sprites/animations
     */
    public static final String PLAYER_SPRITES = "/EntitySpritesheets/Artemis_Finished.png";
    /**
     * The file containing all of the skeleton's sprites/animations
     */
    public static final String SKELETON_SPRITES = "/EntitySpritesheets/Skeleton.png";
    /**
     * the file containing all of the skeleton king's sprites/animations
     */
    public static final String SKELETON_KING_SPRITES = "/EntitySpritesheets/SkeletonKing.png";
    /**
     * the file containing all of the blue projectile sprites/animations
     */
    public static final String BLUE_PROJECTILE = "/EntitySpritesheets/Blue_Projectiles.png";

    // MENU SCREENS ASSETS
    /**
     * The background for the Overworld Screen
     */
    public static final String OVERWORLD_BG = "/overworld1.png";

    /**
     * The background for the Pause Screen
     */
    public static final String PAUSE_MENU = "/PauseElements/pause_menu.png";
    /**
     * The background for the Main Menu
     */
    public static final String MENU_SCREEN = "/TitleScreenElements/TitleScreen1.png";

    /**
     * The background for the Instructions Screen
     */
    public static final String INSTRUCTIONS_SCREEN = "/TitleScreenElements/OptionsScreen.png";
    /**
     * The assets for the death screen overlay
     */
    public static final String DEATHSCREEN = "/death_screen.png";

    /**
     * The assets for the death screen overlay
     */
    public static final String WINSCREEN = "/win_screen.png";

    // BUTTONS
    /**
     * The images making up the Pause Sound Buttons
     */
    public static final String PAUSE_SOUND_BUTTONS = "/PauseElements/pause_sound_buttons.png";
    /**
     * The images making up the Pause Buttons
     */
    public static final String PAUSE_BUTTONS = "/PauseElements/pause_buttons.png";

    /**
     * The images making up the Main Menu Buttons
     */
    public static final String MENU_BUTTONS = "/TitleScreenElements/TitleButtons.png";
    /**
     * The buttons relating to the Level Complete screen as well as Death Screen
     */
    public static final String ENDBUTTONS = "/PauseElements/endButtons.png";

    // WORLD 1 ASSETS
    /**
     * The sprite sheet for all of the tiles in world 1
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

    // LEVEL ASSETS
    /**
     * the RGB values for the default level
     */
    public static final String DEFAULT_LEVEL = "/default_level.png";
    /**
     * the RGB values for Level 1
     */
    public static final String LEVEL1_DATA = "/World1/W1_level_one.png";
    /**
     * the RGB values for Level 2
     */
    public static final String LEVEL2_DATA = "/World1/W1_level_two.png";
    /**
     * the RGB values for Level 3
     */
    public static final String LEVEL3_DATA = "/World1/W1_level_three.png";
//    /**
//     * the RGB values for Level 1
//     */
//    public static final String LEVEL4_DATA = "/World1/level_four_data_long.png";
//    /**
//     * the RGB values for Level 1
//     */
//    public static final String LEVEL5_DATA = "/World1/level_five_data_long.png";

    // WORLD LEVEL TILE ASSETS
    /**
     * the tile sprites for all of world 2
     */
    public static final String WORLD2_SPRITES = "/World2/World2Sprites.png";
    /**
     * the tile sprites for all of world 3
     */
    public static final String WORLD3_SPRITES = "/World3/World3Sprites.png";

    /**
     * this is the green value used to determine which tiles have Skeletons on in from the RGB
     * level data
     */
    private static final int SKELETON_GREEN_VALUE = 50;
    /**
     * this is the green value used to determine which tiles have Skeleton King on in from the
     * RGB level data
     */
    private static final int SKELETON_KING_GREEN_VALUE = 51;

    /**
     * Returns the specified sprite atlas for use in drawing the correct image to the screen
     * 
     * @param filename - The name of the sprite sheet that is requested
     * @return - The specified image as a variable
     */
    public static BufferedImage getSpriteSheet(String filename) {
        // create it here so it can be returned
        BufferedImage img = null;
        // try with resources so it automatically closes
        try (InputStream is = LoadSave.class.getResourceAsStream(filename)) {
            img = ImageIO.read(is);
        } catch (IOException e) {
            System.err.println("NULL");
        }
        return img;
    }

    /**
     * Uses RGB values to generate a tiled level. Red Value sets the tile Green Value sets the
     * enemy Blue value sets the object 50 Green Value = Skeleton 51 Green Value = Skeleton
     * KING
     * 
     * @param level - The path to the file containing the RGB data map for the specific level.
     * @return - A 2D array that is representative of tiles making up the level
     */
    public static int[][] getLevelData(String level) {
        // get the image containing the RGB values
        BufferedImage img = getSpriteSheet(level);
        // the level data will represent every pixel in the RBG map
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];
        // for reach point in the RBG map, assign the value to a
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48) {
                    value = 0;
                }
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
        // get the sheet with all of the sprites on it
        final BufferedImage allBlueSprites = getSpriteSheet(BLUE_PROJECTILE);
        // these are from the sprite sheet, the specific coordinates of what images to use
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

    /**
     * This method gets all of the Skeletons contained in a specific level inside of a List
     * 
     * @param level - the level to get the Skeletons from
     * @return a List containing all Skeletons from that level
     */
    public static List<Skeleton> getSkeletons(String level) {
        List<Skeleton> skelList = new ArrayList<>();
        BufferedImage img = getSpriteSheet(level);
        // for reach point in the RBG map, assign the value to a
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == SKELETON_GREEN_VALUE) {
                    skelList.add(new Skeleton(i * Game.TILES_SIZE, j * Game.TILES_SIZE - SKELETON_HITBOX_HEIGHT,
                            SKELETON_HITBOX_WIDTH, SKELETON_HITBOX_HEIGHT));
                }
            }
        return skelList;
    }

    /**
     * This method gets all of the Skeleton King contained in a specific level inside of a
     * List
     * 
     * @param level - the level to get the Skeleton King from
     * @return a List containing all Skeleton Kings from that level
     */
    public static List<SkeletonKing> getSkeletonKings(String level) {
        List<SkeletonKing> skelList = new ArrayList<>();
        BufferedImage img = getSpriteSheet(level);
        // for reach point in the RBG map, assign the value to a
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == SKELETON_KING_GREEN_VALUE) {
                    skelList.add(new SkeletonKing(i * Game.TILES_SIZE, j * Game.TILES_SIZE, SKELETON_KING_HITBOX_WIDTH,
                            SKELETON_KING_HITBOX_HEIGHT));
                }
            }
        return skelList;
    }

}
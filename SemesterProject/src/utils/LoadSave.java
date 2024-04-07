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
 * LoadSave Class
 * 
 * @author johnbotonakis
 * @description This class is focused on loading in sprite data, level data, and building
 *              levels
 */
public class LoadSave {

    public static final String FONT = "./res/HudFont.ttf"; // Mercy Christole
    public static final String HUDBG = "/HUDbg.png";
    public static final String PLAYER_SPRITES = "/Artemis_Finished.png";
    public static final String PLAYER_PORTRAIT = "/temp_artemis.png";
    public static final String SKELETON_SPRITES = "/Skeleton.png";

    public static final String OVERWORLD_BG = "/overworld1.png";

    public static final String PAUSE_MENU = "/pause_menu.png";
    public static final String PAUSE_SOUND_BUTTONS = "/pause_sound_buttons.png";
    public static final String PAUSE_BUTTONS = "/pause_buttons.png";

    public static final String MENU_BUTTONS = "/button_atlas1.png";
    public static final String MENU_SCREEN = "/TitleScreen1.png";

    public static final String WORLD1_SPRITES = "/World1Sprites.png";
    public static final String WORLD1_BG = "/World1bg.png";
    public static final String WORLD1_BG_MYST = "/World1bg_myst.png";
    public static final String WORLD1_BG_ROCKS = "/World1_rocks.png";

    public static final String WORLD2_SPRITES = "/World2Sprites.png";
    public static final String WORLD3_SPRITES = "/World3Sprites.png";

    public static final String LEVEL1_DATA = "/level_one_data_long.png";

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
     * Uses RGB values to generate a tiled level.
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
            if (!fontFile.canRead()) {
                System.err.println("Font file not accessible.");
                return null;
            }
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(size);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return customFont;
    }

}
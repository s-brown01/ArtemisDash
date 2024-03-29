/**
 * LoadSave Class
 * @author johnbotonakis
 * This class is focused on loading in sprite data, level data, and building levels
 */
package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {

    public static final String PLAYER_SPRITES = "/Artemis_Finished.png";
    public static final String SKELETON_SPRITES = "/Skeleton.png";

    public static final String OVERWORLD_BG = "/Overworld.png";

    public static final String MENU_BUTTONS = "/button_atlas1.png";
    public static final String MENU_SCREEN = "/TitleScreen.png";

    public static final String WORLD1_SPRITES = "/World1Sprites.png";
    public static final String WORLD2_SPRITES = "/World2Sprites.png";
    public static final String WORLD3_SPRITES = "/World3Sprites.png";

    public static final String LEVEL1_DATA = "/level_one_data.png";

    /**
     * Returns the specified sprite atlas for use in drawing the correct image to the screen
     * 
     * @param filename  - The name of the sprite sheet that is requested
     * @return          - The specified image as a variable
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
        int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage img = getSpriteSheet(LEVEL1_DATA);

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
}
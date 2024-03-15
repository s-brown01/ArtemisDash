
package utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import main.Game;

public class LoadSave {

    public static final String PLAYER_ATLAS = "/Artemis_Finished.png";
    public static final String WORLD1_ATLAS = "/Artemis_Finished.png"; // World 1 Levels sprites
    public static final String WORLD2_ATLAS = "/Artemis_Finished.png"; // World 2 Levels sprites
    public static final String WORLD3_ATLAS = "/World3T.png"; // World 3 Levels sprites
    public static final String W1S1 = "/level_one_data.png"; // World 3 Levels sprites
    public static final String SKELETON_WALK = "/skeleton/Skeleton_Walk.png";

    // Returns the sprite atlas for use in drawing the correct image to the screen
    public static BufferedImage getSpriteAtlas(String filename) {
        BufferedImage img = null;

        try (InputStream is = LoadSave.class.getResourceAsStream(filename)) {
            img = ImageIO.read(is);
        } catch (IOException e) {
            System.out.println("NULL");
        }
        return img;
    }

    // USES RGB TO BUILD A LEVEL
    public static int[][] getLevelData() {
        int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage img = getSpriteAtlas(W1S1);

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
/**
 * Level Manager
 * @author johnbotonakis
 * This class will handle things such as importing level sprites, 
 * drawing the level to the screen, and keep track of the current level played
 */
package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;

    public LevelManager(Game game) {
        this.game = game;
        importLevelSprites();
        levelOne = new Level(LoadSave.getLevelData());
    }

    /**
     * Imports the sprites to build a level
     */
    private void importLevelSprites() {
        BufferedImage img = LoadSave.getSpriteSheet(LoadSave.WORLD1_SPRITES);
        levelSprite = new BufferedImage[48]; // 12 sprites wide * 4 sprites tall = 48
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }

    }

    /**
     * Draws the level to the screen with the provided sprite sheet
     * 
     * @param g
     */
    public void draw(Graphics g) {
        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
                int index = levelOne.getSpriteIndex(i, j);
                // index 11 is a transparent tile, might as well not draw it
                if (index != 11)
                    g.drawImage(levelSprite[index], i * Game.TILES_SIZE, Game.TILES_SIZE * j, Game.TILES_SIZE,
                            Game.TILES_SIZE, null);

            }

        }
    }

    public void update() {

    }

    public Level getCurrentLevel() {
        return levelOne;
    }
}

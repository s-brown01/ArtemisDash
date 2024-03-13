package levels;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

import main.Game;
import utils.LoadSave;

public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private Level levelOne;// Just to test Level creation is working

    /**
     * 
     * @param game
     */
    public LevelManager(Game game) {
        this.game = game;
        importWorldSprites();
        levelOne = new Level(LoadSave.getLevelData());
//		levelSprite = LoadSave.GetSpriteAtlas(LoadSave.WORLD3_ATLAS);//TESTING: Shows the WHOLE sprite sheet
    }

    /**
     * Handles the tile set for each level by bringing it in and chopping it up
     */
    private void importWorldSprites() {
        // TESTING: 48 is the test amount total of sprites from a tile sheet; Not actually 48
        levelSprite = new BufferedImage[48];
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.WORLD3_ATLAS);
        // TESTING: j<Number of columns; for testing it is 4
        for (int j = 0; j < 4; j++) {
            // TESTING: j<Number of rows; for testing it is 12
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                // TESTING: 32 is the test area of a single tile on the sheet
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
    }

    /**
     * Renders the level piece by piece, using the indexes passed into it by the imported 2D
     * array
     * 
     * @param g
     */
    public void draw(Graphics g) {
        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
                int index = levelOne.getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], Game.TILES_SIZE * i, Game.TILES_SIZE * j, Game.TILES_SIZE,
                        Game.TILES_SIZE, null);
            }
        }
//		g.drawImage(levelSprite, 0, 0, null);//TESTING: Displays the WHOLE sprite sheet
//		g.drawImage(worldSprite[48], 0, 0, null); //TESTING: Displays a specific sprite value
    }

    //TBD later
    public void update() {

    }

    //Returns the current level. This is set as levelOne for testing now
    public Level getCurrentLevel() {
        return levelOne;
    }
}
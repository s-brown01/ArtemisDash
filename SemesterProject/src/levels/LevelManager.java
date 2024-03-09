package levels;

import java.awt.image.BufferedImage;
import java.awt.Graphics;

import main.Game;
import utils.LoadSave;

public class LevelManager {

    private Game game;
    private BufferedImage levelSprite;
    private BufferedImage[] worldSprite;
    private Level levelOne;// Just to test Level creation is working

    /**
     * 
     * @param game
     */
    public LevelManager(Game game) {
	this.game = game;
//		levelSprite = LoadSave.GetSpriteAtlas(LoadSave.WORLD3_ATLAS);//TESTING: Shows the WHOLE sprite sheet
	levelOne = new Level(LoadSave.getLevelData());
    }

    /**
     * Handles the tile set for each level by bringing it in and chopping it up
     */
    private void importWorldSprites() {
	BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.WORLD3_ATLAS);
	worldSprite = new BufferedImage[48]; // TESTING: 48 is the test amount total of sprites from a tile sheet; Not
					     // actually 48
	for (int j = 0; j < 4; j++) {// TESTING: j<Number of columns; for testing it is 4
	    for (int i = 0; i < 12; i++) {// TESTING: j<Number of rows; for testing it is 12
		int index = j * 12 + i;
		worldSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32); // TESTING: 32 is the test are of a single
									      // tile on the sheet
	    }
	}
    }

    /**
     * Renders the level piece by piece, using the indexes passed into it by the
     * imported 2D array
     * 
     * @param g
     */
    public void draw(Graphics g) {
	importWorldSprites();
//		g.drawImage(levelSprite, 0, 0, null);//TESTING: Displays the WHOLE sprite sheet
//		g.drawImage(worldSprite[48], 0, 0, null); //TESTING: Displays a specific sprite value
	for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
	    for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
		int index = levelOne.intGetSpriteIndex(i, j);
		g.drawImage(worldSprite[index], Game.TILES_SIZE * i, Game.TILES_SIZE * j, Game.TILES_SIZE,
			Game.TILES_SIZE, null);
	    }
	}
    }

    public void update() {

    }
}

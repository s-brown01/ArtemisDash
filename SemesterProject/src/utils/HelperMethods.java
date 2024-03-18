package utils;

import java.awt.geom.Rectangle2D;

import main.Game;

public class HelperMethods {

    /**
     * Checks whether an entity is able to move in a given direction
     * @param x
     * @param y
     * @param width
     * @param height
     * @param levelData
     * @return
     */
    public static boolean canMoveHere(float x, float y, float width, float height, int[][] levelData) {
        // check top left and bottom right first in case of arial movement
        if (!isSolid(x, y, levelData)) {// Checks top left
            if (!isSolid(x + width, y + height, levelData)) {// Checks bottom right
                if (!isSolid(x + width, y, levelData)) {// checks top right
                    if (!isSolid(x, y + height, levelData)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }
    
    /**
     * Determines if a tile is solid enough to walk on
     * @param x - X-Position of the current entity
     * @param y - Y-Position of the current entity
     * @param lvlData - Data of the level to be checked against
     * @return - True if it is able to be walked on, false otherwise
     */
    private static boolean isSolid(float x, float y, int[][] lvlData) {
        if (x < 0 || x >= Game.GAME_WIDTH) {
            return true;
        }

        if (y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        int value = lvlData[(int) yIndex][(int) xIndex];

        // 48 Sprites, 11th sprite is transparent
        if (value >= 48 || value < 0 || value != 11) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Checks when a hitbox collides with something on the right or left side
     * @param hitbox - The hitbox calling this collision check
     * @param xSpeed - The X speed of that hitbox
     * @return The offset of the hitbox as to not clip through
     */
    public static float getXPosWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            // Collision on the Right Side
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else {
            // Collision on the Left Side
            return currentTile * Game.TILES_SIZE;
        }

    }

    /**
     * Checks when a hitbox collides with something on the top or bottom
     * @param hitbox - The hitbox calling this collision check
     * @param airSpeed - The Y Speed of that hitbox 
     * @param hitboxOffset
     * @return
     */
    public static float getYPosRoof(Rectangle2D.Float hitbox, float airSpeed, float hitboxOffset) {
        int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
        if (airSpeed > 0) {
            // Collision on the Bottom
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) ((Game.TILES_SIZE - hitbox.height) + hitboxOffset);
            return tileYPos + yOffset;
        } else {
            // Collision on the top
            return currentTile * Game.TILES_SIZE;
        }
    }
    
    /**
     * Checks to see if the entity is on the floor
     * @param hitbox - The hitbox of the entity that called this method
     * @param lvlData - The data of the floor
     * @return - Returns true if the entity is touching the floor, false if not
     */
    public static boolean gravity(Rectangle2D.Float hitbox, int[][] lvlData) {
        // Check the pixel below bottomleft and bottomright
        if (!isSolid(hitbox.x, hitbox.y + hitbox.height + 10, lvlData))
                if (!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                        return false;

        return true;

}
}

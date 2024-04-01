/**
 * HelperMethods Class
 * @author johnbotonakis
 * This class is full of various methods that will be called by more than just one entity, object, or class
 */
package utils;

import java.awt.geom.Rectangle2D;

import main.Game;

public class HelperMethods {

    /**
     * Checks whether an entity is able to move in a given direction, by checking every corner
     * of the entities hitbox for a collision with another sprite.
     * 
     * The order of checks is: Top left, Bottom right, Top right, Bottom left.
     * 
     * @param x         - X-Position of caller entity
     * @param y         - Y-Position of caller entity
     * @param width     - Width of the hitbox of caller entity
     * @param height    - Height of the hitbox of caller entity
     * @param levelData - 2D Array of data that represents the level
     * @return - True if the entity is able to move, false if it cannot 
     */
    public static boolean canMoveHere(float x, float y, float width, float height, int[][] levelData) {
        // check top left and bottom right first in case of arial movement
        if (!isSolid(x, y, levelData)) { // Checks top left
            if (!isSolid(x + width, y + height, levelData)) { // Checks bottom right
                if (!isSolid(x + width, y, levelData)) { // checks top right
                    if (!isSolid(x, y + height, levelData)) {
                        if (!isSolid(x, y + height / 2, levelData)) {
                            if (!isSolid(x + width, y + height / 2, levelData)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Determines if a tile is solid enough to walk on
     * 
     * @param x       - X-Position of the current entity
     * @param y       - Y-Position of the current entity
     * @param lvlData - Data of the level to be checked against
     * @return        - True if it is able to be walked on, false otherwise
     */
    private static boolean isSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth) {
            return true;
        }

        if (y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        return isTileSolid((int) xIndex, (int) (yIndex), lvlData);

    }

    /**
     * This method will check a specific index in the levelData passed in. If it is not in the
     * tiles being used or is NOT a transparent tile, it is solid.
     * 
     * @param xIndex    - X-Position in the level index; Any drawn game tile on the X-Axis
     * @param yIndex    - Y-Position in the level index; Any drawn game tile on the Y-Axis
     * @param levelData - The data that makes up the level
     * @return          - Returns True if the tile is solid, false otherwise
     */
    private static boolean isTileSolid(int xIndex, int yIndex, int[][] levelData) {
        int value = levelData[(int) yIndex][(int) xIndex];

        // 48 Sprites, 11th sprite in the Level sprite sheet is transparent
        if (value >= 48 || value < 0 || value != 11) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks when a hitbox collides with something on the right or left side
     * 
     * @param hitbox - The hitbox calling this collision check
     * @param xSpeed - The X speed of that hitbox
     * @return       - The offset of the hitbox as to not clip through
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
     * Checks when the caller's hitbox collides with something on the top or bottom,
     * usually after jumping and hitting the ceiling, or jumping and landing on the floor
     * 
     * @param hitbox       - The hitbox calling this collision check
     * @param airSpeed     - The Y Speed of that hitbox
     * @param hitboxOffset - How much the sprite is drawn off center from the hitbox
     * @return             - Returns a float to displace the caller's hitbox.
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
     * 
     * @param hitbox   - The hitbox of the entity that called this method
     * @param lvlData  - The data of the floor
     * @return         - Returns true if the entity is touching the floor, false if not
     */
    public static boolean floorCheck (Rectangle2D.Float hitbox, int[][] lvlData) {
        // Check the pixel below bottomleft and bottomright
        if (!isSolid(hitbox.x, hitbox.y + hitbox.height + 10, lvlData))
            if (!isSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData))
                return false;

        return true;
    }

    /**
     * 
     * @param levelData
     * @param firstHitbox
     * @param secondHitbox
     * @param yTile
     * @return true if there is a clear line of sight between both hitboxes
     */
    public static boolean isSightClear(int[][] levelData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox,
            int yTile) {
        final int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        final int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);

        if (firstXTile == secondXTile)
            return true;

        // make sure that all of the tiles are walkable between both points
        if (firstXTile < secondXTile) {
            // if the first hitbox is on the left, that is the start
            return isAllTilesWalkable(firstXTile, secondXTile, yTile, levelData);
        } else {
            // if the second hitbox is on the left, that is the start
            return isAllTilesWalkable(secondXTile, firstXTile, yTile, levelData);
        }
    }

    /**
     * Check if a tile is walkable based on the xSpeed given into the function.
     * 
     * @param hitbox  - The hitbox of the caller entity
     * @param xSpeed  - The current X-Speed of the caller entity
     * @param lvlData - The data that makes up the current level
     * @return
     */
    public static boolean isTileWalkable(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        if (xSpeed < 0) {
            // moving left, x = x
            return isSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        } else {
            // moving right, x = x + width
            return isSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        }
    }

    /**
     * Checking the row of tiles so make sure that all are walkable
     * 
     * @param xStart  - The starting X-Coordinate
     * @param xEnd    - The ending X-Coordinate
     * @param y       - The Y-Position of both entities
     * @param lvlData - The data that makes up the current level
     * @return
     */
    public static boolean isAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        // check the tiles on same level and 1 down
        for (int i = 0; i < xStart - xEnd; i++) {
            if (isTileSolid(xStart + i, y, lvlData))
                return false;

            if (!isTileSolid(xStart + i, y + 1, lvlData))
                return false;
        }
        return true;
    }
}

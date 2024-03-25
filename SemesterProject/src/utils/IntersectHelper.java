/**
 * IntersectHelper.java
 * @author
 * @date Mar 22, 2024
 * @description
 */
package utils;

import entities.Player;
import levels.Level;
import levels.LevelManager;
import main.Game;

/**
 *
 */
public class IntersectHelper {

    public static void main(String[] args) {
        new Player(1, 1, 1, 1, null);
//        canMoveHere(1, 1, 0, 0, levelManager.getCurrentLevel());
    }

    /**
     * Checks whether an entity is able to a specific x,y coordinate , by checking every
     * corner of the entities hitbox for a collision with another sprite.
     * 
     * The order of checks is: Top left, Bottom right, Top right, Bottom left.
     * 
     * @param x      - X-Position of caller entity to be checked
     * @param y      - Y-Position of caller entity to be checked
     * @param width  - Width of the hitbox of caller entity
     * @param height - Height of the hitbox of caller entity
     * @param level  - The level to be checked
     * @return - True if the entity is able to move, false if it cannot
     */
    public static boolean canMoveHere(float x, float y, float width, float height, Level level) {
        System.out.println((int) (x / Game.TILES_SIZE) + "\t" + (int) (y / Game.TILES_SIZE));

        // check top left and bottom right first in case of arial movement
        final int[][] levelData = level.getLevelData();
        for (int i = 0; i < levelData.length; i++) {
            for (int j = 0; j < levelData[i].length; j++) {
                System.out.print(levelData[i][j] + " ");
            }
            System.out.println();
        }
        return false;
    }

}

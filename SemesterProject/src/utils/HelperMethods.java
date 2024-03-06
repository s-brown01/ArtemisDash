
package utils;

import java.awt.geom.Rectangle2D;

import main.Game;

public class HelperMethods {
    
    /**
     * THIS IS A TEMPORARY METHOD, used to determine if a hitbox is within the screen
     * 
     * @param hitbox
     * @return
     */
    public static boolean InBounds(Rectangle2D.Float hitbox) {
        return (hitbox.x >= 0 && hitbox.x + hitbox.width <= Game.GAME_WIDTH &&
                            hitbox.y >= 0 && hitbox.y + hitbox.height <= Game.GAME_HEIGHT);
    }

}

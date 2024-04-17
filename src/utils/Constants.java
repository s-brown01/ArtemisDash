package utils;

import java.awt.Color;
import java.awt.Point;

import main.Game;

/**
 * Handles every constant variable that will be used for this game for all classes. <BR>
 * Certain constants are within static Classes. This is, in part, an effort to organize
 * and categorize the constants under one descriptive umbrella. (i.e All background
 * variables will be in the BackgroundStates class)
 * 
 * @author John Botonakis and Sean-Paul Brown
 */
public class Constants {

    /**
     * Background Parallax Assets (i.e. mist, rocks, etc)
     */
    public static class BackgroundStates {
        /**
         * How wide the image of the background mist is
         */
        public static final int BGMYST_WIDTH_DEFAULT = 450;
        /**
         * How tall the image of the background mist is
         */
        public static final int BGMYST_HEIGHT_DEFAULT = 101;
        /**
         * How wide the image of the background-rocks is
         */
        public static final int BGROCKS_WIDTH_DEFAULT = 448;
        /**
         * How wide the image of the background-rocks is
         */
        public static final int BGROCKS_HEIGHT_DEFAULT = 101;

        /**
         * How wide the mist will be when scaled with the game
         */
        public static final int BGMYST_WIDTH = (int) (BGMYST_WIDTH_DEFAULT * Game.SCALE);
        /**
         * How tall the mist will be when scaled with the game
         */
        public static final int BGMYST_HEIGHT = (int) (BGMYST_HEIGHT_DEFAULT * Game.SCALE);

        /**
         * How wide the rocks will be scaled with the game
         */
        public static final int BGROCKS_WIDTH = (int) (BGROCKS_WIDTH_DEFAULT * Game.SCALE);
        /**
         * How tall the rocks will be scaled with the Game
         */
        public static final int BGROCKS_HEIGHT = (int) (BGROCKS_HEIGHT_DEFAULT * Game.SCALE);
    }

    /**
     * Menu Button / Pause Button Vars and Constants
     */
    public static class ButtonStates {
        /**
         * How wide the button images are
         */
        public static final int B_WIDTH_DEFAULT = 140;
        /**
         * How tall the button images are
         */
        public static final int B_HEIGHT_DEFAULT = 56;
        /**
         * How wide the buttons will be scaled with the game
         */
        public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
        /**
         * How tall the buttons will be scaled with the game
         */
        public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        /**
         * How big (length and width) each sound button image is
         */
        public static final int SOUNDSIZE_DEFAULT = 42;
        /**
         * How big (length and width) each sound button will be (scaled with the game)
         */
        public static final int SOUNDSIZE = (int) (SOUNDSIZE_DEFAULT * Game.SCALE);
    }

    /**
     * The gravity used for all in-game Entities and Objects
     */
    public static final float GRAVITY = 0.04f * Game.SCALE;
    /**
     * The animation speed for all in-game Entities and Objects
     */
    public static final int ANISPEED = 18;

    /**
     * All Constants for the Overworld
     */
    public static class OverworldButtonConstants {
        /**
         * This is the size of 1 side of the square Overworld Buttons
         */
        public static final int BUTTON_SIZE = (int) (25 / 1.75f * Game.SCALE);

        /**
         * The color of buttons representing hidden levels
         */
        public static final Color HIDDEN = new Color(105, 97, 101);
        /**
         * The color of buttons representing hidden levels when the mouse is over it
         */
        public static final Color HIDDEN_HIGHLIGHT = new Color(50, 50, 50);

        /**
         * The color of buttons representing completed levels
         */
        public static final Color COMPLETED = new Color(128, 191, 255);
        /**
         * The color of buttons representing completed levels when the mouse is over it
         */
        public static final Color COMPLETED_HIGHLIGHT = new Color(77, 166, 255);
        /**
         * The color of buttons representing completed levels when the mouse is clicked on it
         */
        public static final Color COMPLETED_CLICKED = new Color(67, 155, 255);

        /**
         * The color of buttons representing not hidden and not completed levels
         */
        public static final Color DEFAULT = new Color(179, 102, 255);
        /**
         * The color of buttons representing not hidden and not completed levels when the mouse is
         * hovering on it
         */
        public static final Color DEFAULT_HIGHLIGHT = new Color(129, 73, 184);
        /**
         * The color of buttons representing not hidden and not completed levels when the mouse is
         * clicked on it
         */
        public static final Color DEFAULT_CLICKED = new Color(102, 58, 145);

        /**
         * this is the color of the outline around each button
         */
        public static final Color OUTLINE = new Color(0, 0, 55);

        /**
         * Each point here represents one of the clickable buttons that map to different levels on
         * the Overworld menu. The point is the top-right coordinate of each button.
         * 
         * all points were calculated on paper arbitrary points that look good on screen divide by
         * 1.75f because that is the default scale, then multiple by scale
         * 
         */
        private static final Point LEVEL_1 = new Point((int) (660 / 1.75f * Game.SCALE),
                (int) (190 / 1.75f * Game.SCALE));
        private static final Point LEVEL_2 = new Point((int) (680 / 1.75f * Game.SCALE),
                (int) (220 / 1.75f * Game.SCALE));
        private static final Point LEVEL_3 = new Point((int) (680 / 1.75f * Game.SCALE),
                (int) (280 / 1.75f * Game.SCALE));
        private static final Point LEVEL_4 = new Point((int) (720 / 1.75f * Game.SCALE),
                (int) (250 / 1.75f * Game.SCALE));
        private static final Point LEVEL_5 = new Point((int) (520 / 1.75f * Game.SCALE),
                (int) (340 / 1.75f * Game.SCALE));
        private static final Point LEVEL_6 = new Point((int) (650 / 1.75f * Game.SCALE),
                (int) (380 / 1.75f * Game.SCALE));
        private static final Point LEVEL_7 = new Point((int) (795 / 1.75f * Game.SCALE),
                (int) (420 / 1.75f * Game.SCALE));
        private static final Point LEVEL_8 = new Point((int) (760 / 1.75f * Game.SCALE),
                (int) (350 / 1.75f * Game.SCALE));
        private static final Point LEVEL_9 = new Point((int) (870 / 1.75f * Game.SCALE),
                (int) (390 / 1.75f * Game.SCALE));
        private static final Point LEVEL_10 = new Point((int) (400 / 1.75f * Game.SCALE),
                (int) (475 / 1.75f * Game.SCALE));
        private static final Point LEVEL_11 = new Point((int) (600 / 1.75f * Game.SCALE),
                (int) (515 / 1.75f * Game.SCALE));
        private static final Point LEVEL_12 = new Point((int) (800 / 1.75f * Game.SCALE),
                (int) (540 / 1.75f * Game.SCALE));
        private static final Point LEVEL_13 = new Point((int) (870 / 1.75f * Game.SCALE),
                (int) (480 / 1.75f * Game.SCALE));
        private static final Point LEVEL_14 = new Point((int) (800 / 1.75f * Game.SCALE),
                (int) (610 / 1.75f * Game.SCALE));
        private static final Point LEVEL_15 = new Point((int) (900 / 1.75f * Game.SCALE),
                (int) (625 / 1.75f * Game.SCALE));
        /**
         * This is a final array of all 15 Levels, so that it can be easily access by the
         * Overworld GameState
         */
        public static final Point[] BUTTON_POINT_ARRAY = new Point[] { LEVEL_1, LEVEL_2, LEVEL_3, LEVEL_4, LEVEL_5,
                LEVEL_6, LEVEL_7, LEVEL_8, LEVEL_9, LEVEL_10, LEVEL_11, LEVEL_12, LEVEL_13, LEVEL_14, LEVEL_15 };

    }

    /**
     * End Screen buttons (Both for level completed and Death screen)
     */
    public static class EndButtons {
        public static final int ENDBUTTON = 56;
        public static final int ENDBUTTON_SIZE = (int) (ENDBUTTON * Game.SCALE);
    }

    /**
     * Direction Variables
     */
    public static class Directions {
        /**
         * An arbitrary number that represents left
         */
        public static final int LEFT = 0;
        /**
         * An arbitrary number that represents up
         */
        public static final int UP = 1;
        /**
         * An arbitrary number that represents right
         */
        public static final int RIGHT = 2;
        /**
         * An arbitrary number that represents down
         */
        public static final int DOWN = 3;
    }

    /**
     * Player Animation States and Constants
     */
    public static class PlayerStates {
        /**
         * The animation index of being IDLE
         */
        public static final int IDLE = 0;
        /**
         * The animation index of RUNNING or moving
         */
        public static final int RUNNING = 1;
        /**
         * The animation index of JUMPING
         */
        public static final int JUMPSTART = 2;
        /**
         * The animation index of FALLING down
         */
        public static final int FALL = 9;
        /**
         * The animation index of being DYING
         */
        public static final int DIE = 4;
        /**
         * The animation index of being DASHING
         */
        public static final int DASH = 5;
        // public static final int DASHEND = 99;
        /**
         * The animation index of being DRAWING an arrow or shooting
         */
        public static final int DRAW = 6;
        /**
         * The animation index of taking DAMAGE
         */
        public static final int DAMAGE = 8;

        /**
         * This is how much health the Player will be starting each level with
         */
        public static final int STARTING_HEALTH = 2;
        /**
         * This is how wide each player image is in pixels
         */
        public static final int IMAGE_WIDTH = 55;
        /**
         * This is how tall each player image is in pixels
         */
        public static final int IMAGE_HEIGHT = 65;

        /**
         * This is how far (horizontally) the arrow should be spawning from the Player's hitbox's
         * x. This was calculated using the player's animations and how far the bow was from the
         * top left
         */
        public static final int SHOT_OFFSET_X = 34;
        /**
         * This is how far (vertically) the arrow should be spawning from the Player's hitbox's y.
         * This was calculated using the player's animations and how far the bow was from the top
         * left
         */
        public static final int SHOT_OFFSET_Y = 11;

        /**
         * Get the specifc amount of sprites (how many frames an action takes) per action for the
         * Player.
         * 
         * @param player_action - the action to get the sprite amount for, based on the constants
         *                      above in this class.
         * @return - the amount of sprites that the specific action takes, defaults to 1.
         */
        public static int getSpriteAmt(int player_action) {
            switch (player_action) {
            case IDLE:
                return 12;
            case RUNNING:
                return 10;
            case JUMPSTART:
                return 9;
            case FALL:
                return 3;
            case DIE:
                return 20;
            case DASH:
                return 10;
            case DRAW:
                return 6;
            case DAMAGE:
                return 6;
            default:
                return 1;
            }
        }

    }

    /**
     * Enemy Animation States and Constants
     */
    public static class EnemyConstants {
        /**
         * An arbitrary number that correlated to Enemy type Skeleton
         */
        public static final int SKELETON = 0;

        /**
         * This is an arbitrary number that represents the Skeleton King
         */
        public static final int SKELETON_KING = 1;

        /**
         * the animation index correlating to the IDLE animation
         */
        public static final int IDLE = 0;
        /**
         * the animation index correlating to the RUNNING animation
         */
        public static final int RUNNING = 1;
        /**
         * the animation index correlating to the ATTACK animation
         */
        public static final int ATTACK = 2;
        /**
         * the animation index correlating to the HIT (taking damage) animation
         */
        public static final int HIT = 3;
        /**
         * the animation index correlating to the DEAD animation
         */
        public static final int DEAD = 4;

        /**
         * How wide the skeleton's images are
         */
        public static final int SKELETON_WIDTH_DEFAULT = 55;
        /**
         * How tall the skeleton's images are
         */
        public static final int SKELETON_HEIGHT_DEFAULT = 64;
        /**
         * How wide the skeleton's sprites will be in game
         */
        public static final int SKELETON_WIDTH = (int) (SKELETON_WIDTH_DEFAULT * Game.SCALE);
        /**
         * How tall the skeleton's sprites will be in game
         */
        public static final int SKELETON_HEIGHT = (int) (SKELETON_HEIGHT_DEFAULT * Game.SCALE);
        /**
         * How wide the skeleton's hitbox is when drawn on the images
         */
        public static final int SKELETON_HITBOX_WIDTH = (int) (23 * Game.SCALE);
        /**
         * How tall the skeleton's hitbox is when drawn on the images
         */
        public static final int SKELETON_HITBOX_HEIGHT = (int) (36 * Game.SCALE);
        /**
         * How far the horizontal offset is from image to hitbox for a regular skeleton
         */
        public static final int SKELETON_DRAW_OFFSET_X = (int) (18 * Game.SCALE);
        /**
         * How far the vertical offset is from image to hitbox for a regular skeleton
         */
        public static final int SKELETON_DRAW_OFFSET_Y = (int) (28 * Game.SCALE);
        /**
         * This is how many times larger the Skeleton is than a regular Skeleton
         */
        public static final float SKELETON_KING_SCALE = 1f;
        /**
         * How wide the skeleton king's sprites will be in game
         */
        public static final int SKELETON_KING_WIDTH = (int) (SKELETON_KING_SCALE * SKELETON_WIDTH);
        /**
         * How tall the skeleton king's sprites will be in game
         */
        public static final int SKELETON_KING_HEIGHT = (int) (SKELETON_KING_SCALE * SKELETON_HEIGHT);
        /**
         * How wide the skeleton king's hitbox is when drawn on the images
         */
        public static final int SKELETON_KING_HITBOX_WIDTH = (int) (SKELETON_KING_SCALE * SKELETON_HITBOX_WIDTH);
        /**
         * How tall the skeleton king's hitbox is when drawn on the images
         */
        public static final int SKELETON_KING_HITBOX_HEIGHT = (int) (SKELETON_KING_SCALE * SKELETON_HITBOX_HEIGHT);
        /**
         * How far the horizontal offset is from image to hitbox for a Skeleton King
         */
        public static final int SKELETON_KING_DRAW_OFFSET_X = (int) (SKELETON_KING_SCALE * SKELETON_DRAW_OFFSET_X);
        /**
         * How far the vertical offset is from image to hitbox for a Skeleton King
         */
        public static final int SKELETON_KING_DRAW_OFFSET_Y = (int) (SKELETON_KING_SCALE * SKELETON_DRAW_OFFSET_Y);

        /**
         * Get how every many sprites a specific action take for a specific enemy. This returns
         * how many frames/sprites that action takes.
         * 
         * @param enemy_type   - what class the enemy is, based on the static variables in
         *                     EnemyConstants
         * @param enemy_action - what action is happening, based on the static variables in
         *                     EnemyConstants
         * @return - the amount of frames that that specific action takes
         */
        public static int getSpriteAmount(int enemy_type, int enemy_action) {
            // each enemy will have different frame counts for each action
            // first see what enemy it is
            // then see what action, then return appropriate number
            switch (enemy_type) {
            // both Skeleton King and Skeleton have same sprite amounts
            case SKELETON_KING:
            case SKELETON:
                switch (enemy_action) {
                case IDLE:
                    return 10;
                case RUNNING:
                    return 13;
                case ATTACK:
                    return 18;
                case HIT:
                    return 8;
                case DEAD:
                    return 15;
                default:
                    return 0;
                }
            default:
                return 0;
            }
        }

        /**
         * This function will return the maximum health points that a specific enemy type has.
         * This function is based on the static variables of EnemyConstants and it uses a
         * case-switch.
         * 
         * @param enemy_type - what class the enemy is, based on the static variables in
         *                   EnemyConstants
         * @return - return the specific amount of health for that enemy, defaults to 1
         */
        public static int getMaxHealth(int enemy_type) {
            switch (enemy_type) {
            case SKELETON_KING:
                return 3;
            case SKELETON:
                return 1;
            default:
                return 1;
            }
        }

        /**
         * This function will return the damage per attack for a specific enemy type. This
         * function is based on the static variables of EnemyConstants and it uses a case-switch.
         * 
         * @param enemy_type - what class the enemy is, based on the static variables in
         *                   EnemyConstants
         * @return - return the specific amount of damage per attack for that enemy, defaults to 0
         */
        public static int getAttackDamage(int enemy_type) {
            switch (enemy_type) {
            case SKELETON_KING:
                return 1;
            case SKELETON:
                return 1;
            default:
                return 0;
            }
        }

        /**
         * This function returns the walk speed for a specific enemy. It represents how fast the
         * enemy moves while they are in their running animation. It is based on the
         * EnemyConstants values.
         * 
         * @param enemy_type - the integer that represents a specific enemy type (based on the
         *                   EnemyConstants values)
         * @return the walk speed for a specific enemy.
         */
        public static float getWalkSpeed(int enemy_type) {
            switch (enemy_type) {
            case SKELETON_KING:
                return 1.00f;
            case SKELETON:
                return 0.80f;
            default:
                return 0.80f;
            }
        }

        /**
         * This function returns the attacking walk speed for a specific enemy. It represents how
         * fast the enemy moves while they are in their attacking animation. It is based on the
         * EnemyConstants values.
         * 
         * @param enemy_type - the integer that represents a specific enemy type (based on the
         *                   EnemyConstants values)
         * @return the attacking walk speed for a specific enemy.
         */
        public static float getAttackWalkSpeed(int enemy_type) {
            switch (enemy_type) {
            case SKELETON_KING:
                return 1.00f * .5f;
            case SKELETON:
                return 0;
            default:
                return 0;
            }
        }
    }

    /**
     * Projectile Animation States and Constants
     */
    public static class ProjectileConstants {
        /**
         * an arbitrary number that correlates to the type of Projectile an Arrow is
         */
        public static final int ARROW = 0;

        /** the width of the Arrow's image */
        public static final int ARROW_IMG_WIDTH = 12;
        /**
         * the height of the arrow's image
         */
        public static final int ARROW_IMG_HEIGHT = 8;
        /**
         * the width adjusted for the game's scale
         */
        public static final int ARROW_WIDTH = (int) (ARROW_IMG_WIDTH * Game.SCALE);
        /**
         * the height adjusted for the game's scale
         */
        public static final int ARROW_HEIGHT = (int) (ARROW_IMG_HEIGHT * Game.SCALE);
        /**
         * How wide the arrow's hitbox should be
         */
        public static final int ARROW_HITBOX_WIDTH = (int) (40 * Game.SCALE);
        /**
         * how tall the Arrow's hitbox should be
         */
        public static final int ARROW_HITBOX_HEIGHT = (int) (15 * Game.SCALE);
        /**
         * how far to draw the image from the hitbox
         */
        public static final int ARROW_DRAW_OFFSET_X = (int) (0 * Game.SCALE);
        /**
         * how far to draw the image from the hitbox
         */
        public static final int ARROW_DRAW_OFFSET_Y = (int) (0 * Game.SCALE);

        /** the horizontal speed of the arrow */
        public static final float ARROW_SPEED = 3.5f;
        /** how much damage an entity will take if hit with an ARROW */
        public static final int ARROW_DAMAGE = 1;

        /**
         * Get the specific height of a hitbox for a Projectile based on the constants above
         * 
         * @param projType - the type of Projectile to get height for, the constants in this class
         * @return - the height of the hitbox for the Projectile
         */
        public static int getProjHeight(int projType) {
            switch (projType) {
            case ARROW:
                return ARROW_HITBOX_HEIGHT;
            default:
                return 30;
            }
        }

        /**
         * Get the specific width of a hitbox for a Projectile based on the constants above
         * 
         * @param projType - the type of Projectile to get width for, the constants in this class
         * @return - the width of the hitbox for the Projectile
         */
        public static int getProjWidth(int projType) {
            switch (projType) {
            case ARROW:
                return ARROW_HITBOX_WIDTH;
            default:
                return 60;
            }
        }

        /**
         * @deprecated THIS METHOD IS NOT USED
         * 
         *             Get the specific speed of a hitbox for a Projectile based on the constants
         *             above
         * 
         * @param projType - the type of Projectile to get speed for, the constants in this class
         * @return - the speed of the hitbox for the Projectile
         */
        public static float getProjSpeed(int projType) {
            switch (projType) {
            case ARROW:
                return 2.0f * Game.SCALE;
            default:
                return 2.0f * Game.SCALE;
            }
        }

    }
}

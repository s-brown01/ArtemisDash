/**
 * CONSTANTS Class
 * @author johnbotonakis
 * Handles every constant variable that will be used for this game;
 * As of now, it is mainly used to pilot the animations and direct 
 * the game to where each animation is, on the sprite sheet
 */
package utils;

import java.awt.Color;
import java.awt.Point;

import main.Game;

public class Constants {

    public static class UI {
        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }
    }

    public static final float GRAVITY = 0.04f * Game.SCALE;
    public static final int ANISPEED = 18;
    
    public static class OverworldButtonConstants {
        public static final int BUTTON_SIZE = (int)(25/1.75f * Game.SCALE);
        
        // the colors to be used for overworld buttons
        public static final Color HIDDEN = new Color(0, 0, 0);
        public static final Color HIDDEN_HIGHLIGHT = new Color(50, 50, 50);
        public static final Color COMPLETED = new Color(0,0,100);
        public static final Color COMPLETED_HIGHLIGHT = new Color(0,0,150);
        public static final Color COMPLETED_CLICKED = new Color(0,0,200);
        public static final Color DEFAULT = new Color(0,100,0);
        public static final Color DEFAULT_HIGHLIGHT = new Color(0,150,0);
        public static final Color DEFAULT_CLICKED = new Color(0,200,0);
        public static final Color OUTLINE = new Color(212,175,55);
        
        /*
         *  all points were calculated on paper
         *  arbitrary points that look good on screen
         *  divide by 1.75f because that is the default scale, then multiple by scale
         */
        private static final Point POINT_1 = new Point((int)(660/1.75f * Game.SCALE), (int)(190/1.75f * Game.SCALE));
        private static final Point POINT_2 = new Point((int)(680/1.75f * Game.SCALE), (int)(220/1.75f * Game.SCALE));
        private static final Point POINT_3 = new Point((int)(680/1.75f * Game.SCALE), (int)(280/1.75f * Game.SCALE));
        private static final Point POINT_4 = new Point((int)(720/1.75f * Game.SCALE), (int)(250/1.75f * Game.SCALE));
        private static final Point POINT_5 = new Point((int)(520/1.75f * Game.SCALE), (int)(340/1.75f * Game.SCALE));
        private static final Point POINT_6 = new Point((int)(650/1.75f * Game.SCALE), (int)(380/1.75f * Game.SCALE));
        private static final Point POINT_7 = new Point((int)(795/1.75f * Game.SCALE), (int)(420/1.75f * Game.SCALE));
        private static final Point POINT_8 = new Point((int)(760/1.75f * Game.SCALE), (int)(350/1.75f * Game.SCALE));
        private static final Point POINT_9 = new Point((int)(870/1.75f * Game.SCALE), (int)(390/1.75f * Game.SCALE));
        private static final Point POINT_10 = new Point((int)(400/1.75f * Game.SCALE), (int)(475/1.75f * Game.SCALE));
        private static final Point POINT_11 = new Point((int)(600/1.75f * Game.SCALE), (int)(515/1.75f * Game.SCALE));
        private static final Point POINT_12 = new Point((int)(800/1.75f * Game.SCALE), (int)(540/1.75f * Game.SCALE));
        private static final Point POINT_13 = new Point((int)(870/1.75f * Game.SCALE), (int)(480/1.75f * Game.SCALE));
        private static final Point POINT_14 = new Point((int)(800/1.75f * Game.SCALE), (int)(610/1.75f * Game.SCALE));
        private static final Point POINT_15 = new Point((int)(900/1.75f * Game.SCALE), (int)(625/1.75f * Game.SCALE));
        public static final Point[] BUTTON_POINT_ARRAY = new Point[] {POINT_1, POINT_2, POINT_3, POINT_4, POINT_5, POINT_6, POINT_7, POINT_8, POINT_9, POINT_10, POINT_11, POINT_12, POINT_13, POINT_14, POINT_15};

    }

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMPSTART = 2;
        public static final int JUMPEND = 9;
        public static final int DIE = 4;
        public static final int DASH = 5;
        public static final int DASHEND = 99;
        public static final int DRAW = 6;
        public static final int DAMAGE = 999;

        public static int getSpriteAmt(int player_action) {
            switch (player_action) {
            case IDLE:
                return 12;
            case RUNNING:
                return 10;
            case JUMPSTART:
                return 9;
            case JUMPEND:
                return 3;
            case DIE:
                return 20;
            case DASH:
                return 10;
            case DASHEND:
                return 3;
            case DRAW:
                return 6;
            case DAMAGE:
                return 6;
            default:
                return 1;
            }
        }

    }

    public static class EnemyConstants {
        // ENEMY TYPES
        // TODO: MAKE INTS FOR EACH ENEMY TYPE
        public static final int SKELETON = 0;

        // ACTIONS
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        // TODO: DELETE LATER -- TEMPORARY VARAIBLE USING YOUTUBE TUTORIAL ENEMY
        public static final int CRABBY = 1;
        public static final int CRABBY_WIDTH_DEFAULT = 72;
        public static final int CRABBY_HEIGHT_DEFAULT = 32;
        public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * Game.SCALE);
        public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * Game.SCALE);
        // since these will be used for many crabbies they should be here
        // 26 and 9 is the difference from start of hitbox from actual corners
        public static final int CRABBY_DRAW_OFFSET_X = (int) (26 * Game.SCALE);
        public static final int CRABBY_DRAW_OFFSET_Y = (int) (9 * Game.SCALE);

        public static final int SKELETON_WIDTH_DEFAULT = 55;
        public static final int SKELETON_HEIGHT_DEFAULT = 64;
        public static final int SKELETON_WIDTH = (int) (SKELETON_WIDTH_DEFAULT * Game.SCALE);
        public static final int SKELETON_HEIGHT = (int) (SKELETON_HEIGHT_DEFAULT * Game.SCALE);
        public static final int SKELETON_HITBOX_WIDTH = (int) (23 * Game.SCALE);
        public static final int SKELETON_HITBOX_HEIGHT = (int) (36 * Game.SCALE);
        public static final int SKELETON_DRAW_OFFSET_X = (int) (18 * Game.SCALE);
        public static final int SKELETON_DRAW_OFFSET_Y = (int) (28 * Game.SCALE);

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
            case SKELETON:
                switch (enemy_action) {
                case IDLE:
                    return 11;
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
            case CRABBY:
                switch (enemy_action) {
                case IDLE:
                    return 9;
                case RUNNING:
                    return 6;
                case ATTACK:
                    return 7;
                case HIT:
                    return 4;
                case DEAD:
                    return 5;
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
            case SKELETON:
                return 1;
            case CRABBY:
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
            case SKELETON:
                return 1;
            case CRABBY:
                return 1;
            default:
                return 0;
            }
        }
    }

    public static class BossConstants {
        // Boss Types
        public static final int BOSS1 = 0;
        public static final int BOSS2 = 1;
        public static final int BOSS3 = 2;

        // Actions
        public static final int IDLE = 100;
        public static final int RUNNING = 101;
        // I'm not sure on how many attack each boss has... just example code
        public static final int ATTACK1 = 1021;
        public static final int ATTACK2 = 1022;
        public static final int ATTACK3 = 1023;
        public static final int HIT = 103;
        public static final int DEAD = 104;

        /**
         * Get how every many sprites a specific action take for a specific boss. This returns how
         * many frames/sprites that action takes.
         * 
         * @param boss_type   - what class the boss is, based on the static variables in
         *                    BossConstants
         * @param boss_action - what action is happening, based on the static variables in
         *                    BossConstants
         * @return - the amount of frames that that specific action takes
         */
        public static int getSpriteAmount(int boss_type, int boss_action) {
            // first see what boss it is
            // then see what action, then return appropriate number
            switch (boss_type) {
            case BOSS1:
            case BOSS2:
            case BOSS3:
                switch (boss_action) {
                case IDLE:
                case RUNNING:
                case ATTACK1:
                case ATTACK2:
                case ATTACK3:
                case HIT:
                case DEAD:
                default:
                    return 0;
                }
            default:
                return 0;
            }
        }

        /**
         * This function will return the maximum health points that a specific boss type has. This
         * function is based on the static variables of BossConstants and it uses a case-switch.
         * 
         * @param boss_type - what class the enemy is, based on the static variables in
         *                  BossConstants
         * @return - return the specific amount of health for that enemy, defaults to 1
         */
        public static int getMaxHealth(int boss_type) {
            switch (boss_type) {
            case BOSS1:
            case BOSS2:
            case BOSS3:
                return 2;
            default:
                return 0;
            }
        }

        /**
         * This function will return the damage per attack for a specific boss type. This function
         * is based on the static variables of BossConstants and it uses a case-switch.
         * 
         * @param boss_type - what class the enemy is, based on the static variables in
         *                  BossConstants
         * @return - return the specific amount of damage per attack for that enemy, defaults to 0
         */
        public static int getAttackDamage(int boss_type) {
            switch (boss_type) {
            case BOSS1:
            case BOSS2:
            case BOSS3:
                return 2;
            default:
                return 0;
            }
        }

    }
}

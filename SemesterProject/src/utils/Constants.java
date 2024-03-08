/**
 * CONSTANTS Class
 * Handles every constant variable that will be used for this game;
 * As of now, it is mainly used to pilot the animations and direct 
 * the game to where each animation is, on the sprite sheet
 */
package utils;

import main.Game;

public class Constants {

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP_START = 2;
        public static final int LAND = 3;
        public static final int DEATH = 4;
        public static final int DASH_START = 5;
        public static final int DASH_END = 6;
        public static final int ATTACK = 7;
        public static final int ATTACK_END = 9;
        public static final int HIT = 10;
        public static final int FALLING = 11;
        public static final int SLIDE = 12;

        public static int getAnimationLength(int player_action) {
            switch (player_action) {
            case IDLE:
                return 12;
            case RUNNING:
                return 10;
            case JUMP_START:
                return 11;
            case LAND:
                return 3;
            case DEATH:
                return 20;
            case DASH_START:
                return 10;
            case DASH_END:
                return 2;
            case ATTACK:
                return 8;
            case ATTACK_END:
                return 5;
            case HIT:
                return 6;
            case FALLING:
                return 5;
            case SLIDE:
                return 11;
            default:
                return 1;
            }
        }
    }
}

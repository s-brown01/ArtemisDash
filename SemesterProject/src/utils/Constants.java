package utils;

import main.Game;

public class Constants {
	
	public enum Directions {
		LEFT, UP, RIGHT, DOWN;
	}
	
	public static class EntityConstants {
		// multiplying these by SCALE means it will remain proportionally the same
		public static float GRAVITY = 1.5f * Game.SCALE;
		public static float WALK_SPEED = 1.5f * Game.SCALE;
	}
	
	public static class PlayerConstants {
    	// TODO: double check numbers/names
		public static final int IDLE = 1;
        public static final int RUNNING = 2;
        public static final int JUMP = 3;
        public static final int LAND = 4;
        public static final int DEAD = 5;
        public static final int DASH = 6;
        public static final int DASH_END = 7;
        public static final int ATTACK_CHARGE = 8;
        public static final int ATTACK_SHOOT = 9;
        public static final int HIT = 10;
        public static final int FALLING = 11;
        public static final int SLIDE = 12;
        
        /**
         * This method will get however many sprites each animation takes.
         * @param playerAction - what action the player is currently in
         * @return - the amount of sprites/frames per animation
         */
        public static int getSpriteAmount(int playerAction) {
        	switch(playerAction) {
        	case IDLE:
        		return 12;
        	case RUNNING:
        		return 10;
        	case JUMP:
        		return 11;
        	case LAND:
        		return 3;
        	case DEAD:
        		return 20;
        	case ATTACK_CHARGE:
        		return 8;
        	case ATTACK_SHOOT:
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

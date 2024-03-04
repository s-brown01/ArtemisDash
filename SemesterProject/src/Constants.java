package utils;

public class Constants {

	public static class Directions{
		public static final int LEFT = 0;
		public static final int UP= 1;
		public static final int RIGHT= 2;
		public static final int DOWN= 3;
	}
	
	public static class PlayerConstants{
		public static final int IDLE =0;
		public static final int RUNNING =1;
		public static final int JUMP_START=2;
		public static final int JUMP_END=3;
		public static final int DEATH=4;
		public static final int DASH_START=5;
		public static final int DASH_END=6;
		
		public static int getAnimationLength(int player_action) {
			
			switch(player_action) {
			case IDLE:
				return 12;
			case RUNNING:
				return 10;
			case JUMP_START:
				return 11;
			case JUMP_END:
				return 3;
			case DEATH:
				return 20;
			case DASH_START:
				return 10;
			case DASH_END:
				return 2;
			default:
				return 1;
			
			}
		}
	}
}

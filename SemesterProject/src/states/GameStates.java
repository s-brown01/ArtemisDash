package states;

public enum GameStates {
	
	PLAYING, MENU, OVERWORLD, OPTIONS, QUIT;
	
	// every game should start on the menu
	public static GameStates state = MENU;
	// startin on PLAYING for now to test
//	public static GameStates state = PLAYING;

}


package states;

public enum GameStates {

    PLAYING, MENU, OVERWORLD, OPTIONS, QUIT;
	
	//TESTING: Start on PLAYING for testing of that state
	// public static GameStates state = PLAYING;

    //every game should start on the menu
    public static GameStates state = MENU;
    
    
    //TESTING: Start on OVERWORLD for testing of that state
    //public static GameStates state = OVERWORLD;
    
    //TESTING: Start on OPTIONS for testing of that state
    // public static GameStates state = OPTIONS;

}

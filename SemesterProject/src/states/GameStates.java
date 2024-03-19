/**
 * Game States Enum
 * @author johnbotonakis
 * This enum holds every state that the game can be in.
 * A "state" is defined as a part of the game where the standard controls
 * can be remapped to different functions depending on the current part that you are in
 */
package states;

public enum GameStates {

    PLAYING, MENU, OVERWORLD, OPTIONS, QUIT;

    // TESTING: Start on PLAYING for testing of that state
//     public static GameStates state = PLAYING;

    // every game should start on the menu
    public static GameStates state = MENU;

    // TESTING: Start on OVERWORLD for testing of that state
    // public static GameStates state = OVERWORLD;

    // TESTING: Start on OPTIONS for testing of that state
    // public static GameStates state = OPTIONS;

}

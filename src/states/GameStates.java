package states;

/**
 * This enum holds every state that the game can be in. A "state" is defined as a part of
 * the game where the standard controls can be remapped to different functions depending
 * on the current part that you are in
 * 
 * @author johnbotonakis
 */
public enum GameStates {

    PLAYING, MENU, OVERWORLD, OPTIONS, QUIT;

    // every game should start on the menu
    public static GameStates state = MENU;

}

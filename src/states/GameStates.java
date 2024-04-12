package states;

/**
 * This enum holds every state that the game can be in. A "state" is defined as a part of
 * the game where the standard controls can be remapped to different functions depending
 * on the current part that you are in
 * 
 * @author johnbotonakis
 */
public enum GameStates {

    /**
     * All of the different states that the could have.
     */
    PLAYING, MENU, OVERWORLD, OPTIONS, QUIT;

    /**
     * this is what the current state/screen of the game is. Every game starts on the main menu.
     */
    public static GameStates state = MENU;

}

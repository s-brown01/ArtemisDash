package states;

/**
 * This enum holds every state that the game can be in. A "state" is defined as a part of
 * the game where the standard controls can be remapped to different functions depending
 * on the current part that you are in
 * 
 * @author John Botonakis
 */
public enum GameStates {

    /**
     * Represents the state when the game is actively being played.
     */
    PLAYING,

    /**
     * Represents the main menu state of the game.
     */
    MENU,

    /**
     * Represents the overworld state of the game.
     */
    OVERWORLD,

    /**
     * Represents the how-to-play state of the game.
     */
    INSTRUCTIONS,

    /**
     * Represents the state when the player chooses to quit the game.
     */
    QUIT;

    /**
     * this is what the current state/screen of the game is. Every game starts on the main
     * menu.
     */
    public static GameStates state = MENU;

}

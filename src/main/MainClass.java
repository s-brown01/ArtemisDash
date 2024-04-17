package main;

/**
 * This main class acts as a singluar point to branch off from, when starting the game
 * 
 * @author John Botonakis and Sean-Paul Brown
 */
public class MainClass {
    /**
     * This function will create and start the Game
     * 
     * @param args - nothing
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
    }
}

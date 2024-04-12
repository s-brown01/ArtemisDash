package states;

import java.awt.event.MouseEvent;

import main.Game;
import ui.MenuButton;

/**
 * This state class defines how each state should behave when instantiated from this
 * parent class
 * 
 * @author John Botonakis and Sean-Paul Brown
 */
public abstract class State {

    /**
     * The game that the state's will be held/handled in
     * protected = private for children
     */
    protected Game game;

    /**
     * This is the constructor of State
     * @param game - the Game that will hold/handle all of the states
     */
    public State(Game game) {
        // there should be a game that the state is in
        this.game = game;
    }

    /**
     * This function determines if the mouseEvent parameter is contained within the menuButton's bounds.
     * 
     * @param e     - the current information about the MouseEvent
     * @param mb    - the MenuButton to be checked
     * @return true if the mouse is contained in the MenuButton's bounds, false if not
     */
    public boolean hoverOverButton(MouseEvent e, MenuButton mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }

    /**
     * Getter for the game 
     * @return the Game holding this state
     */
    public Game getGame() {
        return game;
    }

}

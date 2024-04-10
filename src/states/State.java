package states;

import java.awt.event.MouseEvent;

import main.Game;
import ui.MenuButton;

/**
 * This state class defines how each state should behave when instantiated from this
 * parent class
 * 
 * @author johnbotonakis
 */
public abstract class State {

    // protected = private for children
    protected Game game;

    public State(Game game) {
        // there should be a game that the state is in
        this.game = game;
    }

    public boolean hoverOverButton(MouseEvent e, MenuButton mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }

    public Game getGame() {
        return game;
    }

}

/**
 * State Abstract Class
 * @author johnbotonakis
 * This state class defines how each state should behave when instantiated from this parent class
 */
package states;

import java.awt.Font;
import java.awt.event.MouseEvent;

import main.Game;
import ui.MenuButton;

public abstract class State {

    // protected = private for children
    protected Game game;
    protected final Font boldFont = new Font(Font.DIALOG, Font.BOLD, 13);

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

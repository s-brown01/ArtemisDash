
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

    public boolean isInMB(MouseEvent e, MenuButton mb) {
        // TODO CHANGE THIS COMMAND, implement once we have MenuButtons
        // check if the given MenuButton contains the MouseEvent coordinates
        return false;
    }

    public Game getGame() {
        return game;
    }

}

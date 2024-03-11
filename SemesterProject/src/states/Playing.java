
package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import entities.Player;
import levels.LevelManager;
import main.Game;

public class Playing extends State implements StateMethods {

    // will keep track if the pause menu should be up or not
    private boolean paused = false;
    private Player player;
    private LevelManager levelManager;

    public Playing(Game game) {
        super(game);
        initClasses();
    }

    /**
     * Initialize all classes for a level here As of now, just player is initialized
     */
    private void initClasses() {
        levelManager = new LevelManager(game);
        player = new Player(200, 200, (int) (65), (int) 100);
        // Player(X-Position on Screen, Y-Position on screen, Width drawn, Height drawn)
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());

    }

    @Override
    public void update() {
        if (paused)
            // update pause menu or pause overlay
            return;

        // if !paused, this code will run
        // update everything else on screen
        player.update();
        return;

    }

    @Override
    public void draw(Graphics g) {
        g.setFont(boldFont);
        levelManager.draw(g);
        player.draw(g);

        // PAUSE SCREEN
        if (paused) {
            g.setColor(new Color(225, 225, 225, 200));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            g.setColor(Color.CYAN);
            g.fillRect(Game.GAME_WIDTH / 2 - 300, Game.GAME_HEIGHT / 2 - 300, 600, 600);
            g.setColor(Color.BLACK);
            g.drawString("PAUSED", Game.GAME_WIDTH / 2 - 20, Game.GAME_HEIGHT / 2);

            // draw the pause menu/overlay here
            // overlays should be after everything else
            return;
        }
        // NOT PAUSED
        g.setColor(Color.white);
        g.drawString("PLAYING SCREEN", Game.GAME_WIDTH / 2 - 30, 100);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    // mouse pressed is when the mouse is pushed down
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        // shoot when the mouse is pressed?

    }

    @Override
    // mouse clicked is when it is a press and release
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        // shoot when the mouse is pressed and released?
        if (e.getButton() == MouseEvent.BUTTON1) {
            player.setAttacking(true);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        // shoot when mouse is released?

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        // if cursor icon changes, updating recticle will be automatic

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // decide what to do with the different key inputs

        switch (e.getKeyCode()) {
        case KeyEvent.VK_W:
            player.setUp(true);
            break;
        case KeyEvent.VK_A:
            player.setLeft(true);
            break;
        case KeyEvent.VK_S:
            player.setDown(true);
            break;
        case KeyEvent.VK_D:
            player.setRight(true);
            break;
        case KeyEvent.VK_ESCAPE:
        case KeyEvent.VK_P:
            // if else statement here is only to debug/see what's happening w/o pause screen
            if (paused)
                System.out.println("Unpause - Playing class");
            else
                System.out.println("Pause - Playing class");
            // switch the current value of paused
            paused = !paused;
            break;
        case KeyEvent.VK_BACK_SPACE:
            GameStates.state = GameStates.OVERWORLD;
            break;
        case KeyEvent.VK_SPACE:
            player.setJump(true);
            break;
        default:
            break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // player.setMoving(false);
        // LATER TO DO: add setting the direction booleans to false
        switch (e.getKeyCode()) {
        case KeyEvent.VK_W:
            player.setUp(false);
            break;
        case KeyEvent.VK_A:
            player.setLeft(false);
            break;
        case KeyEvent.VK_S:
            player.setDown(false);
            break;
        case KeyEvent.VK_D:
            player.setRight(false);
            break;
        case KeyEvent.VK_SPACE:
            player.setJump(false);
            break;
        default:
            break;
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void pauseGame() {
        paused = true;
    }

    public void unpauseGame() {
        paused = false;
    }

    //If focus is lost within this window, 
    //freeze the player inputs and don't move
    public void windowFocusLost() {
        player.resetDirBooleans();
    }
}

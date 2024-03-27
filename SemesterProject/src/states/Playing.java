/**
 * Playing Class
 * @author johnbotonakis
 * This class handles the core game loop of completing levels
 */
package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import projectiles.Arrow;

public class Playing extends State implements StateMethods {

    // will keep track if the pause menu should be up or not
    private boolean paused = false;
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    
    private ArrayList<Arrow> arrowList = new ArrayList<>();

    public Playing(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        player = new Player(200, 200, (int) (55 * Game.SCALE), (int) (65 * Game.SCALE), this);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());

    }

    @Override
    public void update() {
        if (paused) {
            // update pause overlay
            return;
        }
        levelManager.update();
        player.update();
        enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);

    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g);
        enemyManager.draw(g);
        player.renderPlayer(g);
        for (Arrow a : arrowList)
            a.draw(g);
        if (paused) {
            g.setFont(boldFont);
            g.setColor(new Color(150, 150, 150, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            g.setColor(Color.cyan);
            g.drawString("PAUSED", Game.GAME_WIDTH / 2 - 50, Game.GAME_HEIGHT / 2);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        player.checkAttack(this, e); // THIS IS BAD, NOT WORK CORRECT
        // user has to click on 6th frame to shoot
        if (e.getButton() == MouseEvent.BUTTON1)
            player.setAttack(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_A:
            player.setLeft(true);
            break;
        case KeyEvent.VK_D:
            player.setRight(true);
            break;
        case KeyEvent.VK_SPACE:
            player.setJump(true);
            break;
        case KeyEvent.VK_P:
            paused = !paused;
            break;
        case KeyEvent.VK_BACK_SPACE:
            GameStates.state = GameStates.MENU;
            break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_A:
            player.setLeft(false);
            break;
        case KeyEvent.VK_D:
            player.setRight(false);
            break;
        case KeyEvent.VK_SPACE:
            player.setJump(false);
            break;
        case KeyEvent.VK_K:
            player.kill();
            break;
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void windowFocusLost() {
        player.resetDirBools();
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * @param x 
     * @param y 
     * @param slope 
     * 
     */
    public void addPlayerArrow(float x, float y, float slope) {
        System.out.println("ADDING NEW ARROW");
        arrowList.add(new Arrow((int)x, (int)y, (int)slope));
    }

}

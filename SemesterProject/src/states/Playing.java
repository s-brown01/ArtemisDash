package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import entities.Player;
import main.Game;

public class Playing extends State implements StateMethods {
	
	// will keep track if the pause menu should be up or not
	private boolean paused = false;
	private Player player;
	
	
	public Playing(Game game) {
		super(game);
		initClasses();
	}
	
	private void initClasses() {
		// create all classes here
		// player
		player = new Player();
		
	}
	
	@Override
	public void update() {
		if (paused)
			// update pause menu or pause overlay
			return;
		// if !paused, this code will run
		// update everything else on screen
		return;
		
	}

	public void draw(Graphics g, BufferedImage img) {
		draw(g);
		final BufferedImage temp = img.getSubimage(100, 70, 64, 60);
		g.drawImage(temp, Game.GAME_WIDTH/2 - temp.getWidth()/2, Game.GAME_HEIGHT/2 - temp.getHeight()/2, 120,120, null);
	}
	
	@Override
	public void draw(Graphics g) {
		// draw everything
		// background - tiles - player/enemies
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
		g.setColor(Color.white);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		g.drawString("PLAYING SCREEN", Game.GAME_WIDTH / 2 - 30, 100);
		if (paused)
			// draw the pause menu/overlay here
			// overlays should be after everything else
			return;
	}

	@Override
	public void MouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	// mouse pressed is when the mouse is pushed down
	public void MousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		// shoot when the mouse is pressed?
		
	}

	@Override
	// mouse clicked is when it is a press and release
	public void MouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		// shoot when the mouse is pressed and released?
		
		
	}

	@Override
	public void MouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		// shoot when mouse is released?
		
	}

	@Override
	public void MouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		// if cursor icon changes, updating recticle will be automatic
		
	}

	@Override
	public void KeyPressed(KeyEvent e) {
		// decide what to do with the different key inputs
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			System.out.println("W - Move Up - Playing");
			break;
		case KeyEvent.VK_A:
			System.out.println("A - Move Left - Playing");
			break;
		case KeyEvent.VK_S:
			System.out.println("S - Move Down - Playing");
			break;
		case KeyEvent.VK_D:
			System.out.println("D - Move Right - Playing");
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
			// REMOVE THIS LATER
			System.out.println("SWITCH BACK TO MENU");
			GameStates.state = GameStates.PLAYING;
		default:
			break;
		}
	}

	@Override
	public void KeyReleased(KeyEvent e) {
		// LATER TO DO: add setting the direction booleans to false
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			break;
		case KeyEvent.VK_A:
			break;
		case KeyEvent.VK_S:
			break;
		case KeyEvent.VK_D:
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

}

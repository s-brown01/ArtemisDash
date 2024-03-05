package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;

public class Overworld extends State implements StateMethods {
	
	private BufferedImage background;

	public Overworld(Game game) {
		super(game);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(175, 175, 200, 200));
		g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
		g.setColor(Color.black);
		g.drawString("OVERWORLD", Game.GAME_WIDTH / 2 - 15, 20);
		g.drawString("Click anywhere to continue to game", Game.GAME_WIDTH / 2 - 100, Game.GAME_HEIGHT / 2);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("click");
		if (e.getButton() == MouseEvent.BUTTON1) {
			System.out.println("SWITCH FROM OVERWORLD TO PLAYING");
			GameStates.state = GameStates.PLAYING;
		}

		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}

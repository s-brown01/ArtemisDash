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
		g.drawRect(100, 150, 50, 50); // example square 
		g.drawString("OVERWORLD", Game.GAME_WIDTH / 2 - 15, 20);
		g.drawString("Click anywhere to continue to game", Game.GAME_WIDTH - 100, Game.GAME_HEIGHT / 2);
	}

	@Override
	public void MouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void MousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void MouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void MouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void MouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void KeyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void KeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}

package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private MouseInputs mouseInputs;
	private float xDelta = 100, yDelta = 100;
	private float xDir = 1f, yDir = 1f;
	private Color color = new Color(150,20,90);
	private Random random = new Random();
	
	public GamePanel() {
		mouseInputs = new MouseInputs(this);
		addKeyListener(new KeyboardInputs(this));//Forwards all key listener events to input class
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}
	
	public void changeXDelta(int value) {
		this.xDelta += value;
	}
	public void setRectPos(int x, int y) {
		this.xDelta = x;
		this.yDelta = y;
	}
	public void changeYDelta(int value) {
		this.yDelta += value;
	}
	
	//Magic method called when game starts 
	//graphics allows us to draw and redraw inside panel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);//Uses JPanel's own paint method
		g.setColor(color);

		g.fillRect((int)xDelta,(int) yDelta, 200, 50);
		updateRectangle();
		
		
		
		
		g.drawRect((int)xDelta, (int)yDelta, 200, 50);//draws rectangle for testing
		
	}
//Anything regarding a rectangle is to draw SOMETHING to the screen so I'm sure it's working properly
	private void updateRectangle() {
		// TODO Auto-generated method stub
		xDelta += xDir;
		if(xDelta > 200 || xDelta < 0) {
			xDir *=-1;
			color = getRandColor();
		}
		yDelta +=yDir;
		if(yDelta > 330 || yDelta < 0) {
			yDir *=-1;
			color = getRandColor();
		}
	}
//Generates random color for DVD Logo Rectangle
	private Color getRandColor() {
		// TODO Auto-generated method stub
		int r = random.nextInt(255);
		int g = random.nextInt(255);
		int b = random.nextInt(255);
		return new Color(r,g,b);
	}
}

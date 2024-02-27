package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

public class GamePanel extends JPanel{
	
	private MouseInputs mouseInputs;
	private float xDelta = 100, yDelta = 100;
	private BufferedImage img;
	private String image = "player_sprites.png";
	public GamePanel() {
		mouseInputs = new MouseInputs(this);//Forwards all mouse listener events to input class
		addKeyListener(new KeyboardInputs(this));//Forwards all key listener events to input class
		
		importImg();
		
		setPanelSize();
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}
	
	public void importImg() {
		InputStream is = getClass().getClassLoader().getResourceAsStream(image);
		
			try {
				img = ImageIO.read(is);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private void setPanelSize() {
		Dimension size = new Dimension(1280,800);
		setPreferredSize(size);
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
		
		g.drawImage(img.getSubimage(100, 70, 64, 60), 0, 0, 120,120, null);
		//100 px offset from top left corner, 70px offset from top
		//Width is ~64, Height ~60
		//Doubled X and Y size 
	}
}

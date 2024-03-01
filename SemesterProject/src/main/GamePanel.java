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
	
	private Game game;
	
	private MouseInputs mouseInputs;
	private float xDelta = 100, yDelta = 100;
	private BufferedImage img;
	private String image = "player_sprites.png";
	private BufferedImage[] idleAnimation;
	private int aniTick,aniIndex,aniSpeed = 10; //120 fps, 12 animations/second = 30
	
	public GamePanel(Game game) {
		// adding a Game to the constructor allows us to access the GameState from the gamePanel
		/*
		 * How to create a custom cursor, perhaps for a recticle on mouse to show shots?
		 * 
		 * Toolkit toolkit = Toolkit.getDefaultToolkit();
		 * Image image = toolkit.getImage("icons/handwriting.gif");
		 * Cursor c = toolkit.createCustomCursor(image , new Point(mainPane.getX(), 
		 *            mainPane.getY()), "img");
		 * mainPane.setCursor (c);
		 * 
		 * how to restore to default cursor (good for the menu?):
		 * setCursor(Cursor.getDefaultCursor());
		 */
		
		this.game = game;
		mouseInputs = new MouseInputs(this);//Forwards all mouse listener events to input class
		addKeyListener(new KeyboardInputs(this));//Forwards all key listener events to input class
		
		importImg();
		loadAnimations();
		
		setPanelSize();
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}
	
	private void loadAnimations() {
		idleAnimation = new BufferedImage[12]; //12 is the amount of frames in the idle animation
		for (int i = 0; i <idleAnimation.length;i++) {
			if (i == 0) {
				idleAnimation[i] = img.getSubimage(100, 70, 64, 64);
			}else {
				idleAnimation[i] = img.getSubimage(i*400, 70, 64, 64);
			}
		}
	}

	public void importImg() {
		InputStream is = getClass().getClassLoader().getResourceAsStream(image);
			try {
				img = ImageIO.read(is);
			} catch (IOException e) {
				System.out.println("NULL");
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e){
					System.out.println("NULL");
					e.printStackTrace();
				}
			}
	}

	private void setPanelSize() {
		Dimension size = new Dimension(1280,800);//SIZE OF GAME WINDOW/PANEL
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
		updateAniTick();
		game.render(g, img);
//		g.drawImage(img.getSubimage(100, 70, 64, 60), 0, 0, 120,120, null);

//		g.drawImage(idleAnimation[aniIndex], (int)xDelta, (int)yDelta, 120,120, null);
		//100 px offset from top left corner, 70px offset from top
		//Width is ~64, Height ~60
		//Doubled X and Y size 
	}

	private void updateAniTick() {
		aniTick ++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= idleAnimation.length) {
				aniIndex = 0;
			}
		}
	}
	
	public Game getGame() {
		return game;
	}
}

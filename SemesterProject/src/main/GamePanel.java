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
import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;

public class GamePanel extends JPanel{

	private Game game;
	
	private MouseInputs mouseInputs;
	private float xDelta = 100, yDelta = 100;
	private BufferedImage img;
	private String image = "Artemis_Finished.png";
	private BufferedImage[][] animations;
	private int aniTick,aniIndex,aniSpeed = 10; //120 fps, 12 animations/second = 30
	private int player_action = IDLE;
	private int player_direction = -1;
	private boolean moving = false;
	
	// THIS IS A TEMPORARY VARIABLE, ONLY USING TO HIDE OR SHOW THE PLAYER
	private boolean drawPlayer = false;
	
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
		
//		importImg();
//		loadAnimations(); 
		
		setPanelSize();
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}
	
	//LOADS IN ANIMATIONS USING A 2D ARRAY OF THE LENGTH OF THE ANIMATIONS THEMSELVES
	private void loadAnimations() {
		animations = new BufferedImage[12][12]; //12 is the amount of frames in the idle animation
		for(int j = 0; j<animations.length;j++) {
			for (int i = 0; i <animations.length;i++) {
				animations[j][i] = img.getSubimage(i * 60, j*45, 60, 45); //i* sprite WIDTH
			
		}
//Test Idle Animation: img.getSubimage(i*60, 0, 60, 45); Set BufferedImage[12]
//Test Run Animation: img.getSubimage(i*60, 45, 60, 45); Set BufferedImage[10]
//Test Jump Animation: img.getSubimage(i*60, 90, 60, 45);Set BufferedImage[11]
//Test Land Animation: img.getSubimage(i*60, 135, 60, 45);Set BufferedImage[3]
//Test Death Animation: img.getSubimage(i*60, 180, 60, 45);Set BufferedImage[20]
				
				//img.getSubimage(WIDTH of sprite, Y position, Width of selection, Height of selection)
		}
	}

	//READS IN THE SPRITE SHEET TO BE USED AND CUT UP INTO SMALLER FRAMES
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
		Dimension size = new Dimension(Game.GAME_WIDTH, Game.GAME_HEIGHT);//SIZE OF GAME WINDOW/PANEL
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
	
	public void setDirection(int direction) {
		this.player_direction = direction;
		moving = true;
	}
	
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	
	public void updateGame() {
		game.update();
//		updateAniTick();
//		setAnimation();
//		updatePos();
	}
	
	//Magic method called when game starts 
	//graphics allows us to draw and redraw inside panel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);//Uses JPanel's own paint method
//		updateAniTick();
		game.render(g);
//		g.drawImage(img.getSubimage(100, 70, 64, 60), 0, 0, 120,120, null);

//		g.drawImage(idleAnimation[aniIndex], (int)xDelta, (int)yDelta, 120,120, null);
		//100 px offset from top left corner, 70px offset from top
		//Width is ~64, Height ~60
		//Doubled X and Y size 
//		if (drawPlayer)
//			g.drawImage(animations[player_action][aniIndex], (int)xDelta, (int)yDelta, 420,420, null);
			// g.drawImage(img.getSubimage(720, 0, 60, 45), 0, 0, 220,220, null);	//draws one image
	}

	private void updatePos() {
		if(moving) {
			switch(player_direction) {
			case LEFT:
				xDelta -= 5;
				break;
			case UP:
				yDelta -= 5;
				break;
			case RIGHT:
				xDelta += 5;
				break;
			case DOWN:
				yDelta += 5;
				break;
			default:
				break;
			}
		}
	}

	private void setAnimation() {
		if (moving) {
			player_action = RUNNING;
		}else {
			player_action = IDLE;
		}
	}

	private void updateAniTick() {
		aniTick ++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= getAnimationLength(player_action)) {
				aniIndex = 0;
			}
		}
	}
	
	public Game getGame() {
		return game;
	}
	
	public void setDrawPlayer(boolean draw) {
		drawPlayer = draw;
	}
}

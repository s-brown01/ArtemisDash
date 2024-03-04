package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static utils.Constants.PlayerConstants.*;

public class Player extends Entity {
	
	private BufferedImage[][] imgs;
	private int aniIndex, aniTick;
	private int aniSpeed = 120 / 10; // 120 frames per second / 10 animations per second
	private int playerAction = IDLE;
	private boolean left, right, up, down;
	
	private int maxHealth = 2;
	private int currentHealth = maxHealth;

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void draw(Graphics g) {
		drawHitbox(g, 0);
	}
	
	public int getHealth() {
		return currentHealth;
	}

}

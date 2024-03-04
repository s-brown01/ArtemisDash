package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

abstract class Entity {
	protected float x, y, width, height;
	protected final Rectangle2D.Float hitbox;
	
	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		hitbox = new Rectangle2D.Float(x, y, width, height);
		
	}
	
	abstract protected void update();
	abstract protected void draw(Graphics g);
	
	protected void drawHitbox(Graphics g, int xlvlOffset) {
		// drawing the hitbox
		g.setColor(Color.CYAN);
		g.drawRect((int)hitbox.x - xlvlOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
		
		// for the text
		g.setColor(Color.BLACK);
		g.fillRect((int)hitbox.x - xlvlOffset, (int)(hitbox.y+hitbox.height+15), 90, 30);
		g.setColor(Color.WHITE);
		g.drawString(" ^ TEMP HITBOX", (int)hitbox.x - xlvlOffset, (int)(hitbox.y+hitbox.height+30));
		
		
	}
	
	public Rectangle2D  getHitbox() {
		return hitbox;
	}
	

}

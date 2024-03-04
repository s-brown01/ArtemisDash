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
		g.setColor(Color.pink);
		g.drawRect((int)hitbox.x - xlvlOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
		
	}
	
	public Rectangle2D  getHitbox() {
		return hitbox;
	}
	

}

package javagame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * The Bullet Class used to create bullet objects
 * 
 * @author Andrew Perkins w12015296
 * @version 1.0
 *
 */

public class Bullet {

	private Vector2f pos;
	private Vector2f speed;
	private boolean firesUpward = false;
	private Color color;
	private int lived = 0;
	
	private boolean active = true;
	
	private static int MAX_LIFETIME = 1500;
	
	public Bullet (Vector2f pos, Vector2f speed, boolean firesUpward, Color color)
	{
		this.pos = pos;
		this.speed = speed;
		this.firesUpward = firesUpward;
		this.color = color;
	}	
	
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		g.setColor(color);
		g.fillOval(pos.getX(), pos.getY() , 20, 20);
	}
	
	public void update(int t)
	{
		if(active){
			Vector2f realSpeed = speed.copy();
			realSpeed.scale((t/1000.0f));
			
			if(firesUpward){
				pos.y -= realSpeed.y;	
			}
			else{
				pos.y += realSpeed.y;
			}			 
			
			lived += t;
			if(lived > MAX_LIFETIME) active = false;			
		}
	}
	
	public boolean isActive()
	{
		return this.active;
	}	
	
	public void isActive(boolean isActive)
	{
		this.active = isActive;		
	}
	
	public float xPosition(){
		return pos.x;
	}
	
	public float yPosition(){
		return pos.y;
	}
	
}

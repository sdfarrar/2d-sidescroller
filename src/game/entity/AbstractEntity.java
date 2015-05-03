package game.entity;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import math.Vector2f;

public abstract class AbstractEntity implements Entity{
	protected Vector2f previousPosition;
	protected Vector2f position;
	protected Rectangle2D hitbox;
	
	protected float height, width;	
	protected Color color;
	
	public AbstractEntity(float x, float y, float width, float height){
		this.previousPosition = new Vector2f(x, y);
		this.position = new Vector2f(x, y);
		this.width = width;
		this.height = height;
		this.hitbox = new Rectangle2D.Float(x, y, width, height);
		
		color = Color.WHITE;
	}
	
	public abstract void init();
	public abstract void dispose();
	
	public boolean collidesWith(Entity other){
		return hitbox.contains(other.getPosition().x, other.getPosition().y, other.getWidth(), other.getHeight());
	}

	public Vector2f getPosition(){
		return position;
	}
	
	public float getWidth(){
		return width;
	}
	
	public float getHeight(){
		return height;
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public void setColor(Color c){
		this.color = c;
	}
	
}

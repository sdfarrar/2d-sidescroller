package game.entity;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import math.Vector2f;

public abstract class AbstractEntity implements Entity{
	protected Vector2f previousPosition;
	protected Vector2f position;
	protected Hitbox hitbox;
	
	protected float height, width;	
	protected Color color, debugColor;
	
	public AbstractEntity(float cx, float cy, float width, float height){
		this.previousPosition = new Vector2f(cx, cy);
		this.position = new Vector2f(cx, cy);
		this.width = width;
		this.height = height;
		rebuildHitbox();
		
		color = Color.WHITE;
		debugColor = Color.GREEN;
	}
	
	public abstract void init();
	public abstract void dispose();
	
	protected void rebuildHitbox(){
    hitbox = new Hitbox(position, new Vector2f(width, height));
  }
	
	public boolean collidesWith(Entity other){
		return hitbox.intersects(other.getHitbox());
	}
	
	public boolean contains(Entity other){
		return false;
	  //return hitbox.contains(other.getPosition().x, other.getPosition().y, other.getWidth(), other.getHeight());
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
	
	public Hitbox getHitbox(){
	  return this.hitbox;
	}
}

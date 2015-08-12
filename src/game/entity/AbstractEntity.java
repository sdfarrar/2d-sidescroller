package game.entity;

import java.awt.Color;
import java.util.List;

import math.Vector2f;

public abstract class AbstractEntity implements Entity{
	protected Vector2f previousPosition;
	protected Vector2f position;
	//protected Rectangle2D hitbox;
	protected Hitbox hitbox;
	
	protected float height, width;	
	protected Color color, debugColor;
	
	public AbstractEntity(float cx, float cy, float width, float height){
		this.previousPosition = new Vector2f(cx, cy);
		this.position = new Vector2f(cx, cy);
		this.width = width;
		this.height = height;
		//this.hitbox = new Rectangle2D.Float(cx, cy, width, height);
		this.rebuildHitbox();
		
		color = Color.WHITE;
		debugColor = Color.GREEN;
	}
	
	protected void rebuildHitbox(){
		//float x = position.x - width/2f; float y = position.y + height/2f;		
		//this.hitbox = new Rectangle2D.Float(x, y, width, height);
		hitbox = new Hitbox(position, new Vector2f(width, height));
	}
	
	public abstract void init();
	public abstract void dispose();
	public abstract void resolveCollision(AbstractEntity e);
	
	public boolean collidesWith(Entity other){
		//return hitbox.intersects(other.getPosition().x, other.getPosition().y, other.getWidth(), other.getHeight());
		return hitbox.intersects(other.getHitbox());
	}
	
	public List<Hitbox.Collision> getCollisionSide(Entity other){
		return hitbox.collides(other.getHitbox());
	}
	
	public boolean contains(Entity other){
		//return hitbox.contains(other.getPosition().x, other.getPosition().y, other.getWidth(), other.getHeight());
		return false;
	}

	public Vector2f getPosition(){
		return position;
	}
	
	public void setPosition(Vector2f pos){
		this.position = pos;
		rebuildHitbox();
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

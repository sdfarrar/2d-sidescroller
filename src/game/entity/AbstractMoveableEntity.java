package game.entity;

import math.Vector2f;

public abstract class AbstractMoveableEntity extends AbstractEntity implements MoveableEntity{
	protected Vector2f velocity;
	protected Vector2f direction;
	
	public AbstractMoveableEntity(float x, float y, float width, float height){
		this(x, y, width, height, new Vector2f());
	}
	
	public AbstractMoveableEntity(float x, float y, float width, float height, Vector2f initialVelocity) {
		super(x, y, width, height);
		this.velocity = initialVelocity;
		direction = new Vector2f(); //TODO probably isn't right
	}
	
	public float getDx(){
		return velocity.x;
	}
	
	public float getDy(){
		return velocity.y;
	}
	
	public void setVelocity(Vector2f vel){
		this.velocity = vel;
	}
	
	public Vector2f getVelocity(){
		return this.velocity;
	}
}

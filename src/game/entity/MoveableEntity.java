package game.entity;

import math.Vector2f;

public interface MoveableEntity {
	public float getDx();
	public float getDy();
	public void setVelocity(Vector2f vel);
	public Vector2f getVelocity();
}

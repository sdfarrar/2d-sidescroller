package game.entity;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import game.entity.Hitbox.Collision;
import game.input.KeyInput;
import graphics.render.LayeredRenderer;

import java.awt.Color;
import java.util.Deque;
import java.util.List;

import math.Vector2f;
import physics.PhysicsEngine;

public class Player extends AbstractMoveableEntity {

	public Player(float x, float y, float width, float height) {
		super(x, y, width, height);
		color = Color.ORANGE;
	}

	@Override
	public void input() {
		Vector2f force = new Vector2f();
		if(KeyInput.isKeyDown(GLFW_KEY_D)){			
			force = force.add(new Vector2f(speed, 0.0f));
		}
		if(KeyInput.isKeyDown(GLFW_KEY_A)){
			force = force.add(new Vector2f(-speed, 0.0f));
		}
		if(KeyInput.isKeyDown(GLFW_KEY_W)){
			force = force.add(new Vector2f(0.0f, speed));
		}	
		if(KeyInput.isKeyDown(GLFW_KEY_S)){
			force = force.add(new Vector2f(0.0f, -speed));
		}
		this.setVelocity(force);
	}

	@Override
	public void update(PhysicsEngine physics, float delta) {
		previousPosition = position;
		physics.update(this, delta);
		
		this.rebuildHitbox();
	}

	@Override
	public void render(LayeredRenderer renderer, float alpha) {
		renderer.drawRect(position.x, position.y, width, height, color);
	}

	@Override
	public void debugRender(LayeredRenderer renderer, float alpha) {
		Vector2f interpolatedPosition = previousPosition.lerp(position, alpha);
		float x = interpolatedPosition.x;
		float y = interpolatedPosition.y;
		renderer.drawDebugRectOutline(x, y, width, height, debugColor);
		this.renderHitbox(renderer, alpha);
	}

	@Override
	public void renderHitbox(LayeredRenderer renderer, float alpha) {
		renderer.drawRectOutline(hitbox.getCenter().x, hitbox.getCenter().y, hitbox.getDimensions().x, hitbox.getDimensions().y, Color.RED);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resolveCollision(AbstractEntity e) {
		// Movement directions
		boolean left = velocity.x < 0.0f;
		boolean right = velocity.x > 0.0f;
		boolean up = velocity.y > 0.0f;
		boolean down = velocity.y < 0.0f;;
		boolean noMovement = false;
		System.out.println(velocity);
		while(this.collidesWith(e) && !noMovement){
			boolean noX = false, noY = false;
			List<Hitbox.Collision> sides = this.getCollisionSide(e);
			if(left && sides.contains(Hitbox.Collision.LEFT)){
				this.position.x++;
			}else if(right && sides.contains(Hitbox.Collision.RIGHT)){
				this.position.x--;
			}else{
				noX = true;
			}
			
			if(up && sides.contains(Hitbox.Collision.TOP)){
				this.position.y--;
			}else if(down && sides.contains(Hitbox.Collision.BOTTOM)){
				this.position.y++;
			}else{
				noY = true;
			}
			noMovement = noX && noY;
			this.rebuildHitbox();
		}
	}
	
	public void resolveCollision(Tile tile){
		//System.out.println("resolving collision");
		List<Collision> collisions = hitbox.collides(tile.getHitbox());
		
		collisions.forEach((e)->{
			if(e.equals(Hitbox.Collision.LEFT)){//right collision				
				float x = tile.getHitbox().getRightBound() + this.getHitbox().getDimensions().x/2 + 1;
				float y = getPosition().y;
				Vector2f newPos = new Vector2f(x,y);

				//this.setPosition(newPos);
			}else if(e.equals(Hitbox.Collision.RIGHT)){//left collision
				float x = tile.getHitbox().getLeftBound() - this.getHitbox().getDimensions().x/2 - 1;
				float y = getPosition().y;
				Vector2f newPos = new Vector2f(x,y);
				
				//this.setPosition(newPos);
			}
			
			if(e.equals(Hitbox.Collision.BOTTOM)){
				int y = (int) (tile.getHitbox().getTopBound() + this.getHitbox().getDimensions().y/2 + 1);
				int x = (int) getPosition().x;
				Vector2f newPos = new Vector2f(x,y);

//				this.setPosition(newPos);
			}else if(e.equals(Hitbox.Collision.TOP)){
				float y = tile.getHitbox().getBottomBound() - this.getHitbox().getDimensions().y/2 - 1;
				float x = getPosition().x;
				Vector2f newPos = new Vector2f(x,y);

				//this.setPosition(newPos);
			}
		});
		
		
//		Deque<Collision> collisions = hitbox.collides2(tile.getHitbox());
//		
//		Collision c = null;
//		
//		while(!collisions.isEmpty()){
//			c=collisions.pop();
//			System.out.println(c);
//			if(c.equals(Hitbox.Collision.LEFT)){//right collision				
//				float x = tile.getHitbox().getRightBound() + this.getHitbox().getDimensions().x/2 + 2;
//				float y = getPosition().y;
//				Vector2f newPos = new Vector2f(x,y);
//
//				this.setPosition(newPos);
//			}else if(c.equals(Hitbox.Collision.RIGHT)){//left collision
//				float x = tile.getHitbox().getLeftBound() - this.getHitbox().getDimensions().x/2 - 2;
//				float y = getPosition().y;
//				Vector2f newPos = new Vector2f(x,y);
//				
//				this.setPosition(newPos);
//			}
//			
//			if(c.equals(Hitbox.Collision.BOTTOM)){
//				int y = (int) (tile.getHitbox().getTopBound() + this.getHitbox().getDimensions().y/2 + 2);
//				int x = (int) getPosition().x;
//				Vector2f newPos = new Vector2f(x,y);
//
//				this.setPosition(newPos);
//			}else if(c.equals(Hitbox.Collision.TOP)){
//				float y = tile.getHitbox().getBottomBound() - this.getHitbox().getDimensions().y/2 - 2;
//				float x = getPosition().x;
//				Vector2f newPos = new Vector2f(x,y);
//
//				this.setPosition(newPos);
//			}
//			
//			if(collisions.isEmpty()){
//				// check if we still colliding
//				//collisions = hitbox.collides2(tile.getHitbox());
//			}
//		}
		
		System.out.println("Colliding with tile. Player is " + collisions + " of the tile");		
	}
	
	private float speed = 3.5f;
	
	public void increaseSpeed(){
		speed+=.2f;
	}
	
	public void decreaseSpeed(){
		speed-=.2f;
	}

}

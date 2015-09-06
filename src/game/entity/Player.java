package game.entity;

import static org.lwjgl.glfw.GLFW.*;

import java.awt.Color;

import math.Vector2f;
import graphics.render.LayeredRenderer;
import game.collision.MinimumTranslationVector;
import game.input.KeyInput;

public class Player extends AbstractMoveableEntity {

	public Player(float x, float y, float width, float height) {
		super(x, y, width, height);
		color = Color.GRAY;
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
	public void update(float delta) {
		previousPosition = position;
		position = position.add(velocity);
		rebuildHitbox();
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
//		renderer.drawDebugRectOutline(position.x, position.y, width, height, debugColor);
	}

	@Override
	public void renderHitbox(LayeredRenderer renderer, float alpha) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
private float speed = 3.5f;
  
  public void increaseSpeed(){
    speed+=.2f;
  }
  
  public void decreaseSpeed(){
    speed-=.2f;
  }
  
  public void translate(MinimumTranslationVector mtv){
    Vector2f v = mtv.getAxis().scale(mtv.getDistance());
    this.position = this.position.add(v);
  }

  public void restrictToBounds(int xBoundMin, int xBoundMax, int yBoundMin, int yBoundMax){
    if(position.x-width/2f < xBoundMin){
      position.x = xBoundMin + width/2f;
    }
    if(position.x+width/2f > xBoundMax){
      position.x = xBoundMax - width/2f;
    }
    
    if(position.y-height/2f < yBoundMin){
      position.y = yBoundMin + height/2f;
    }
    if(position.y+height/2f > yBoundMax){
      position.y = yBoundMax - height/2f - 1f;
    }
    rebuildHitbox();
  }

}

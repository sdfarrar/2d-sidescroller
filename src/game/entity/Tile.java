package game.entity;

import graphics.opengl.Texture;
import graphics.render.LayeredRenderer;

import java.awt.Color;

import math.Vector2f;

public class Tile {
	public static final int WIDTH=50, HEIGHT=50;
	
	private float x, y;
	private Texture texture;
	private TileType type;
	private Color color, debugColor;
	private Hitbox hitbox;
	
	private boolean checkCollision = false;
	
	public Tile(float x, float y, TileType type){
		this.x = x;
		this.y = y;
		this.type = type;
		hitbox = new Hitbox(new Vector2f(x,y), new Vector2f(WIDTH, HEIGHT));
		debugColor = Color.WHITE;
		//this.texture = texture;
	}
	
	public void render(LayeredRenderer renderer){
		renderer.drawRect(x, y, WIDTH, HEIGHT, type.color);
	}
	
	public void debugDraw(LayeredRenderer renderer){
		//renderer.drawRectOutline(x, y, WIDTH, HEIGHT, Color.WHITE);
		if(checkCollision){
			renderer.drawRect(x, y, WIDTH, HEIGHT, Color.RED);
		}else{
			renderer.drawRectOutline(x, y, WIDTH, HEIGHT, Color.WHITE);
		}
	}
	
	public void setDebugColor(Color color){
		this.debugColor = color;
	}
	
	public boolean collidesWith(AbstractMoveableEntity entity){
		// TODO check collision. 1px up down left right of entity is contained by this
		// TODO 1px should probably be a 1px outline of entity
		// TODO OR determine where entity lies from this and check 1px line outside of this
		// Might return with side is being collided with
		float el = entity.getPosition().x - entity.width/2f;
		float er = entity.getPosition().x + entity.width/2f; // el + entity.width
		float et = entity.getPosition().y + entity.height/2f;
		float eb = entity.getPosition().y - entity.height/2f;
		
		if(x<el){//tile is to the left, check right side
			
		}else{//tile is to the right, check left
			
		}
		
		if(y>et){//tile is above, check tile bottom
			
		}else{//tile is below, check top of tile
			
		}
		return hitbox.intersects(entity.getHitbox());
	}
	
	public Hitbox getHitbox(){
		return this.hitbox;
	}
	
	public TileType getTileType(){
		return type;
	}
	
	public void checkCollision(boolean check){
		this.checkCollision = check;
	}
}

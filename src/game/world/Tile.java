package game.world;

import java.awt.Color;

import game.collision.AABB;
import game.collision.Hitbox;
import game.entity.AbstractMoveableEntity;
import graphics.opengl.Texture;
import graphics.render.LayeredRenderer;
import math.Vector2f;

public class Tile {
public static final int WIDTH=50, HEIGHT=50;
  
  private float x, y;
  private Texture texture;
  private TileType type;
  private Color color, debugColor;
  private Hitbox hitbox;
  private AABB aabb;
  
  private boolean checkCollision = false;
  private boolean isColliding = false;
  
  public Tile(float x, float y, TileType type){
    this.x = x;
    this.y = y;
    this.type = type;
    hitbox = new Hitbox(new Vector2f(x,y), new Vector2f(WIDTH, HEIGHT));
    aabb = new AABB(new Vector2f(x,y), new Vector2f(WIDTH, HEIGHT));
    debugColor = Color.WHITE;
    //this.texture = texture;
  }
  
  public void render(LayeredRenderer renderer){
    renderer.drawRect(x, y, WIDTH, HEIGHT, type.color);
  }
  
  public void debugDraw(LayeredRenderer renderer){
    //renderer.drawRectOutline(x, y, WIDTH, HEIGHT, Color.WHITE);
    if(isColliding){
      renderer.drawRect(x, y, WIDTH, HEIGHT, Color.RED);
    }else if(checkCollision){
      renderer.drawRect(x, y, WIDTH, HEIGHT, Color.YELLOW);
    }else{
      renderer.drawRectOutline(x, y, WIDTH, HEIGHT, Color.WHITE);
    }
  }
  
  public void setIsColliding(boolean colliding){
    this.isColliding = colliding;
  }
  
  public void setDebugColor(Color color){
    this.debugColor = color;
  }
  
  public boolean collidesWith(AbstractMoveableEntity entity){
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
  
  public AABB getAABB(){
    return this.aabb;
  }
}

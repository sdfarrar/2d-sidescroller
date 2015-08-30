package game.world;

import java.awt.Color;

public enum TileType {
  GROUND("res/ground.png", Color.GREEN), SKY("res/sky.png", Color.CYAN), PLATFORM("res/platform.png", Color.GRAY);
  
  public final String texturePath;
  public final Color color;
  
  private TileType(String texturePath, Color color){
    this.texturePath = texturePath;
    this.color = color;
  }
}

package game.collision;

import math.Vector2f;

public class MinimumTranslationVector{
  private Vector2f axis;
  private float distance;
  
  public MinimumTranslationVector(){
    this(new Vector2f(), 0.0f);
  }
  
  public MinimumTranslationVector(Vector2f axis, float distance){
    this.axis = (axis==null) ? new Vector2f() : axis;
    this.distance = distance;
  }
  
  public Vector2f getAxis(){
    return this.axis;
  }
  
  public float getDistance(){
    return this.distance;
  }
  
  public String toString(){
    String s = "";
    s = axis.toString() + " length: " + distance;
    return s;
  }
}

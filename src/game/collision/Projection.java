package game.collision;

import math.Vector2f;

public class Projection{
  
  private float min,max;
  
  public Projection(Vector2f[] vertices, Vector2f axis){
    createProjection(vertices, axis);
  }
  
  private void createProjection(Vector2f[] vertices, Vector2f axis){
    float min = vertices[0].dot(axis);
    float max = min;
    
    for(int i=1; i<vertices.length; i++){
      float p = vertices[i].dot(axis);
      if(p<min){
        min = p;
      }else if(p>max){
        max = p;
      }
    }
    this.min = min;
    this.max = max;
  }
  
  /**
   * Determines if the two projections overlap
   * @param other the other Projection
   * @return true if the two projections overlap otherwise false
   */
  public boolean overlap(Projection other){
    if(other.max<this.min || this.max<other.min){
      return false;
    }
    return true;
  }
  
  /**
   * Computes the overlap of two projections
   * @param other the other Projection
   * @return the overlap of the two projections. If this is no overlap -1 is returned
   */
  public float getOverlap(Projection other){
    if(other.min<this.max && other.min>this.min){
      return Math.abs(other.min-this.max);
    }else if(other.max>this.min && other.max<this.max){
      return Math.abs(other.max-this.min);
    }
    return -1;
  }
}

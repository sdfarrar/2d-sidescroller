package game.collision;

import math.Vector2f;

public class SAT{
  
  public static MinimumTranslationVector checkCollision(AABB a, AABB b, boolean debug){
    float overlap = 999999999;
    Vector2f direction = null;
    
    Vector2f[] axes1 = a.getAxes();
    Vector2f[] axes2 = b.getAxes();
    
    for(int i=0; i<axes1.length; i++){
      Vector2f axis = axes1[i];
      Projection p1 = a.projectOnto(axis);
      Projection p2 = b.projectOnto(axis);
      
      if(!p1.overlap(p2)){
        return new MinimumTranslationVector(); // no collision
      }else{
        float o = p1.getOverlap(p2);
        // check for containment
        if(p1.contains(p2) || p2.contains(p1)){
          float max = Math.abs(p1.getMax() - p2.getMax());
          float min = Math.abs(p1.getMin() - p2.getMin());
          if(max>min){
            axis = axis.negate();
            o += min;
          }else{
            o += max;
          }
        }
        
        if(o<overlap){
          overlap=o;
          direction = axis;
//          if(debug) System.out.println("overlap: " + o);
        }
      }
    }
    
    for(int i=0; i<axes2.length; i++){
      Vector2f axis = axes2[i];
      Projection p1 = a.projectOnto(axis);
      Projection p2 = b.projectOnto(axis);
      
      if(!p1.overlap(p2)){
        return new MinimumTranslationVector(); // no collision
      }else{
        float o = p1.getOverlap(p2);
        // check for containment
        if(p1.contains(p2) || p2.contains(p1)){
          float max = Math.abs(p1.getMax() - p2.getMax());
          float min = Math.abs(p1.getMin() - p2.getMin());
          if(max>min){
            axis = axis.negate();
            o += min;
          }else{
            o += max;
          }
        }

        if(o<overlap){
          overlap=o;
          direction = axis;
//          if(debug)System.out.println("overlap: " + o);
        }
      }
    }
    
    // Make sure our direction vector is in the correct direction
    Vector2f v = a.getCenter().to(b.getCenter());
    if(v.dot(direction)>=0){
      direction = direction.negate();
    }
    
    
    
    MinimumTranslationVector mtv = new MinimumTranslationVector(direction, overlap);
    if(debug) System.out.println(mtv);
    return mtv;    
  }
}

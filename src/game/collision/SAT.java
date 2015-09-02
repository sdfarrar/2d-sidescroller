package game.collision;

import math.Vector2f;

public class SAT{
  
  public static MinimumTranslationVector checkCollision(AABB a, AABB b){
    float overlap = 999999999;
    Vector2f axis = null;
    
    Vector2f[] axes1 = a.getAxes();
    Vector2f[] axes2 = b.getAxes();
    
    for(int i=0; i<axes1.length; i++){
      Projection p1 = a.projectOnto(axes1[i]);
      Projection p2 = b.projectOnto(axes1[i]);
      
      if(!p1.overlap(p2)){
        return new MinimumTranslationVector(); // no collision
      }else{
        float o = p1.getOverlap(p2);
        if(o<overlap){
          overlap=o;
          axis = axes1[i];
        }
      }
    }
    
    for(int i=0; i<axes2.length; i++){
      Projection p1 = a.projectOnto(axes2[i]);
      Projection p2 = b.projectOnto(axes2[i]);
      
      if(!p1.overlap(p2)){
        return new MinimumTranslationVector(); // no collision
      }else{
        float o = p1.getOverlap(p2);
        if(o<overlap){
          overlap=o;
          axis = axes2[i];
        }
      }
    }
    
    // Make sure our axis vector is in the correct direction
    Vector2f v = a.getCenter().to(b.getCenter());
    if(v.dot(axis)>=0){
      axis = axis.negate();
    }
    
    MinimumTranslationVector mtv = new MinimumTranslationVector(axis, overlap);
    return mtv;    
  }
}

package game.collision;

import math.Vector2f;

public class AABB{
  
  private Vector2f center;
  private Vector2f dimensions;
  private Vector2f[] vertices; // CCW order
  
  public AABB(Vector2f center, Vector2f dimensions){
    this.center = center;
    this.dimensions = dimensions;
    this.vertices = new Vector2f[4];
    constructVertices();
  }
  
  private void constructVertices(){
    // start top left
    vertices[0] = new Vector2f(center.x-dimensions.x/2, center.y+dimensions.y/2);
    vertices[1] = new Vector2f(center.x-dimensions.x/2, center.y-dimensions.y/2);
    vertices[2] = new Vector2f(center.x+dimensions.x/2, center.y-dimensions.y/2);
    vertices[3] = new Vector2f(center.x+dimensions.x/2, center.y+dimensions.y/2);
  }
  
  public Vector2f[] getAxes(){
    Vector2f[] axes = new Vector2f[vertices.length];
    for(int i=0; i<vertices.length; i++){
      Vector2f v1 = vertices[i];
      //Vector2f v2 = (i+1 < vertices.length) ? vertices[i+1] : vertices[0];
      Vector2f v2 = vertices[i + 1 == vertices.length ? 0 : i + 1];
      Vector2f edge = v1.to(v2);
      Vector2f normal = edge.getNormal();
      axes[i] = normal.normalize();
    }
    return axes;
  }
  
  public Projection projectOnto(Vector2f axis){
    return new Projection(vertices, axis);
  }
  
  public Vector2f[] getVertices(){
    return this.vertices;
  }
  
  public Vector2f getCenter(){
    return this.center;
  }
  
  public static AABB merge(AABB a, AABB b){
    // Find which AABB is on the left
    //if(a.center.x)
    Vector2f center = null;// = new Vector2f();
    Vector2f dimensions = null;// = new Vector2f();
    
    if(a.center.x==b.center.x){
      float y = 0.0f;
      float height = a.dimensions.y + b.dimensions.y;
      if(a.center.y < b.center.y){
        y = a.center.y + b.dimensions.y / 2.0f;
      }else{
        y = b.center.y + a.dimensions.y / 2.0f;
      }
      center = new Vector2f(a.center.x, y);
      dimensions = new Vector2f(a.dimensions.x, height);
    }
    
    if(a.center.y==b.center.y){
      float x = 0.0f;
      float width = a.dimensions.x + b.dimensions.x;
      
      if(a.center.x < b.center.x){
        x = a.center.x + b.dimensions.x / 2.0f;
      }else{
        x = b.center.x + a.dimensions.x / 2.0f;
      }
      center = new Vector2f(x, a.center.y);
      dimensions = new Vector2f(width, a.dimensions.y);
    }
    
    if(center==null || dimensions==null){ // the AABBs weren't on the same plane in either x or y directions
      return null;
    }
    
    return new AABB(center, dimensions);
  }
  
  
}

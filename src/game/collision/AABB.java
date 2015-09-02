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
  
  public Vector2f getCenter(){
    return this.center;
  }
  
  
}

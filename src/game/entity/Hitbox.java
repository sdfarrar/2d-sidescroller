package game.entity;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import math.Vector2f;

public class Hitbox {
	private Vector2f center;
	private Vector2f dimensions;
	private Vector2f x1y1, x2y2;
	public enum Collision { NONE, LEFT, RIGHT, TOP, BOTTOM };
	
//	private Line bottom, top, left, right;
	
	public Hitbox(){
		this(new Vector2f(), new Vector2f());
	}
	
	public Hitbox(Vector2f center, Vector2f dimensions){
		this.center = center;
		this.dimensions = dimensions;
		
		x1y1 = new Vector2f(this.center.x - this.dimensions.x/2f -1f, this.center.y + this.dimensions.y/2f + 1f);//top left corner
		x2y2 = new Vector2f(this.center.x + this.dimensions.x/2f +1f, this.center.y - this.dimensions.y/2f - 1f);//bottom right corner
		
//		Vector2f p1 = new Vector2f(this.center.x - this.dimensions.x/2f -1f, this.center.y + this.dimensions.y/2f + 1f);//top left corner
//		Vector2f p2 = new Vector2f(this.center.x + this.dimensions.x/2f +1f, this.center.y - this.dimensions.y/2f - 1f);//bottom right corner
//		Vector2f p3 = new Vector2f(this.center.x + this.dimensions.x/2f +1f, this.center.y + this.dimensions.y/2f + 1f);//top right corner
//		Vector2f p4 = new Vector2f(this.center.x - this.dimensions.x/2f -1f, this.center.y - this.dimensions.y/2f - 1f);//bottom left corner
//		
//		bottom = new Line(p4, p2);
//		top = new Line(p1, p3);
//		left = new Line(p1, p4);
//		right = new Line(p3, p2);
	}
	
	public boolean intersects(Hitbox other){
		return
			((this.x1y1.x <= other.x2y2.x) && (this.x2y2.x >= other.x1y1.x) &&
			 (this.x1y1.y >= other.x2y2.y) && (this.x2y2.y <= other.x1y1.y));
	}
	
	/**
	 * Uses Minkowski sum to determine which side <code>this.hitbox</code> collides with another entity's hitbox 
	 * @param other
	 * @return
	 */
	public List<Collision> collides(Hitbox other){
		List<Collision> sides = new ArrayList<Collision>();
		float w = 0.5f * (this.dimensions.x + other.dimensions.x);
		float h = 0.5f * (this.dimensions.y + other.dimensions.y);
		float dx = this.center.x - other.center.x;
		float dy = this.center.y - other.center.y;

//		if (Math.abs(dx) <= w && Math.abs(dy) <= h){
//		    float wy = w * dy;
//		    float hx = h * dx;
//
//		    if (wy > hx){
//		        if (wy > -hx){
//		        	sides.add(Collision.TOP);
//		        }else{
//		        	sides.add(Collision.LEFT);
//		        }
//		    }else{
//		        if (wy > -hx){
//		        	sides.add(Collision.RIGHT);
//		        }else{
//		        	sides.add(Collision.BOTTOM);
//		        }
//		    }
//		}
		
		if(this.center.x - other.center.x<0)
			sides.add(Collision.RIGHT);
		else
			sides.add(Collision.LEFT);
		
		if(this.center.y - other.center.y<0)
			sides.add(Collision.TOP);
		else
			sides.add(Collision.BOTTOM);
		
		return sides;
	}
	
	public Deque<Collision> collides2(Hitbox other){
		Deque<Collision> sides = new ArrayDeque<Collision>();
		float w = 0.5f * (this.dimensions.x + other.dimensions.x);
		float h = 0.5f * (this.dimensions.y + other.dimensions.y);
		float dx = this.center.x - other.center.x;
		float dy = this.center.y - other.center.y;

//		if (Math.abs(dx) <= w && Math.abs(dy) <= h){
//		    float wy = w * dy;
//		    float hx = h * dx;
//
//		    if (wy > hx){
//		        if (wy > -hx){
//		        	sides.add(Collision.TOP);
//		        }else{
//		        	sides.add(Collision.LEFT);
//		        }
//		    }else{
//		        if (wy > -hx){
//		        	sides.add(Collision.RIGHT);
//		        }else{
//		        	sides.add(Collision.BOTTOM);
//		        }
//		    }
//		}
		
		if(this.center.x - other.center.x<0)
			sides.add(Collision.RIGHT);
		else
			sides.add(Collision.LEFT);
		
		if(this.center.y - other.center.y<0)
			sides.add(Collision.TOP);
		else
			sides.add(Collision.BOTTOM);
		
		return sides;
	}
	
	public Vector2f getCenter(){
		return this.center;
	}
	
	public Vector2f getDimensions(){
		return this.dimensions;
	}
	
	public int getTopBound(){
		return (int)(this.center.y + this.dimensions.y/2);
	}
	
	public int getBottomBound(){
		return (int)(this.center.y - this.dimensions.y/2);
	}
	
	public int getLeftBound(){
		return (int)(this.center.x - this.dimensions.x/2);
	}
	
	public int getRightBound(){
		return (int)(this.center.x + this.dimensions.x/2);
	}
	
	private class Line{
		Vector2f a,b;
		public Line(Vector2f a, Vector2f b){
			this.a = a;
			this.b = b;
		}
	}
	
}

package graphics;

import game.entity.AbstractEntity;
import game.entity.AbstractMoveableEntity;
import math.Matrix4f;
import math.Vector3f;

public class Camera {
	private Matrix4f ortho;
	private Vector3f position;
	private AbstractEntity entity;
	
	private int screenWidth, screenHeight;

	public Camera(float screenWidth, float screenHeight){
		this(0, screenWidth, 0, screenHeight);
	}
	
	public Camera(float left, float right, float bottom, float top){
		this(left, right, bottom, top, -1f, 1f);
	}
	
	public Camera(float left, float right, float bottom, float top, float near, float far){
		ortho = Matrix4f.orthographic(left, right, bottom, top, near, far);
		position = new Vector3f(left, bottom, 0);
		screenWidth = (int) Math.abs(right-left);
		screenHeight = (int) Math.abs(top-bottom);
	}
	
	public void translateCamera(int dx, int dy, int dz){
		position = position.subtract(new Vector3f(dx, dy, dz));
		
		float left = 0 + position.x;	//TODO 0 may need to change
		float right = position.x + screenWidth;
		float bottom = 0 + position.y;	//TODO 0 may need to change
		float top = position.y + screenHeight;

    	ortho = Matrix4f.orthographic(left, right, bottom, top, -1f, 1f);
	}
	
	public void centerCameraOn(float cx, float cy){
		float left = cx - screenWidth/2;
		float right = cx + screenWidth/2;
		float bottom = cy - screenHeight/2;
		float top = cy + screenHeight/2;
		ortho = Matrix4f.orthographic(left, right, bottom, top, -1f, 1f);
	}
	
	public void followEntity(AbstractEntity e){
		this.entity = e;
	}
	
	public Matrix4f getOrthoMatrix(){
		if(entity==null){
			return this.ortho;
		}else{
			this.centerCameraOn(entity.getPosition().x, entity.getPosition().y);			
			return this.ortho;
		}
	}
	
	public int getScreenWidth(){
		return this.screenWidth;
	}
	
	public int getScreenHeight(){
		return this.screenHeight;
	}
	
}
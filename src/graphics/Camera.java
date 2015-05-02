package graphics;

import math.Matrix4f;
import math.Vector3f;

public class Camera {
	private Matrix4f ortho;
	private Vector3f position;
	
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
	
	public Matrix4f getOrthoMatrix(){
		return this.ortho;
	}
	
	public int getScreenWidth(){
		return this.screenWidth;
	}
	
	public int getScreenHeight(){
		return this.screenHeight;
	}
	
}
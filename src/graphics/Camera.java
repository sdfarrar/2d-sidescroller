package graphics;

import game.entity.AbstractEntity;
import math.Matrix4f;
import math.Vector2f;
import math.Vector3f;

public class Camera {
	private Matrix4f ortho;
	private Vector3f focus;
	private AbstractEntity followEntity;
	
	private int screenWidth, screenHeight;
	
	private AbstractEntity panTarget;
	private boolean panning;
	private int panSpeed;

	public Camera(float screenWidth, float screenHeight){
//		this(0, screenWidth, 0, screenHeight);
		this(0, screenWidth, 0, screenHeight);
	}
	
	public Camera(float left, float right, float bottom, float top){
		this(left, right, bottom, top, -1f, 1f);
	}
	
	public Camera(float left, float right, float bottom, float top, float near, float far){
		ortho = Matrix4f.orthographic(left, right, bottom, top, near, far);
		screenWidth = (int) Math.abs(right-left);
		screenHeight = (int) Math.abs(top-bottom);
		centerFocus(left, bottom);
		panning = false;
	}
	
	public void update(float delta){
		panCamera(delta);
	}
	
	public void translateCamera(float dx, float dy, float dz){
		Vector3f temp = focus.subtract(new Vector3f(dx, dy, dz));
		
		float left = 0 + temp.x;	//TODO 0 may need to change
		float right = temp.x + screenWidth;
		float bottom = 0 + temp.y;	//TODO 0 may need to change
		float top = temp.y + screenHeight;

		centerFocus(left, bottom);
    	ortho = Matrix4f.orthographic(left, right, bottom, top, -1f, 1f);
	}
	
	public void centerCameraOn(float cx, float cy){
		panning = false;
		float left = cx - screenWidth/2;
		float right = cx + screenWidth/2;
		float bottom = cy - screenHeight/2;
		float top = cy + screenHeight/2;
		centerFocus(left, bottom);
		ortho = Matrix4f.orthographic(left, right, bottom, top, -1f, 1f);
	}
	
	public void followEntity(AbstractEntity e){
		panning = false;
		this.followEntity = e;
	}
	
	public void panCameraTo(AbstractEntity e, int speed){
		panTarget = e;
		panSpeed = speed;
		panning = true;
		
		followEntity=null;
	}
	
	public Matrix4f getOrthoMatrix(){
		if(followEntity==null){
			return this.ortho;
		}else{
			this.centerCameraOn(followEntity.getPosition().x, followEntity.getPosition().y);			
			return this.ortho;
		}
	}
	
	public int getScreenWidth(){
		return this.screenWidth;
	}
	
	public int getScreenHeight(){
		return this.screenHeight;
	}
	
	public Vector3f getFocus(){
		return new Vector3f(focus.x, focus.y, focus.z);
	}
	
	public Vector2f getResolution(){
		return new Vector2f(screenWidth, screenHeight);
	}
	
	/**
	 * Sets <code>position</code> to the center of the screen 
	 * @param left x coordinate of the left side of the screen
	 * @param bottom y coordinate of the bottom side of the screen
	 */
	private void centerFocus(float left, float bottom){
		focus = new Vector3f(left + screenWidth/2f, bottom + screenHeight/2f, 0);
	}
	
	/**
	 * Pans the camera to <code>panTarget</code>
	 * @param delta time between updates
	 */
	private void panCamera(float delta){
		if(panning){
			Vector2f pos = new Vector2f(focus.x, focus.y);
			float nudge = 1/pos.distance(panTarget.getPosition())*panSpeed;
			
    		delta = ((delta+=nudge)>1) ? 1.0f : delta;
			
			Vector2f interpolatedPosition = pos.lerp(panTarget.getPosition(), delta);
			float x = interpolatedPosition.x;
			float y = interpolatedPosition.y;
			
			focus = new Vector3f(x,y,0);
			
			float left = focus.x - screenWidth/2;
			float right = focus.x + screenWidth/2;
			float bottom = focus.y - screenHeight/2;
			float top = focus.y + screenHeight/2;

	    	ortho = Matrix4f.orthographic(left, right, bottom, top, -1f, 1f);
	    	
	    	if(Math.abs(panTarget.getPosition().x-focus.x) <= 1f && Math.abs(panTarget.getPosition().y-focus.y) <= 1f){
	    		centerCameraOn(panTarget.getPosition().x, panTarget.getPosition().y);
	    		panning = false;
	    	}
		}
	}
}
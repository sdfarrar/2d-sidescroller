package game;

import static org.lwjgl.glfw.GLFW.*;
import java.awt.Color;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

import game.core.VariableTimestepGame;
import game.entity.Player;
import game.input.KeyInput;
import game.input.MouseInput;
import game.world.World;
import graphics.opengl.Texture;
import math.Vector2f;


public class SidescrollerGame extends VariableTimestepGame{
	
	private GLFWKeyCallback keycallback;
	private GLFWMouseButtonCallback mBtnCallback;
	private GLFWCursorPosCallback mousePosCallback;
	private Texture texture;
	
	private World world;
	
	private boolean showStats;
	
	public SidescrollerGame() {
		super();
		
		showStats = true;
		
	}
	
	public void init(){
		super.init();

		long id = glfwGetCurrentContext();
		glfwSetKeyCallback(id, keycallback = new KeyInput());
		
		MouseInput m = new MouseInput();
		glfwSetMouseButtonCallback(id, mBtnCallback = m.getButtonCallback());
		glfwSetCursorPosCallback(id, mousePosCallback = m.getPositionCallback());
		
		texture = Texture.loadTexture("res/default_tex.png");
	}

	@Override
	public void initGameObjects() {
		world = new World(new Player(250, 400, 55, 115));
	}

	@Override
	public void updateGameObjects(float delta) {
	  world.update(delta);
	}

	@Override
	public void renderGameObjects(float alpha) {		
		texture.bind(); // binds a simple texture to be used for drawing
		renderer.setActiveLayer(1);		
		world.render(renderer, alpha);		
	}

	@Override
	public void disposeGameObjects() {
		mBtnCallback.release();
		mousePosCallback.release();
		keycallback.release();
		texture.delete();
	}

	@Override
	public void input() {
	  world.input();  
    
    if(KeyInput.isKeyDown(GLFW_KEY_G) && !KeyInput.isKeyToggled(GLFW_KEY_G)){
      KeyInput.setKeyToggled(GLFW_KEY_G);
      //physics.toggleGravity();
    }
    if(KeyInput.isKeyDown(GLFW_KEY_F1) && !KeyInput.isKeyToggled(GLFW_KEY_F1)){
      KeyInput.setKeyToggled(GLFW_KEY_F1);
      world.toggleWireframes();
    }
    if(KeyInput.isKeyDown(GLFW_KEY_F2) && !KeyInput.isKeyToggled(GLFW_KEY_F2)){
      KeyInput.setKeyToggled(GLFW_KEY_F2);
      showStats = !showStats;
    }
    if(KeyInput.isKeyDown(GLFW_KEY_1)){
      renderer.panCameraTo(world.getPlayer(), 5);
    }
    if(KeyInput.isKeyDown(GLFW_KEY_2)){
      //renderer.panCameraTo(player2, 5);
    }
    if(KeyInput.isKeyDown(GLFW_KEY_3)){
      renderer.followEntity(world.getPlayer());
    }
    if(KeyInput.isKeyDown(GLFW_KEY_4)){
      //renderer.followEntity(player2);
    }
    if(KeyInput.isKeyDown(GLFW_KEY_UP)){
      world.getPlayer().increaseSpeed();
    }
    if(KeyInput.isKeyDown(GLFW_KEY_DOWN)){
      world.getPlayer().decreaseSpeed();
    }
    if(KeyInput.isKeyDown(GLFW_KEY_ESCAPE)){
      long id = glfwGetCurrentContext();
      window.close(id);
    }
    if(MouseInput.isButtonPressed(GLFW_MOUSE_BUTTON_1)){
      
    }
    if(MouseInput.isButtonPressed(GLFW_MOUSE_BUTTON_2)){
      
    }
	}

	@Override
	public void renderText() {
		Color fc = Color.green, uc = Color.green;
		int fps = timer.getFPS();
		if(fps<60)
			fc = Color.yellow;
		else if(fps<30)
			fc = Color.red;
		
		int ups = timer.getUPS();
		if(ups<60)
			uc = Color.yellow;
		else if(ups<30)
			uc = Color.red;
		
		float [] mousePos = MouseInput.getMousePosition();
		float [] camPos = {	renderer.getCamera().getFocus().x, renderer.getCamera().getFocus().y };
			
		renderer.setActiveLayer(3);
		renderer.disableCamera();
		renderer.drawDebugText("FPS:"+timer.getFPS(), 950, 745, fc);
		renderer.drawDebugText("UPS:"+timer.getUPS(), 950, 725, uc);
		renderer.drawDebugText("Mouse: " + mousePos[0] + ", " + mousePos[1], 10, 745, Color.white);
		renderer.drawDebugText("Player: " + world.getPlayer().getPosition().x + ", " + world.getPlayer().getPosition().y, 10, 725, Color.white);
		renderer.drawDebugText("Camera: " + camPos[0] + ", " + camPos[1], 10, 705, Color.white);
		renderer.enableCamera();
	}

}

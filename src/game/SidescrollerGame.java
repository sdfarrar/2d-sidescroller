package game;

import static org.lwjgl.glfw.GLFW.*;
import game.core.VariableTimestepGame;
import game.input.KeyInput;

import java.awt.Color;

import org.lwjgl.glfw.GLFWKeyCallback;


public class SidescrollerGame extends VariableTimestepGame{
	
	private GLFWKeyCallback keycallback;
	
	public SidescrollerGame() {
		super();
	}
	
	public void init(){
		super.init();

		long id = glfwGetCurrentContext();
		glfwSetKeyCallback(id, keycallback = new KeyInput());
	}

	@Override
	public void initGameObjects() {
		
	}

	@Override
	public void updateGameObjects(float delta) {
		
	}

	@Override
	public void renderGameObjects(float alpha) {		
//		renderer.drawSquare(200, 100, 45, 45, Color.white);
		staticRenderer.drawRect(400, 200, 600, 175, Color.blue);
		staticRenderer.flush();		// TODO i don't like having to call this here
		shapeRenderer.drawSquare(200, 100, 45, 45, Color.white);
		shapeRenderer.drawCircle(600, 500, 50, Color.red);
		shapeRenderer.drawLine(0, 0, 800, 400, Color.red);
		shapeRenderer.drawSquare(500, 300, 150, 75, Color.darkGray);
	}

	@Override
	public void disposeGameObjects() {
		keycallback.release();
		
	}

	@Override
	public void input() {
		if(KeyInput.isKeyDown(GLFW_KEY_D)){
			shapeRenderer.moveCamera(5, 0, 0);
		}
		if(KeyInput.isKeyDown(GLFW_KEY_A)){
			shapeRenderer.moveCamera(-5, 0, 0);
		}
		if(KeyInput.isKeyDown(GLFW_KEY_W)){
			shapeRenderer.moveCamera(0, 5, 0);
		}
		if(KeyInput.isKeyDown(GLFW_KEY_S)){
			shapeRenderer.moveCamera(0, -5, 0);
		}
		if(KeyInput.isKeyDown(GLFW_KEY_ESCAPE)){
			long id = glfwGetCurrentContext();
			window.close(id);
		}
	}

	@Override
	public void renderText() {
		textRenderer.drawDebugText("FPS:"+timer.getFPS(), 950, 745, Color.green);
		textRenderer.drawDebugText("UPS:"+timer.getUPS(), 950, 725, Color.green);
	}

}

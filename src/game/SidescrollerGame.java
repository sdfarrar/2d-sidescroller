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
		renderer.drawSquare(200, 100, 45, 45, Color.white);
	}

	@Override
	public void disposeGameObjects() {
		keycallback.release();
		
	}

	@Override
	public void input() {
		if(KeyInput.isKeyDown(GLFW_KEY_D)){
			renderer.translateView(5, 0, 0);
		}
		if(KeyInput.isKeyDown(GLFW_KEY_A)){
			renderer.translateView(-5, 0, 0);
		}
		if(KeyInput.isKeyDown(GLFW_KEY_W)){
			renderer.translateView(0, 5, 0);
		}
		if(KeyInput.isKeyDown(GLFW_KEY_S)){
			renderer.translateView(0, -5, 0);
		}
		if(KeyInput.isKeyDown(GLFW_KEY_ESCAPE)){
			long id = glfwGetCurrentContext();
			window.close(id);
		}
	}

	@Override
	public void renderText() {
		
	}

}

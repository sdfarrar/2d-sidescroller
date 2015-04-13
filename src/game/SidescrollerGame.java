package game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;

import java.awt.Color;

import org.lwjgl.glfw.GLFWKeyCallback;
import game.core.VariableTimestepGame;


public class SidescrollerGame extends VariableTimestepGame{
	
	private GLFWKeyCallback keycallback;
	
	public SidescrollerGame() {
		super();
	}
	
	public void init(){
		super.init();

		long id = glfwGetCurrentContext();
		glfwSetKeyCallback(id, keycallback = new GLFWKeyCallback(){
			@Override
			public void invoke(long id, int key, int scancode, int action, int mods) {
				if(key==GLFW_KEY_D && (action==GLFW_PRESS || action==GLFW_REPEAT)){
					System.out.println("D");
					renderer.translateView(5, 0, 0);
				}
				if(key==GLFW_KEY_A && (action==GLFW_PRESS || action==GLFW_REPEAT)){
					System.out.println("A");
					renderer.translateView(-5, 0, 0);
				}
				if(key==GLFW_KEY_W && (action==GLFW_PRESS || action==GLFW_REPEAT)){
					System.out.println("A");
					renderer.translateView(0, 5, 0);
				}
				if(key==GLFW_KEY_S && (action==GLFW_PRESS || action==GLFW_REPEAT)){
					System.out.println("A");
					renderer.translateView(0, -5, 0);
				}
				if(key==GLFW_KEY_ESCAPE){					
					window.close(id);
				}
			}
		});

	}

	@Override
	public void initGameObjects() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateGameObjects(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderGameObjects(float alpha) {
		// TODO Auto-generated method stub
		renderer.drawSquare(200, 100, 45, 45, Color.white);
	}

	@Override
	public void disposeGameObjects() {
		keycallback.release();
		
	}

	@Override
	public void input() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void renderText() {
		// TODO Auto-generated method stub
		
	}

}

package game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.glfwGetCurrentContext;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import game.core.VariableTimestepGame;
import game.input.KeyInput;
import graphics.opengl.Texture;

import java.awt.Color;

import org.lwjgl.glfw.GLFWKeyCallback;


public class SidescrollerGame extends VariableTimestepGame{
	
	private GLFWKeyCallback keycallback;
	private Texture texture;
	
	public SidescrollerGame() {
		super();
	}
	
	public void init(){
		super.init();

		long id = glfwGetCurrentContext();
		glfwSetKeyCallback(id, keycallback = new KeyInput());
		texture = Texture.loadTexture("res/default_tex.png");
	}

	@Override
	public void initGameObjects() {
		
	}

	@Override
	public void updateGameObjects(float delta) {
		
	}

	@Override
	public void renderGameObjects(float alpha) {		
		texture.bind(); // binds a simple texture to be used for drawing
		renderer.setActiveLayer(0);
		renderer.drawRect(200, 100, 45, 45, Color.white);
//		renderer.drawCircle(600, 500, 50, Color.red);
//		renderer.setActiveLayer(1);
//		renderer.drawLine(0, 0, 800, 400, Color.red);
//		renderer.drawRect(500, 300, 150, 75, new Color(92,26,122));
		renderer.setActiveLayer(1);
		renderer.drawRect(500, 100, 15, 75, new Color(255,162,0));
		renderer.setActiveLayer(0);
		renderer.drawRect(500, 75, 50, 50, Color.red);
	}

	@Override
	public void disposeGameObjects() {
		keycallback.release();
		texture.delete();
	}

	@Override
	public void input() {
		if(KeyInput.isKeyDown(GLFW_KEY_D)){
			renderer.moveCamera(5, 0, 0);
		}
		if(KeyInput.isKeyDown(GLFW_KEY_A)){
			renderer.moveCamera(-5, 0, 0);
		}
		if(KeyInput.isKeyDown(GLFW_KEY_W)){
			renderer.moveCamera(0, 5, 0);
		}
		if(KeyInput.isKeyDown(GLFW_KEY_S)){
			renderer.moveCamera(0, -5, 0);
		}
		if(KeyInput.isKeyDown(GLFW_KEY_ESCAPE)){
			long id = glfwGetCurrentContext();
			window.close(id);
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
			
		renderer.setActiveLayer(3);
		renderer.disableCamera();
		renderer.drawDebugText("FPS:"+timer.getFPS(), 950, 745, fc);
		renderer.drawDebugText("UPS:"+timer.getUPS(), 950, 725, uc);
		renderer.enableCamera();
	}

}

package game;

import static org.lwjgl.glfw.GLFW.*;
import game.core.VariableTimestepGame;
import game.entity.Block;
import game.input.KeyInput;
import game.input.MouseInput;
import graphics.opengl.Texture;

import java.awt.Color;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;


public class SidescrollerGame extends VariableTimestepGame{
	
	private GLFWKeyCallback keycallback;
	private GLFWMouseButtonCallback mBtnCallback;
	private GLFWCursorPosCallback mousePosCallback;
	private Texture texture;
	
	private Block block;
	
	public SidescrollerGame() {
		super();
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
		block = new Block(150, 200, 25, 25);
	}

	@Override
	public void updateGameObjects(float delta) {
		
	}

	@Override
	public void renderGameObjects(float alpha) {		
		texture.bind(); // binds a simple texture to be used for drawing
		renderer.setActiveLayer(0);
//		renderer.drawRect(200, 100, 45, 45, Color.white);
		renderer.drawCircle(600, 500, 50, Color.red);
		renderer.drawCircleOutline(400, 500, 50, Color.red);
//		renderer.setActiveLayer(1);
//		renderer.drawLine(0, 0, 800, 400, Color.red);
		renderer.setActiveLayer(2);
		renderer.drawRectOutline(500, 300, 150, 75, Color.cyan);
		renderer.setActiveLayer(1);
		renderer.drawRect(500, 100, 15, 75, new Color(255,162,0));
		renderer.setActiveLayer(2);
		block.render(renderer, alpha);
//		renderer.drawRect(500, 75, 50, 50, Color.red);
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
		
		float [] pos = MouseInput.getMousePosition();
			
		renderer.setActiveLayer(3);
		renderer.disableCamera();
		renderer.drawDebugText("FPS:"+timer.getFPS(), 950, 745, fc);
		renderer.drawDebugText("UPS:"+timer.getUPS(), 950, 725, uc);
		renderer.drawDebugText("Mouse: " + pos[0] + ", " + pos[1], 10, 745, Color.white);
		renderer.enableCamera();
	}

}

package game;

import static org.lwjgl.glfw.GLFW.*;
import game.core.VariableTimestepGame;
import game.entity.Block;
import game.entity.Player;
import game.input.KeyInput;
import game.input.MouseInput;
import graphics.opengl.Texture;

import java.awt.Color;

import math.Vector2f;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;


public class SidescrollerGame extends VariableTimestepGame{
	
	private GLFWKeyCallback keycallback;
	private GLFWMouseButtonCallback mBtnCallback;
	private GLFWCursorPosCallback mousePosCallback;
	private Texture texture;
	
	private Block floor;
	private Player player;
	private Player player2;
	
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
		player = new Player(250, 400, 55, 115);
		player2 = new Player(1250, 400, 55, 115);
		floor = new Block(1000, 50, 2000, 50);
		//renderer.getCamera().followEntity(player);
		//renderer.getCamera().followEntity(floor);
	}

	@Override
	public void updateGameObjects(float delta) {
		player.update(delta);
	}

	@Override
	public void renderGameObjects(float alpha) {		
		texture.bind(); // binds a simple texture to be used for drawing
		renderer.setActiveLayer(1);		
		floor.debugRender(renderer, alpha);
		player.debugRender(renderer, alpha);
		player2.debugRender(renderer, alpha);		
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
		Vector2f playerVel = new Vector2f();
		if(KeyInput.isKeyDown(GLFW_KEY_D)){
			playerVel = playerVel.add(new Vector2f(5.0f, 0.0f));
		}
		if(KeyInput.isKeyDown(GLFW_KEY_A)){
			playerVel = playerVel.add(new Vector2f(-5.0f, 0.0f));
		}
		if(KeyInput.isKeyDown(GLFW_KEY_W)){
			playerVel = playerVel.add(new Vector2f(0.0f, 5.0f));
		}
		if(KeyInput.isKeyDown(GLFW_KEY_S)){
			playerVel = playerVel.add(new Vector2f(0.0f, -5.0f));
		}
		if(KeyInput.isKeyDown(GLFW_KEY_1)){
			renderer.getCamera().panCameraTo(player, 5);
		}
		if(KeyInput.isKeyDown(GLFW_KEY_2)){
			renderer.getCamera().panCameraTo(player2, 5);
		}
		if(KeyInput.isKeyDown(GLFW_KEY_3)){
			renderer.getCamera().followEntity(player);
		}
		if(KeyInput.isKeyDown(GLFW_KEY_4)){
			renderer.getCamera().followEntity(player2);
		}
		if(KeyInput.isKeyDown(GLFW_KEY_ESCAPE)){
			long id = glfwGetCurrentContext();
			window.close(id);
		}
		if(MouseInput.isButtonPressed(GLFW_MOUSE_BUTTON_1)){
			
		}
		if(MouseInput.isButtonPressed(GLFW_MOUSE_BUTTON_2)){
			
		}
		
		player.setVelocity(playerVel);
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
		renderer.drawDebugText("Player: " + player.getPosition().x + ", " + player.getPosition().y, 10, 725, Color.white);
		renderer.drawDebugText("Camera: " + camPos[0] + ", " + camPos[1], 10, 705, Color.white);
		renderer.enableCamera();
	}

}

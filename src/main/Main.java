package main;

import org.lwjgl.glfw.GLFW;

import game.SidescrollerGame;
import game.core.AbstractGame;

public class Main {

	public static void main(String[] args) {
		AbstractGame game = new SidescrollerGame();
		try{
			game.start();
		}finally{
			GLFW.glfwTerminate();
		}
	}

}

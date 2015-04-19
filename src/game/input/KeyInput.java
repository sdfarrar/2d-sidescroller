package game.input;

import org.lwjgl.glfw.GLFWKeyCallback;
import static org.lwjgl.glfw.GLFW.*;

public class KeyInput extends GLFWKeyCallback {
	
	public static boolean[] keys = new boolean[512];
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) {
		keys[key] = action != GLFW_RELEASE;
	}
	
	public static boolean isKeyDown(int key){
		return KeyInput.keys[key];
	}

}

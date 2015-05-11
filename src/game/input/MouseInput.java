package game.input;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseInput{
	
	private MousePositionInput posInput;
	private MouseButtonInput btnInput;
	
	public MouseInput(){
		posInput = new MousePositionInput();
		btnInput = new MouseButtonInput();
	}
	
	public GLFWCursorPosCallback getPositionCallback(){
		return posInput;
	}
	
	public GLFWMouseButtonCallback getButtonCallback(){
		return btnInput;
	}
	
	public static boolean isButtonPressed(int button){
		return MouseButtonInput.buttons[button];
	}
	
	public static float[] getMousePosition(){
		return new float[]{MousePositionInput.position[0], MousePositionInput.position[1]};
	}
	
	private static class MousePositionInput extends GLFWCursorPosCallback{
		public static float[] position = new float[2];
		@Override
		public void invoke(long window, double xpos, double ypos) {
            IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
            GLFW.glfwGetFramebufferSize(window, null, heightBuffer);
            int height = heightBuffer.get();            
			position[0] = (float) xpos;
			position[1] = (float) (height-ypos);	// flip the y axis
		}
		
	}
	
	private static class MouseButtonInput extends GLFWMouseButtonCallback{
		public static boolean[] buttons = new boolean[8]; 
		@Override
		public void invoke(long window, int button, int action, int mods) {
			buttons[button] = action != GLFW_RELEASE;
		}
		
	}
}

package graphics;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

import java.util.ArrayDeque;

public class RenderingPrimitivesManager {
	
	private ArrayDeque<RenderingChunk> deque;
	
	public RenderingPrimitivesManager(){
		deque = new ArrayDeque<RenderingChunk>();
	}
	
	public void addRenderingData(int primitiveType, int verticesAdded){
		if(deque.isEmpty()){			
			deque.add(new RenderingChunk(primitiveType, verticesAdded));
			return;
		}
		
		RenderingChunk lastAdded = deque.getLast();
		if(primitiveType==lastAdded.primitiveType){
			lastAdded.verticesCount += verticesAdded;
		}else{
			deque.add(new RenderingChunk(primitiveType, verticesAdded));
		}
	}
	
	public RenderingChunk getNextChunk(){
		if(deque.isEmpty())
			return null;
		return deque.remove();
	}
	
	public class RenderingChunk{
		int primitiveType;
		int verticesCount;
		
		public RenderingChunk(int type, int count){
			this.primitiveType = type;
			this.verticesCount = count;
		}
		
		public String toString(){
			String type = "UNKNOWN";
			switch(primitiveType){
			case GL_TRIANGLES:
				type = "GL_TRIANGLE";break;
			case GL_LINES:
				type = "GL_LINES";break;
			case GL_LINE_LOOP:
				type = "GL_LINE_LOOP";break;
			}
			return "Primitive: " + type + " Vertices: " + verticesCount;
		}
	}
}

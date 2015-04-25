package graphics;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STREAM_DRAW;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import graphics.RenderingPrimitivesManager.RenderingChunk;
import graphics.opengl.Shader;
import graphics.opengl.ShaderProgram;
import graphics.opengl.VertexArrayObject;
import graphics.opengl.VertexBufferObject;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import math.Vector2f;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

public class ShapeRenderer extends AbstractRenderer {
	
	private int windowWidth, windowHeight;
	private RenderingPrimitivesManager primitivesManager;
	//private Camera camera;
	
	public ShapeRenderer() {
		super();
		windowWidth = windowHeight = 0;
		primitivesManager = new RenderingPrimitivesManager();
	}

	@Override
	public void init() {
		// create our vertex array object
        vao = new VertexArrayObject();
        vao.bind();

        // create out vertex buffer object
        vbo = new VertexBufferObject();
        vbo.bind(GL_ARRAY_BUFFER);

        // create our buffer of vertices to draw with
        vertices = BufferUtils.createFloatBuffer(BUFFER_SIZE);

        // allocate storage for the vbo by sending null data to the gpu
        long size = BUFFER_SIZE * Float.BYTES;
        vbo.uploadData(GL_ARRAY_BUFFER, size, GL_STREAM_DRAW);

        // load our default shaders
        vertexShader = Shader.loadShader(GL_VERTEX_SHADER, "res/test_vertex.glsl");
        fragmentShader = Shader.loadShader(GL_FRAGMENT_SHADER, "res/test_fragment.glsl");

        // create the shader program
        program = new ShaderProgram();
        program.attachShader(vertexShader);
        program.attachShader(fragmentShader);

        program.bindFragmentDataLocation(0, "fragColor");

        program.link();
        program.use();

        // get the window width and height for our orthographic matrix
        long window = GLFW.glfwGetCurrentContext();
        IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
        GLFW.glfwGetFramebufferSize(window, widthBuffer, heightBuffer);
        int width = widthBuffer.get();
        int height = heightBuffer.get();
        windowWidth = width;
        windowHeight = height;

        // specify the vertex attributes. describe the order/structure of our vertices buffer
        specifyVertexAttributes();

        // set the texture uniform in the shader
        int uniTex = program.getUniformLocation("texImage");
        program.setUniform(uniTex, 0);

        // create the orthographic matrix and set the uniform in the shader
        camera = new Camera(width, height);
        int uniOrtho = program.getUniformLocation("ortho");
        program.setUniform(uniOrtho, camera.getOrthoMatrix());

        // enable blending
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

	@Override
	public void dispose() {
		if (vao != null) {
            vao.delete();
        }
        vbo.delete();
        vertexShader.delete();
        fragmentShader.delete();
        program.delete();
	}
	
	protected void specifyVertexAttributes() {
    	// Specify vertex pointer
    	int posAttrib = program.getAttributeLocation("position");
    	program.enableVertexAttribute(posAttrib);
    	program.pointVertexAttribute(posAttrib, 2, 7 * Float.BYTES, 0);

    	// Specify color pointer
    	int colAttrib = program.getAttributeLocation("color");
    	program.enableVertexAttribute(colAttrib);
    	program.pointVertexAttribute(colAttrib, 3, 7 * Float.BYTES, 2 * Float.BYTES);

    	// Specify texture pointer
    	int texAttrib = program.getAttributeLocation("texcoord");
    	program.enableVertexAttribute(texAttrib);
    	program.pointVertexAttribute(texAttrib, 2, 7 * Float.BYTES, 5 * Float.BYTES);
    }	

	@Override
	public void flush() {
		if (numVertices > 0) {
            vertices.flip();

            vao.bind();

            program.use();

            // create the orthographic matrix and set the uniform in the shader
            int uniOrtho = program.getUniformLocation("ortho");
            program.setUniform(uniOrtho, camera.getOrthoMatrix());
            
            /* Upload the new vertex data */
            vbo.bind(GL_ARRAY_BUFFER);
            vbo.uploadSubData(GL_ARRAY_BUFFER, 0, vertices);

            /* Draw batches */
            RenderingChunk chunk;
            int first = 0;
            while((chunk = primitivesManager.getNextChunk())!=null){
            	System.out.println(chunk);
            	glDrawArrays(chunk.primitiveType, first, chunk.verticesCount);
            	first+=chunk.verticesCount;
            }
            System.out.println("=========================");

            /* Clear vertex data for next batch */
            vertices.clear();
            numVertices = 0;
        }
	}
	
	public void drawLine(float x1, float y1, float x2, float y2, Color color){
		if(vertices.remaining()< 2*7)
			flush();
		
		float r = color.getRed();
		float g = color.getGreen();
		float b = color.getBlue();
		
		vertices.put(x1).put(y1).put(r).put(g).put(b).put(0).put(0);
		vertices.put(x2).put(y2).put(r).put(g).put(b).put(0).put(0);
		numVertices+=2;
		
		primitivesManager.addRenderingData(GL_LINES, 2);
	}
    
	public void drawCircle(float cx, float cy, float radius, Color color) {
		float increment = 0.075f;
		int iterations = (int)Math.ceil(2*Math.PI/increment);

		if(vertices.remaining()< 7*iterations)
			flush();

		float r = color.getRed();
		float g = color.getGreen();
		float b = color.getBlue();
		
		int points = 0;
		for(float i=0; i<2*Math.PI; i+=increment){
			float x = cx + (float) (radius * Math.cos(i));
			float y = cy + (float) (radius * Math.sin(i));
			vertices.put(x).put(y).put(r).put(g).put(b).put(0).put(0);
			points++;
		}		

		primitivesManager.addRenderingData(GL_LINE_LOOP, points);
		numVertices += points;		
	}
	
	public void drawRect(float x, float y, float width, float height, Color color){
		if(vertices.remaining() < 6*7){
			flush();
		}
	
		float r = color.getRed();
		float g = color.getGreen();
		float b = color.getBlue();
		
		System.out.println("rgb: " + r + ", " + g + ", " + b);
		
		Vector2f tl = new Vector2f(x-width/2,y+height/2);
		Vector2f bl = new Vector2f(x-width/2,y-height/2);
		Vector2f tr = new Vector2f(x+width/2, y+height/2);
		Vector2f br = new Vector2f(x+width/2, y-height/2);
		
		vertices.put(bl.x).put(bl.y).put(r).put(g).put(b).put(0).put(0);
		vertices.put(tl.x).put(tl.y).put(r).put(g).put(b).put(0).put(0);
		vertices.put(tr.x).put(tr.y).put(r).put(g).put(b).put(0).put(0);
		
		vertices.put(bl.x).put(bl.y).put(r).put(g).put(b).put(0).put(0);
		vertices.put(tr.x).put(tr.y).put(r).put(g).put(b).put(0).put(0);
		vertices.put(br.x).put(br.y).put(r).put(g).put(b).put(0).put(0);
		
		numVertices += 6;
		
		primitivesManager.addRenderingData(GL_TRIANGLES, 6);
	}
	
	public void moveCamera(int dx, int dy, int dz){
		camera.translateCamera(dx, dy, dz);
	}

}

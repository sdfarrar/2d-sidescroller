package graphics;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STREAM_DRAW;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import graphics.opengl.Shader;
import graphics.opengl.ShaderProgram;
import graphics.opengl.Texture;
import graphics.opengl.VertexArrayObject;
import graphics.opengl.VertexBufferObject;

import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import math.Matrix4f;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

public abstract class AbstractRenderer implements Renderer {
	public static final int BUFFER_SIZE = 4096;
	
	protected static boolean drawing;
	
	protected VertexArrayObject vao;
	protected VertexBufferObject vbo;
	protected Shader vertexShader;
	protected Shader fragmentShader;
	protected ShaderProgram program;    
    
	protected FloatBuffer vertices;
	protected int numVertices;
	
	//protected Matrix4f ortho;
	protected Camera camera;
	
	public AbstractRenderer(){
		numVertices = 0;
		drawing = false;
	}

	@Override
	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	
	/**
     * Draws the currently bound texture on specified coordinates.
     *
     * @param texture Used for getting width and height of the texture
     * @param x X position of the texture
     * @param y Y position of the texture
     */
    public void drawTexture(Texture texture, float x, float y) {
        drawTexture(texture, x, y, Color.WHITE);
    }

    /**
     * Draws the currently bound texture on specified coordinates and with
     * specified color.
     *
     * @param texture Used for getting width and height of the texture
     * @param x X position of the texture
     * @param y Y position of the texture
     * @param c The color to use
     */
    public void drawTexture(Texture texture, float x, float y, Color c) {
        /* Vertex positions */
        float x1 = x;
        float y1 = y;
        float x2 = x1 + texture.getWidth();
        float y2 = y1 + texture.getHeight();

        /* Texture coordinates */
        float s1 = 0f;
        float t1 = 0f;
        float s2 = 1f;
        float t2 = 1f;

        drawTextureRegion(x1, y1, x2, y2, s1, t1, s2, t2, c);
    }

    /**
     * Draws the currently bound texture on specified coordinates and with
     * specified width and height.
     * 
     * @param texture
     * @param x X position of the texture
     * @param y Y position of the texture
     * @param width desired width of the texture
     * @param height desired height of the texture 
     */
    public void drawTexture(Texture texture, float x, float y, float width, float height){
    	float x2 = x+width;
    	float y2 = y+height;
    	
    	drawTextureRegion(x, y, x2, y2, 0, 0, 1, 1);
    }

    /**
     * Draws a texture region with the currently bound texture on specified
     * coordinates.
     *
     * @param texture Used for getting width and height of the texture
     * @param x X position of the texture
     * @param y Y position of the texture
     * @param regX X position of the texture region
     * @param regY Y position of the texture region
     * @param regWidth Width of the texture region
     * @param regHeight Height of the texture region
     */
    public void drawTextureRegion(Texture texture, float x, float y, float regX, float regY, float regWidth, float regHeight) {
        drawTextureRegion(texture, x, y, regX, regY, regWidth, regHeight, Color.WHITE);
    }

    /**
     * Draws a texture region with the currently bound texture on specified
     * coordinates.
     *
     * @param texture Used for getting width and height of the texture
     * @param x X position of the texture
     * @param y Y position of the texture
     * @param regX X position of the texture region
     * @param regY Y position of the texture region
     * @param regWidth Width of the texture region
     * @param regHeight Height of the texture region
     * @param c The color to use
     */
    public void drawTextureRegion(Texture texture, float x, float y, float regX, float regY, float regWidth, float regHeight, Color c) {
        /* Vertex positions */
        float x1 = x;
        float y1 = y;
        float x2 = x + regWidth;
        float y2 = y + regHeight;

        /* Texture coordinates */
        float s1 = regX / texture.getWidth();
        float t1 = regY / texture.getHeight();
        float s2 = (regX + regWidth) / texture.getWidth();
        float t2 = (regY + regHeight) / texture.getHeight();

        drawTextureRegion(x1, y1, x2, y2, s1, t1, s2, t2, c);
    }

    /**
     * Draws a texture region with the currently bound texture on specified
     * coordinates.
     *
     * @param x1 Bottom left x position
     * @param y1 Bottom left y position
     * @param x2 Top right x position
     * @param y2 Top right y position
     * @param s1 Bottom left s coordinate
     * @param t1 Bottom left t coordinate
     * @param s2 Top right s coordinate
     * @param t2 Top right t coordinate
     */
    public void drawTextureRegion(float x1, float y1, float x2, float y2, float s1, float t1, float s2, float t2) {
        drawTextureRegion(x1, y1, x2, y2, s1, t1, s2, t2, Color.WHITE);
    }

    /**
     * Draws a texture region with the currently bound texture on specified
     * coordinates.
     *
     * @param x1 Bottom left x position
     * @param y1 Bottom left y position
     * @param x2 Top right x position
     * @param y2 Top right y position
     * @param s1 Bottom left s coordinate
     * @param t1 Bottom left t coordinate
     * @param s2 Top right s coordinate
     * @param t2 Top right t coordinate
     * @param c The color to use
     */
    public void drawTextureRegion(float x1, float y1, float x2, float y2, float s1, float t1, float s2, float t2, Color c) {
    	if (vertices.remaining() < 7 * 6) {
            /* We need more space in the buffer, so flush it */
            flush();
        }

        float r = c.getRed() / 255f;
        float g = c.getGreen() / 255f;
        float b = c.getBlue() / 255f;

        vertices.put(x1).put(y1).put(r).put(g).put(b).put(s1).put(t1);
        vertices.put(x1).put(y2).put(r).put(g).put(b).put(s1).put(t2);
        vertices.put(x2).put(y2).put(r).put(g).put(b).put(s2).put(t2);

        vertices.put(x1).put(y1).put(r).put(g).put(b).put(s1).put(t1);
        vertices.put(x2).put(y2).put(r).put(g).put(b).put(s2).put(t2);
        vertices.put(x2).put(y1).put(r).put(g).put(b).put(s2).put(t1);

        numVertices += 6;
    }
    
    private void specifyVertexAttributes() {
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

}

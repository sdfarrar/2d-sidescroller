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
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import math.Matrix4f;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

import text.Font;

public class TextRenderer extends AbstractRenderer{
	private static final int BUFFER_SIZE = 4096;
    
    private Font font;
	private Font debugFont;
	
    /**
     * Initializes the renderer.
     *
     * @param defaultContext Specifies if the OpenGL context is 3.2 compatible
     */
    public void init() {
    	numVertices = 0;
        
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
        vertexShader = Shader.loadShader(GL_VERTEX_SHADER, "res/default_vertex.glsl");
        fragmentShader = Shader.loadShader(GL_FRAGMENT_SHADER, "res/default_fragment.glsl");

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

        // specify the vertex attributes. describe the order/structure of our vertices buffer
        specifyVertexAttributes();

        // set the texture uniform in the shader
        int uniTex = program.getUniformLocation("texImage");
        program.setUniform(uniTex, 0);

        // create the orthographic matrix and set the uniform in the shader
        Matrix4f ortho = Matrix4f.orthographic(0f, width, 0f, height, -1f, 1f);
        int uniOrtho = program.getUniformLocation("ortho");
        program.setUniform(uniOrtho, ortho);

        // enable blending
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        try {
            font = new Font(new FileInputStream("res/Inconsolata.otf"), 16);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(GameRenderer.class.getName()).log(Level.CONFIG, null, ex);
            font = new Font();
        }
		debugFont = new Font(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 16), true);
    }
    
    /**
     * Dispose renderer and clean up its used data.
     */
    public void dispose() {
        if (vao != null) {
            vao.delete();
        }
        vbo.delete();
        vertexShader.delete();
        fragmentShader.delete();
        program.delete();
        font.dispose();
		debugFont.dispose();
    }

    /**
     * Flushes the data to the GPU to let it get rendered.
     */
    public void flush() {
        if (numVertices > 0) {
            vertices.flip();

            vao.bind();

            program.use();

            /* Upload the new vertex data */
            vbo.bind(GL_ARRAY_BUFFER);
            vbo.uploadSubData(GL_ARRAY_BUFFER, 0, vertices);

            /* Draw batch */
            glDrawArrays(GL_TRIANGLES, 0, numVertices);

            /* Clear vertex data for next batch */
            vertices.clear();
            numVertices = 0;
        }
    }
    
    /**
     * Calculates total width of a text.
     *
     * @param text The text
     * @return Total width of the text
     */
    public int getTextWidth(CharSequence text) {
        return font.getWidth(text);
    }

    /**
     * Calculates total height of a text.
     *
     * @param text The text
     * @return Total width of the text
     */
    public int getTextHeight(CharSequence text) {
        return font.getHeight(text);
    }

    /**
     * Calculates total width of a debug text.
     *
     * @param text The text
     * @return Total width of the text
     */
    public int getDebugTextWidth(CharSequence text) {
        return debugFont.getWidth(text);
    }

    /**
     * Calculates total height of a debug text.
     *
     * @param text The text
     * @return Total width of the text
     */
    public int getDebugTextHeight(CharSequence text) {
        return debugFont.getHeight(text);
    }

    /**
     * Draw text at the specified position.
     *
     * @param text Text to draw
     * @param x X coordinate of the text position
     * @param y Y coordinate of the text position
     */
    public void drawText(CharSequence text, float x, float y) {
        font.drawText(this, text, x, y);
    }

    /**
     * Draw debug text at the specified position.
     *
     * @param text Text to draw
     * @param x X coordinate of the text position
     * @param y Y coordinate of the text position
     */
    public void drawDebugText(CharSequence text, float x, float y) {
        debugFont.drawText(this, text, x, y);
    }

    /**
     * Draw text at the specified position and color.
     *
     * @param text Text to draw
     * @param x X coordinate of the text position
     * @param y Y coordinate of the text position
     * @param c Color to use
     */
    public void drawText(CharSequence text, float x, float y, Color c) {
        font.drawText(this, text, x, y, c);
    }

    /**
     * Draw debug text at the specified position and color.
     *
     * @param text Text to draw
     * @param x X coordinate of the text position
     * @param y Y coordinate of the text position
     * @param c Color to use
     */
    public void drawDebugText(CharSequence text, float x, float y, Color c) {
        debugFont.drawText(this, text, x, y, c);
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

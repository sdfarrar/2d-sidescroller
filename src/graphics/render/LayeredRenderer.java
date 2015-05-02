package graphics.render;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import graphics.Camera;
import graphics.render.utils.Layer;
import graphics.render.utils.RenderingPrimitivesManager.RenderingChunk;

import java.awt.Color;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import math.Vector2f;
import text.Font;


public class LayeredRenderer extends AbstractLayeredRenderer {

	private Font font, debugFont;
		
	public LayeredRenderer(int numOfLayers) {
		super(numOfLayers);
	}
	
	public void init(){
		super.init();
		try {
            font = new Font(new FileInputStream("res/Inconsolata.otf"), 16);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(LayeredRenderer.class.getName()).log(Level.CONFIG, null, ex);
            font = new Font();
        }
		debugFont = new Font(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 16), true);
	}
	
	public void dispose(){
		super.dispose();
		font.dispose();
		debugFont.dispose();
	}
	
	@Override
	public void flush() {
		int count = 0;
		for(Layer layer : getLayerManager().getLayers()){
			if (layer.numVertices > 0) {
				System.out.println("flushing { layer: " + (count++) + ", vertices: " + layer.numVertices + " }");
				
				layer.vertices.flip();
				
				vao.bind();

				program.use();

				// create the orthographic matrix and set the uniform in the shader				
				int uniOrtho = program.getUniformLocation("ortho");
				if(this.isCameraEnabled()){
					program.setUniform(uniOrtho, camera.getOrthoMatrix());
				}else{
					// utilizes default orthographic matrix created from a camera 
					program.setUniform(uniOrtho, new Camera(camera.getScreenWidth(), camera.getScreenHeight()).getOrthoMatrix());
				}

				/* Upload the new vertex data */
				vbo.bind(GL_ARRAY_BUFFER);
				vbo.uploadSubData(GL_ARRAY_BUFFER, 0, layer.vertices);
				
				/* Draw batches */
				RenderingChunk chunk;
				int first = 0;
				while((chunk = layer.getNextRenderingDataChunk())!=null){
					glDrawArrays(chunk.primitiveType, first, chunk.verticesCount);
					first+=chunk.verticesCount;
				}				

				/* Clear vertex data for next batch */
				layer.vertices.clear();
				layer.numVertices = 0;
			}
			
		}
	}
	
	public void drawLine(float x1, float y1, float x2, float y2, Color color){
		Layer layer = this.getActiveLayer();
		
		if(layer.vertices.remaining()< 2*7)
			flush();
		
		float r = color.getRed();
		float g = color.getGreen();
		float b = color.getBlue();
		
		layer.vertices.put(x1).put(y1).put(r).put(g).put(b).put(0).put(0);
		layer.vertices.put(x2).put(y2).put(r).put(g).put(b).put(0).put(0);
		layer.numVertices+=2;
		
		this.getLayerManager().addRenderingData(GL_LINES, 2);
	}
    
	public void drawCircle(float cx, float cy, float radius, Color color) {
		float increment = 0.075f;
		int iterations = (int)Math.ceil(2*Math.PI/increment);
		
		Layer layer = this.getActiveLayer();

		if(layer.vertices.remaining()< 7*iterations)
			flush();

		float r = color.getRed();
		float g = color.getGreen();
		float b = color.getBlue();
		
		int points = 0;
		for(float i=0; i<2*Math.PI; i+=increment){
			float x = cx + (float) (radius * Math.cos(i));
			float y = cy + (float) (radius * Math.sin(i));
			layer.vertices.put(x).put(y).put(r).put(g).put(b).put(0).put(0);
			points++;
		}

		layer.numVertices += points;
		this.getLayerManager().addRenderingData(GL_LINE_LOOP, points);
	}
	
	public void drawRect(float x, float y, float width, float height, Color color){
		Layer layer = getLayerManager().getActiveLayer();
		
		if(layer.vertices.remaining() < 6*7){
			flush();
		}
	
		float r = color.getRed();
		float g = color.getGreen();
		float b = color.getBlue();
		
		Vector2f tl = new Vector2f(x-width/2,y+height/2);
		Vector2f bl = new Vector2f(x-width/2,y-height/2);
		Vector2f tr = new Vector2f(x+width/2, y+height/2);
		Vector2f br = new Vector2f(x+width/2, y-height/2);
		
		layer.vertices.put(bl.x).put(bl.y).put(r).put(g).put(b).put(0).put(0);
		layer.vertices.put(tl.x).put(tl.y).put(r).put(g).put(b).put(0).put(0);
		layer.vertices.put(tr.x).put(tr.y).put(r).put(g).put(b).put(0).put(0);
		
		layer.vertices.put(bl.x).put(bl.y).put(r).put(g).put(b).put(0).put(0);
		layer.vertices.put(tr.x).put(tr.y).put(r).put(g).put(b).put(0).put(0);
		layer.vertices.put(br.x).put(br.y).put(r).put(g).put(b).put(0).put(0);
		
		layer.numVertices += 6;
		
		getLayerManager().addRenderingData(GL_TRIANGLES, 6);
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

	

}

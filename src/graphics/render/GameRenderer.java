package graphics.render;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import graphics.render.utils.RenderingPrimitivesManager;

import java.awt.Color;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import math.Vector2f;
import text.Font;

public abstract class GameRenderer extends AbstractTextureRenderer implements TextRenderer, ShapeRenderer{

	private RenderingPrimitivesManager primitivesManager;	
	private Font font, debugFont;
	
	public void init(){
		super.init();
		try {
            font = new Font(new FileInputStream("res/Inconsolata.otf"), 16);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(GameRenderer.class.getName()).log(Level.CONFIG, null, ex);
            font = new Font();
        }
		debugFont = new Font(new java.awt.Font(java.awt.Font.SANS_SERIF, java.awt.Font.PLAIN, 16), true);
	}
	
	public void dispose(){
		super.dispose();
		
	}
	
	@Override
	public void drawLine(float x1, float y1, float x2, float y2, Color color) {
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

	@Override
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

	@Override
	public void drawRect(float x, float y, float width, float height, Color color) {
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

	@Override
	public int getTextWidth(CharSequence text) {
		return font.getWidth(text);
	}

	@Override
	public int getTextHeight(CharSequence text) {
		return font.getHeight(text);
	}

	@Override
	public int getDebugTextWidth(CharSequence text) {
		 return debugFont.getWidth(text);
	}

	@Override
	public int getDebugTextHeight(CharSequence text) {
		return debugFont.getHeight(text);
	}

//	@Override
//	public void drawText(CharSequence text, float x, float y) {
//		font.drawText(this, text, x, y);		
//	}
//
//	@Override
//	public void drawDebugText(CharSequence text, float x, float y) {
//		debugFont.drawText(this, text, x, y);
//	}
//
//	@Override
//	public void drawText(CharSequence text, float x, float y, Color c) {
//		font.drawText(this, text, x, y, c);		
//	}
//
//	@Override
//	public void drawDebugText(CharSequence text, float x, float y, Color c) {
//		debugFont.drawText(this, text, x, y, c);
//	}

}

package graphics.render;

import graphics.opengl.Texture;

import java.awt.Color;

public interface TextureRenderer {
	public void drawTexture(Texture texture, float x, float y);
	public void drawTexture(Texture texture, float x, float y, Color c);
	public void drawTexture(Texture texture, float x, float y, float width, float height);
	public void drawTextureRegion(Texture texture, float x, float y, float regX, float regY, float regWidth, float regHeight);
	public void drawTextureRegion(Texture texture, float x, float y, float regX, float regY, float regWidth, float regHeight, Color c);
	public void drawTextureRegion(float x1, float y1, float x2, float y2, float s1, float t1, float s2, float t2);
	public void drawTextureRegion(float x1, float y1, float x2, float y2, float s1, float t1, float s2, float t2, Color c);	
}
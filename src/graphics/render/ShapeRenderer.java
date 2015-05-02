package graphics.render;

import java.awt.Color;

public interface ShapeRenderer {
	public void drawLine(float x1, float y1, float x2, float y2, Color color);
	public void drawCircle(float cx, float cy, float radius, Color color);
	public void drawRect(float x, float y, float width, float height, Color color);
}

package graphics.render;

import java.awt.Color;

public interface TextRenderer {
	public int getTextWidth(CharSequence text);
    public int getTextHeight(CharSequence text);
    public int getDebugTextWidth(CharSequence text);
    public int getDebugTextHeight(CharSequence text);
    public void drawText(CharSequence text, float x, float y);
    public void drawDebugText(CharSequence text, float x, float y);
    public void drawText(CharSequence text, float x, float y, Color c);
    public void drawDebugText(CharSequence text, float x, float y, Color c);
}

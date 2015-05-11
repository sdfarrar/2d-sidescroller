package game.entity;


import java.awt.Color;

import graphics.render.LayeredRenderer;

public class Block extends AbstractEntity {

	public Block(float cx, float cy, float width, float height) {
		super(cx, cy, width, height);
	}

	@Override
	public void input() {

	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void render(LayeredRenderer renderer, float alpha) {
		renderer.drawRect(position.x, position.y, width, height, color);
	}
	
	@Override
	public void debugRender(LayeredRenderer renderer, float alpha){
		renderer.drawDebugRectOutline(position.x, position.y, width, height, debugColor);
		//this.renderHitbox(renderer, alpha);
	}
	
	@Override 
	public void renderHitbox(LayeredRenderer renderer, float alpha){
		float x = (float)hitbox.getX(), y = (float)hitbox.getY(), width = (float)hitbox.getWidth(), height = (float)hitbox.getHeight();
		renderer.drawRectOutline(x, y, width, height, Color.RED);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}

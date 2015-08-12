package game.entity;


import graphics.render.LayeredRenderer;

import java.awt.Color;

import physics.PhysicsEngine;

public class Block extends AbstractEntity {

	public Block(float cx, float cy, float width, float height) {
		super(cx, cy, width, height);
	}

	@Override
	public void input() {

	}

	@Override
	public void update(PhysicsEngine physics, float delta) {

	}

	@Override
	public void render(LayeredRenderer renderer, float alpha) {
		renderer.drawRect(position.x, position.y, width, height, color);
	}
	
	@Override
	public void debugRender(LayeredRenderer renderer, float alpha){
		renderer.drawDebugRectOutline(position.x, position.y, width, height, debugColor);
		this.renderHitbox(renderer, alpha);
	}
	
	@Override 
	public void renderHitbox(LayeredRenderer renderer, float alpha){
		renderer.drawRectOutline(hitbox.getCenter().x, hitbox.getCenter().y, hitbox.getDimensions().x, hitbox.getDimensions().y, Color.RED);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resolveCollision(AbstractEntity e) {
		// TODO Auto-generated method stub
		
	}

}

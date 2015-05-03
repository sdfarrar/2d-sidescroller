package game.entity;

import java.awt.Color;

import math.Vector2f;
import graphics.render.LayeredRenderer;

public class Player extends AbstractMoveableEntity {

	public Player(float x, float y, float width, float height) {
		super(x, y, width, height);
		color = Color.GRAY;
	}

	@Override
	public void input() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float delta) {
		previousPosition = position;
		position = position.add(velocity);
	}

	@Override
	public void render(LayeredRenderer renderer, float alpha) {
		renderer.drawRect(position.x, position.y, width, height, color);
	}

	@Override
	public void debugRender(LayeredRenderer renderer, float alpha) {
		Vector2f interpolatedPosition = previousPosition.lerp(position, alpha);
		float x = interpolatedPosition.x;
		float y = interpolatedPosition.y;
		renderer.drawDebugRectOutline(x, y, width, height, debugColor);
//		renderer.drawDebugRectOutline(position.x, position.y, width, height, debugColor);
	}

	@Override
	public void renderHitbox(LayeredRenderer renderer, float alpha) {
		// TODO Auto-generated method stub

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

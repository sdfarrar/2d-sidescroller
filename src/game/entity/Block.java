package game.entity;

import graphics.render.LayeredRenderer;

public class Block extends AbstractEntity {

	public Block(float x, float y, float width, float height) {
		super(x, y, width, height);
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
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}

package physics;

import game.entity.AbstractEntity;
import game.entity.AbstractMoveableEntity;
import game.entity.Player;
import game.entity.Tile;
import game.entity.TileType;

import java.util.List;

import math.Vector2f;

public class PhysicsEngine {
	private static float GRAVITY_VEL = -2.5f;
	
	private boolean gravity;
	
	public PhysicsEngine(){
		gravity = false;
	}
	
	public void update(AbstractMoveableEntity e, float delta){
		Vector2f vel = e.getVelocity();
		if(gravity){
			//vel = vel.add(new Vector2f(0, GRAVITY_VEL)).scale(delta*100);
			vel = new Vector2f(e.getVelocity().x, GRAVITY_VEL*delta*100);
			e.setVelocity(vel);
		}
		Vector2f pos = e.getPosition();
		e.setPosition(pos.add(vel));
	}
	
	public void checkCollision(AbstractMoveableEntity target, List<AbstractEntity> entities, Tile[][] tiles){
//		entities.forEach((entity) -> {
//			if(!entity.equals(target) && target.collidesWith(entity)){
//				//List<Hitbox.Collision> sides = target.getCollisionSide(entity);
//				target.resolveCollision(entity);
//				//System.out.println("side: " + sides);
//				//System.out.println(target.getVelocity());
//				
//				entity.setColor(Color.RED);
//			}else{
//				entity.setColor(Color.WHITE);
//			}
//		});
		
	}
	
	public void checkPlayerTileCollision(Player player, Tile[][] tiles){
		int topTile = (int)(Math.floor((float)player.getHitbox().getTopBound()/Tile.HEIGHT));
		int botTile = (int)(Math.ceil((float)player.getHitbox().getBottomBound()/Tile.HEIGHT)) - 1;
		int leftTile = (int)(Math.floor((float)player.getHitbox().getLeftBound()/Tile.WIDTH));
		int rightTile = (int)(Math.ceil((float)player.getHitbox().getRightBound()/Tile.WIDTH)) - 1;
		
		for(int i=leftTile; i<=rightTile; ++i){
			for(int j=botTile; j<=topTile; ++j){
				Tile tile = tiles[i][j];
				boolean isSky = tile.getTileType().equals(TileType.SKY); 
				if(!isSky && tile.collidesWith(player)){
					player.resolveCollision(tile);
				}
			}
		}
	}
	
	public void toggleGravity(){
		gravity = !gravity;
	}
}

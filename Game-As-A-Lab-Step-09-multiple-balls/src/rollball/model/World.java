package rollball.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import rollball.common.*;
import rollball.physics.BoundaryCollision;

public class World {
	
	private List<GameObject> picks;
	private HashMap<String,GameObject> balls;
	private RectBoundingBox mainBBox;
	private WorldEventListener evListener;
	
	public World(RectBoundingBox bbox){
		picks = new ArrayList<GameObject>();
		balls = new HashMap<String,GameObject>();
		mainBBox = bbox;
	}

	public void setEventListener(WorldEventListener l){
		evListener = l;
	}
	
	public void addBall(String id, GameObject ball){
		balls.put(id, ball);
	}

	public void removeBall(GameObject ball){
		balls.remove(ball);
	}
	
	public void addPickUp(GameObject obj){
		picks.add(obj);
	}

	public void removePickUp(GameObject obj){
		picks.remove(obj);
	}
	
	public void updateState(int dt){
		for (GameObject ball: balls.values()) {
			ball.updatePhysics(dt, this);
		}
	}

	public Optional<BoundaryCollision> checkCollisionWithBoundaries(P2d pos, CircleBoundingBox box){
		P2d ul = mainBBox.getULCorner();
		P2d br = mainBBox.getBRCorner();
		double r = box.getRadius();
		if (pos.y + r> ul.y){
			return Optional.of(new BoundaryCollision(BoundaryCollision.CollisionEdge.TOP, new P2d(pos.x,ul.y)));
		} else if (pos.y - r < br.y){
			return Optional.of(new BoundaryCollision(BoundaryCollision.CollisionEdge.BOTTOM, new P2d(pos.x,br.y)));
		} else if (pos.x + r > br.x){
			return Optional.of(new BoundaryCollision(BoundaryCollision.CollisionEdge.RIGHT, new P2d(br.x,pos.y)));
		} else if (pos.x - r < ul.x){
			return Optional.of(new BoundaryCollision(BoundaryCollision.CollisionEdge.LEFT, new P2d(ul.x,pos.y)));
		} else {
			return Optional.empty();
		}
	}

	public Optional<GameObject> checkCollisionWithPickUpObj(P2d pos, CircleBoundingBox box){
		double radius = box.getRadius();
		for (GameObject obj: picks){
			if (obj.getBBox().isCollidingWith(pos,radius)){
				return Optional.of(obj);
			}
		}
		return Optional.empty();
	}
	
	public void notifyWorldEvent(WorldEvent ev){
		evListener.notifyEvent(ev);
	}
	
	public RectBoundingBox getBBox(){
		return mainBBox;
	}
	
	public Collection<GameObject> getBalls(){
		return balls.values();
	}

	public GameObject getBall(String id){
		return balls.get(id);
	}

	public List<GameObject> getPickableObj(){
		return picks;
	}

	public List<GameObject> getSceneEntities(){
		List<GameObject> entities = new ArrayList<GameObject>();
		entities.addAll(picks);
		entities.addAll(balls.values());
		return entities;
	}
	
}

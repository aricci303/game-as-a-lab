package rollball.model;

import java.util.ArrayList;
import java.util.List;
import rollball.common.*;

public class World {
	
	private List<PickUpObj> picks;
	private Ball ball;
	private RectBoundingBox mainBBox;
	private WorldEventListener evListener;
	
	public World(RectBoundingBox bbox){
		picks = new ArrayList<PickUpObj>();
		mainBBox = bbox;
	}

	public void setEventListener(WorldEventListener l){
		evListener = l;
	}
	
	public void setBall(Ball ball){
		this.ball = ball;
	}
	
	public void addPickUp(PickUpObj obj){
		picks.add(obj);
	}

	public void removePickUp(PickUpObj obj){
		picks.remove(obj);
	}
	
	public void updateState(int dt){
		picks.stream().forEach(obj -> { obj.updateState(dt);});
		ball.updateState(dt);		
		checkBoundaries();
		checkCollisions();
	}
	
	private void checkBoundaries(){
		P2d pos = ball.getCurrentPos();
		P2d ul = mainBBox.getULCorner();
		P2d br = mainBBox.getBRCorner();
		double r = ball.getRadius();
		if (pos.y + r> ul.y){
			ball.setPos(new P2d(pos.x, ul.y - r));
			ball.flipVelOnY();
			evListener.notifyEvent(new HitBorderEvent(new P2d(pos.x,ul.y)));
		} else if (pos.y - r < br.y){
			ball.setPos(new P2d(pos.x, br.y + r));
			ball.flipVelOnY();
			evListener.notifyEvent(new HitBorderEvent(new P2d(pos.x,br.y)));
		}
		if (pos.x + r > br.x){
			ball.setPos(new P2d(br.x - r, pos.y));
			ball.flipVelOnX();
			evListener.notifyEvent(new HitBorderEvent(new P2d(br.x,pos.y)));
		} else if (pos.x - r < ul.x){
			ball.setPos(new P2d(ul.x + r, pos.y));
			ball.flipVelOnX();
			evListener.notifyEvent(new HitBorderEvent(new P2d(ul.x,pos.y)));
		}
	}

	private void checkCollisions(){
		P2d ballpos = ball.getCurrentPos();
		double radius = ball.getRadius();
		PickUpObj found = null;
		for (PickUpObj obj: picks){
			if (obj.getBBox().isCollidingWith(ballpos,radius)){
				found = obj;
				break;
			}
		}
		if (found != null){
			evListener.notifyEvent(new HitPickableEvent(found));
		}
	}
	
	public List<GameObject> getSceneEntities(){
		List<GameObject> entities = new ArrayList<GameObject>();
		entities.addAll(picks);
		entities.add(ball);
		return entities;
	}
		
	public RectBoundingBox getBBox(){
		return mainBBox;
	}
	
	public Ball getBall(){
		return ball;
	}
}

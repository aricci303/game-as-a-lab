package rollball.physics;

import java.util.Optional;

import rollball.common.P2d;
import rollball.model.*;

public class BallPhysicsComponent extends PhysicsComponent {

	public void update(int dt, GameObject obj, World w) {
		super.update(dt, obj, w);
		//w.checkBoundaries(obj);
		CircleBoundingBox bbox = (CircleBoundingBox) obj.getBBox();
		Optional<BoundaryCollision> binfo = w.checkCollisionWithBoundaries(obj.getCurrentPos(), bbox);
		if (binfo.isPresent()){
			BoundaryCollision info = binfo.get();
			P2d pos = obj.getCurrentPos();
			
			switch (info.getEdge()){
			case TOP: 
				obj.setPos(new P2d(pos.x, info.getWhere().y - bbox.getRadius()));
				obj.flipVelOnY();
				w.notifyWorldEvent(new HitBorderEvent(obj,info.getWhere()));
				break;
			case BOTTOM: 
				obj.setPos(new P2d(pos.x, info.getWhere().y + bbox.getRadius()));
				obj.flipVelOnY();
				w.notifyWorldEvent(new HitBorderEvent(obj,info.getWhere()));
				break;
			case LEFT: 
				obj.setPos(new P2d(info.getWhere().x + bbox.getRadius(), pos.y));
				obj.flipVelOnX();
				w.notifyWorldEvent(new HitBorderEvent(obj,info.getWhere()));
				break;
			case RIGHT: 
				obj.setPos(new P2d(info.getWhere().x - bbox.getRadius(), pos.y));
				obj.flipVelOnX();
				w.notifyWorldEvent(new HitBorderEvent(obj,info.getWhere()));
				break;
			}
		}
		
		Optional<GameObject> pick = w.checkCollisionWithPickUpObj(obj.getCurrentPos(), bbox);
		if (pick.isPresent()){
			w.notifyWorldEvent(new HitPickUpEvent(obj,pick.get()));
		}
		
	}

}

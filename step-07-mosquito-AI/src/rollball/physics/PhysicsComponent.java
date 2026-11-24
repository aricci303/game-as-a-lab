package rollball.physics;

import rollball.common.*;
import rollball.model.*;

public abstract class PhysicsComponent {

	public void update(long dt, GameObject obj, World w){
		P2d pos = obj.getCurrentPos();
		V2d vel = obj.getCurrentVel();
		obj.setPos(pos.sum(vel.mul(0.001*dt)));
	}
}

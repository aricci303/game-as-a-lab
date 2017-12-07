package rollball.model.ser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import rollball.common.*;
import rollball.model.RectBoundingBox;
import rollball.physics.BoundaryCollision;

public class WorldSer implements Serializable {
	
	private List<GameObjectSer> picks;
	private List<GameObjectSer> balls;
	
	public WorldSer(List<GameObjectSer> picks, List<GameObjectSer> balls){
		this.picks = picks;
		this.balls = balls;
	}
	
	public Collection<GameObjectSer> getBalls(){
		return balls;
	}

	public List<GameObjectSer> getPickableObj(){
		return picks;
	}
	
}

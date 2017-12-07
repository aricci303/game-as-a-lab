package rollball.model;

import java.io.Serializable;

import rollball.common.P2d;

public interface BoundingBox extends Serializable {

	boolean isCollidingWith(P2d p, double radius);
	
}

package rollball.model;

import rollball.common.*;

public class PickUpObj extends GameObject {

	private double edge;
	
	public PickUpObj(P2d pos, double edge){
		super(pos, new V2d(0,0), new CircleBoundingBox(new P2d(pos.x, pos.y), edge/2));
		this.edge = edge;
	}
	
	public double getEdge(){
		return edge;
	}
	
}

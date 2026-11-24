package rollball.model;

import rollball.common.P2d;

public class RectBoundingBox implements BoundingBox {

	private P2d p0,p1;
	
	public RectBoundingBox(P2d p0, P2d p1){
		this.p0 = p0;
		this.p1 = p1;
	}
	
	public P2d getULCorner(){
		return p0;
	}
	
	public P2d getBRCorner(){
		return p1;
	}
	
	/**
	 * @TODO to be implemented
	 */
	public boolean isCollidingWith(P2d p, double radius){
		assert(false);
		return false;
	}
}

package rollball.model;

import rollball.common.*;

public class HitBorderEvent extends AbstractBallEvent {

	private P2d where;
	
	public HitBorderEvent(GameObject ball, P2d where){
		super(ball);
		this.where = where;
	}
	
	public P2d getWhere(){
		return where;
	}
}

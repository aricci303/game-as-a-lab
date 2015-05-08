package rollball.model;

import rollball.common.*;

public class GameObject {

	private P2d pos;
	private V2d vel;
	private BoundingBox bbox;
	
	protected GameObject(P2d pos, V2d vel, BoundingBox box){
		this.pos = pos;
		this.vel = vel;
		this.bbox = box;
	}
	
	public void setPos(P2d pos){
		this.pos = pos;
	}

	public void setVel(V2d vel){
		this.vel = vel;
	}
	
	public void updateState(int dt){
		this.pos = this.pos.sum(vel.mul(0.001*dt));
	}
	
	public BoundingBox getBBox(){
		return bbox;
	}
	
	public P2d getCurrentPos(){
		return pos;
	}
	
	public V2d getCurrentVel(){
		return vel;
	}

	public void flipVelOnY(){
		this.vel = new V2d(vel.x, -vel.y);
	}

	public void flipVelOnX(){
		this.vel = new V2d(-vel.x, vel.y);
	}
	
}

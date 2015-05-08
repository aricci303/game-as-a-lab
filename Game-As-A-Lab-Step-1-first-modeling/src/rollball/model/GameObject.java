package rollball.model;

import rollball.common.*;

/**
 * Classe base per rappresentare elementi del gioco.
 * 
 * @author aricci
 *
 */
public class GameObject  {

	private P2d pos;
	private V2d vel;
	
	protected GameObject(P2d pos, V2d vel){
		this.pos = pos;
		this.vel = vel;
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
	
	public P2d getCurrentPos(){
		return pos;
	}
	
	public V2d getCurrentVel(){
		return vel;
	}
}

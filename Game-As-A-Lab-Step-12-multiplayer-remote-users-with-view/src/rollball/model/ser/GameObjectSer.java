package rollball.model.ser;

import java.io.Serializable;

import rollball.common.*;
import rollball.graphics.*;
import rollball.input.*;
import rollball.model.BoundingBox;
import rollball.model.GameObject;
import rollball.model.GameObject.Type;
import rollball.physics.*;

public class GameObjectSer implements Serializable {


	private GameObject.Type type;
	private P2d pos;
	private V2d vel;
	private BoundingBox bbox;
	
	public GameObjectSer(GameObject.Type type, P2d pos, V2d vel, BoundingBox box){
		this.type = type;
		this.pos = pos;
		this.vel = vel;
		this.bbox = box;
	}
	
	public GameObject.Type getType(){
		return type;
	}
	
	public void setPos(P2d pos){
		this.pos = pos;
	}

	public void setVel(V2d vel){
		this.vel = vel;
	}

	public void flipVelOnY(){
		this.vel = new V2d(vel.x, -vel.y);
	}

	public void flipVelOnX(){
		this.vel = new V2d(-vel.x, vel.y);
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

}

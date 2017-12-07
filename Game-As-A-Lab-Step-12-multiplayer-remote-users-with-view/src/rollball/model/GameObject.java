package rollball.model;

import rollball.common.*;
import rollball.graphics.*;
import rollball.input.*;
import rollball.model.ser.GameObjectSer;
import rollball.physics.*;

public class GameObject {

	public static enum Type { BALL, PICKABLE_OBJ }

	private Type type;
	private P2d pos;
	private V2d vel;
	private BoundingBox bbox;
	
	private InputComponent input;
	private GraphicsComponent graph;
	private PhysicsComponent phys;
	
	public GameObject(Type type, P2d pos, V2d vel, BoundingBox box, InputComponent input, GraphicsComponent graph, PhysicsComponent phys){
		this.type = type;
		this.pos = pos;
		this.vel = vel;
		this.bbox = box;
		this.input = input;
		this.graph = graph;
		this.phys = phys;
	}
	
	public GameObject(GameObjectSer ser, InputComponent input, GraphicsComponent graph, PhysicsComponent phys){
		this.type = ser.getType();
		this.pos = ser.getCurrentPos();
		this.vel = ser.getCurrentVel();
		this.bbox = ser.getBBox();
		this.input = input;
		this.graph = graph;
		this.phys = phys;
	}

	public Type getType(){
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

	public void updateInput(){
		input.update(this);
	}

	public void updatePhysics(int dt, World w){
		phys.update(dt, this, w);
	}
	
	public void updateGraphics(Graphics g){
		graph.update(this, g);
	}
	
	public InputComponent getInputComponent() {
		return input;
	}
	
	public GameObjectSer serialize() {
		return new GameObjectSer(type, pos, vel, bbox); 
	}
}

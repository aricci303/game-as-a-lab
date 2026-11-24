package rollball.core;

import rollball.common.P2d;
import rollball.common.V2d;
import rollball.model.*;
import rollball.physics.*;
import rollball.graphics.*;
import rollball.input.*;

public class GameFactory {

	static private GameFactory instance;
	
	static public GameFactory getInstance(){
		if (instance == null){
			instance = new GameFactory();
		}
		return instance;
	}
	
	public GameObject createPlayerBall(P2d pos, double radius, V2d vel, Player player){
		return new GameObject(GameObject.Type.BALL, pos, vel, new CircleBoundingBox(new P2d(pos.x, pos.y), radius),
				new PlayerInputComponentWithAcc(player),
				new BallGraphicsComponent(),
				new BallPhysicsComponent());
	}
	
	public GameObject createBall(P2d pos, double radius, V2d vel, InputComponent input){
		return new GameObject(GameObject.Type.BALL, pos, vel, new CircleBoundingBox(new P2d(pos.x, pos.y), radius),
				input,
				new BallGraphicsComponent(),
				new BallPhysicsComponent());
	}
	
	public GameObject createPickUpObject(P2d pos, double edge){
		return new GameObject(GameObject.Type.PICKABLE_OBJ, pos, new V2d(0,0), new CircleBoundingBox(new P2d(pos.x, pos.y), edge/2),
				new NullInputComponent(),
				new PickUpObjGraphicsComponent(),
				new PickUpObjPhysicsComponent());
	}
}

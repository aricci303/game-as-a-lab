package rollball.model;

public class BallCollisionEvent extends AbstractBallEvent {

	private GameObject ball;
	
	public BallCollisionEvent(GameObject ballWho, GameObject target){
		super(ballWho);
		this.ball = target;
	}
	
	public GameObject getTargetBall(){
		return ball;
	}
}

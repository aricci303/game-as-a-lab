package rollball.model;

public abstract class AbstractBallEvent implements WorldEvent {

	private GameObject ball;
	
	protected AbstractBallEvent(GameObject ball) {
		this.ball = ball;
	}

	
	public GameObject getBall() {
		return ball;
	}
	
}

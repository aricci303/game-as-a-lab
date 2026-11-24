package rollball.model;


public class HitPickUpEvent extends AbstractBallEvent {

	private GameObject obj;
	
	public HitPickUpEvent(GameObject ball, GameObject obj){
		super(ball);
		this.obj = obj;
	}
	
	public GameObject getCollisionObj(){
		return obj;
	}
}

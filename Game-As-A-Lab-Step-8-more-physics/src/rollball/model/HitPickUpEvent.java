package rollball.model;


public class HitPickUpEvent implements WorldEvent {

	private GameObject obj;
	
	public HitPickUpEvent(GameObject obj){
		this.obj = obj;
	}
	
	public GameObject getCollisionObj(){
		return obj;
	}
}

package rollball.model;

import java.util.ArrayList;
import java.util.List;

public class World  {
	
	private List<PickUpObj> picks;
	private Ball ball;
	
	public World(){
		picks = new ArrayList<PickUpObj>();
	}

	public void setBall(Ball ball){
		this.ball = ball;
	}
	
	public void addPickUp(PickUpObj obj){
		picks.add(obj);
	}
	
	public void updateState(int dt){
		picks.stream().forEach(obj -> { obj.updateState(dt);});
		ball.updateState(dt);
	}
	
	public List<GameObject> getSceneEntities(){
		List<GameObject> entities = new ArrayList<GameObject>();
		entities.addAll(picks);
		entities.add(ball);
		return entities;
	}
	
	public Ball getBall(){
		return ball;
	}
}

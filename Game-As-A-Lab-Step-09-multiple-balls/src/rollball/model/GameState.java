package rollball.model;

import rollball.common.P2d;
import rollball.common.V2d;
import rollball.core.GameFactory;
import rollball.input.MosquitoAIInputComponent;

public class GameState {
	
	private int score;
	private World world;
	
	public GameState(WorldEventListener l){
		GameFactory f = GameFactory.getInstance();
		
		score = 0;
		world = new World(new RectBoundingBox(new P2d(-9,8), new P2d(9,-8)));
		world.addBall("main",f.createUserBall(new P2d(0,0), 1, new V2d(0,0)));
		world.addBall("bot",f.createBall(new P2d(0,0), 1, new V2d(8,0), new MosquitoAIInputComponent()));
		world.addPickUp(f.createPickUpObject(new P2d(0,5), 1));
		world.addPickUp(f.createPickUpObject(new P2d(6,0), 1));
		world.addPickUp(f.createPickUpObject(new P2d(-4,3), 1));
		world.addPickUp(f.createPickUpObject(new P2d(-1,-7), 1));
		world.setEventListener(l);
	}
	
	public World getWorld(){
		return world;
	}
	
	public void incScore(){
		score++;
	}

	public void decScore(){
		score--;
	}
	
	public int getScore(){
		return score;
	}
	
	public boolean isGameOver(){
		return world.getPickableObj().size() == 0;
	}
	
	public void update(int dt){
		world.updateState(dt);
	}
}

package rollball.core;

import rollball.model.*;
import rollball.common.P2d;
import rollball.common.V2d;
import rollball.graphics.*;

public class GameEngine {

	private long period = 20;
	private World world;
	private Scene view;
	
	public GameEngine(){
	}
	
	public void setup(){
		world = new World();
		world.setBall(new Ball(new P2d(-1,-1), new V2d(1,1)));
		world.addPickUp(new PickUpObj(new P2d(0,1)));
		world.addPickUp(new PickUpObj(new P2d(2,0)));
		view = new Scene(world, 600, 600);
		
	}
	
	public void mainLoop(){
		long previousCycleStartTime = System.currentTimeMillis();
		while(true){
			long currentCycleStartTime = System.currentTimeMillis();
			long elapsed = currentCycleStartTime - previousCycleStartTime;
			processInput();
			updateGame(elapsed);
			render();
			waitForNextFrame(currentCycleStartTime);
			previousCycleStartTime = currentCycleStartTime;
		}
	}

	protected void waitForNextFrame(long cycleStartTime){
		long dt = System.currentTimeMillis() - cycleStartTime;
		if (dt < period){
			try {
				Thread.sleep(period - dt);
			} catch (Exception ex){}
		}
	}
	
	protected void processInput(){
	}
	
	protected void updateGame(long elapsed){
		world.updateState(elapsed);
	}
	
	protected void render(){
		view.render();
	}
}

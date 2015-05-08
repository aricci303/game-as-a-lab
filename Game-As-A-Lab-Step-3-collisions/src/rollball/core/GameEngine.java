package rollball.core;

import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import rollball.model.*;
import rollball.common.P2d;
import rollball.common.V2d;
import rollball.graphics.*;
import rollball.input.*;

public class GameEngine implements Controller {

	private long period = 20; 
	
	private World world;
	private Scene view;
	private BlockingQueue<Command> cmdQueue; 
	
	public GameEngine(){
		cmdQueue = new ArrayBlockingQueue<Command>(100);
	}
	
	public void setup(){
		world = new World(new RectBoundingBox(new P2d(-9,8), new P2d(9,-8)));
		world.setBall(new Ball(new P2d(0,0), 1, new V2d(8,3)));
		world.addPickUp(new PickUpObj(new P2d(0,5), 1));
		world.addPickUp(new PickUpObj(new P2d(6,0), 1));
		world.addPickUp(new PickUpObj(new P2d(-4,3), 1));
		world.addPickUp(new PickUpObj(new P2d(-1,-7), 1));
		view = new Scene(world, 600, 600, 20,20);
		view.setInputController(this);
		
	}
	
	public void mainLoop(){
		long lastTime = System.currentTimeMillis();
		while(true){
			long current = System.currentTimeMillis();
			int elapsed = (int)(current - lastTime);
			processInput();
			updateGame(elapsed);
			render();
			waitForNextFrame(current);
			lastTime = current;
		}
	}

	protected void waitForNextFrame(long current){
		long dt = System.currentTimeMillis() - current;
		if (dt < period){
			try {
				Thread.sleep(period-dt);
			} catch (Exception ex){}
		}
	}
	
	protected void processInput(){
		Command cmd = cmdQueue.poll();
		if (cmd != null){
			cmd.execute(world);
		}
	}
	
	protected void updateGame(int elapsed){
		world.updateState(elapsed);
	}
	
	protected void render(){
		view.render();
	}

	@Override
	public void notifyCommand(Command cmd) {
		cmdQueue.add(cmd);
	}
}

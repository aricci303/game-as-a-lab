package rollball.core;

import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import rollball.model.*;
import rollball.graphics.*;
import rollball.input.*;

public class GameEngine implements Controller, WorldEventListener {

	private long period = 20; 
	
	private Scene view;
	private BlockingQueue<Command> cmdQueue; 
	private LinkedList<WorldEvent> eventQueue;
	private GameState gameState;
	
	public GameEngine(){
		cmdQueue = new ArrayBlockingQueue<Command>(100);
		eventQueue = new LinkedList<WorldEvent>();
	}
	
	public void initGame(){
		gameState = new GameState(this);
		view = new Scene(gameState, 600, 600, 20,20);
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
			cmd.execute(gameState);
		}
	}
	
	protected void updateGame(int elapsed){
		gameState.update(elapsed);		
		checkEvents();
	}
	
	protected void checkEvents(){
		World scene = gameState.getWorld();
		eventQueue.stream().forEach(ev -> {
			if (ev instanceof HitPickableEvent){
				HitPickableEvent cev = (HitPickableEvent) ev;
				scene.removePickUp(cev.getCollisionObj());
				gameState.incScore();
				/* launch other effects */
			} else if (ev instanceof HitBorderEvent){
				// HitBorderEvent bev = (HitBorderEvent) ev;
				gameState.decScore();
				/* launch other effects */
			}
		});
		eventQueue.clear();
	}
	
	protected void render(){
		view.render();
	}

	@Override
	public void notifyCommand(Command cmd) {
		cmdQueue.add(cmd);
	}

	@Override
	public void notifyEvent(WorldEvent ev) {
		eventQueue.add(ev);
	}
}

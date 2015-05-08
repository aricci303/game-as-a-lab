package rollball.core;

import java.util.LinkedList;
import rollball.model.*;
import rollball.graphics.*;
import rollball.input.*;

public class GameEngine implements WorldEventListener {

	private long period = 20; 	
	private Scene view;
	private LinkedList<WorldEvent> eventQueue;
	private GameState gameState;
	private KeyboardInputController controller;
	
	public GameEngine(){
		eventQueue = new LinkedList<WorldEvent>();
	}
	
	public void initGame(){
		gameState = new GameState(this);
		controller = new KeyboardInputController();
		view = new SwingScene(gameState, controller, 600, 600, 20,20);
	}
	
	public void mainLoop(){
		long lastTime = System.currentTimeMillis();
		while (!gameState.isGameOver()) {
			long current = System.currentTimeMillis();
			int elapsed = (int)(current - lastTime);
			processInput();
			updateGame(elapsed);
			render();
			waitForNextFrame(current);
			lastTime = current;
		}
		renderGameOver();
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
		gameState.getWorld().getBall().updateInput(controller);
	}
	
	protected void updateGame(int elapsed){
		gameState.getWorld().updateState(elapsed);
		checkEvents();
	}
	
	protected void checkEvents(){
		World scene = gameState.getWorld();
		eventQueue.stream().forEach(ev -> {
			if (ev instanceof HitPickUpEvent){
				HitPickUpEvent cev = (HitPickUpEvent) ev;
				scene.removePickUp(cev.getCollisionObj());
				gameState.incScore();
			} else if (ev instanceof HitBorderEvent){
				// HitBorderEvent bev = (HitBorderEvent) ev;
				gameState.decScore();
			}
		});
		eventQueue.clear();
	}
	
	protected void render(){
		view.render();
	}

	protected void renderGameOver(){
		view.renderGameOver();
	}

	@Override
	public void notifyEvent(WorldEvent ev) {
		eventQueue.add(ev);
	}
}

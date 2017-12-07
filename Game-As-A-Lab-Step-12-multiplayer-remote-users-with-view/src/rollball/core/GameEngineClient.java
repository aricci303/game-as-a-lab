package rollball.core;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import rollball.model.*;
import rollball.model.ser.GameStateSer;
import rollball.remote.NetworkClientAgent;
import rollball.graphics.*;
import rollball.input.*;
import static java.awt.event.KeyEvent.*;

public class GameEngineClient implements GameEngineInterface {

	private long period = 20; 	
	private Scene view;
	private GameState gameState;
		
	private BlockingQueue<GameStateSer> gameStateQueue;
	private NetworkClientAgent clientAgent;
	private String serverHost;
	private int serverPort;
	
	public GameEngineClient(String host, int port){
		gameStateQueue = new LinkedBlockingQueue<GameStateSer>();
		this.serverHost = host;
		this.serverPort = port;
		
	}
	
	public void initGame(){
		gameState = new GameState();
		view = new SwingScene(gameState, this, 600, 700, 20,20);

		clientAgent = new NetworkClientAgent(serverHost,serverPort,this);
		clientAgent.start();
	}
	
	public void mainLoop(){
		long lastTime = System.currentTimeMillis();
		while (!gameState.isGameOver()) {
			long current = System.currentTimeMillis();
			int elapsed = (int)(current - lastTime);
			// processInput();
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
		for (GameObject ball: gameState.getWorld().getBalls()) {
			ball.updateInput();
		}
	}
	
	protected void updateGame(int elapsed){
		GameStateSer state = gameStateQueue.poll();
		if (state != null) {
			gameState.update(state);
		}
	}
	
	protected void checkEvents(){
	}
	
	protected void render(){
		view.render();
	}

	protected void renderGameOver(){
		view.renderGameOver();
	}

	@Override
	public void notifyEvent(WorldEvent ev) {
	}
	
	public void notifyNewState(GameStateSer ser) {
		gameStateQueue.add(ser);
	}

	
	public InputController getController(String name) throws InputControllerNotFoundException {
		return null;
	}

	public Collection<KeyboardInputController> getKeyboardInputControllers() {
		return null;
	}
	
}

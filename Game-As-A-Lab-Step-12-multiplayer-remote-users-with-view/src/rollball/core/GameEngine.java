package rollball.core;

import java.util.*;
import rollball.model.*;
import rollball.remote.NetworkServerAgent;
import rollball.graphics.*;
import rollball.input.*;
import static java.awt.event.KeyEvent.*;

public class GameEngine implements GameEngineInterface {

	public static int REMOTE_CONTROLLER_PORT_A = 5000;

	private long period = 20; 	
	private Scene view;
	private LinkedList<WorldEvent> eventQueue;
	private GameState gameState;
	private HashMap<String,InputController> controllers;
	private NetworkServerAgent serverAgent;

	public GameEngine(){
		eventQueue = new LinkedList<WorldEvent>();
	}
	
	public void initGame(){
		controllers = new HashMap<String,InputController>();

		KeyboardInputController contrA = new KeyboardInputController(VK_UP,VK_DOWN,VK_LEFT,VK_RIGHT);
		KeyboardInputController contrB = new KeyboardInputController(VK_W,VK_Z,VK_A,VK_S);		
		controllers.put("KeyPadA", contrA);
		controllers.put("KeyPadB", contrB);
		
		RemoteInputController remoteA = new RemoteInputController(REMOTE_CONTROLLER_PORT_A);
		controllers.put("RemoteA", remoteA);
		
		gameState = new GameState(this);
		view = new SwingScene(gameState, this, 600, 700, 20,20);

		serverAgent = new NetworkServerAgent(6000);
		serverAgent.start();

	}
	
	public void mainLoop(){
		long lastTime = System.currentTimeMillis();
		while (!gameState.isGameOver()) {
			long current = System.currentTimeMillis();
			int elapsed = (int)(current - lastTime);
			processInput();
			updateGame(elapsed);
			render();
			serverAgent.notifyState(gameState.serialize());
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
		gameState.getWorld().updateState(elapsed);
		checkEvents();
	}
	
	protected void checkEvents(){
		World scene = gameState.getWorld();
		eventQueue.stream().forEach(ev -> {
			if (ev instanceof HitPickUpEvent){
				HitPickUpEvent cev = (HitPickUpEvent) ev;
				scene.removePickUp(cev.getCollisionObj());
				InputComponent input = cev.getBall().getInputComponent();
				if (input instanceof AbstractPlayerInputComponent) {
					((AbstractPlayerInputComponent) input).getPlayer().updateScore(1);
				}
			} else if (ev instanceof HitBorderEvent){
				HitBorderEvent bev = (HitBorderEvent) ev;				
				InputComponent input = bev.getBall().getInputComponent();
				if (input instanceof AbstractPlayerInputComponent) {
					((AbstractPlayerInputComponent) input).getPlayer().updateScore(-1);
				}
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
	
	public InputController getController(String name) throws InputControllerNotFoundException {
		InputController ctrl = controllers.get(name);
		if (ctrl == null) {
			throw new InputControllerNotFoundException();
		} else {
			return ctrl;
		}
	}

	public Collection<KeyboardInputController> getKeyboardInputControllers() {
		Collection<KeyboardInputController> contr = new ArrayList<KeyboardInputController>();
		for (InputController c: controllers.values()) {
			if (c instanceof KeyboardInputController) {
				contr.add((KeyboardInputController) c);
			}
		}
		return contr;
	}
}

package rollball.core;

import java.util.logging.*;

/**
 * Game engine skeleton.
 * 
 * @author aricci
 *
 */
public class GameEngine  {

	private long period = 20; /* 20 ms = 50 frame al secondo */
	private Logger logger = Logger.getLogger("GameEngine");
	
	public GameEngine(){
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

	/**
	 * Take a little nap to synch with the frame rate
	 * 
	 * @param current
	 */
	protected void waitForNextFrame(long current){
		long dt = System.currentTimeMillis() - current;
		if (dt < period){
			try {
				Thread.sleep(period-dt);
			} catch (Exception ex){}
		}
	}
	
	protected void processInput(){
		logger.log(Level.INFO, "..process input..");
	}
	
	protected void updateGame(int elapsed){
		logger.log(Level.INFO, "..update game: elapsed "+elapsed);
	}
	
	protected void render(){
		logger.log(Level.INFO, "..render..");
	}

}

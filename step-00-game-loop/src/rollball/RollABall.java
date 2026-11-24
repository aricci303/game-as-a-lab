package rollball;

import rollball.core.GameEngine;

public class RollABall {

	public static void main(String[] args) {
		
		/* frame per secondo */
		int fps = 25;
		
		GameEngine engine = new GameEngine(fps);
		engine.mainLoop();
	}

}

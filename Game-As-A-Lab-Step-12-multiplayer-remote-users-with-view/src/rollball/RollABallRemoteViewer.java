package rollball;

import rollball.core.GameEngineClient;

public class RollABallRemoteViewer {

	public static void main(String[] args) {
		GameEngineClient engine = new GameEngineClient("localhost",6000);
		engine.initGame();
		engine.mainLoop();
	}

}

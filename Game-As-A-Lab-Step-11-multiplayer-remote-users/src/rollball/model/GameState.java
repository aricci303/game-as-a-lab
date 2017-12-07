package rollball.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import rollball.common.P2d;
import rollball.common.V2d;
import rollball.core.GameEngine;
import rollball.core.GameFactory;
import rollball.input.KeyboardInputController;
import rollball.input.MosquitoAIInputComponent;

public class GameState {
	
	private World world;
	private HashMap<String,Player> players;

	public GameState(GameEngine engine){
		GameFactory f = GameFactory.getInstance();		
		world = new World(new RectBoundingBox(new P2d(-9,8), new P2d(9,-8)));

		players = new HashMap<String,Player>();
		try {
			players.put("pippo", new Player("pippo", engine.getController("KeyPadA")));
			// players.put("pluto", new Player("pluto", engine.getController("KeyPadB")));
			players.put("pluto", new Player("pluto", engine.getController("RemoteA")));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		double pos = 0;

		world.addBall(f.createPlayerBall(new P2d(pos,0), 1, new V2d(0,0), players.get("pippo")));
		world.addBall(f.createPlayerBall(new P2d(pos+1,0), 1, new V2d(0,0), players.get("pluto")));

		
		world.addBall(f.createBall(new P2d(0,0), 1, new V2d(8,0), new MosquitoAIInputComponent()));
		world.addPickUp(f.createPickUpObject(new P2d(0,5), 1));
		world.addPickUp(f.createPickUpObject(new P2d(6,0), 1));
		world.addPickUp(f.createPickUpObject(new P2d(-4,3), 1));
		world.addPickUp(f.createPickUpObject(new P2d(-1,-7), 1));
		world.setEventListener(engine);
	}
	
	public World getWorld(){
		return world;
	}

	public HashMap<String,Player> getPlayers(){
		return players;
	};
	
	public boolean isGameOver(){
		return world.getPickableObj().size() == 0;
	}
	
	public void update(int dt){
		world.updateState(dt);
	}
}

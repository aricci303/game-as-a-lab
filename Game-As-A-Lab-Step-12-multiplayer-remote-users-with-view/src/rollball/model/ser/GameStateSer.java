package rollball.model.ser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import rollball.common.P2d;
import rollball.common.V2d;
import rollball.core.GameEngine;
import rollball.core.GameEngineInterface;
import rollball.core.GameFactory;
import rollball.input.KeyboardInputController;
import rollball.input.MosquitoAIInputComponent;

public class GameStateSer implements Serializable {
	
	private WorldSer world;
	private List<PlayerSer> players;

	public GameStateSer(WorldSer world, List<PlayerSer> players){
		this.world = world;
		this.players = players;
	}
	
	public WorldSer getWorld(){
		return world;
	}

	public List<PlayerSer> getPlayers(){
		return players;
	};
	
}

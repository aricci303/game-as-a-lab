package rollball.input;

import rollball.model.Player;

public abstract class AbstractPlayerInputComponent implements InputComponent {

	private Player player;
	
	public AbstractPlayerInputComponent(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
}

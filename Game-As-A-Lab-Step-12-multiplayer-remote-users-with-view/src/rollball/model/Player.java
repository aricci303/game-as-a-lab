package rollball.model;

import rollball.input.InputController;
import rollball.model.ser.PlayerSer;

public class Player {
	
	private String name;
	private int score;
	private InputController controller;
	
	public Player(String name, InputController controller) {
		this.name = name;
		this.score = 0;
		this.controller = controller;
	}
	
	public Player(PlayerSer ser) {
		this.name = ser.getName();
		this.score = ser.getScore();
	}

	public String getName() {
		return name;
	}
	
	public InputController getController() {
		return controller;
	}
	
	public int getScore() {
		return score;
	}
	
	public void updateScore(int delta) {
		score += delta;
	}
	
	public PlayerSer serialize() {
		return new PlayerSer(name,score);
	}
}

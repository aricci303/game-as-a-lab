package rollball.model;

import rollball.input.InputController;

public class Player {
	
	private String name;
	private int score;
	private InputController controller;
	
	public Player(String name, InputController controller) {
		this.name = name;
		this.score = 0;
		this.controller = controller;
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
}

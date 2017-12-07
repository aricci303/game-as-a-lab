package rollball.model.ser;

import java.io.Serializable;

public class PlayerSer implements Serializable {
	
	private String name;
	private int score;
	
	public PlayerSer(String name, int score) {
		this.name = name;
		this.score = score;
	}
	
	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
	
}

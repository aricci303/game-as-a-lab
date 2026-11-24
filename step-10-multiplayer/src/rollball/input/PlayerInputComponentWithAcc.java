package rollball.input;

import rollball.common.V2d;
import rollball.model.*;

public class PlayerInputComponentWithAcc extends AbstractPlayerInputComponent {
	
	public PlayerInputComponentWithAcc(Player player) {
		super(player);
	}
	
	public void update(GameObject ball){
		InputController ctrl = this.getPlayer().getController();
		V2d vel = ball.getCurrentVel();
		if (ctrl.isMoveUp()){
			ball.setVel(vel.sum(new V2d(0,0.2)));
		} else if (ctrl.isMoveDown()){
			ball.setVel(vel.sum(new V2d(0,-0.2)));
		} else if (ctrl.isMoveLeft()){
			ball.setVel(vel.sum(new V2d(-0.2,0)));
		} else if (ctrl.isMoveRight()){
			ball.setVel(vel.sum(new V2d(0.2,0)));
		}
	
	}

}

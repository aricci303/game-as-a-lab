package rollball.input;

import rollball.common.V2d;
import rollball.model.*;

public class PlayerInputComponent extends AbstractPlayerInputComponent {

	public PlayerInputComponent(Player player) {
		super(player);
	}
	
	public void update(GameObject ball){
		InputController ctrl = getPlayer().getController();
		if (ctrl.isMoveUp()){
			double speed = ball.getCurrentVel().module();
			ball.setVel(new V2d(0,1).mul(speed));
		} else if (ctrl.isMoveDown()){
			double speed = ball.getCurrentVel().module();
			ball.setVel(new V2d(0,-1).mul(speed));
		} else if (ctrl.isMoveLeft()){
			double speed = ball.getCurrentVel().module();
			ball.setVel(new V2d(-1,0).mul(speed));			
		} else if (ctrl.isMoveRight()){
			double speed = ball.getCurrentVel().module();
			ball.setVel(new V2d(1,0).mul(speed));			
		}
	
	}

}

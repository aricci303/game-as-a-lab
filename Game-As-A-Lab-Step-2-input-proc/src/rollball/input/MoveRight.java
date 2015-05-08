package rollball.input;

import rollball.common.V2d;
import rollball.model.*;

public class MoveRight implements Command {
	
	@Override
	public void execute(World scene) {
		Ball ball = scene.getBall();
		double speed = ball.getCurrentVel().module();
		ball.setVel(new V2d(1,0).mul(speed));
	}

}

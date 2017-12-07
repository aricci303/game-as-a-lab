package rollball.core;

import java.util.Collection;
import rollball.input.InputController;
import rollball.input.KeyboardInputController;
import rollball.model.WorldEvent;
import rollball.model.WorldEventListener;

public interface GameEngineInterface extends WorldEventListener {

	public void notifyEvent(WorldEvent ev);
	
	public InputController getController(String name) throws InputControllerNotFoundException;

	public Collection<KeyboardInputController> getKeyboardInputControllers();
	
}

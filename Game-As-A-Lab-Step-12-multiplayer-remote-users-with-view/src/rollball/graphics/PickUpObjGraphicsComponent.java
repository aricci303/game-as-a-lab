package rollball.graphics;

import rollball.model.GameObject;

public class PickUpObjGraphicsComponent implements GraphicsComponent {

	@Override
	public void update(GameObject obj, Graphics w) {
		w.drawPickableObj(obj);
	}

}

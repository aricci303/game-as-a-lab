package rollball.input;

public class KeyboardInputController implements InputController {

	private int keyCodeMoveUp;
	private int keyCodeMoveDown;
	private int keyCodeMoveLeft;
	private int keyCodeMoveRight;
	
	private boolean isMoveUp;
	private boolean isMoveDown;
	private boolean isMoveLeft;
	private boolean isMoveRight;
	
	public KeyboardInputController(int keyCodeMoveUp, int keyCodeMoveDown, int keyCodeMoveLeft, int keyCodeMoveRight) {
		this.keyCodeMoveUp = keyCodeMoveUp;
		this.keyCodeMoveDown = keyCodeMoveDown;
		this.keyCodeMoveLeft = keyCodeMoveLeft;
		this.keyCodeMoveRight = keyCodeMoveRight;
	}
	
	@Override
	public boolean isMoveUp() {
		return isMoveUp;
	}

	@Override
	public boolean isMoveDown() {
		return isMoveDown;
	}

	@Override
	public boolean isMoveLeft() {
		return isMoveLeft;
	}

	@Override
	public boolean isMoveRight() {
		return isMoveRight;
	}
	
	public void notifyKeyPressed(int keyCode) {
		if (keyCode == keyCodeMoveUp){
			isMoveUp = true;
		} else if (keyCode == keyCodeMoveDown){
			isMoveDown = true;
		} else if (keyCode == keyCodeMoveRight){
			isMoveRight = true;
		} else if (keyCode == keyCodeMoveLeft){
			isMoveLeft = true;
		}		
	}

	public void notifyKeyReleased(int keyCode) {
		if (keyCode == keyCodeMoveUp){
			isMoveUp = false;
		} else if (keyCode == keyCodeMoveDown){
			isMoveDown = false;
		} else if (keyCode == keyCodeMoveRight){
			isMoveRight = false;
		} else if (keyCode == keyCodeMoveLeft){
			isMoveLeft = false;
		}		
	}
	
}

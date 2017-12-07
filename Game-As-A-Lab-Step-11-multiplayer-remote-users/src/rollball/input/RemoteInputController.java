package rollball.input;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class RemoteInputController implements InputController {
	
	static final int MSG_MOVE_UP = 1;
	static final int MSG_MOVE_DOWN = 2;
	static final int MSG_MOVE_RIGHT = 3;
	static final int MSG_MOVE_LEFT = 4;
	static final int MSG_STOP_MOVING_UP = 5;
	static final int MSG_STOP_MOVING_DOWN = 6;
	static final int MSG_STOP_MOVING_RIGHT = 7;
	static final int MSG_STOP_MOVING_LEFT = 8;
	
	private boolean isMoveUp;
	private boolean isMoveDown;
	private boolean isMoveLeft;
	private boolean isMoveRight;

	private MsgBroker msgBroker;
	
	public RemoteInputController(int port) {
		msgBroker = new MsgBroker(port);
		msgBroker.start();
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
	
	void notifyMsg(byte msg) {
		if (msg == MSG_MOVE_UP) {
			isMoveUp = true;
		} else if (msg == MSG_MOVE_DOWN) {
			isMoveDown = true;
		} else if (msg == MSG_MOVE_LEFT) {
			isMoveLeft = true;
		} else if (msg == MSG_MOVE_RIGHT) {
			isMoveRight = true;
		} else if (msg == MSG_STOP_MOVING_UP) {
			isMoveUp = false;
		} else if (msg == MSG_STOP_MOVING_DOWN) {
			isMoveDown = false;
		} else if (msg == MSG_STOP_MOVING_LEFT) {
			isMoveLeft = false;
		}else if (msg == MSG_STOP_MOVING_RIGHT) {
			isMoveRight = false;
		}  
	}

	protected void log(String msg) {
		System.out.println("[REMOTE INPUT CONTROLLER] "+msg);
	}

	class MsgBroker extends Thread {
		
		private ServerSocket mainSocket = null;

		MsgBroker(int port){
			try {						
				this.mainSocket = new ServerSocket(port);
				mainSocket.setReuseAddress(true);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}

		public void run(){
				try {								
					Socket socket = mainSocket.accept();
					DataInputStream inStream = new DataInputStream(socket.getInputStream());				
					while (true) {
						byte msg = inStream.readByte();
						log("["+new Date()+"] new msg: "+msg);
						notifyMsg(msg);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} 
		}		
	}

}




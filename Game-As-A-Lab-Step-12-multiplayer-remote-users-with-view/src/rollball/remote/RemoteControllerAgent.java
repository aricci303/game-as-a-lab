package rollball.remote;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import static java.awt.event.KeyEvent.*;


public class RemoteControllerAgent extends Thread implements KeyListener  {

	static final int MSG_MOVE_UP = 1;
	static final int MSG_MOVE_DOWN = 2;
	static final int MSG_MOVE_RIGHT = 3;
	static final int MSG_MOVE_LEFT = 4;
	static final int MSG_STOP_MOVING_UP = 5;
	static final int MSG_STOP_MOVING_DOWN = 6;
	static final int MSG_STOP_MOVING_RIGHT = 7;
	static final int MSG_STOP_MOVING_LEFT = 8;

	private int keyCodeMoveUp;
	private int keyCodeMoveDown;
	private int keyCodeMoveLeft;
	private int keyCodeMoveRight;

	private Socket socket;
	private DataOutputStream outStream;
	
	private RemoteControllerView view;
	private String host;
	private int port;
	
	public RemoteControllerAgent(String host, int port) {
		this.host = host;
		this.port = port;
		this.keyCodeMoveUp = VK_UP;
		this.keyCodeMoveDown = VK_DOWN;
		this.keyCodeMoveLeft = VK_LEFT;
		this.keyCodeMoveRight = VK_RIGHT;
	}
	
	public void setView(RemoteControllerView view) {
		this.view = view;
	}
	
	public void run() {
		try {
			socket = new Socket(host, port);
			outStream = new DataOutputStream(socket.getOutputStream());
			log("socket created - "+socket);
			view.notifyConntected();
			try {
				while (true) {
					/* outStream.writeByte(MSG_MOVE_RIGHT);
					sleep(1000);
					outStream.writeByte(MSG_MOVE_LEFT);*/
					sleep(1000);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (java.net.UnknownHostException ex){
			ex.printStackTrace();
		} catch (IOException ex){
			ex.printStackTrace();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		log("PRESSED "+e.getKeyCode());			
		try {
			int keyCode = e.getKeyCode();
			if (keyCode == keyCodeMoveUp){
				outStream.writeByte(MSG_MOVE_UP);
				outStream.flush();
			} else if (keyCode == keyCodeMoveDown){
				outStream.writeByte(MSG_MOVE_DOWN);
				outStream.flush();
			} else if (keyCode == keyCodeMoveRight){
				outStream.writeByte(MSG_MOVE_RIGHT);
				outStream.flush();
			} else if (keyCode == keyCodeMoveLeft){
				outStream.writeByte(MSG_MOVE_LEFT);
				outStream.flush();
			}		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		log("RELEASED "+e.getKeyCode());			
		try {
			int keyCode = e.getKeyCode();
			if (keyCode == keyCodeMoveUp){
				outStream.writeByte(MSG_STOP_MOVING_UP);
				outStream.flush();
			} else if (keyCode == keyCodeMoveDown){
				outStream.writeByte(MSG_STOP_MOVING_DOWN);
				outStream.flush();
			} else if (keyCode == keyCodeMoveRight){
				outStream.writeByte(MSG_STOP_MOVING_RIGHT);
				outStream.flush();
			} else if (keyCode == keyCodeMoveLeft){
				outStream.writeByte(MSG_STOP_MOVING_LEFT);
				outStream.flush();
			}		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	protected void log(String msg) {
		System.out.println("[REMOTE CONTROLLER AGENT] "+msg);
	}
}

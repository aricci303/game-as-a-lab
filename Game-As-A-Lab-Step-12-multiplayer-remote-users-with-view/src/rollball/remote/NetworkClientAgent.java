package rollball.remote;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Date;

import rollball.core.GameEngineClient;
import rollball.model.GameState;
import rollball.model.ser.GameStateSer;

import static java.awt.event.KeyEvent.*;


public class NetworkClientAgent extends Thread   {

	private Socket socket;
	private ObjectInputStream inStream;
	
	private String host;
	private int port;
	
	private GameEngineClient engine;
	
	public NetworkClientAgent(String host, int port, GameEngineClient engine) {
		this.host = host;
		this.port = port;
		this.engine = engine;
	}
	
	public void run() {
		try {
			socket = new Socket(host, port);
			inStream = new ObjectInputStream(socket.getInputStream());
			log("connected.");
			try {
				while (true) {
					GameStateSer state = (GameStateSer) inStream.readObject();
					log("["+new Date()+"] new state notified.");
					engine.notifyNewState(state);
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
	
	protected void log(String msg) {
		System.out.println("[NET CLIENT AGENT] "+msg);
	}
}

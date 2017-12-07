package rollball.remote;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

	private Socket socket;
	private ObjectOutputStream out;
	
	public Client(Socket socket, ObjectOutputStream out) {
		this.socket = socket;
		this.out = out;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public ObjectOutputStream getStream() {
		return out;
	}
}

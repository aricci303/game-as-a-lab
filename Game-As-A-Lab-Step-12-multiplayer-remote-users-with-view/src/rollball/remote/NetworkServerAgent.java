package rollball.remote;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import rollball.model.GameState;
import rollball.model.ser.GameStateSer;

public class NetworkServerAgent extends Thread   {

	private ServerSocket mainSocket = null;
	private List<Client> clients;

	public NetworkServerAgent(int port){
		try {						
			this.mainSocket = new ServerSocket(port);
			mainSocket.setReuseAddress(true);
			clients = new LinkedList<Client>(); 
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public void run(){
		try {								
			while (true) {
				Socket socket = mainSocket.accept();
				ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());				
				log("new client: "+socket.getRemoteSocketAddress());
				synchronized (clients) {
					clients.add(new Client(socket, outStream));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}	
	
	protected void log(String msg) {
		System.out.println("[NET SERVER AGENT] "+msg);
	}
	
	public void notifyState(GameStateSer ser) {
		synchronized (clients) {
			Iterator<Client> it = clients.iterator();
			while (it.hasNext()) {
				Client c = it.next();
				try {
					c.getStream().writeObject(ser);
					c.getStream().flush();
				} catch (Exception ex) {
					log("client no more connected - "+c.getSocket());
					it.remove();
				}
			}
		}
		
	}
	
}

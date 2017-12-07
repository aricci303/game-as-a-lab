package rollball;

import javax.swing.*;

import rollball.remote.RemoteControllerAgent;
import rollball.remote.RemoteControllerView;

public class RollABallRemote {
	
	public static int CONTROLLER_PORT = 5000;
	
	public static void main(String[] args) {		
		RemoteControllerAgent remoteControllerAgent = new RemoteControllerAgent("localhost", CONTROLLER_PORT);
		SwingUtilities.invokeLater(() -> {
			new RemoteControllerView(remoteControllerAgent);
		});
	}
	
}

package rollball;

import javax.swing.*;

import rollball.remote.RemoteControllerAgent;
import rollball.remote.RemoteControllerView;

public class RollABallRemote {
	public static void main(String[] args) {
		
		RemoteControllerAgent remoteControllerAgent = new RemoteControllerAgent("localhost", 5000);
		SwingUtilities.invokeLater(() -> {
			new RemoteControllerView(remoteControllerAgent);
		});
	}
	
}

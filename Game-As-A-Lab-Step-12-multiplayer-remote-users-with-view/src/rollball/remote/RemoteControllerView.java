package rollball.remote;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class RemoteControllerView extends JFrame {
	
	private JButton connectButton;
	private JLabel state;
	
	public RemoteControllerView(RemoteControllerAgent remoteControllerAgent){
		this.setTitle("Roll A Ball Remote Controller");
		this.setSize(300,50);
		setMinimumSize(new Dimension(300,50));
		setResizable(false);
		JPanel panel = new JPanel();
		connectButton = new JButton("Connect");
		panel.setSize(300,50);
		panel.add(connectButton);		
		
		state = new JLabel("Unconnected");
		panel.add(state);
		
		panel.setMinimumSize(new Dimension(300,50));
		getContentPane().add(panel);
		setFocusable(true);
		
		addKeyListener(remoteControllerAgent);
		panel.addKeyListener(remoteControllerAgent);
		connectButton.addKeyListener(remoteControllerAgent);
		
		connectButton.addActionListener((ActionEvent ev) -> {
			remoteControllerAgent.setView(this);
			remoteControllerAgent.start();
		});
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent ev){
				System.exit(-1);
			}
			public void windowClosed(WindowEvent ev){
				System.exit(-1);
			}
		});
		pack();
		setVisible(true);		
	}
	
	public void notifyConntected() {
		SwingUtilities.invokeLater(() -> {
			state.setText("Connected");
			connectButton.setEnabled(false);
		});
		
	}


}

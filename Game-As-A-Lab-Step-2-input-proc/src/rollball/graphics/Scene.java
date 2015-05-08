package rollball.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

import rollball.common.P2d;
import rollball.input.*;
import rollball.model.*;

public class Scene  {
    
	private JFrame frame;
    private ScenePanel panel;
    private Controller controller;
    private World scene;
    
    public Scene(World scene, int w, int h){
    	frame = new JFrame("Roll A Ball");
    	frame.setSize(w,h);
    	frame.setMinimumSize(new Dimension(w,h));
        frame.setResizable(false);
        // frame.setUndecorated(true); // Remove title bar
        this.scene = scene;
        panel = new ScenePanel(w,h);
        frame.getContentPane().add(panel);
        frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent ev){
				System.exit(-1);
			}
			public void windowClosed(WindowEvent ev){
				System.exit(-1);
			}
		});
        frame.pack();
        frame.setVisible(true);
    }
    
    public void render(){
    	try {
	    	SwingUtilities.invokeAndWait(() -> {
	    		frame.repaint();
	    	});
    	} catch (Exception ex){
    		ex.printStackTrace();
    	}
    }
    
    public void setInputController(Controller c){
    	controller = c;
    }
    
    public class ScenePanel extends JPanel implements KeyListener {
    	private int centerX;
    	private int centerY;
    	
    	public ScenePanel(int w, int h){
            setSize(w,h);
            centerX = w/2;
            centerY = h/2;
            this.addKeyListener(this);
            setFocusable(true);
            setFocusTraversalKeysEnabled(false);
            requestFocusInWindow(); 
        }
        
        public void paint(Graphics g){
    		Graphics2D g2 = (Graphics2D) g;
    		
    		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    		          RenderingHints.VALUE_ANTIALIAS_ON);
    		g2.setRenderingHint(RenderingHints.KEY_RENDERING,
    		          RenderingHints.VALUE_RENDER_QUALITY);
    		g2.clearRect(0,0,this.getWidth(),this.getHeight());
            
    		List<GameObject> entities = scene.getSceneEntities();
			Stroke strokeBall = new BasicStroke(4f);
			Stroke strokePick = new BasicStroke(8f);
			
			entities.stream().forEach( e -> {
    			P2d pos = e.getCurrentPos();
    			int x = (int) Math.round(centerX + pos.x * 100);
    			int y = (int) Math.round(centerY - pos.y * 100);
    			if (e instanceof Ball){
    				g2.setColor(Color.BLUE);
    				g2.setStroke(strokeBall);
    				g2.drawOval(x-20, y-20, 40, 40);
    			} else if (e instanceof PickUpObj){
    				g2.setColor(Color.RED);
    				g2.setStroke(strokePick);
    				g2.drawRect(x-20, y-20, 40, 40);
    			}
    		});
            
        }

		@Override
		public void keyPressed(KeyEvent e) {
	     	if (e.getKeyCode() == 38){
	     		controller.notifyCommand(new MoveUp());
	     	} else if (e.getKeyCode() == 40){
	     		controller.notifyCommand(new MoveDown());
	     	} else if (e.getKeyCode() == 39){
	     		controller.notifyCommand(new MoveRight());	     		
	     	} else if (e.getKeyCode() == 37){
	     		controller.notifyCommand(new MoveLeft());	     		
	     	}
		}

		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}
        
    }
}

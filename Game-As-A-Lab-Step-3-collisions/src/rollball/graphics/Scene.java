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
    
    public Scene(World scene, int w, int h, double width, double height){
    	frame = new JFrame("Roll A Ball");
    	frame.setSize(w,h);
    	frame.setMinimumSize(new Dimension(w,h));
        frame.setResizable(false);
        // frame.setUndecorated(true); // Remove title bar
        this.scene = scene;
        panel = new ScenePanel(w,h, width, height);
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
    	private double ratioX;
    	private double ratioY;
    	
    	public ScenePanel(int w, int h, double width, double height){
            setSize(w,h);
            centerX = w/2;
            centerY = h/2;
            ratioX = w/width;
            ratioY = h/height;
            this.addKeyListener(this);
            setFocusable(true);
            setFocusTraversalKeysEnabled(false);
            requestFocusInWindow(); 
        }
        
    	private int getPixelX(P2d p){
    		return (int) Math.round(centerX + p.x * ratioX);
    	}

    	private int getPixelY(P2d p){
    		return (int)  Math.round(centerY - p.y * ratioY);
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
			Stroke strokeBorder = new BasicStroke(2f);
			
			RectBoundingBox bbox = scene.getBBox();
			int x0 = getPixelX(bbox.getULCorner());
			int y0 = getPixelY(bbox.getULCorner());
			int x1 = getPixelX(bbox.getBRCorner());
			int y1 = getPixelY(bbox.getBRCorner());
			
			g2.setColor(Color.BLACK);
			g2.setStroke(strokeBorder);			
			g2.drawRect(x0, y0, x1-x0, y1-y0);
			
			entities.stream().forEach( e -> {
    			P2d pos = e.getCurrentPos();
    			int x = getXinPixel(pos);
    			int y = getYinPixel(pos);
    			if (e instanceof Ball){
    				Ball b = (Ball) e;
    				g2.setColor(Color.BLUE);
    				g2.setStroke(strokeBall);
    				int rad = getDeltaXinPixel(b.getRadius());
    				g2.drawOval(x-rad, y-rad, rad*2, rad*2);
    			} else if (e instanceof PickUpObj){
    				PickUpObj pick = (PickUpObj) e;
    				int edge = getDeltaXinPixel(pick.getEdge());
    				g2.setColor(Color.RED);
    				g2.setStroke(strokePick);
    				g2.drawRect(x-edge/2, y-edge/2, edge, edge);
    			}
    		});
        }

       	private int getXinPixel(P2d p){
    		return (int) Math.round(centerX + p.x * ratioX);
    	}

    	private int getYinPixel(P2d p){
    		return (int)  Math.round(centerY - p.y * ratioY);
    	}

    	private int getDeltaXinPixel(double dx){
    		return (int)  Math.round(dx * ratioX);
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

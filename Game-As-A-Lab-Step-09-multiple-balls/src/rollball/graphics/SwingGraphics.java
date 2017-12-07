package rollball.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import rollball.common.P2d;
import rollball.model.CircleBoundingBox;
import rollball.model.GameObject;

public class SwingGraphics implements Graphics {

	private Graphics2D g2;
	private  static final Stroke strokeBall = new BasicStroke(4f);
	private  static final Stroke strokePick = new BasicStroke(8f);
	
	private int centerX;
	private int centerY;
	private double ratioX;
	private double ratioY;

	public SwingGraphics(Graphics2D g2, int centerX, int centerY, double ratioX, double ratioY){
		this.g2 = g2;
		this.centerX = centerX;
		this.centerY = centerY;
		this.ratioX = ratioX;
		this.ratioY = ratioY;
	}
	
	@Override
	public void drawBall(GameObject obj) {
		P2d pos = obj.getCurrentPos();
		int x = getXinPixel(pos);
		int y = getYinPixel(pos);
		g2.setColor(Color.BLUE);
		g2.setStroke(strokeBall);
		int rad = getDeltaXinPixel(((CircleBoundingBox)(obj.getBBox())).getRadius());
		g2.drawOval(x-rad, y-rad, rad*2, rad*2);
	}

	@Override
	public void drawPickableObj(GameObject obj) {
		P2d pos = obj.getCurrentPos();
		int x = getXinPixel(pos);
		int y = getYinPixel(pos);
		int edge = getDeltaXinPixel(((CircleBoundingBox)(obj.getBBox())).getRadius()*2);
		g2.setColor(Color.RED);
		g2.setStroke(strokePick);
		g2.drawRect(x-edge/2, y-edge/2, edge, edge);
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
}

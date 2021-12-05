package src.main.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Border extends Decorator{
	private int t;
	public Border(Widget wid, int x, int y, int w, int h, int t){
		super(wid,x,y,w,h);
		this.t=t;
	}
	@Override
	public void draw(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		Stroke oldStroke = g2d.getStroke();
		Color oldColor = g2d.getColor();
		g2d.setStroke(new BasicStroke(t));
		g2d.setColor(Color.black);
		g2d.drawRect(x,y,w,h);
		g2d.setStroke(oldStroke);
		g2d.setColor(oldColor);
	}
}
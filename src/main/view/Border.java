package src.main.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;


/** 
Border class, a concrete decorator to create borders
*/
public class Border extends Decorator{
	private int t;
/**
Constructs a Border object, the border object is a concrete decorator
 @param  wid a Widget Reference passed in
 @param  x the x position of the Border
 @param  y the y position of the Border
 @param  w the width of the Border
 @param h the height of the Border
 @param t the thickness of the Border
 */
	public Border(Widget wid, int x, int y, int w, int h, int t){
		super(wid,x,y,w,h);
		this.t=t;
	}
	
/**
Function that draws the Border based on its constructor
 @param  g a Graphics Reference passed in by the calling page
 */
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
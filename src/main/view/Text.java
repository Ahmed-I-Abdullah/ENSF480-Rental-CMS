package src.main.view;

import java.awt.Graphics;

/**
concrete class that realizes the Widget interface, draws non modifiable Text onto a page
*/
public class Text implements Widget{
	private String text;
	private int x;
	private int y;
	
/**
Constructor for the Text class
@param x the x position of the text
@param y the y position of the text
@param t the text content 
*/	
	public Text(int x, int y, String t){
		this.x=x;
		this.y=y;
		this.text=t;	
	}
	
	/**
Function that draws the Text based on its constructor
 @param  g a Graphics Reference passed in by the calling page
 */
	@Override
	public void draw(Graphics g){
		g.drawString(text, x, y);	
	}
}
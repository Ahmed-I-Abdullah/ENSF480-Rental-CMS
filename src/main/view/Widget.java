package src.main.view;

import java.awt.Graphics;

/**
Widget interface for implementing the decorator design pattern
*/
public interface Widget{
	/**
Function that draws the Widget
 @param  g a Graphics Reference passed in by the calling page
 */
	public void draw(Graphics g);
}
package src.main.view;

import java.awt.Graphics;
public class Text implements Widget{
	private String text;
	private int x;
	private int y;
	
	public Text(int x, int y, String t){
		this.x=x;
		this.y=y;
		this.text=t;	
	}
	@Override
	public void draw(Graphics g){
		g.drawString(text, x, y);	
	}
}
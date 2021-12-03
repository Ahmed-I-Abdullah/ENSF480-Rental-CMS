package src.main.view;

import java.awt.Graphics;
import javax.swing.*;

public class BrowseListingsPage extends Page {
	protected String [] text;
	
	public BrowseListingsPage(Widget w){
		widget=w;
	}
	@Override
	public void draw(String[] t){
		this.text =t;
		
		
		for(int i=0; i< text.length ; i++){
			JButton clickme = new JButton("View");
			clickme.setBounds(500, 50+(i*50), 75,50);
			final int p=i;
			clickme.addActionListener( e->{
				for(int z=0; z<text.length; z++){
					if(z==p)
						System.out.println(text[z]);
				}
			});
			f.add(clickme);
		}
		
		
		f.getContentPane().add(this);
		f.setVisible(true);
	}
	
	public void paintComponent(Graphics g){
		//System.out.println("paint");
		widget=new Text(300, 40, "BROWSE LISTINGS");
		g.setFont(titleFont);
		widget.draw(g);
		widget=new Border(widget,0,0, 785,762, 10);
		widget.draw(g);
		g.setFont(mainText);
		for(int i=0; i< text.length ; i++){
			widget=new Border(widget,250, 50+(i*50), 250,50, 5);
			widget.draw(g);
			widget=new Text(275, 80+(i*50), text[i]);
			widget.draw(g);
		}
	}
	
	
	
	
}
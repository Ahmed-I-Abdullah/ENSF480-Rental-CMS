package src.main.view;

import java.awt.Graphics;
import javax.swing.*;

import src.main.controller.ViewController;

public class NotificationsPage extends Page {

	public NotificationsPage(Widget w, ViewController c) {
		super(c);
		widget = w;
		switchEvent = 5;
	}

	@Override
	public void draw() {
			JButton view = new JButton("View");
			view.setBounds(500, 50, 75, 50);
			view.addActionListener(e -> {
				f.setVisible(false);
				f.getContentPane().removeAll();
				switchEvent = 6;
			});
			f.add(view);

		f.getContentPane().add(this);
		f.setVisible(true);
	}

	public void paintComponent(Graphics g) {
    
		// System.out.println("paint");
		widget = new Text(300, 40, "SUBSCRIBED LISTINGS");
		g.setFont(titleFont);
		widget.draw(g);
		widget = new Border(widget, 0, 0, 785, 762, 10);
		widget.draw(g);
		g.setFont(mainText);
			widget = new Border(widget, 250, 50, 250, 50, 5);
			widget.draw(g);
			widget = new Text(275, 80, "subbed listing");
			widget.draw(g);
		
	}

}

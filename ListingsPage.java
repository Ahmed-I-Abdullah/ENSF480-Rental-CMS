package src.main.view;

import java.awt.Graphics;
import javax.swing.*;

import src.main.controller.ViewController;

public class ListingsPage extends Page {
    protected String[] text;

    public ListingsPage(Widget w, ViewController c) {
        super(c);
        widget = w;
        switchEvent = 4;
    }

    @Override
    public void draw() {
        JButton back = new JButton("Back");
        back.setBounds(100, 50, 75, 50);
        back.addActionListener(e -> {
            f.setVisible(false);
			f.getContentPane().removeAll();
            // switch event should refer to previous page
			switchEvent=1;			
        });
        f.add(back);

        f.getContentPane().add(this);
        f.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        // System.out.println("paint");
        widget = new Text(300, 40, "LISTING DETAILS");
        g.setFont(titleFont);
        widget.draw(g);
        widget = new Border(widget, 0, 0, 785, 762, 10);
        widget.draw(g);
        g.setFont(mainText);
        widget = new Text(275, 80, "Bedrooms: " + text[0]);
        widget.draw(g);
        widget = new Text(275, 130, "Bathrooms: " + text[1]);
        widget.draw(g);
        widget = new Text(275, 180, "SQFT: " + text[2]);
        widget.draw(g);
        widget = new Text(275, 230, "Garage: " + text[3]);
        widget.draw(g);
        widget = new Text(275, 280, "Levels: " + text[4]);
        widget.draw(g);
    }
}

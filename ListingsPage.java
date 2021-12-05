package src.main.view;

import java.awt.Graphics;
import javax.swing.*;

import src.main.controller.ViewController;

public class ListingsPage extends Page {
    protected String[] text;

    public ListingsPage(Widget w, ViewController c) {
        super(c);
        widget = w;
        switchEvent = 6;
    }

    @Override
    public void draw() {
        JButton back = new JButton("Back");
        back.setBounds(100, 50, 75, 50);

        back.addActionListener(
                e -> {
                    f.setVisible(false);
                    f.removeAll();
                    resetSwitchEvent();
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
        widget = new Text(275, 80, "Bedrooms: " + 2);
        widget.draw(g);
        widget = new Text(275, 130, "Bathrooms: " + 2);
        widget.draw(g);
        widget = new Text(275, 180, "SQFT: " + 2300);
        widget.draw(g);
        widget = new Text(275, 230, "Garage: " + "yes");
        widget.draw(g);
        widget = new Text(275, 280, "Levels: " + 2);
        widget.draw(g);
    }
}

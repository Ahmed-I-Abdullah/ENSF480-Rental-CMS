package src.main.view;

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.*;
import src.main.model.property.Property;

import src.main.controller.ViewController;

public class BrowseListingsPage extends Page {
  protected String[] text;

  public BrowseListingsPage(Widget w, ViewController c) {
    super(c);
    widget = w;
  }

  private String getFormattedAddress(Property property) {
    return
      property.getAddress().getStreet() +
      ", " +
      property.getSpecifications().getCityQuadrant() +
      ", " +
      property.getAddress().getCity() +
      ", " +
      property.getAddress().getProvince() +
      ", " +
      property.getAddress().getCountry();
  }

  public void draw() {
    final ArrayList<Property> properties = controller.getAllProperties();
    for (int i = 0; i < properties.size(); i++) {
      JButton clickme = new JButton("View");
      clickme.setBounds(540, 50 + (i * 50), 75, 50);
      final int p = i;
      clickme.addActionListener(
        e -> {
          for (int z = 0; z < properties.size(); z++) {
            if (z == p) 
			System.out.println(getFormattedAddress(properties.get(z)));
          }
        }
      );
      f.add(clickme);
    }

    f.getContentPane().add(this);
    f.setVisible(true);
  }

  public void paintComponent(Graphics g) {
    //System.out.println("paint");
    widget = new Text(230, 40, "BROWSE LISTINGS");
    final ArrayList<Property> properties = controller.getAllProperties();
    g.setFont(titleFont);
    widget.draw(g);
    widget = new Border(widget, 0, 0, 785, 762, 10);
    widget.draw(g);
    g.setFont(mainText);
    for (int i = 0; i < properties.size(); i++) {
      widget = new Border(widget, 190, 50 + (i * 50), 350, 50, 5);
      widget.draw(g);
      widget = new Text(195, 80 + (i * 50), getFormattedAddress(properties.get(i)));
      widget.draw(g);
    }
  }
}

package src.main.view;

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.*;
import src.main.controller.ViewController;
import src.main.model.property.Property;
import src.main.model.user.RegisteredRenter;
import src.main.model.user.UserType;

public class NotificationsPage extends Page {

  private ArrayList<Property> notificationProperties;

  public NotificationsPage(Widget w, ViewController c) {
    super(c);
    widget = w;
    if (
      controller.getUserController().getAuthenticatedUser().getUserType() ==
      UserType.RENTER
    ) {
      switchEvent = 5;
      RegisteredRenter registeredRenter = (RegisteredRenter) controller
        .getUserController()
        .getAuthenticatedUser();
      notificationProperties = registeredRenter.getNotifications();
      try {
        registeredRenter.setViewedNotificationTime();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public String getFormattedAddress(Property property) {
    return (
      property.getAddress().getStreet() +
      ", " +
      property.getSpecifications().getCityQuadrant() +
      ", " +
      property.getAddress().getCity() +
      ", " +
      property.getAddress().getProvince() +
      ", " +
      property.getAddress().getCountry()
    );
  }

  public void draw() {
    JButton back = new JButton("Back");
    back.setBounds(5, 10, 75, 50);

    for (int i = 0; i < notificationProperties.size(); i++) {
      JButton clickme = new JButton("View");
      clickme.setBounds(540, 50 + (i * 50), 75, 50);
      final int p = i;
      clickme.addActionListener(
        e -> {
          for (int z = 0; z < notificationProperties.size(); z++) {
            if (z == p) System.out.println(
              getFormattedAddress(notificationProperties.get(z))
            );
          }
          switchEvent = 6;
        }
      );
      f.add(clickme);
    }

    back.addActionListener(
      e -> {
        f.setVisible(false);
        f.removeAll();
        resetSwitchEvent();
      }
    );

    f.add(back);

    f.getContentPane().add(this);
    f.setVisible(true);
  }

  public void paintComponent(Graphics g) {
    widget = new Text(230, 40, "SUBSCRIBED LISTINGS");
    g.setFont(titleFont);
    widget.draw(g);
    widget = new Border(widget, 0, 0, 785, 762, 10);
    widget.draw(g);
    g.setFont(mainText);
    for (int i = 0; i < notificationProperties.size(); i++) {
      widget = new Border(widget, 190, 50 + (i * 50), 350, 50, 5);
      widget.draw(g);
      widget =
        new Text(
          195,
          80 + (i * 50),
          getFormattedAddress(notificationProperties.get(i))
        );
      widget.draw(g);
    }
  }
}

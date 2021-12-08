package src.main.view;

import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.*;
import src.main.controller.ViewController;
import src.main.model.property.Property;
import src.main.model.user.RegisteredRenter;
import src.main.model.user.UserType;

/**
Page that shows a registered renter's notifications for their search criteria
*/
public class NotificationsPage extends Page {

  private ArrayList<Property> notificationProperties;

/**
constructor for Notifications Page, gets list of properies a registered renter may be interested in based on their search critera
@param w Widget reference passed in, used to draw non listening components
@param c ViewController reference passed in, used to communicate with the rest of the system 
*/
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
      notificationProperties =
        controller.getRenterNotifications(registeredRenter);
      try {
        registeredRenter.setViewedNotificationTime();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
/**
a function to get a formatted address from a property
@param property the property to get the address from
@return a formatted String address 
*/
  public String getFormattedAddress(Property property) {
    return (
      property.getAddress().getStreet() +
      " " +
      property.getSpecifications().getCityQuadrant() +
      " " +
      property.getAddress().getCity() +
      ", " +
      property.getAddress().getProvince() +
      ", " +
      property.getAddress().getCountry()
    );
  }
/**
a function to draw all action listening components on the page
*/
  public void draw() {
    JButton back = new JButton("Back");
    back.setBounds(5, 10, 75, 50);

    for (int i = 0; i < notificationProperties.size(); i++) {
      JButton clickme = new JButton("View");
      clickme.setBounds(540, 50 + (i * 50), 75, 50);
      final int p = i;
      clickme.addActionListener(
        e -> {
          controller.setCurrentProperty(p);
          f.setVisible(false);
          f.removeAll();
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
/**
a function to draw all non-action listening components on the page
@param g Graphics object reference passed in from JPanel calling
*/
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

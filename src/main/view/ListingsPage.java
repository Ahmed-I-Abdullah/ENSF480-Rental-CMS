package src.main.view;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;
import src.main.controller.ViewController;
import src.main.model.property.Property;
import src.main.model.user.UserType;

public class ListingsPage extends Page {

    final ArrayList<Property> properties = controller.getAllProperties();

    public ListingsPage(Widget w, ViewController c, int switchEvent) {
        super(c);
        widget = w;
        this.switchEvent = switchEvent;
    }

    public void draw() {
        JButton back = new JButton("Back");
        final Property property = properties.get(switchEvent - 6);

        try {
            if (controller.getUserController().getAuthenticatedUser().getUserType() != null
                    && (controller.getUserController().getAuthenticatedUser().getUserType() == UserType.LANDLORD
                            && property.getPostedBy() == controller.getUserController().getUser().getName())
                    || controller.getUserController().getAuthenticatedUser().getUserType() == UserType.MANAGER) {
                String[] listingStateOptions = { "ACTIVE", "RENTED", "CANCELLED", "SUSPENDED" };
                JComboBox<String> changeListingState = new JComboBox<String>(listingStateOptions);
                changeListingState.setBounds(300, 300, 100, 30);
                f.add(changeListingState);
            }
        } catch (Exception e) {
            System.out.println("Not Logged In");
        }
        back.setBounds(100, 50, 75, 50);

        back.addActionListener(
                e -> {
                    f.setVisible(false);
                    f.removeAll();
                    // needs adjustment in case where view button is pressed from notifictaions page
                    switchEvent = 1;
                });

        f.add(back);
        f.getContentPane().add(this);
        f.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        final Property property = properties.get(switchEvent - 6);
        // System.out.println("paint");
        widget = new Text(300, 40, "LISTING DETAILS");
        g.setFont(titleFont);
        widget.draw(g);
        widget = new Border(widget, 0, 0, 785, 762, 10);
        widget.draw(g);
        g.setFont(mainText);
        widget = new Text(275, 230, "Type: " + property.getSpecifications().getHousingType());
        widget.draw(g);
        widget = new Text(275, 80, "Bedrooms: " + property.getSpecifications().getNumOfBedrooms());
        widget.draw(g);
        widget = new Text(275, 130, "Bathrooms: " + property.getSpecifications().getNumOfBathrooms());
        widget.draw(g);
        widget = new Text(275, 180, "Furnished: " + property.getSpecifications().getFurnished());
        widget.draw(g);
        try {
            if (controller.getUserController().getAuthenticatedUser().getUserType() != null
                    && (controller.getUserController().getAuthenticatedUser().getUserType() == UserType.LANDLORD
                            && property.getPostedBy() == controller.getUserController().getUser().getName())
                    || controller.getUserController().getAuthenticatedUser().getUserType() == UserType.MANAGER) {
                widget = new Text(275, 280, "ListingState: " + property.getSpecifications().getState());
                widget.draw(g);
            }
        } catch (Exception e) {
            System.out.println("Not Logged In");
        }
    }
}

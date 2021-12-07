package src.main.view;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.*;
import src.main.controller.ViewController;
import src.main.model.property.ListingState;
import src.main.model.property.Property;
import src.main.model.user.UserType;

public class ListingsPage extends Page {

    final ArrayList<Property> properties = controller.getAllProperties();

    public ListingsPage(Widget w, ViewController c) {
        super(c);
        widget = w;
    }

    public void draw() {
        JButton back = new JButton("Back");
        final Property property = controller.getCurrentProperty();

        back.setBounds(100, 50, 75, 50);
        try {
            if (controller.getUserController().getAuthenticatedUser() != null
                    && (controller.getUserController().getAuthenticatedUser().getUserType() == UserType.LANDLORD
                            && property.getPostedBy() == controller.getUserController().getUser().getName())
                    || controller.getUserController().getAuthenticatedUser().getUserType() == UserType.MANAGER) {
                final JTextField type = new JTextField(property.getSpecifications().getHousingType());
                final JTextField bedrooms = new JTextField(property.getSpecifications().getHousingType());
                final JTextField bathrooms = new JTextField(property.getSpecifications().getHousingType());
                final JTextField furnished = new JTextField(property.getSpecifications().getHousingType());
                final JTextField quadrant = new JTextField(property.getSpecifications().getHousingType());
                final JButton edit = new JButton("Edit");
                ListingState[] listingStateOptions = { ListingState.ACTIVE, ListingState.RENTED, ListingState.CANCELLED,
                        ListingState.SUSPENDED };
                JComboBox<ListingState> changeListingState = new JComboBox<ListingState>(listingStateOptions);
                changeListingState.setBounds(300, 500, 100, 30);
                f.add(changeListingState);

                type.setBounds(275, 230, 100, 30);
                bedrooms.setBounds(275, 80, 100, 30);
                bathrooms.setBounds(275, 130, 100, 30);
                furnished.setBounds(275, 180, 100, 30);
                quadrant.setBounds(275, 230, 100, 30);
                edit.setBounds(275, 280, 75, 50);
                edit.addActionListener(e -> {
                    controller.getCurrentProperty().getSpecifications()
                            .setState((ListingState) changeListingState.getSelectedItem());
                    try {
                        controller.getCurrentProperty().getSpecifications()
                                .setNumOfBedrooms(Integer.valueOf(bedrooms.toString()));
                        controller.getCurrentProperty().getSpecifications()
                                .setNumOfBathrooms(Integer.valueOf(bathrooms.toString()));
                        controller.getCurrentProperty().getSpecifications()
                                .setFurnished(Boolean.valueOf(furnished.toString()));
                    } catch (Exception ex) {
                        System.out.println("Wrong data types");
                    }
                    controller.getCurrentProperty().getSpecifications().setCityQuadrant(quadrant.toString());
                });
                f.add(type);
                f.add(bedrooms);
                f.add(bathrooms);
                f.add(furnished);
                f.add(quadrant);
                f.add(edit);
            }
        } catch (Exception e) {
            System.out.println("Not Authorized to Edit Listing");
        }

        back.addActionListener(
                e -> {
                    f.setVisible(false);
                    f.getContentPane().removeAll();
                    // needs adjustment in case where view button is pressed from notifictaions page
                    switchEvent = 1;
                });

        f.add(back);

        f.getContentPane().add(this);
        f.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        final Property property = controller.getCurrentProperty();
        // System.out.println("paint");
        widget = new Text(300, 40, "LISTING DETAILS");
        g.setFont(titleFont);
        widget.draw(g);
        widget = new Border(widget, 0, 0, 785, 762, 10);
        widget.draw(g);
        g.setFont(mainText);
        if (controller.getUserController().getAuthenticatedUser() == null
                || ((controller.getUserController().getAuthenticatedUser().getUserType() != UserType.LANDLORD
                        && property.getPostedByName() == controller.getUserController().getAuthenticatedUser()
                                .getName())
                        && controller.getUserController().getAuthenticatedUser()
                                .getUserType() != UserType.MANAGER)) {
            widget = new Text(275, 280, "Type: " + property.getSpecifications().getHousingType());
            widget.draw(g);
            widget = new Text(275, 80, "Bedrooms: " + property.getSpecifications().getNumOfBedrooms());
            widget.draw(g);
            widget = new Text(275, 130, "Bathrooms: " + property.getSpecifications().getNumOfBathrooms());
            widget.draw(g);
            widget = new Text(275, 180, "Furnished: " + property.getSpecifications().getFurnished());
            widget.draw(g);
            widget = new Text(275, 230, "Quadrant: " + property.getSpecifications().getCityQuadrant());
            widget.draw(g);
        }
        try {
            if (controller.getUserController().getAuthenticatedUser() != null
                    && (controller.getUserController().getAuthenticatedUser().getUserType() == UserType.LANDLORD
                            && property.getPostedBy() == controller.getUserController().getUser().getName())
                    || controller.getUserController().getAuthenticatedUser().getUserType() == UserType.MANAGER) {
                widget = new Text(275, 480, "ListingState: " + property.getSpecifications().getState());
                widget.draw(g);
            }
        } catch (Exception e) {
            System.out.println("Not Authorized to view listing state");
        }
    }
}

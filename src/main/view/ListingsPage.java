package src.main.view;

import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;

import src.main.model.user.UserType;

import src.main.controller.ViewController;
import src.main.model.property.*;

public class ListingsPage extends Page {

    public ListingsPage(Widget w, ViewController c) {
        super(c);
        widget = w;
        switchEvent = 6;
    }

    public void draw() {
        JButton back = new JButton("Back");
        back.setBounds(100, 50, 75, 50);

        back.addActionListener(
                e -> {
                    f.setVisible(false);
                    f.removeAll();
                    resetSwitchEvent();
                });

        String id = "2e535bb8-54cd-11ec-8d3e-0afc51b797af";

        JLabel state = new JLabel(controller.getPostingController().getListingState(id).toString());
        state.setBounds(375, 310, 100, 30);

        if (
        controller.getUserController().getAuthenticatedUser() != null &&
        (controller.getUserController().getAuthenticatedUser().getUserType() ==
        UserType.LANDLORD || controller.getUserController().getAuthenticatedUser().getUserType() ==
        UserType.MANAGER)
        ) {
            JButton filters = new JButton("Change Listing State");
            filters.setBounds(475, 310, 200, 30);

            filters.addActionListener(
            e -> {
                JFrame pop = new JFrame("Select the Listing State");
                pop.setSize(250, 150);
                pop.setLocationRelativeTo(null);
                pop.setLayout(new GridBagLayout());

                GridBagConstraints c = new GridBagConstraints();
                c.fill = GridBagConstraints.HORIZONTAL;

                int chosenState = 0;
                JComboBox<String> changeListingState = new JComboBox<String>(ListingState.getNames(ListingState.class));
                changeListingState.setSelectedIndex(chosenState);

                JButton save = new JButton("Save");

                save.addActionListener(
                p -> {
                    ListingState new_state = ListingState.valueOf(changeListingState.getSelectedItem().toString());
                    controller.getPostingController().changeListingState(id, new_state.ordinal());
                    state.setText(controller.getPostingController().getListingState(id).toString()); //change listing state on main page
                    pop.setVisible(false);
                }
                );

                c.gridwidth = 1;
                c.weightx = 0.0;
                c.gridx = 0;
                c.gridy = 0;
                pop.add(changeListingState, c);

                c.gridx = 0;
                c.gridy = 1;
                pop.add(save, c);

                pop.setVisible(true);
            }
            );

            f.add(filters);
        }
        f.add(state);
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
        widget = new Text(275, 330, "Listing State: ");
        widget.draw(g);
    }
}

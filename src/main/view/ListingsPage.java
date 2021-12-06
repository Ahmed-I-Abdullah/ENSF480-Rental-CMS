package src.main.view;

import java.awt.Graphics;
import javax.swing.*;
import src.main.model.user.UserType;

import src.main.controller.ViewController;
import src.main.controller.AdminController;
import src.main.model.property.*;

public class ListingsPage extends Page {

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
        
        String id = "2e535bb8-54cd-11ec-8d3e-0afc51b797af";
        int new_state = 2;
        //change listing state
        if (
        controller.getUserController().getAuthenticatedUser() != null &&
        (controller.getUserController().getAuthenticatedUser().getUserType() ==
        UserType.LANDLORD || controller.getUserController().getAuthenticatedUser().getUserType() ==
        UserType.MANAGER)
        ) {
            ListingState current_state = controller.getPostingController().getListingState(id);
            widget = new Text(275, 330, "Listing State: " + current_state);
            widget.draw(g);
            if(ListingState.values()[new_state] != current_state){
                widget = new Text(275, 370, "New Listing State: " + controller.getPostingController().changeListingState(id, new_state));
                widget.draw(g);
            }
        }
    }
}

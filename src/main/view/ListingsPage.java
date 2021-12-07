package src.main.view;

import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;

import src.main.model.user.User;
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
		
		JButton email = new JButton("Email Landlord");
		email.setBounds(600, 80, 150, 50);
		
		
		email.addActionListener(e->{
			JFrame pop = new JFrame("Email");
			pop.setSize(350,350);
			pop.setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
			c.fill=GridBagConstraints.VERTICAL;
			
			JButton send = new JButton("Send");
			
			final JLabel title= new JLabel("Email Landlord");
			
			final JTextField body = new JTextField();
			
			send.addActionListener(p->{
				if(body.getText().length()==0){
					title.setText("Please enter a message");
				}else{
					controller.getUserController().emailLandlord(controller.getCurrentProperty(), body.getText());
					pop.setVisible(false);
				}
			});
			
			
			c.gridx=0;
			c.gridy=0;
			pop.add(title, c);
			
			 c.gridx=0;
			 c.gridy=2;
			 pop.add(send, c);
			 
			 c.gridx=0;
			 c.gridy=1;
			 c.gridwidth=2;
			 c.ipady=220;
			 c.ipadx=220;
			 pop.add(body, c);
			 pop.setVisible(true);
			 
			 
			
		});
		
		
        back.addActionListener(
        e -> {
            f.setVisible(false);
            f.removeAll();
            switchEvent=1;
            //resetSwitchEvent();
        });

        String id = controller.getCurrentProperty().getHouseID();
        JLabel state = new JLabel(controller.getPostingController().getListingState(id).toString());
        state.setBounds(375, 310, 100, 30);

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

        User currentUser = controller.getUserController().getAuthenticatedUser();
        

        f.add(state);
		if(currentUser!=null){
			f.add(email);
            
            if ((currentUser.getUserType() == UserType.LANDLORD && controller.getCurrentProperty().getPostedBy().equals(currentUser.getEmail()))  //landlord only views their own postings
            || currentUser.getUserType() == UserType.MANAGER)
            {
                f.add(filters);
            }
		}
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
        widget = new Text(275, 80, "Bedrooms: " + controller.getCurrentProperty().getSpecifications().getNumOfBedrooms());
        widget.draw(g);
        widget = new Text(275, 130, "Bathrooms: " + controller.getCurrentProperty().getSpecifications().getNumOfBedrooms());
        widget.draw(g);
        widget = new Text(275, 180, "Description: "+controller.getCurrentProperty().getDescription());
        widget.draw(g);
        widget = new Text(275, 230, "Furnished: " + controller.getCurrentProperty().getSpecifications().getFurnished());
        widget.draw(g);
        widget = new Text(275, 330, "Listing State: ");
        widget.draw(g);
    }
}

package src.main.view;

import java.awt.*;
import java.awt.Graphics;
import javax.swing.*;
import src.main.controller.ViewController;
import src.main.model.property.*;
import src.main.model.user.User;
import src.main.model.user.UserType;

public class ListingsPage extends Page {

  public ListingsPage(Widget w, ViewController c) {
    super(c);
    widget = w;
    switchEvent = 6;
  }

  public void draw() {
    User currentUser = null;
    try {
      currentUser = controller.getUserController().getAuthenticatedUser();
    } catch (Exception e) {
      System.out.println("Not logged in");
    }

    JLabel listingtype = new JLabel(
      controller.getCurrentProperty().getSpecifications().getHousingType()
    );
    JLabel listingBedrooms = new JLabel(
      String.valueOf(
        controller.getCurrentProperty().getSpecifications().getNumOfBedrooms()
      )
    );
    JLabel listingBathrooms = new JLabel(
      String.valueOf(
        controller.getCurrentProperty().getSpecifications().getNumOfBathrooms()
      )
    );
    JLabel listingQuadrant = new JLabel(
      controller.getCurrentProperty().getSpecifications().getCityQuadrant()
    );
    JLabel listingFurnished = new JLabel(
      String.valueOf(
        controller.getCurrentProperty().getSpecifications().getFurnished()
      )
    );

    listingtype.setBounds(375, 260, 200, 30);
    listingBedrooms.setBounds(375, 60, 200, 30);
    listingBathrooms.setBounds(375, 110, 200, 30);
    listingQuadrant.setBounds(375, 210, 200, 30);
    listingFurnished.setBounds(375, 160, 200, 30);

    f.add(listingBathrooms);
    f.add(listingBedrooms);
    f.add(listingFurnished);
    f.add(listingQuadrant);
    f.add(listingtype);

    JButton back = new JButton("Back");
    if (
      currentUser != null &&
      (
        (
          currentUser.getUserType() == UserType.LANDLORD &&
          controller
            .getCurrentProperty()
            .getPostedBy()
            .equals(currentUser.getEmail())
        ) ||
        currentUser.getUserType() == UserType.MANAGER
      )
    ) {
      final JButton edit = new JButton("Edit");

      edit.setBounds(175, 280, 75, 50);
      edit.addActionListener(
        e -> {
          JFrame pop = new JFrame("EDIT");
          pop.setSize(350, 350);
          pop.setLocationRelativeTo(null);
          pop.setLayout(new GridBagLayout());

          GridBagConstraints c = new GridBagConstraints();
          c.fill = GridBagConstraints.HORIZONTAL;

          JButton save = new JButton("Save");

          String[] quad = { "SE", "SW", "NE", "NW" };

          JLabel quadrantLabel = new JLabel("Quadrant");
          JComboBox<String> quadrant = new JComboBox<String>(quad);
          quadrant.setSelectedItem(
            controller
              .getCurrentProperty()
              .getSpecifications()
              .getCityQuadrant()
          );

          Boolean[] furnish = { true, false };

          JLabel furnishedLabel = new JLabel("Furnished");
          JComboBox<Boolean> furnished = new JComboBox<Boolean>(furnish);
          furnished.setSelectedItem(
            controller.getCurrentProperty().getSpecifications().getFurnished()
          );

          JTextField type = new JTextField(
            controller.getCurrentProperty().getSpecifications().getHousingType()
          );

          JLabel bedroomsLabel = new JLabel("Number of Bedrooms");
          JSlider bedrooms = new JSlider(
            JSlider.HORIZONTAL,
            0,
            5,
            controller
              .getCurrentProperty()
              .getSpecifications()
              .getNumOfBedrooms()
          );
          bedrooms.setMajorTickSpacing(1);
          bedrooms.setPaintTicks(true);
          bedrooms.setPaintLabels(true);
          bedrooms.setFont(mainText);

          JLabel bathroomsLabel = new JLabel("Number of Bathrooms");
          JSlider bathrooms = new JSlider(
            JSlider.HORIZONTAL,
            0,
            5,
            controller
              .getCurrentProperty()
              .getSpecifications()
              .getNumOfBathrooms()
          );
          bathrooms.setMajorTickSpacing(1);
          bathrooms.setPaintTicks(true);
          bathrooms.setPaintLabels(true);
          bathrooms.setFont(mainText);

          save.addActionListener(
            p -> {
              String id = controller.getCurrentProperty().getHouseID();
              controller
                .getCurrentProperty()
                .getSpecifications()
                .setCityQuadrant(quadrant.getSelectedItem().toString());
              controller
                .getPostingController()
                .changeListingQuadrant(
                  id,
                  quadrant.getSelectedItem().toString()
                );

              controller
                .getCurrentProperty()
                .getSpecifications()
                .setNumOfBathrooms(Integer.valueOf(bathrooms.getValue()));
              controller
                .getPostingController()
                .changeListingBathrooms(
                  id,
                  Integer.valueOf(bathrooms.getValue())
                );

              controller
                .getCurrentProperty()
                .getSpecifications()
                .setNumOfBedrooms(Integer.valueOf(bedrooms.getValue()));
              controller
                .getPostingController()
                .changeListingBedrooms(
                  id,
                  Integer.valueOf(bedrooms.getValue())
                );

              controller
                .getCurrentProperty()
                .getSpecifications()
                .setHousingType(type.getText());
              controller
                .getPostingController()
                .changeListingType(id, type.getText());

              controller
                .getCurrentProperty()
                .getSpecifications()
                .setFurnished((boolean) furnished.getSelectedItem());
              controller
                .getPostingController()
                .changeListingFurnishing(
                  id,
                  (boolean) furnished.getSelectedItem()
                );

              listingtype.setText(
                controller
                  .getCurrentProperty()
                  .getSpecifications()
                  .getHousingType()
              );
              listingBedrooms.setText(
                String.valueOf(
                  controller
                    .getCurrentProperty()
                    .getSpecifications()
                    .getNumOfBedrooms()
                )
              );
              listingBathrooms.setText(
                String.valueOf(
                  controller
                    .getCurrentProperty()
                    .getSpecifications()
                    .getNumOfBathrooms()
                )
              );
              listingQuadrant.setText(
                controller
                  .getCurrentProperty()
                  .getSpecifications()
                  .getCityQuadrant()
              );
              listingFurnished.setText(
                String.valueOf(
                  controller
                    .getCurrentProperty()
                    .getSpecifications()
                    .getFurnished()
                )
              );
              pop.setVisible(false);
            }
          );

          c.gridx = 0;
          c.gridy = 2;
          pop.add(type, c);

          c.gridx = 0;
          c.gridy = 4;
          pop.add(quadrantLabel, c);

          c.gridx = 1;
          c.gridy = 4;
          pop.add(furnishedLabel, c);

          c.gridx = 0;
          c.gridy = 5;
          pop.add(quadrant, c);

          c.gridx = 1;
          c.gridy = 5;
          pop.add(furnished, c);

          c.gridx = 0;
          c.gridy = 6;
          pop.add(bedroomsLabel, c);
          c.gridx = 0;
          c.gridy = 6;
          pop.add(bedrooms, c);

          c.gridx = 0;
          c.gridy = 8;
          pop.add(bathroomsLabel, c);

          c.gridx = 0;
          c.gridy = 10;
          pop.add(save, c);

          c.gridwidth = 2;
          c.weightx = 0.0;
          c.gridx = 0;
          c.gridy = 7;
          pop.add(bedrooms, c);

          c.gridwidth = 2;
          c.weightx = 0.0;
          c.gridx = 0;
          c.gridy = 9;
          pop.add(bathrooms, c);

          pop.setVisible(true);
        }
      );
      f.add(edit);
    }

    back.setBounds(100, 50, 75, 50);

    JButton email = new JButton("Email Landlord");
    email.setBounds(600, 80, 150, 50);

    email.addActionListener(
      e -> {
        JFrame pop = new JFrame("Email");
        pop.setSize(350, 350);
        pop.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;

        JButton send = new JButton("Send");

        final JLabel title = new JLabel("Email Landlord");

        final JTextField body = new JTextField();

        send.addActionListener(
          p -> {
            if (body.getText().length() == 0) {
              title.setText("Please enter a message");
            } else {
              controller
                .getUserController()
                .emailLandlord(controller.getCurrentProperty(), body.getText());
              pop.setVisible(false);
            }
          }
        );

        c.gridx = 0;
        c.gridy = 0;
        pop.add(title, c);

        c.gridx = 0;
        c.gridy = 2;
        pop.add(send, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.ipady = 220;
        c.ipadx = 220;
        pop.add(body, c);
        pop.setVisible(true);
      }
    );

    back.addActionListener(
      e -> {
        f.setVisible(false);
        f.removeAll();
        if (controller.getUseNotification()) {
          setSwitchEvent(5);
        } else {
          setSwitchEvent(1);
        }
      }
    );

    String id = controller.getCurrentProperty().getHouseID();
    JLabel state = new JLabel(
      controller.getPostingController().getListingState(id).toString()
    );
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

        //int chosenState = 0;
        JComboBox<String> changeListingState;
        if (
          controller.getUserController().getAuthenticatedUser().getUserType() ==
          UserType.MANAGER
        ) {
          changeListingState =
            new JComboBox<String>(ListingState.getNames(ListingState.class)); //all listing states available
        } else {
          String[] renterState = {
            ListingState.ACTIVE.toString(),
            ListingState.RENTED.toString(),
            ListingState.CANCELLED.toString(),
          };
          changeListingState = new JComboBox<String>(renterState);
        }
        //changeListingState.setSelectedIndex(chosenState);

        JButton save = new JButton("Save");

        save.addActionListener(
          p -> {
            ListingState new_state = ListingState.valueOf(
              changeListingState.getSelectedItem().toString()
            );
            controller
              .getPostingController()
              .changeListingState(id, new_state.ordinal());
            state.setText(
              controller.getPostingController().getListingState(id).toString()
            ); // change
            // //
            // listing
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

    f.add(state);
    if (currentUser != null) {
      f.add(email);

      if (
        (
          currentUser.getUserType() == UserType.LANDLORD &&
          controller
            .getCurrentProperty()
            .getPostedBy()
            .equals(currentUser.getEmail())
        ) || // landlord only
        // views their own
        // postings
        currentUser.getUserType() ==
        UserType.MANAGER
      ) {
        f.add(filters);
      }
    }
    f.add(back);
    f.getContentPane().add(this);
    f.setVisible(true);
  }

  public void paintComponent(Graphics g) {
    widget = new Text(300, 40, "LISTING DETAILS");
    g.setFont(titleFont);
    widget.draw(g);
    widget = new Border(widget, 0, 0, 785, 762, 10);
    widget.draw(g);
    g.setFont(mainText);
    widget = new Text(275, 80, "Bedrooms: ");
    widget.draw(g);
    widget = new Text(275, 130, "Bathrooms: ");
    widget.draw(g);
    widget = new Text(275, 180, "Furnished: ");
    widget.draw(g);
    widget = new Text(275, 230, "Quadrant: ");
    widget.draw(g);
    widget = new Text(275, 280, "Type: ");
    widget.draw(g);
    widget = new Text(275, 330, "Listing State: ");
    widget.draw(g);
  }
}

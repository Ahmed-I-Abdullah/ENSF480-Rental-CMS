package src.main.view;

import java.awt.*;
import java.awt.Graphics;
import java.awt.event.*;
import java.lang.ModuleLayer.Controller;
import javax.swing.*;
import src.main.controller.ViewController;
import src.main.model.property.*;
import src.main.model.property.ListingDetails;
import src.main.model.property.ListingState;
import src.main.model.property.Property;
import src.main.model.user.User;

public class CreateListingsPage extends Page {

  private Property addedProperty;
  private boolean savePressed = false;

  public CreateListingsPage(Widget w, ViewController c) {
    super(c);
    widget = w;
    switchEvent = 5;
  }

  public void draw() {
    f.setLayout(new GridBagLayout());

    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;

    final JButton pay = new JButton(
      String.format(
        "Pay $%s for %s months",
        controller.getPostingController().getFeeAmount(),
        controller.getPostingController().getFeeDuration()
      )
    );

    final JButton save = new JButton("Save");

    String[] select = { "Yes", "No" };

    String[] quad = { "NW", "SW", "SE", "NE" };

    final JLabel quadrantLabel = new JLabel("Quadrant");
    final JComboBox<String> quadrant = new JComboBox<String>(quad);
    quadrant.setSelectedIndex(1);

    final JLabel descriptionLabel = new JLabel("Enter a description");
    final JTextField description = new JTextField();

    final JLabel addressLabel = new JLabel("Fill in the Address");
    final JTextField city = new JTextField("City");
    final JTextField country = new JTextField("Country");
    final JTextField province = new JTextField("Province");
    final JTextField street = new JTextField("Street");
    final JLabel label = new JLabel("Street number");
    final JTextField streetNo = new JTextField(25);
    setLayout(new FlowLayout());
    //label.setForeground(Color.red);
    streetNo.addKeyListener(
      new KeyAdapter() {
        public void keyPressed(KeyEvent ke) {
          String value = streetNo.getText();
          //int l = value.length();

          if (
            (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') ||
            ke.getKeyCode() == 8
          ) { //8 is code for delete
            streetNo.setEditable(true);
            label.setText("Street number");
          } else {
            streetNo.setEditable(false);
            label.setText("* Enter only numeric digits(0-9)");
          }
        }
      }
    );
    final JTextField postalCode = new JTextField("Postal Code");

    final JLabel furnishedLabel = new JLabel("Furnished");
    final JComboBox<String> furnished = new JComboBox<String>(select);
    furnished.setSelectedIndex(1);

    final JLabel typeLabel = new JLabel("Enter House Type:");
    final JTextField type = new JTextField("Apartment, townhouse, etc.");

    final JLabel bedroomsLabel = new JLabel("Number of Bedrooms");
    final JSlider bedrooms = new JSlider(JSlider.HORIZONTAL, 0, 5, 0);
    bedrooms.setMajorTickSpacing(1);
    bedrooms.setPaintTicks(true);
    bedrooms.setPaintLabels(true);
    bedrooms.setFont(mainText);

    final JLabel bathroomsLabel = new JLabel("Number of Bathrooms");
    final JSlider bathrooms = new JSlider(JSlider.HORIZONTAL, 0, 5, 0);
    bathrooms.setMajorTickSpacing(1);
    bathrooms.setPaintTicks(true);
    bathrooms.setPaintLabels(true);
    bathrooms.setFont(mainText);

    JButton back = new JButton("Back");
    back.setBounds(0, 0, 75, 50);

    back.addActionListener(
      e -> {
        f.setVisible(false);
        resetSwitchEvent();
      }
    );

    final JLabel sucess = new JLabel(
      "Your listing is now available for renters to view!"
    );

    pay.addActionListener(
      p -> {
        //change state from registered to active
        if (!savePressed) {
          boolean furnish = false;
          if (furnished.getSelectedItem().toString() == "Yes") {
            furnish = true;
          }
          ListingDetails listingDetails = new ListingDetails(
            ListingState.ACTIVE,
            bedrooms.getValue(),
            bathrooms.getValue(),
            type.getText(),
            furnish,
            quadrant.getSelectedItem().toString()
          );
          Address address = new Address(
            city.getText(),
            province.getText(),
            country.getText(),
            streetNo.getText() + ", " + street.getText(),
            postalCode.getText()
          );
          User currentUser = controller
            .getUserController()
            .getAuthenticatedUser();
          addedProperty =
            controller
              .getPostingController()
              .addPropertyToDatabase(
                currentUser,
                address,
                listingDetails,
                currentUser.getEmail(),
                description.getText()
              );
        }
        try {
          controller.getPostingController().payFee(addedProperty);
          c.gridx = 1;
          c.gridy = 15;
          f.add(sucess, c);
          quadrant.setEnabled(false);
          description.setEnabled(false);
          city.setEnabled(false);
          country.setEnabled(false);
          province.setEnabled(false);
          street.setEnabled(false);
          streetNo.setEnabled(false);
          type.setEnabled(false);
          bedrooms.setEnabled(false);
          bathrooms.setEnabled(false);
          furnished.setEnabled(false);
          postalCode.setEnabled(false);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    );

    save.addActionListener(
      p -> {
        boolean furnish = false;
        if (furnished.getSelectedItem().toString() == "Yes") {
          furnish = true;
        }
        if (!savePressed) {
          ListingDetails listingDetails = new ListingDetails(
            ListingState.REGISTERED,
            bedrooms.getValue(),
            bathrooms.getValue(),
            type.getText(),
            furnish,
            quadrant.getSelectedItem().toString()
          );
          Address address = new Address(
            city.getText(),
            province.getText(),
            country.getText(),
            streetNo.getText() + ", " + street.getText(),
            postalCode.getText()
          );
          User currentUser = controller
            .getUserController()
            .getAuthenticatedUser();
          addedProperty =
            controller
              .getPostingController()
              .addPropertyToDatabase(
                currentUser,
                address,
                listingDetails,
                currentUser.getEmail(),
                description.getText()
              );
          savePressed = true;
          quadrant.setEnabled(false);
          description.setEnabled(false);
          city.setEnabled(false);
          country.setEnabled(false);
          province.setEnabled(false);
          street.setEnabled(false);
          streetNo.setEnabled(false);
          type.setEnabled(false);
          bedrooms.setEnabled(false);
          bathrooms.setEnabled(false);
          furnished.setEnabled(false);
          postalCode.setEnabled(false);
        }
      }
    );

    c.gridx = 0;
    c.gridy = 0;
    f.add(back, c);

    c.weightx = 0.0;
    c.gridwidth = 1;
    c.gridx = 0;
    c.gridy = 1;
    f.add(addressLabel, c);

    c.gridx = 0;
    c.gridy = 2;
    f.add(label, c);

    c.gridx = 1;
    c.gridy = 2;
    f.add(streetNo, c);

    c.gridx = 0;
    c.gridy = 3;
    f.add(street, c);

    c.gridx = 1;
    c.gridy = 3;
    f.add(postalCode, c);

    c.gridx = 0;
    c.gridy = 4;
    f.add(city, c);

    c.gridx = 1;
    c.gridy = 4;
    f.add(province, c);

    c.gridx = 2;
    c.gridy = 4;
    f.add(country, c);

    c.gridx = 0;
    c.gridy = 5;
    f.add(quadrantLabel, c);

    c.gridx = 1;
    c.gridy = 5;
    f.add(furnishedLabel, c);

    c.gridx = 0;
    c.gridy = 6;
    f.add(quadrant, c);

    c.gridx = 1;
    c.gridy = 6;
    f.add(furnished, c);

    c.gridx = 0;
    c.gridy = 7;
    f.add(typeLabel, c);

    c.gridwidth = 2;
    c.gridx = 0;
    c.gridy = 8;
    f.add(type, c);

    c.gridwidth = 1;
    c.gridx = 0;
    c.gridy = 8;
    f.add(bedroomsLabel, c);

    c.gridx = 0;
    c.gridy = 10;
    f.add(bathroomsLabel, c);

    c.gridx = 0;
    c.gridy = 12;
    f.add(descriptionLabel, c);

    c.gridx = 1;
    c.gridy = 14;
    f.add(pay, c);

    c.gridx = 0;
    c.gridy = 14;
    f.add(save, c);

    c.gridwidth = 2;
    c.weightx = 0.0;
    c.gridx = 0;
    c.gridy = 9;
    f.add(bedrooms, c);

    c.gridwidth = 2;
    c.gridx = 0;
    c.gridy = 11;
    f.add(bathrooms, c);

    c.gridwidth = 2;
    c.weightx = 1.0;
    c.gridx = 0;
    c.gridy = 13;
    f.add(description, c);

    f.setVisible(true);
    f.getContentPane().add(this);
  }

  public void paintComponent(Graphics g) {
    widget = new Text(230, 40, "Register your property:");
    g.setFont(titleFont);
    widget.draw(g);
  }
}

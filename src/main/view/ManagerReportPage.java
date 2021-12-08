package src.main.view;

import java.awt.*;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import src.main.controller.AdminController;
import src.main.controller.ViewController;
import src.main.model.property.Property;

public class ManagerReportPage extends Page {
  private String dateErrors = "";

  public ManagerReportPage(Widget w, ViewController v) {
    super(v);
    widget = w;
    switchEvent = 4;
  }

  private boolean checkDate(String inputDate) {
    dateErrors = "";
    String dateRegex = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";

    Pattern pattern = Pattern.compile(dateRegex);
    Matcher dateMatcher = pattern.matcher(inputDate);
    if (!dateMatcher.matches()) {
      dateErrors = "Enter a valid date";
      return false;
    }
    return true;
  }

  private String[] getFormattedProperty(Property p) {
    String[] info= new String[3];
    info[0] = p.getPostedByName();
    info[1] = p.getHouseID();
    info[2] =
      p.getAddress().getStreet() +
      " " +
      p.getAddress().getCity() +
      ", " +
      p.getAddress().getProvince() +
      ", " +
      p.getAddress().getCountry() +
      p.getAddress().getPostalCode();

      return info;
  }

  public void draw() {
    f.setLayout(new GridBagLayout());

    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;

    final JLabel label = new JLabel("Select Report Period:");
    final JLabel from = new JLabel("from");
    final JTextField period1 = new JTextField("MM/DD/YYYY");
    final JLabel to = new JLabel("to");
    final JTextField period2 = new JTextField("MM/DD/YYYY");

    final JButton submit = new JButton("Submit");

    JButton back = new JButton("Back");
    back.setBounds(0, 0, 75, 50);
  
    back.addActionListener(
    e -> {
      f.setVisible(false);
      resetSwitchEvent();
    }
    );

    final JLabel submissionErrors = new JLabel(dateErrors);
    submissionErrors.setForeground(Color.red);

    final JLabel listed = new JLabel("Total number of houses listed: ");
    final JLabel listedAmount = new JLabel("");
    final JLabel rented = new JLabel("Total number of houses rented: ");
    final JLabel rentedAmount = new JLabel("");
    final JLabel active = new JLabel("Total number of active listings: ");
    final JLabel activeAmount = new JLabel("");

    final JLabel list = new JLabel("List of houses rented:");
    Font font = new Font("Courier", Font.BOLD, 12);

    final JLabel landlord = new JLabel("Landlord Name");
    landlord.setFont(font);
    final JLabel houseID = new JLabel("House Id");
    houseID.setFont(font);
    final JLabel houseAddress = new JLabel("House Address");
    houseAddress.setFont(font);

    submit.addActionListener(
      p -> {
        if (checkDate(period1.getText()) && checkDate(period2.getText())) {
          dateErrors = "";
          submissionErrors.setText(dateErrors);
          AdminController adminController;
          try {
            adminController = new AdminController(controller.getUserController().getAuthenticatedUser());
          } catch(Exception ex) {
              ex.printStackTrace();
              dateErrors = "UnAuthorized";
              return;
          }

          int numListed = 0;
          int numRented = 0;
          int numActive = 0;
          ArrayList<Property> rentedProperties = new ArrayList<Property>();

          String[] date1 = period1.getText().split("/");
          String[] date2 = period2.getText().split("/");
          String formattedFrom = date1[2] + "-" + date1[0] + "-" + date1[1];
          String formattedTo = date2[2] + "-" + date2[0] + "-" + date2[1];

          try {
            numListed =
              adminController.numListingsInPeriod(formattedFrom, formattedTo);
            numRented =
              adminController.numRentedInPeriod(formattedFrom, formattedTo);
            numActive = adminController.totalNumActive();
            rentedProperties =
              adminController.getRentedInPeriod(formattedFrom, formattedTo);
          } catch (Exception e) {
            e.printStackTrace();
          }
          // add AdminController, use the period set by doing period1.getTextField()

          listedAmount.setText(Integer.toString(numListed));
          rentedAmount.setText(Integer.toString(numRented));
          activeAmount.setText(Integer.toString(numActive));

          c.gridwidth = 1;
          c.gridx = 0;
          c.gridy = 5;
          f.add(listed, c);

          c.gridx = 1;
          c.gridy = 5;
          f.add(listedAmount, c);

          c.gridx = 0;
          c.gridy = 6;
          f.add(rented, c);

          c.gridx = 1;
          c.gridy = 6;
          f.add(rentedAmount, c);

          c.gridx = 0;
          c.gridy = 7;
          f.add(active, c);

          c.gridx = 1;
          c.gridy = 7;
          f.add(activeAmount, c);

          c.gridx = 0;
          c.gridy = 9;
          f.add(list, c);

          c.gridx = 0;
          c.gridy = 10;
          f.add(landlord, c);

          c.gridx = 1;
          c.gridy = 10;
          f.add(houseID, c);

          c.gridx = 2;
          c.gridy = 10;
          f.add(houseAddress, c);

          for (int i = 0; i < rentedProperties.size(); i++) {
              String[] info = getFormattedProperty(rentedProperties.get(i));
              for(int j = 0; j < info.length; j++) {
                JLabel houseText = new JLabel(info[j]);
                c.gridx = j;
                c.gridy = 11 + i;
                f.add(houseText, c);
              }
          }

          f.setVisible(true);
          f.getContentPane().add(this);
        } else {
          submissionErrors.setText(dateErrors);
        }
      }
    );

    // back.addActionListener(
    // e -> {
    // f.setVisible(false);
    // f.removeAll();
    // setSwitchEvent(3);
    // }
    // );

    c.weightx = 0.0;
    c.gridwidth = 1;

    c.gridx = 0;
    c.gridy = 0;
    f.add(back, c);

    c.weightx = 0.0;
    c.gridwidth = 1;

    c.gridx = 1;
    c.gridy = 1;
    f.add(submissionErrors, c);

    c.gridx = 0;
    c.gridy = 1;
    f.add(label, c);

    c.gridx = 0;
    c.gridy = 2;
    f.add(from, c);

    c.gridx = 1;
    c.gridy = 2;
    f.add(period1, c);

    c.gridx = 0;
    c.gridy = 3;
    f.add(to, c);

    c.gridx = 1;
    c.gridy = 3;
    f.add(period2, c);

    c.gridx = 0;
    c.gridy = 4;
    c.gridwidth = 2;
    f.add(submit, c);

    f.setVisible(true);
    f.getContentPane().add(this);
  }

  public void paintComponent(Graphics g) {
    // widget.draw(g);
  }
}

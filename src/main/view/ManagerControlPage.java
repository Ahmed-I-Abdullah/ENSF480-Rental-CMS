package src.main.view;

import java.awt.*;
import java.awt.Graphics;
import javax.swing.*;
import src.main.controller.AdminController;
import src.main.controller.ViewController;

public class ManagerControlPage extends Page {

  private String currentFee =
    "Current fee: $" + controller.getPostingController().getFeeAmount();
  private String currentDuration =
    "Current duration(month): " +
    controller.getPostingController().getFeeDuration();

  public ManagerControlPage(Widget w, ViewController c) {
    super(c);
    this.widget = w;
    switchEvent = 3;
  }

  public void draw() {
    JButton back = new JButton("Back");
    back.setBounds(10, 10, 75, 50);

    JButton periodical = new JButton("Periodical");
    periodical.setBounds(100, 100, 200, 150);

    JButton users = new JButton("Users");
    users.setBounds(100, 250, 200, 150);

    JButton fees = new JButton("Fees");
    fees.setBounds(300, 100, 200, 150);

    JButton properties = new JButton("Properties");
    properties.setBounds(300, 250, 200, 150);

    back.addActionListener(
      e -> {
        f.setVisible(false);
        resetSwitchEvent();
      }
    );

    periodical.addActionListener(
      e -> {
        f.setVisible(false);
        f.removeAll();
        switchEvent = 4; //may need to be changed based on page class index
      }
    );

    fees.addActionListener(
      e -> {
        JFrame pop = new JFrame("Edit Fees");
        pop.setSize(300, 250);
        pop.setLocationRelativeTo(null);
        pop.setLayout(new GridBagLayout());

        JLabel feeLabel = new JLabel(currentFee);

        JLabel durationLabel = new JLabel(currentDuration);

        JTextField price = new JTextField("New Fee");

        JTextField term = new JTextField("New Term");

        JButton update = new JButton("Update");

        update.addActionListener(
          p -> {
            String newFee = price.getText();
            String newTerm = term.getText();
            double numericFee = 0;
            int numericDuration = 0;
            boolean good = true;
            System.out.println(price.getText() + "\n" + term.getText());
            try {
              numericFee = Double.parseDouble(newFee);
              numericDuration = Integer.parseInt(newTerm);
            } catch (Exception z) {
              good = false;
              JFrame popup = new JFrame("Invalid data entered");
              popup.setSize(250, 250);
              popup.setLocationRelativeTo(null);
              JLabel text = new JLabel("Please only enter digits");
              text.setBounds(150, 100, 100, 30);
              popup.add(text);
              popup.setVisible(true);
            }
            if (good) {
              try {
                AdminController aController = new AdminController(
                  controller.getUserController().getAuthenticatedUser()
                );
                aController.changeFeeAmount(numericFee);
                aController.changeFeeDuration(numericDuration);
                currentFee = "Current fee: $" + numericFee;
                currentDuration = "Current duration(month): " + numericDuration;
              } catch (Exception ex) {
                ex.printStackTrace();
              }
              pop.setVisible(false);
            }
          }
        );

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        pop.add(feeLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        pop.add(durationLabel, c);

        c.gridx = 0;
        c.gridy = 1;
        pop.add(price, c);

        c.gridx = 1;
        c.gridy = 1;
        pop.add(term, c);

        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 2.0;
        pop.add(update, c);
        pop.setVisible(true);
      }
    );

    users.addActionListener(
      e -> {
        JFrame pop = new JFrame("Find User");
        pop.setSize(250, 250);
        pop.setLocationRelativeTo(null);
        pop.setLayout(new GridBagLayout());

        JTextField search = new JTextField();

        JLabel found = new JLabel("Search User");

        JButton delete = new JButton("delete");

        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        search.addActionListener(
          p -> {
            System.out.println(search.getText());
            //controller.searchUsers(search.getText());
            pop.setVisible(false);
            if (true) { //if the user is found
              found.setText("User found!");
              c.gridx = 0;
              c.gridy = 2;
              pop.add(delete, c);
            } else {
              found.setText("User not found");
            }
            pop.setVisible(true);
          }
        );

        delete.addActionListener(
          p -> {
            System.out.println("user deleted");
            //viewController.deleteUser(user);
          }
        );

        c.gridx = 0;
        c.gridy = 0;
        pop.add(found, c);

        c.gridx = 0;
        c.gridy = 1;
        pop.add(search, c);

        pop.setVisible(true);
      }
    );

    properties.addActionListener(
      e -> {
        f.setVisible(false);
        f.removeAll();
        switchEvent = 1;
      }
    );
    f.add(periodical);
    f.add(users);
    f.add(properties);
    f.add(fees);
    f.add(back);
    f.getContentPane().add(this);
    f.setVisible(true);
  }

  public void paintComponent(Graphics g) {
    widget = new Text(230, 40, "MANAGER CONTROL");
    g.setFont(titleFont);
    widget.draw(g);
    widget = new Border(widget, 0, 0, 785, 762, 10);
    widget.draw(g);
  }
}

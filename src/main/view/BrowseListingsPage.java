package src.main.view;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;

import src.main.controller.ViewController;
import src.main.model.property.Property;
import src.main.model.user.UserType;
import src.main.model.user.RegisteredRenter;

public class BrowseListingsPage extends Page {
  private boolean isSubscribed = false;
  private static final String[] quad = { "NW", "SW", "SE", "NE" };
  private static final String[] select = { "Yes", "No" };
  private static final String chooseTypeText =
    "Type: apartment, townhouse etc.";
  private String searchCriteriaErrors = "";
  private String chosenApartmentType = chooseTypeText;
  private int chosenNumBedrooms = 0;
  private int chosenNumBathrooms = 0;
  private int chosenQuadrantIndex = 0;
  private int chosenFurnishedIndex = 0;

  public BrowseListingsPage(Widget w, ViewController c) {
    super(c);
    widget = w;
    switchEvent = 1;
    if(controller.getUserController().getAuthenticatedUser() != null &&
    controller.getUserController().getAuthenticatedUser().getUserType() == UserType.RENTER) {
      RegisteredRenter r = (RegisteredRenter)controller.getUserController().getAuthenticatedUser();
      isSubscribed = r.getIsSubscribed();
    }
  }

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

  public boolean checkSearchErrors() {
    searchCriteriaErrors = "";
    if (chosenApartmentType.equals(chooseTypeText)) {
      searchCriteriaErrors = "Choose property type.";
      return false;
    }
    return true;
  }

  private void setUserCritera() {
    if (
      controller.getUserController().getAuthenticatedUser() == null ||
      controller.getUserController().getAuthenticatedUser().getUserType() !=
      UserType.RENTER
    ) {
      return;
    }

    controller
      .getUserController()
      .setRenterSearchCriteria(
        chosenNumBedrooms,
        chosenNumBathrooms,
        chosenApartmentType,
        chosenFurnishedIndex == 0,
        quad[chosenQuadrantIndex]
      );
  }

  public void draw() {
    f = new Scroller();
    f.setVisible(true);
  }

  public class Scroller extends JFrame {

    public Scroller() throws HeadlessException {
        final JPanel panel = new JPanel();
        JLabel title = new JLabel("BROWSE LISTINGS");
        title.setFont(new Font("Helvetica", Font.BOLD, 35));
        title.setBounds(240,0,400,50);
        panel.add(title);
        final ArrayList<Property> properties = controller.getAllProperties();
        for (int i = 0; i < properties.size(); i++) {
          JLabel address = new JLabel(getFormattedAddress(properties.get(i)));
          address.setFont(new Font("Serif", Font.PLAIN, 20));
          address.setBounds(195, 50 + (i * 50), 420, 50);
          address.setBorder(LineBorder.createBlackLineBorder());
          panel.add(address);

          JButton clickme = new JButton("View");
          clickme.setBounds(620, 50 + (i * 50), 75, 50);
          final int p = i;
          clickme.addActionListener(
            e -> {
              controller.setCurrentProperty(p);
              f.setVisible(false);
              f.removeAll();
              switchEvent = 6;
            }
          );
          panel.add(clickme);
        }

        JButton back = new JButton("Back");
        back.setBounds(5, 10, 75, 50);

        final JButton subscriptions = new JButton("Unsubscribe");
        subscriptions.setBounds(40, 150, 145, 50);

        JButton filters = new JButton("Set Search Criteria");
        filters.setBounds(40, 100, 145, 50);

        filters.addActionListener(
          e -> {
            JFrame pop = new JFrame("Select Your Preferences");
            pop.setSize(350, 350);
            pop.setLocationRelativeTo(null);
            pop.setLayout(new GridBagLayout());

            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;

            JButton ok = new JButton("OK");

            JButton save = new JButton("Save");

            JLabel quadrantLabel = new JLabel("Quadrant");
            JComboBox<String> quadrant = new JComboBox<String>(quad);
            quadrant.setSelectedIndex(chosenQuadrantIndex);

            JLabel criteriaErrors = new JLabel(searchCriteriaErrors);
            criteriaErrors.setForeground(Color.red);

            JLabel furnishedLabel = new JLabel("Furnished");
            JComboBox<String> furnished = new JComboBox<String>(select);
            furnished.setSelectedIndex(chosenFurnishedIndex);

            JTextField type = new JTextField(chosenApartmentType);

            JLabel bedroomsLabel = new JLabel("Number of Bedrooms");
            JSlider bedrooms = new JSlider(
              JSlider.HORIZONTAL,
              0,
              5,
              chosenNumBedrooms
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
              chosenNumBathrooms
            );
            bathrooms.setMajorTickSpacing(1);
            bathrooms.setPaintTicks(true);
            bathrooms.setPaintLabels(true);
            bathrooms.setFont(mainText);

            ok.addActionListener(
              p -> {
                chosenApartmentType = type.getText();
                chosenNumBathrooms = bathrooms.getValue();
                chosenNumBedrooms = bedrooms.getValue();
                chosenQuadrantIndex = quadrant.getSelectedIndex();
                chosenFurnishedIndex = furnished.getSelectedIndex();
                if (checkSearchErrors()) {
                  pop.setVisible(false);
                } else {
                  criteriaErrors.setText(searchCriteriaErrors);
                }
              }
            );

            save.addActionListener(
              p -> {
                chosenApartmentType = type.getText();
                chosenNumBathrooms = bathrooms.getValue();
                chosenNumBedrooms = bedrooms.getValue();
                chosenQuadrantIndex = quadrant.getSelectedIndex();
                chosenFurnishedIndex = furnished.getSelectedIndex();
                if (checkSearchErrors()) {
                  pop.setVisible(false);
                  setUserCritera();
                  panel.add(subscriptions);
                  panel.repaint();
                } else {
                  criteriaErrors.setText(searchCriteriaErrors);
                }
              }
            );

            c.gridx = 0;
            c.gridy = 0;
            pop.add(criteriaErrors, c);

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
            c.gridy = 8;
            pop.add(bathroomsLabel, c);

            c.gridx = 1;
            c.gridy = 10;
            pop.add(ok, c);

            if (
              controller.getUserController().getAuthenticatedUser() != null &&
              controller.getUserController().getAuthenticatedUser().getUserType() ==
              UserType.RENTER
            ) {
              c.gridx = 0;
              c.gridy = 10;
              pop.add(save, c);
            }
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

        back.addActionListener(
          e -> {
            f.setVisible(false);
            f.removeAll();
            resetSwitchEvent();
          }
        );

        subscriptions.addActionListener(
          e -> {
            RegisteredRenter r = (RegisteredRenter)controller.getUserController().getAuthenticatedUser();
            try {
              r.unsubscribe();
              panel.remove(subscriptions);
              panel.repaint();
            } catch(Exception ex) {
              ex.printStackTrace();
            }
          }
        );

        if (
          controller.getUserController().getAuthenticatedUser() != null &&
          controller.getUserController().getAuthenticatedUser().getUserType() == UserType.RENTER
        ) {
          if(isSubscribed) {
            panel.add(subscriptions);
          }
        }
    
        panel.add(back);
        panel.add(filters);
        //panel.setBorder(BorderFactory.createLineBorder(Color.red));
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(750, 80 + properties.size()*50));

        final JScrollPane scroll = new JScrollPane(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setLayout(new BorderLayout());
        add(scroll); //, BorderLayout.CENTER
        setLocationRelativeTo(null);
        setSize(800, 800);
        setVisible(true);
    }
  }
  public void paintComponent(Graphics g) {
    widget = new Text(230, 40, "BROWSE LISTINGS");
    g.setFont(titleFont);
    widget.draw(g);
    widget = new Border(widget, 0, 0, 785, 762, 10);
    widget.draw(g);
  }
}

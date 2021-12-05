package src.main.controller;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import src.main.model.property.Address;
import src.main.model.property.ListingDetails;
import src.main.model.property.ListingState;
import src.main.model.property.Property;

public class ViewController {
  private ArrayList<Property> allProperties;
  private PostingController postingController;
  private UserController userController;

  public ViewController() {
      allProperties = new ArrayList<Property>();
    try {
      Connection connection = ControllerManager.getConnection();
      String propertyQuery = "SELECT * FROM PROPERTY WHERE Current_state=?";

      PreparedStatement pStatment = connection.prepareStatement(propertyQuery);
      pStatment.setInt(1, ListingState.ACTIVE.ordinal());

      ResultSet result = pStatment.executeQuery();

      while (result.next()) {
        Address propertyAddress = new Address(
          "",
          result.getString("province"),
          result.getString("country"),
          result.getString("street_address"),
          result.getString("postal_code")
        );

        String propertyFurnished = result.getString("is_furnished");

        ListingDetails listingDetails = new ListingDetails(
          ListingState.values()[result.getInt("current_state")],
          result.getInt("no_bedrooms"),
          result.getInt("no_bathrooms"),
          result.getString("property_type"),
          propertyFurnished.equals("1"),
          result.getString("city_quadrant")
        );

        Property property = new Property(
          result.getString("id"),
          propertyAddress,
          listingDetails,
          result.getString("landlord_email"),
          "",
          false
        );

        allProperties.add(property);
        System.out.println(property.getHouseID());
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

  public ArrayList<Property> getAllProperties() {
      return this.allProperties;
  }

  public void setUserController(UserController controller) {
    this.userController = controller;
  }

  public UserController getUserController() {
    return this.userController;
  }
}

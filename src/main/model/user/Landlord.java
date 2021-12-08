package src.main.model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import src.main.controller.ControllerManager;
import src.main.model.property.Address;
import src.main.model.property.ListingDetails;
import src.main.model.property.ListingState;
import src.main.model.property.Property;

public class Landlord implements User {

  private String name;
  private String email;
  private ArrayList<Property> properties;

  public Landlord(String email, String name) {
    setName(name);
    setEmail(email);
    this.properties = new ArrayList<Property>();
    try {
      Connection connection = ControllerManager.getConnection();
      String propertyQuery = "SELECT * FROM PROPERTY WHERE Landlord_email=?";

      PreparedStatement pStatment = connection.prepareStatement(propertyQuery);
      pStatment.setString(1, email);

      ResultSet result = pStatment.executeQuery();

      while (result.next()) {
        Address propertyAddress = new Address(
          result.getString("city"),
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
          result.getString("property_description"),
          false
        );

        properties.add(property);
        System.out.println(property.getHouseID());
        System.out.println(email);
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

  public Property createProperty(String houseID,
  Address address,
  ListingDetails specifications,
  String postedBy,
  String description) {
      try {
        Property property = new Property(
            houseID,
            address,
            specifications,
            postedBy,
            description,
            true
          );
          this.properties.add(property);
          return property;
      } catch(Exception e) {
        e.printStackTrace();
        System.exit(-1);
        return null;
      }

  }

  public ArrayList<Property> getLandlordProperties() {
    return this.properties;
  }

  public String getName() {
    return this.name;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int addProperty(Property property) {
    this.properties.add(property);
    return this.properties.size();
  }

  public int removeProperty(Property property) {
    this.properties.add(property);
    return this.properties.size();
  }

  public UserType getUserType() {
    return UserType.LANDLORD;
  }
}

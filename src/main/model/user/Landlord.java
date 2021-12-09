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
  
  // Constructor
  public Landlord(String email, String name) {
    setName(name);
    setEmail(email);
    this.properties = new ArrayList<Property>();
    
    // Connect to DB
    try {
      Connection connection = ControllerManager.getConnection();
      String propertyQuery = "SELECT * FROM PROPERTY WHERE Landlord_email=?";

      PreparedStatement pStatment = connection.prepareStatement(propertyQuery);
      pStatment.setString(1, email);

      ResultSet result = pStatment.executeQuery();
      
      // Read in all properties belonging to this landlord, and add them to properties array
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
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }
  
  // Promises: return created property
  // Requires: houseID, address, specifications, landlord who posted th property
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
  
  // Promises: return all porperties owned by this landlord
  public ArrayList<Property> getLandlordProperties() {
    return this.properties;
  }
  
  // name getter
  public String getName() {
    return this.name;
  }
  
  // email getter
  public String getEmail() {
    return this.email;
  }
  
  // email setter
  public void setEmail(String email) {
    this.email = email;
  }
  
  // name setter
  public void setName(String name) {
    this.name = name;
  }
  
  // Promises: add porperty to this landlord's properties array
  // Requires: property to be added
  public int addProperty(Property property) {
    this.properties.add(property);
    return this.properties.size();
  }
  
  // Promises: remove property from this landlord's properties array
  // Requires: property to be removed
  public int removeProperty(Property property) {
    this.properties.add(property);
    return this.properties.size();
  }
  
  // Promises: return type of user, which is LANDLORD
  public UserType getUserType() {
    return UserType.LANDLORD;
  }
}

package src.main.model.property;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import src.main.controller.ControllerManager;

public class Property {

  private String houseID;
  private String postedBy;
  private String postedByName;
  private Address address;
  private ListingDetails specifications;
  private String description;

  //PROMISES: creates Property object
  //REQUIRES: the houseID, address property, more details about the property, the email of the owner, further descriptions, whether the property should be added to the db
  public Property(
    String houseID,
    Address address,
    ListingDetails specifications,
    String postedBy,
    String description,
    boolean addToDatabase
  )
    throws SQLException {
    this.address = address;
    this.specifications = specifications;
    this.postedBy = postedBy;
    this.description = description;
    if (addToDatabase) {
      this.houseID = addPropertyToDatabase(); //id generated from database
    } else {
      this.houseID = houseID; //user can set the house id
    }
  }

  //PROMISES: inserts the property into the database and returns the id generated from the database
  private String addPropertyToDatabase() throws SQLException {
    Connection connection = ControllerManager.getConnection();

    String update =
      "INSERT INTO PROPERTY(ID, Landlord_email, Property_type, Current_state, No_bedrooms,No_bathrooms, Is_furnished, City_quadrant, Country, Province, City, Property_description, Street_address, Postal_code) " +
      "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?::bit, ?, ?, ?, ?, ?, ?, ?) RETURNING ID;";

    String furnished = "0";
    if (specifications.getFurnished()) { //if furnished is true set it to 1 in the database
      furnished = "1";
    }

    PreparedStatement statment = connection.prepareStatement(update);
    statment.setString(1, postedBy);
    statment.setString(2, specifications.getHousingType());
    statment.setInt(3, specifications.getState().ordinal());
    statment.setInt(4, specifications.getNumOfBedrooms());
    statment.setInt(5, specifications.getNumOfBathrooms());
    statment.setString(6, furnished);
    statment.setString(7, specifications.getCityQuadrant());
    statment.setString(8, address.getCountry());
    statment.setString(9, address.getProvince());
    statment.setString(10, address.getCity());
    statment.setString(11, description);
    statment.setString(12, address.getStreet());
    statment.setString(13, address.getPostalCode());

    ResultSet result = statment.executeQuery();
    result.next();
    return result.getString("id");
  }

  //PROMISES: returns the ListingState of the current property through the ListingDetails class
  public ListingState checkState() {
    return specifications.getState();
  }

  //PROMISES: returns the id of the property (either database or user generated)
  public String getHouseID() {
    return this.houseID;
  }

  //PROMISES: returns the address of the property (includes street, postal code, city, province, country)
  public Address getAddress() {
    return this.address;
  }

  //PROMISES: sets the address of the property
  //REQUIRES: the address of the property
  public void setAddress(Address address) {
    this.address = address;
  }

  //PROMISES: returns more details about the property (city quadrant, number of bathrooms and bedrooms etc.)
  public ListingDetails getSpecifications() {
    return this.specifications;
  }

  //PROMSIES: sets further details about the property
  //REQUIRES: the city quadrant, number of bedrooms and bathrooms, housing type, listing state, if its furnished
  public void setSpecifications(ListingDetails specifications) {
    this.specifications = specifications;
  }

  //PROMSIES: returns the email of the landlord that posted the property
  public String getPostedBy() {
    return this.postedBy;
  }

  //PROMISES: sets the owners email
  //REQUIRES: the landlords emails
  public void setPostedBy(String postedBy) {
    this.postedBy = postedBy;
  }

  //PROMISES: gets the landlords names
  public String getPostedByName() {
    return this.postedByName;
  }

  //PROMISES: sets the landlords name
  //REQUIRES: the name of the propertys owner
  public void setPostedByName(String postedByName) {
    this.postedByName = postedByName;
  }

  //PROMSIES: gets the description about the property
  public String getDescription() {
    return this.description;
  }

  //PROMSIES: sets the description about the property
  //REQUIRES: the propertys description y(optional)
  public void setDescription(String description) {
    this.description = description;
  }

  //PROMISES: updates the state of the current property in the database
  //REQUIRES: the new state for the property
  public void updateState(ListingState newState) throws SQLException {
    Connection connection = ControllerManager.getConnection();
    String updatePropertyState = "UPDATE PROPERTY " +
    "SET Current_state = ? " +
    "WHERE ID = ?::uuid;";

    PreparedStatement pStatement = connection.prepareStatement(updatePropertyState);
    pStatement.setInt(1, newState.ordinal());
    pStatement.setString(2, houseID);

    pStatement.executeUpdate();
  }
}

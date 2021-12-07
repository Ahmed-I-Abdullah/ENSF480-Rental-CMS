package src.main.model.property;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import src.main.controller.ControllerManager;
import src.main.model.user.Landlord;

public class Property {

  private String houseID;
  private String postedBy;
  private String postedByName;
  private Address address;
  private ListingDetails specifications;
  private String description;

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
      this.houseID = addPropertyToDatabase();
    } else {
      this.houseID = houseID;
    }
  }

  private String addPropertyToDatabase() throws SQLException {
    Connection connection = ControllerManager.getConnection();

    String update =
      "INSERT INTO PROPERTY(ID, Landlord_email, Property_type, Current_state, No_bedrooms,No_bathrooms, Is_furnished, City_quadrant, Country, Province, City, Property_description, Street_address, Postal_code) " +
      "VALUES(DEFAULT, ?, ?, ?, ?, ?, ?::bit, ?, ?, ?, ?, ?, ?, ?) RETURNING ID;";

    String furnished = "0";
    if (specifications.getFurnished()) {
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

  public ListingState checkState() {
    return specifications.getState();
  }

  public String getHouseID() {
    return this.houseID;
  }

  public Address getAddress() {
    return this.address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public ListingDetails getSpecifications() {
    return this.specifications;
  }

  public void setSpecifications(ListingDetails specifications) {
    this.specifications = specifications;
  }

  public String getPostedBy() {
    return this.postedBy;
  }

  public void setPostedBy(String postedBy) {
    this.postedBy = postedBy;
  }

  public String getPostedByName() {
    return this.postedByName;
  }

  public void setPostedByName(String postedByName) {
    this.postedByName = postedByName;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}

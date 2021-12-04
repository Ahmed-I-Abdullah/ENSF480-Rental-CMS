package src.main.model.property;

import java.util.*;
import src.main.model.user.Landlord;
import src.main.controller.ControllerManager;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Property {

  private String houseID;
  private String postedBy;
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
  ) throws SQLException {
    this.address = address;
    this.specifications = specifications;
    this.postedBy = postedBy;
    this.description = description;
    if(addToDatabase) {
        houseID = addPropertyToDatabase();
    } else {
        this.houseID = houseID;
    }
  }

  private String addPropertyToDatabase() throws SQLException {
    Connection connection = ControllerManager.getConnection();

    String update =
      "INSERT INTO PROPERTY(ID, Landlord_email, Property_type, Current_state, No_bedrooms, Is_furnished, City_quadrant, Country, Province, Street_address, Postal_code) " +
      "VALUES(DEFAULT, ?, ?, ?, ?, ?::bit, ?, ?, ?, ?, ?) RETURNING ID;";

      String furnished = "0";
      if(specifications.getFurnished()) {
          furnished = "1";
      }

    PreparedStatement statment = connection.prepareStatement(update);
    statment.setString(1, postedBy);
    statment.setString(2, specifications.getHousingType());
    statment.setInt(3, specifications.getState().ordinal());
    statment.setInt(4, specifications.getNumOfBedrooms());
    statment.setString(5, furnished);
    statment.setString(6, specifications.getCityQuadrant());
    statment.setString(7, address.getCountry());
    statment.setString(8, address.getProvince());
    statment.setString(9, address.getStreet());
    statment.setString(10, address.getPostalCode());

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

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}

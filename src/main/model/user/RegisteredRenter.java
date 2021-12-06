package src.main.model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import src.main.model.property.ListingDetails;
import src.main.model.property.Property;
import src.main.controller.UserNotFoundException;
import src.main.controller.ControllerManager;

public class RegisteredRenter implements Observer, User {

  private String name;
  private String email;
  private Subject subject;
  private boolean isSubscribed;
  private ListingDetails searchCriteria;

  public RegisteredRenter(String email, String name) {
    setEmail(email);
    setName(name);
  }

  public RegisteredRenter(String email, String name, ListingDetails criteria) {
    setEmail(email);
    setName(name);
    this.searchCriteria = criteria; 
  }

  public String getName() {
    return this.name;
  }

  public String getEmail() {
    return this.email;
  }

  public Subject getSubject() {
    return this.subject;
  }

  public ListingDetails getSearchCriteria() {
    return this.searchCriteria;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  public void setSearchCriteria(ListingDetails criteria) throws SQLException, UserNotFoundException {
    this.searchCriteria = criteria;

    Connection connection = ControllerManager.getConnection();

    String userQuery = "SELECT * FROM RENTER r WHERE r.Email = ?";

    PreparedStatement pStatment = connection.prepareStatement(userQuery);
    pStatment.setString(1, email);

    ResultSet result = pStatment.executeQuery();

    if (!result.isBeforeFirst()) {
      throw new UserNotFoundException("User not found in databse.");
    }

    String updateRenter =
      "UPDATE RENTER " +
      "SET Is_furnished=?::bit, City_quadrant=?, No_bedrooms=?, No_bathrooms=?, Property_type=? " +
      "WHERE Email = ?;";

    PreparedStatement pStatmentTwo = connection.prepareStatement(updateRenter);

    String furnished = "1";
    if(!criteria.isFurnished()) {
        furnished = "0";
    }

    pStatmentTwo.setString(1, furnished);
    pStatmentTwo.setString(2, criteria.getCityQuadrant());
    pStatmentTwo.setInt(3, criteria.getNumOfBedrooms());
    pStatmentTwo.setInt(4, criteria.getNumOfBathrooms());
    pStatmentTwo.setString(5, criteria.getHousingType());
    pStatmentTwo.setString(6, email);

    pStatmentTwo.executeUpdate();
  }

  public void emailLandlord() {}

  public void update(Property property) {}

  public UserType getUserType() {
    return UserType.RENTER;
  }
}

package src.main.controller;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import src.main.model.property.*;
import src.main.model.user.Landlord;
import src.main.model.user.User;

public class PostingController {

  public PostingController() {}

  public void payFee(Property p) throws SQLException {
    p.updateState(ListingState.ACTIVE);
    String feeID = getFeeID();

    Connection connection = ControllerManager.getConnection();
    String updatePropertyFee = "INSERT INTO PAID_BY(Landlord_email, Fee_id, Property_id, Start_date, Num_periods) " +
    "VALUES(?, ?::uuid, ?::uuid, DEFAULT, ?)";

    PreparedStatement pStatement = connection.prepareStatement(updatePropertyFee);
    pStatement.setString(1, p.getPostedBy());
    pStatement.setString(2, feeID);
    pStatement.setString(3, p.getHouseID());
    pStatement.setInt(4, 1);

    pStatement.executeUpdate();
  }

  public Property addPropertyToDatabase(
    User u,
    Address address,
    ListingDetails specifications,
    String postedBy,
    String description
  ) {
    Landlord landlord = (Landlord) u;
    return landlord.createProperty("", address, specifications, postedBy, description);
  }

  public ListingState getListingState(String id) {
    try {
      Connection connection = ControllerManager.getConnection();

      String stateOfListing =
        "SELECT current_state " +
        "FROM PROPERTY p " +
        "WHERE p.id = '" + id + "';" ;

      Statement statment = connection.createStatement();
      ResultSet result = statment.executeQuery(stateOfListing);
      result.next();
      Integer state = (Integer)result.getObject("current_state");
      ListingState listingState = ListingState.values()[state];
      return listingState;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public ListingState changeListingState(String id, Integer new_state) {
    try {
      Connection connection = ControllerManager.getConnection();

      String stateOfListing =
        "UPDATE property " +
        "SET current_state = ?" +
        "WHERE id = '" + id + "';" ;

      PreparedStatement pStatement = connection.prepareStatement(stateOfListing);
      pStatement.setInt(1,new_state);
      pStatement.executeUpdate();
      ListingState listingState = ListingState.values()[new_state];
      return listingState;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public double getFeeAmount() {
    try {
      Connection connection = ControllerManager.getConnection();

      String mostRecentFee =
        "SELECT * " +
        "FROM POSTING_FEE p1 " +
        "WHERE p1.Date_updated = (SELECT MAX(Date_updated) FROM POSTING_FEE p2);";

      Statement statment = connection.createStatement();
      ResultSet result = statment.executeQuery(mostRecentFee);
      result.next();
      BigDecimal decimalValue = (BigDecimal)result.getObject("amount");
      double doubleAmount = decimalValue.doubleValue();
      return doubleAmount;
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }
  }

  public int getFeeDuration() {
    try {
      Connection connection = ControllerManager.getConnection();

      String mostRecentFee =
        "SELECT * " +
        "FROM POSTING_FEE p1 " +
        "WHERE p1.Date_updated = (SELECT MAX(Date_updated) FROM POSTING_FEE p2);";

      Statement statment = connection.createStatement();
      ResultSet result = statment.executeQuery(mostRecentFee);
      result.next();
      return result.getInt("duration");
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }
  }


  public String getFeeID() {
    try {
      Connection connection = ControllerManager.getConnection();

      String mostRecentFee =
        "SELECT * " +
        "FROM POSTING_FEE p1 " +
        "WHERE p1.Date_updated = (SELECT MAX(Date_updated) FROM POSTING_FEE p2);";

      Statement statment = connection.createStatement();
      ResultSet result = statment.executeQuery(mostRecentFee);
      result.next();
      return result.getString("id");
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }
}

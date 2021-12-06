package src.main.controller;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.math.RoundingMode;
import src.main.model.property.*;
import src.main.model.user.Landlord;
import src.main.model.user.User;

public class PostingController {

  public PostingController() {}

  public void payFee() {}

  public void addPropertyToDatabase(
    User u,
    Address address,
    ListingDetails specifications,
    String postedBy,
    String description
  ) {
    Landlord landlord = (Landlord) u;
    landlord.createProperty("", address, specifications, postedBy, description);
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
}

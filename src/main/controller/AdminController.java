package src.main.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import src.main.model.property.ListingState;
import src.main.model.user.User;
import src.main.model.user.UserType;

public class AdminController extends UserController {

  private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(
    "yyyy-MM-dd"
  );

  public AdminController(User u) throws UnAuthorizedException {
    super(u);
    if (u.getUserType() != UserType.MANAGER) {
      throw new UnAuthorizedException("User is not a manager.");
    }
  }

  public void generateReport(Date startDate, Date endDate) throws SQLException {
    String from = dateFormatter.format(startDate);
    String to = dateFormatter.format(endDate);

    int numListings = numListingsInPeriod(from, to);
    int numRented = numRentedInPeriod(from, to);
    int totalNumActive = totalNumActive();

    System.out.println("listings added in period: " + numListings);
    System.out.println("rents in period: " + numRented);
    System.out.println("total active listing is: " + totalNumActive());
  }

  private int numListingsInPeriod(String from, String to) throws SQLException {
    Connection connection = ControllerManager.getConnection();
    String listedQuery =
      "SELECT COUNT(DISTINCT p.Property_id) " +
      "FROM PROPERTY_STATE p " +
      "Where p.Property_id IN ( " +
      "SELECT p1.Property_id " +
      "FROM PROPERTY_STATE p1 " +
      "WHERE p1.State_date >= ?::timestamp AND p1.State_date <= ?::timestamp AND p1.State = ?" +
      "EXCEPT " +
      "SELECT p2.Property_id " + // remove cases where property was suspended/cancelled and got re-activated
      "FROM PROPERTY_STATE p2 " +
      "WHERE p2.State_date < ?::timestamp AND p2.State = 0 " +
      ");";

    PreparedStatement pStatment = connection.prepareStatement(listedQuery);

    pStatment.setString(1, from);
    pStatment.setString(2, to);
    pStatment.setInt(3, ListingState.ACTIVE.ordinal());
    pStatment.setString(4, from);

    ResultSet result = pStatment.executeQuery();
    result.next();
    return result.getInt("count");
  }

  private int numRentedInPeriod(String from, String to) throws SQLException {
    Connection connection = ControllerManager.getConnection();
    String rentedQuery =
      "SELECT COUNT(DISTINCT p.Property_id) " +
      "FROM PROPERTY_STATE p " +
      "WHERE p.State_date >= ?::timestamp AND p.State_date <= ?::timestamp AND p.State = ?";

    PreparedStatement pStatment = connection.prepareStatement(rentedQuery);

    pStatment.setString(1, from);
    pStatment.setString(2, to);
    pStatment.setInt(3, ListingState.RENTED.ordinal());

    ResultSet result = pStatment.executeQuery();
    result.next();
    return result.getInt("count");
  }

  private int totalNumActive() throws SQLException {
    Connection connection = ControllerManager.getConnection();
    String activeQuery =
      "SELECT COUNT(DISTINCT p1.Property_id) " +
      "FROM PROPERTY_STATE p1 " +
      "WHERE State_date = (SELECT MAX(State_date) FROM PROPERTY_STATE p2 WHERE p1.Property_id = p2.Property_id);";

    PreparedStatement pStatment = connection.prepareStatement(activeQuery);

    ResultSet result = pStatment.executeQuery();
    result.next();
    return result.getInt("count");
  }
}

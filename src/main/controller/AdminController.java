package src.main.controller;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import src.main.model.property.*;
import src.main.model.user.User;
import src.main.model.user.UserType;

public class AdminController extends UserController {

  private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(
    "yyyy-MM-dd"
  );

  /**
   * A constructor, checks user authorization and creates
   * an AdminController Object
   * @param u User that will use the Admin Controller
   */
  public AdminController(User u) throws UnAuthorizedException {
    super(u);
    if (u.getUserType() != UserType.MANAGER) {
      throw new UnAuthorizedException("User is not a manager.");
    }
  }

  /**
   * Changes the amount of fee to post a property in the database
   * @param amount double representing the new fee
   * @throws SQLException
   */
  public void changeFeeAmount(double amount) throws SQLException {
    Connection connection = ControllerManager.getConnection();

    String mostRecentFee =
      "SELECT * " +
      "FROM POSTING_FEE p1 " +
      "WHERE p1.Date_updated = (SELECT MAX(Date_updated) FROM POSTING_FEE p2);";

    Statement statment = connection.createStatement();
    ResultSet result = statment.executeQuery(mostRecentFee);
    result.next();
    int previousDuration = result.getInt("duration");

    String amountUpdate =
      "INSERT INTO POSTING_FEE(ID, Amount, Duration, Date_updated) " +
      "VALUES(DEFAULT, ?, ?, NOW());";

    PreparedStatement pStatment = connection.prepareStatement(amountUpdate);

    BigDecimal decimalAmount = new BigDecimal(Double.toString(amount));

    pStatment.setBigDecimal(1, decimalAmount);
    pStatment.setInt(2, previousDuration);

    pStatment.executeUpdate();
  }

  /**
   * Changes the duration of fee to post a property in the database
   * @param amount double int representing the new duration
   * @throws SQLException
   */
  public void changeFeeDuration(int durationMonth) throws SQLException {
    Connection connection = ControllerManager.getConnection();

    String mostRecentFee =
      "SELECT * " +
      "FROM POSTING_FEE p1 " +
      "WHERE p1.Date_updated = (SELECT MAX(Date_updated) FROM POSTING_FEE p2);";

    Statement statment = connection.createStatement();
    ResultSet result = statment.executeQuery(mostRecentFee);
    result.next();
    int previousAmount = result.getInt("amount");

    String amountUpdate =
      "INSERT INTO POSTING_FEE(ID, Amount, Duration, Date_updated) " +
      "VALUES(DEFAULT, ?, ?, NOW());";

    PreparedStatement pStatment = connection.prepareStatement(amountUpdate);

    pStatment.setInt(1, previousAmount);
    pStatment.setInt(2, durationMonth);

    pStatment.executeUpdate();
  }

  /**
   * Gets number of listings registered in a specific period
   * @param from String representing the start date in format yyyy-MM-dd
   * @param to String representing the end date in format yyyy-MM-dd
   * @return An int representing the number of listed properties
   * @throws SQLException
   */
  public int numListingsInPeriod(String from, String to) throws SQLException {
    Connection connection = ControllerManager.getConnection();
    String listedQuery =
      "SELECT COUNT(DISTINCT p.Property_id) " +
      "FROM PROPERTY_STATE p " +
      "Where p.Property_id IN ( " +
      "SELECT p1.Property_id " +
      "FROM PROPERTY_STATE p1 " +
      "WHERE p1.State_date >= ?::timestamp AND p1.State_date <= ?::timestamp AND (p1.State = ? OR p1.state= ?) " +
      "EXCEPT " +
      "SELECT p2.Property_id " + // remove cases where property was suspended/cancelled and got re-activated
      "FROM PROPERTY_STATE p2 " +
      "WHERE p2.State_date < ?::timestamp AND (p2.State = ? OR p2.State = ?) " +
      ");";

    PreparedStatement pStatment = connection.prepareStatement(listedQuery);

    pStatment.setString(1, from);
    pStatment.setString(2, to);
    pStatment.setInt(3, ListingState.ACTIVE.ordinal());
    pStatment.setInt(4, ListingState.REGISTERED.ordinal());
    pStatment.setString(5, from);
    pStatment.setInt(6, ListingState.ACTIVE.ordinal());
    pStatment.setInt(7, ListingState.REGISTERED.ordinal());

    ResultSet result = pStatment.executeQuery();
    result.next();
    return result.getInt("count");
  }

  /**
   * Gets number of rented properties in a specific period
   * @param from String representing the start date in format yyyy-MM-dd
   * @param to String representing the end date in format yyyy-MM-dd
   * @return An int representing the number of rented properties
   * @throws SQLException
   */
  public int numRentedInPeriod(String from, String to) throws SQLException {
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

  /**
   * Gets total number of currently active properties
   * @return An int representing the number of active properties
   * @throws SQLException
   */
  public int totalNumActive() throws SQLException {
    Connection connection = ControllerManager.getConnection();
    String activeQuery =
      "SELECT COUNT(DISTINCT p.ID) " +
      "FROM PROPERTY p " +
      "WHERE Current_state = ?;";

    PreparedStatement pStatment = connection.prepareStatement(activeQuery);
    pStatment.setInt(1, ListingState.ACTIVE.ordinal());

    ResultSet result = pStatment.executeQuery();
    result.next();
    return result.getInt("count");
  }

  /**
   * Gets rented properties in a specific period
   * @param from String representing the start date in format yyyy-MM-dd
   * @param to String representing the end date in format yyyy-MM-dd
   * @return An ArrayList containing the rented properties
   * @throws SQLException
   */
  public ArrayList<Property> getRentedInPeriod(String from, String to)
    throws SQLException {
    ArrayList<Property> rentedProperties = new ArrayList<Property>();
    Connection connection = ControllerManager.getConnection();
    String listedQuery =
      "SELECT DISTINCT ON (p.ID) * " +
      "FROM PROPERTY_STATE ps, PROPERTY p, PERSON psn " +
      "Where ps.Property_id IN ( " +
      "SELECT DISTINCT ps1.Property_id " +
      "FROM PROPERTY_STATE ps1 " +
      "WHERE ps1.State_date >= ?::timestamp AND ps1.State_date <= ?::timestamp AND ps1.State = ?" +
      ") AND ps.property_id = p.ID AND psn.Email = p.Landlord_email;";

    PreparedStatement pStatment = connection.prepareStatement(listedQuery);

    pStatment.setString(1, from);
    pStatment.setString(2, to);
    pStatment.setInt(3, ListingState.RENTED.ordinal());

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

      property.setPostedByName(result.getString("name"));

      rentedProperties.add(property);
    }

    return rentedProperties;
  }
}

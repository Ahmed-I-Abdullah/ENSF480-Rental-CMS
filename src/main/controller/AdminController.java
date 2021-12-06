package src.main.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import src.main.model.property.*;
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
    System.out.println("total active listing is: " + totalNumActive);
  }

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
    System.out.println("received amount is: " + amount);

    BigDecimal decimalAmount = new BigDecimal(Double.toString(amount));
    System.out.println("big decimal is: " + decimalAmount);

    pStatment.setBigDecimal(1, decimalAmount);
    pStatment.setInt(2, previousDuration);

    pStatment.executeUpdate();
  }

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

  public ArrayList<Property> getRentedInPeriod(String from, String to) throws SQLException {
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
      System.out.println("HHHHHHHHHHHHHHHH");
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
      System.out.println(property.getHouseID());
    }

    return rentedProperties;
  }
}

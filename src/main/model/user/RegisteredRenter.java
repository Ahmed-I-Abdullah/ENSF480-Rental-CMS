package src.main.model.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import src.main.controller.ControllerManager;
import src.main.controller.UserNotFoundException;
import src.main.model.property.*;
import src.main.model.property.ListingDetails;
import src.main.model.property.Property;

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

  public void setSearchCriteria(ListingDetails criteria)
    throws SQLException, UserNotFoundException {
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
    if (!criteria.isFurnished()) {
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

  public void setViewedNotificationTime() throws SQLException {
    Connection connection = ControllerManager.getConnection();

    String notificationsUpdate =
      "INSERT INTO RENTER_NOTIFICATIONS(Renter_email, Last_viewed) VALUES(? , DEFAULT);";

    PreparedStatement pStatment = connection.prepareStatement(
      notificationsUpdate
    );

    pStatment.setString(1, email);

    pStatment.executeUpdate();
  }

  public ArrayList<Property> getNotifications() {
    ArrayList<Property> notificationsProperties = new ArrayList<Property>();
    try {
      Connection connection = ControllerManager.getConnection();
      String notificationsQuery =
        "SELECT DISTINCT ON (p.ID) * " +
        "FROM PROPERTY p, PROPERTY_STATE ps, RENTER r, RENTER_NOTIFICATIONS rn " +
        "WHERE rn.Renter_email = ? AND " +
        "rn.Last_viewed = (SELECT MAX(rn2.Last_viewed) FROM RENTER_NOTIFICATIONS rn2 WHERE rn2.Renter_email = ?) " +
        "AND r.Email = rn.Renter_email AND p.Current_state = ? AND p.City_quadrant = r.City_quadrant " +
        "AND p.Is_furnished = r.Is_furnished AND p.No_bathrooms = r.No_bathrooms " +
        "AND p.No_bedrooms = r.No_bedrooms AND p.ID IN ( " +
        "SELECT DISTINCT ps.Property_id " +
        "FROM PROPERTY_STATE ps " +
        "WHERE ps.State_date >= rn.Last_viewed AND ps.state = ? " +
        ");";

      PreparedStatement pStatment = connection.prepareStatement(
        notificationsQuery
      );
      pStatment.setString(1, email);
      pStatment.setString(2, email);
      pStatment.setInt(3, ListingState.ACTIVE.ordinal());
      pStatment.setInt(4, ListingState.ACTIVE.ordinal());

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

        notificationsProperties.add(property);
        System.out.println(property.getHouseID());
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
    return notificationsProperties;
  }

  public void update(Property property) {}

  public UserType getUserType() {
    return UserType.RENTER;
  }
}

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

  /**
   * Default constructor
   */
  public PostingController() {}

  /**
   * marks property fee as paid in remote database
   * @param p Property for which the fee will be paid
   * @throws SQLException
   */
  public void payFee(Property p) throws SQLException {
    p.updateState(ListingState.ACTIVE);
    String feeID = getFeeID();

    Connection connection = ControllerManager.getConnection();
    String updatePropertyFee =
      "INSERT INTO PAID_BY(Landlord_email, Fee_id, Property_id, Start_date, Num_periods) " +
      "VALUES(?, ?::uuid, ?::uuid, DEFAULT, ?)";

    PreparedStatement pStatement = connection.prepareStatement(
      updatePropertyFee
    );
    pStatement.setString(1, p.getPostedBy());
    pStatement.setString(2, feeID);
    pStatement.setString(3, p.getHouseID());
    pStatement.setInt(4, 1);

    pStatement.executeUpdate();
  }

  /**
   * adds a property to the remote database
   * @param u User who posted the property
   * @param address Address representing the property address
   * @param specifications ListingDetails representing specifications of the property
   * @param postedBy String containing email of poster
   * @param description String containing a description of the property
   * @return Property, the newly added property
   */
  public Property addPropertyToDatabase(
    User u,
    Address address,
    ListingDetails specifications,
    String postedBy,
    String description
  ) {
    Landlord landlord = (Landlord) u;
    return landlord.createProperty(
      "",
      address,
      specifications,
      postedBy,
      description
    );
  }

  /**
   * gets a property's state from remote database
   * @param id String representing the property's ID
   * @return ListingState representing the property's state
   */
  public ListingState getListingState(String id) {
    try {
      Connection connection = ControllerManager.getConnection();

      String stateOfListing =
        "SELECT current_state " +
        "FROM PROPERTY p " +
        "WHERE p.id = '" +
        id +
        "';";

      Statement statment = connection.createStatement();
      ResultSet result = statment.executeQuery(stateOfListing);
      result.next();
      Integer state = (Integer) result.getObject("current_state");
      ListingState listingState = ListingState.values()[state];
      return listingState;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Updates a property's state in remote database
   * @param id String representing the property's ID
   * @param new_state ordinal of new state from thr LisitngState enum
   * @return ListingState containing the property's new specifications
   */
  public ListingState changeListingState(String id, Integer new_state) {
    try {
      Connection connection = ControllerManager.getConnection();

      String stateOfListing =
        "UPDATE property " + "SET current_state = ? " + "WHERE ID = ?::uuid;";

      PreparedStatement pStatement = connection.prepareStatement(
        stateOfListing
      );
      pStatement.setInt(1, new_state);
      pStatement.setString(2, id);
      pStatement.executeUpdate();
      ListingState listingState = ListingState.values()[new_state];
      return listingState;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Updates a property's quadrant in remote database
   * @param id String representing the property's ID
   * @param new_quadrant String representing the new quadrant
   * @return String containing the new quadrant
   */
  public String changeListingQuadrant(String id, String new_quadrant) {
    try {
      Connection connection = ControllerManager.getConnection();

      String quadrantOfListing =
        "UPDATE property " + "SET City_quadrant= ? " + "WHERE ID = ?::uuid;";

      PreparedStatement pStatement = connection.prepareStatement(
        quadrantOfListing
      );
      pStatement.setString(1, new_quadrant);
      pStatement.setString(2, id);
      pStatement.executeUpdate();
      String listingQuadrant = new_quadrant;
      return listingQuadrant;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Updates a property's number of bedrooms in remote database
   * @param id String representing the property's ID
   * @param new_bedrooms int representing the new number of bedrooms
   * @return int representing the new number of bedrooms
   */
  public int changeListingBedrooms(String id, int new_bedrooms) {
    try {
      Connection connection = ControllerManager.getConnection();

      String bedroomsOfListing =
        "UPDATE property " + "SET No_bedrooms= ? " + "WHERE ID = ?::uuid;";

      PreparedStatement pStatement = connection.prepareStatement(
        bedroomsOfListing
      );
      pStatement.setInt(1, new_bedrooms);
      pStatement.setString(2, id);
      pStatement.executeUpdate();
      int listingBedrooms = new_bedrooms;
      return listingBedrooms;
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }
  }

  /**
   * Updates a property's number of bathrooms in remote database
   * @param id String representing the property's ID
   * @param new_bedrooms int representing the new number of bathrooms
   * @return int representing the new number of bathrooms
   */
  public int changeListingBathrooms(String id, int new_bathrooms) {
    try {
      Connection connection = ControllerManager.getConnection();

      String bathroomsOfListing =
        "UPDATE property " + "SET No_bathrooms = ? " + "WHERE ID = ?::uuid;";

      PreparedStatement pStatement = connection.prepareStatement(
        bathroomsOfListing
      );
      pStatement.setInt(1, new_bathrooms);
      pStatement.setString(2, id);
      pStatement.executeUpdate();
      int listingBathrooms = new_bathrooms;
      return listingBathrooms;
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }
  }

  /**
   * Updates a property's type in remote database
   * @param id String representing the property's ID
   * @param new_type String representing the new type
   * @return String representing the new type
   */
  public String changeListingType(String id, String new_type) {
    try {
      Connection connection = ControllerManager.getConnection();

      String typeOfListing =
        "UPDATE property " + "SET Property_type= ?" + "WHERE ID = ?::uuid;";

      PreparedStatement pStatement = connection.prepareStatement(typeOfListing);
      pStatement.setString(1, new_type);
      pStatement.setString(2, id);
      pStatement.executeUpdate();
      String listingtype = new_type;
      return listingtype;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Updates a property's furnishing in remote database
   * @param id String representing the property's ID
   * @param new_furnishing Boolean representing the new furnishing state
   * @return Boolean representing the new furnishing state
   */
  public Boolean changeListingFurnishing(String id, Boolean new_furnishing) {
    try {
      Connection connection = ControllerManager.getConnection();
      String furnished = "1";
      if (!new_furnishing) {
        furnished = "0";
      }

      String furnishingOfListing =
        "UPDATE property " +
        "SET Is_furnished = ?::bit " +
        "WHERE ID = ?::uuid;";

      PreparedStatement pStatement = connection.prepareStatement(
        furnishingOfListing
      );
      pStatement.setString(1, furnished);
      pStatement.setString(2, id);
      pStatement.executeUpdate();
      Boolean listingFurnishing = new_furnishing;
      return listingFurnishing;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Gets most recent fee amount from database
   * @return Double representing the fee amount
   */
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
      BigDecimal decimalValue = (BigDecimal) result.getObject("amount");
      double doubleAmount = decimalValue.doubleValue();
      return doubleAmount;
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }
  }

  /**
   * Gets most recent fee duration from database
   * @return int representing the fee duration in month
   */
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

  /**
   * Gets most recent fee ID from database
   * @return String representing the fee ID
   */
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

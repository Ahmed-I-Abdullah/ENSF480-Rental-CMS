package src.main.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import src.main.model.property.Address;
import src.main.model.property.ListingDetails;
import src.main.model.property.ListingState;
import src.main.model.property.Property;
import src.main.model.user.Landlord;
import src.main.model.user.RegisteredRenter;
import src.main.model.user.UserType;

public class ViewController {

  private ArrayList<Property> allProperties;
  private ArrayList<Property> filteredProperties;
  private ArrayList<Property> renterNotifications;
  private boolean useFilter;
  private boolean useNotifications;
  private Property currentProperty;
  private PostingController postingController;
  private UserController userController;

  /**
   * Default constructor
   */
  public ViewController() {
    postingController = new PostingController();
    allProperties = new ArrayList<Property>();
    filteredProperties = new ArrayList<Property>();
    renterNotifications = new ArrayList<Property>();
  }

  /**
   * return properties from the database according to user type
   * @return ArrayList of property objects
   */
  public ArrayList<Property> getAllProperties() {
    if (
      userController.getAuthenticatedUser() == null ||
      userController.getAuthenticatedUser().getUserType() == UserType.RENTER
    ) {
      try {
        ArrayList<Property> localProperties = new ArrayList<Property>();
        Connection connection = ControllerManager.getConnection();
        String propertyQuery = "SELECT * FROM PROPERTY WHERE Current_state=?";

        PreparedStatement pStatment = connection.prepareStatement(
          propertyQuery
        );
        pStatment.setInt(1, ListingState.ACTIVE.ordinal());
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
          if (!postingController.propertyPayValid(property.getHouseID())) {
            try {
              property.updateState(ListingState.REGISTERED);
            } catch (Exception e) {
              e.printStackTrace();
            }
          } else {
            localProperties.add(property);
          }
        }
        this.allProperties = localProperties;
      } catch (Exception e) {
        e.printStackTrace();
        System.exit(-1);
      }
    } else if (
      userController.getAuthenticatedUser().getUserType() == UserType.MANAGER
    ) {
      try {
        ArrayList<Property> localProperties = new ArrayList<Property>();
        Connection connection = ControllerManager.getConnection();
        String propertyQuery = "SELECT * FROM PROPERTY";

        PreparedStatement pStatment = connection.prepareStatement(
          propertyQuery
        );
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

          localProperties.add(property);
        }
        this.allProperties = localProperties;
      } catch (Exception e) {
        e.printStackTrace();
        System.exit(-1);
      }
    } else if (
      userController.getAuthenticatedUser().getUserType() == UserType.LANDLORD
    ) {
      Landlord l = (Landlord) userController.getAuthenticatedUser();
      this.allProperties = l.getLandlordProperties();
    }
    useFilter = false;
    useNotifications = false;
    return this.allProperties;
  }

  /**
   * return properties from the database according to user type and specified filters
   * @param type String representing the property's type
   * @param quadrant String representing the property's city quadrant
   * @param isFurnished Boolean representing if the property is furnished
   * @param numBedrooms int representing the property's number of bedrooms
   * @param numBathrooms int representing the property's number of bathrooms
   * @return ArrayList of property objects
   */
  public ArrayList<Property> getFilteredProperties(
    String type,
    String quadrant,
    boolean isFurnished,
    int numBedrooms,
    int numBathrooms
  ) {
    String furnished = "1";
    if (!isFurnished) {
      furnished = "0";
    }
    if (
      userController.getAuthenticatedUser() == null ||
      userController.getAuthenticatedUser().getUserType() == UserType.RENTER
    ) {
      try {
        ArrayList<Property> localProperties = new ArrayList<Property>();
        Connection connection = ControllerManager.getConnection();
        String propertyQuery =
          "SELECT * FROM PROPERTY WHERE Current_state = ? AND Property_type ILIKE ? " +
          "AND City_quadrant = ? AND Is_furnished = ?::bit AND No_bedrooms = ? AND No_bathrooms = ?;";

        PreparedStatement pStatment = connection.prepareStatement(
          propertyQuery
        );
        pStatment.setInt(1, ListingState.ACTIVE.ordinal());
        pStatment.setString(2, type);
        pStatment.setString(3, quadrant);
        pStatment.setString(4, furnished);
        pStatment.setInt(5, numBedrooms);
        pStatment.setInt(6, numBathrooms);

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

          localProperties.add(property);
        }
        this.filteredProperties = localProperties;
      } catch (Exception e) {
        e.printStackTrace();
        System.exit(-1);
      }
    } else if (
      userController.getAuthenticatedUser().getUserType() == UserType.MANAGER
    ) {
      try {
        ArrayList<Property> localProperties = new ArrayList<Property>();
        Connection connection = ControllerManager.getConnection();
        String propertyQuery =
          "SELECT * FROM PROPERTY WHERE Property_type ILIKE ? " +
          "AND City_quadrant = ? AND Is_furnished = ?::bit AND No_bedrooms = ? AND No_bathrooms = ?;";

        PreparedStatement pStatment = connection.prepareStatement(
          propertyQuery
        );

        pStatment.setString(1, type);
        pStatment.setString(2, quadrant);
        pStatment.setString(3, furnished);
        pStatment.setInt(4, numBedrooms);
        pStatment.setInt(5, numBathrooms);
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

          localProperties.add(property);
        }
        this.filteredProperties = localProperties;
      } catch (Exception e) {
        e.printStackTrace();
        System.exit(-1);
      }
    } else if (
      userController.getAuthenticatedUser().getUserType() == UserType.LANDLORD
    ) {
      try {
        ArrayList<Property> localProperties = new ArrayList<Property>();
        Connection connection = ControllerManager.getConnection();
        String propertyQuery =
          "SELECT * FROM PROPERTY WHERE Landlord_email = ? AND Property_type ILIKE ? " +
          "AND City_quadrant = ? AND Is_furnished = ?::bit AND No_bedrooms = ? AND No_bathrooms = ?;";

        PreparedStatement pStatment = connection.prepareStatement(
          propertyQuery
        );

        pStatment.setString(
          1,
          userController.getAuthenticatedUser().getEmail()
        );
        pStatment.setString(2, type);
        pStatment.setString(3, quadrant);
        pStatment.setString(4, furnished);
        pStatment.setInt(5, numBedrooms);
        pStatment.setInt(6, numBathrooms);
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

          localProperties.add(property);
        }
        this.filteredProperties = localProperties;
      } catch (Exception e) {
        e.printStackTrace();
        System.exit(-1);
      }
    }
    useFilter = true;
    useNotifications = false;
    return this.filteredProperties;
  }

  /**
   * indicates if notifications page is being used
   * @return Boolean representing notifications state
   */
  public boolean getUseNotification() {
    return this.useNotifications;
  }

  /**
   * gets renter notifications
   * @param r RegisteredRenter to get the notifications for
   * @return ArrayList of Property objects
   */
  public ArrayList<Property> getRenterNotifications(RegisteredRenter r) {
    if (renterNotifications.size() == 0) {
      this.renterNotifications = r.getNotifications();
    }
    useNotifications = true;
    useFilter = false;

    return this.renterNotifications;
  }

  /**
   * sets UserContoller
   * @param controller UserController to be set
   */
  public void setUserController(UserController controller) {
    this.userController = controller;
  }

  /**
   * sets property to be seen in singlelisting page
   * @param i index of property in ArrayList of properties
   */
  public void setCurrentProperty(int i) {
    if (useFilter) {
      this.currentProperty = this.filteredProperties.get(i);
    } else if (useNotifications) {
      this.currentProperty = this.renterNotifications.get(i);
    } else {
      this.currentProperty = this.allProperties.get(i);
    }
  }

  /**
   * return property to be seen in singlelisting page
   * @return Property to be displayed
   */
  public Property getCurrentProperty() {
    return this.currentProperty;
  }

  /**
   * return userController
   * @return UserController of the current logged in user
   */
  public UserController getUserController() {
    return this.userController;
  }

  /**
   * return postingController
   * @return postingControllers
   */
  public PostingController getPostingController() {
    return this.postingController;
  }
}

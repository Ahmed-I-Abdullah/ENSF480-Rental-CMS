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
import src.main.model.user.UserType;

public class ViewController {

  private ArrayList<Property> allProperties;
  private ArrayList<Property> filteredProperties;
  private boolean useFilter;
  private Property currentProperty;
  private PostingController postingController;
  private UserController userController;

  public ViewController() {
    postingController = new PostingController();
    allProperties = new ArrayList<Property>();
    filteredProperties = new ArrayList<Property>();
  }

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

          localProperties.add(property);
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
    return this.allProperties;
  }

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
    return this.filteredProperties;
  }

  public void setUserController(UserController controller) {
    this.userController = controller;
  }

  public void setCurrentProperty(int i) {
    if (useFilter) {
      this.currentProperty = this.filteredProperties.get(i);
    } else {
      this.currentProperty = this.allProperties.get(i);
    }
  }

  public Property getCurrentProperty() {
    return this.currentProperty;
  }

  public UserController getUserController() {
    return this.userController;
  }

  public PostingController getPostingController() {
    return this.postingController;
  }
}

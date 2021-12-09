package src.main.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import src.main.model.property.ApplicationEmail;
import src.main.model.property.ListingDetails;
import src.main.model.property.ListingState;
import src.main.model.property.Property;
import src.main.model.user.Landlord;
import src.main.model.user.Manager;
import src.main.model.user.RegisteredRenter;
import src.main.model.user.User;
import src.main.model.user.UserType;

public class UserController {

  private User authenticatedUser;

  /**
   * Default constructor
   */
  public UserController() {}

  /**
   * Constructor
   * @param u User current logged in user
   */
  public UserController(User u) {
    this.authenticatedUser = u;
  }

  /**
   * gets authenticated user
   * @return User current logged in user
   */

  public User getAuthenticatedUser() {
    return this.authenticatedUser;
  }

  /**
   * sets authenticated user
   * @param u User to be set as authenticated
   */
  public void setAuthenticatedUser(User u) {
    this.authenticatedUser = u;
  }

  /**
   * registers a user in the database
   * @param email String representing the user email
   * @param name String representing the user's name
   * @param password String containing the user's password
   * @param userType UserType representing the type of user
   * @throws SQLException when databse driver throws an error
   * @throws NoSuchAlgorithmException when password hashing fails
   */
  public void signUp(
    String email,
    String name,
    String password,
    UserType userType
  )
    throws SQLException, NoSuchAlgorithmException {
    String hashedPassword = hashPassword(password);
    Connection connection = ControllerManager.getConnection();
    String insertPersonUpdate =
      "INSERT INTO PERSON (Email, Name, Hashed_password, Role) " +
      "VALUES(?, ?, ?, ?);";
    PreparedStatement pStatment = connection.prepareStatement(
      insertPersonUpdate
    );

    pStatment.setString(1, email);
    pStatment.setString(2, name);
    pStatment.setString(3, hashedPassword);
    pStatment.setInt(4, userType.ordinal());

    pStatment.executeUpdate();

    if (userType == userType.RENTER) {
      String insertRenterUpdate = "INSERT INTO RENTER (Email) " + "VALUES(?);";
      PreparedStatement pStatmentTwo = connection.prepareStatement(
        insertRenterUpdate
      );

      pStatmentTwo.setString(1, email);

      pStatmentTwo.executeUpdate();
    }

    if (userType == UserType.MANAGER) {
      authenticatedUser = new Manager(email, name);
      return;
    }

    if (userType == UserType.LANDLORD) {
      authenticatedUser = new Landlord(email, name);
      return;
    }

    if (userType == UserType.RENTER) {
      authenticatedUser = new RegisteredRenter(email, name);
      return;
    }
  }

  /**
   * checks user credentials and login user
   * @param email String representing the user email
   * @param password String containing the user's password
   * @throws SQLException when databse driver throws an error
   * @throws NoSuchAlgorithmException when password hashing fails
   * @throws UserNotFoundException when user is not found in the database
   * @throws UnAuthorizedException when user exists but password is incorrect
   */
  public void logIn(String email, String password)
    throws UserNotFoundException, UnAuthorizedException, SQLException, NoSuchAlgorithmException {
    Connection connection = ControllerManager.getConnection();
    String userQuery = "SELECT * FROM PERSON p WHERE p.Email = ?";

    PreparedStatement pStatment = connection.prepareStatement(userQuery);
    pStatment.setString(1, email);

    ResultSet result = pStatment.executeQuery();

    if (!result.isBeforeFirst()) {
      throw new UserNotFoundException("User not found in databse.");
    }

    result.next();
    String databasePassword = result.getString("hashed_password");
    String hashedUserPassword = hashPassword(password);
    if (!databasePassword.equals(hashedUserPassword)) {
      throw new UnAuthorizedException("Wrong Password.");
    }

    String name = result.getString("name");
    int integerRole = result.getInt("role");
    UserType role = UserType.values()[integerRole];

    if (role == UserType.MANAGER) {
      authenticatedUser = new Manager(email, name);
      return;
    }

    if (role == UserType.LANDLORD) {
      authenticatedUser = new Landlord(email, name);
      return;
    }

    String renterQuery = "SELECT * FROM RENTER r WHERE r.Email = ?";
    pStatment = connection.prepareStatement(renterQuery);

    pStatment.setString(1, email);

    ResultSet renterResult = pStatment.executeQuery();

    if (!renterResult.isBeforeFirst()) {
      throw new UserNotFoundException("Renter not found in databse.");
    }

    renterResult.next();

    String propertyFurnished = renterResult.getString("is_furnished");

    ListingDetails userSearchCriteria = new ListingDetails(
      ListingState.ACTIVE,
      renterResult.getInt("no_bedrooms"),
      renterResult.getInt("no_bathrooms"),
      renterResult.getString("property_type"),
      "1".equals(propertyFurnished),
      renterResult.getString("city_quadrant")
    );

    authenticatedUser = new RegisteredRenter(email, name, userSearchCriteria);
  }

  /**
   * saves the seacrh criteria of the renter in the database
   * @param numOfBedrooms int representing the number of bedrooms
   * @param numOfBathrooms int representing the number of bathrooms
   * @param housingType String representing the house type
   * @param furnished boolean representing if property should be furnished
   * @param cityQuadrant String representing preferred city quadrant
   */
  public void setRenterSearchCriteria(
    int numOfBedrooms,
    int numofBathrooms,
    String housingType,
    boolean furnished,
    String cityQuadrant
  ) {
    RegisteredRenter renter = (RegisteredRenter) authenticatedUser;

    ListingDetails renterPrefrence = new ListingDetails(
      ListingState.ACTIVE,
      numOfBedrooms,
      numofBathrooms,
      housingType,
      furnished,
      cityQuadrant
    );

    try {
      renter.setSearchCriteria(renterPrefrence);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * hashes and salts the user password for secure storage in the database
   * @param password String containing the user's password
   * @retrun String containing the hashed password
   * @throws NoSuchAlgorithmException if hashing algorithm was not found
   */
  private String hashPassword(String password) throws NoSuchAlgorithmException {
    StringBuilder hashedPassword = new StringBuilder();
    String salt = "!15wRP/8N:F8*uu5A>?4"; // should probably be in environment variables
    MessageDigest md = MessageDigest.getInstance("SHA-512");
    md.update(salt.getBytes(StandardCharsets.UTF_8));
    byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
    for (int i = 0; i < bytes.length; i++) {
      hashedPassword.append(
        Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1)
      );
    }
    return hashedPassword.toString();
  }

  /**
   * sends and email to the property owner
   * @param p Property being inquired about
   * @param String containing the email content
   */
  public void emailLandlord(Property p, String body) {
    String subject =
      "Renter " +
      this.authenticatedUser.getName() +
      "is interested in " +
      p.getHouseID();
    ApplicationEmail e = new ApplicationEmail(p, subject, body);
    e.sendMessage();
  }

  /**
   * searches for a user in the database
   * @param email String containing the user's email
   * @return String[] containing the user's name, email and role
   */
  public String[] findUser(String email) {
    try {
      Connection connection = ControllerManager.getConnection();
      String userQuery = "SELECT * FROM PERSON p WHERE p.Email = ?";
      PreparedStatement pStatment = connection.prepareStatement(userQuery);
      pStatment.setString(1, email);
      ResultSet res = pStatment.executeQuery();
      if (!res.isBeforeFirst()) {
        return null;
      }
      res.next();
      String name = res.getString("name");
      UserType type = UserType.values()[res.getInt("role")];
      String role = "";
      if (type == UserType.RENTER) {
        role = "Renter";
      } else if (type == UserType.MANAGER) {
        role = "Manager";
      } else if (type == UserType.LANDLORD) {
        role = "Landlord";
      }

      String[] result = { name, email, role };
      return result;
    } catch (Exception e) {
      System.exit(1);
    }
    return null;
  }
}

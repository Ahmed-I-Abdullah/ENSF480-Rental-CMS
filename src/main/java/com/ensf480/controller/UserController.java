package com.ensf480.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.ensf480.model.user.User;
import com.ensf480.model.user.UserType;

public class UserController {

  private User user;

  public UserController(User u) {
    this.user = u;
  }

  public void setUser(User u) {
    this.user = u;
  }

  public User getUser() {
    return user;
  }

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
  }

  public void logIn(String email, String password)
    throws UserNotFoundException, UnAuthorizedException, SQLException, NoSuchAlgorithmException {
    Connection connection = ControllerManager.getConnection();
    String userQuery =
      "SELECT Email, Hashed_password " + "FROM PERSON p " + "WHERE p.Email = ?";

    PreparedStatement pStatment = connection.prepareStatement(userQuery);
    pStatment.setString(1, email);

    ResultSet result = pStatment.executeQuery();

    if (!result.isBeforeFirst()) {
      throw new UserNotFoundException("user not found in databse.");
    }

    result.next();
    String databasePassword = result.getString("hashed_password");
    String hashedUserPassword = hashPassword(password);
    if (!databasePassword.equals(hashedUserPassword)) {
      throw new UnAuthorizedException("Wrong Password.");
    }
  }

  public String hashPassword(String password) throws NoSuchAlgorithmException {
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
    System.out.println("Hashed password is: " + hashedPassword.toString());
    return hashedPassword.toString();
  }
}

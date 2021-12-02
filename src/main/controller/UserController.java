package src.main.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import src.main.model.user.User;

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

//   public boolean signUp(
//     String email,
//     String name,
//     String password,
//     UserType userType
//   ) {
//     String hashedPassword = hashPassword(password);
//     Connection connection = ControllerManager.getConnection();
//     String insertPersonUpdate = 
//     PreparedStatement pStatment = connection.prepareStatement(listedQuery);
//   }

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

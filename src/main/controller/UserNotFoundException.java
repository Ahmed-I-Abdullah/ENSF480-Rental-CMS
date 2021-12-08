package src.main.controller;

public class UserNotFoundException extends Exception {

  /**
   * Exception constructor
   * @param message String representing error message
   */
  public UserNotFoundException(String message) {
    super(message);
  }
}

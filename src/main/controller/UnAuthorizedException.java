package src.main.controller;

public class UnAuthorizedException extends Exception {

  /**
   * Exception constructor
   * @param message String representing error message
   */
  public UnAuthorizedException(String message) {
    super(message);
  }
}

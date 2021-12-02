package src.main.controller;

public class UnAuthorizedException extends Exception {
	public UnAuthorizedException(String message){
		super(message);
  }
}
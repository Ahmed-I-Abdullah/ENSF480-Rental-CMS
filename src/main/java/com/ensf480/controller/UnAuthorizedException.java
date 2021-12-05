package com.ensf480.controller;

public class UnAuthorizedException extends Exception {
	public UnAuthorizedException(String message){
		super(message);
  }
}
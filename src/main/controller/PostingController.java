package src.main.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import src.main.model.property.*;
import src.main.model.user.Landlord;
import src.main.model.user.User;

public class PostingController {

    public PostingController() {}

  public void payFee() {}

  public void addPropertyToDatabase(
    User u,
    Address address,
    ListingDetails specifications,
    String postedBy,
    String description
  ) {
    Landlord landlord = (Landlord) u;
    landlord.createProperty("", address, specifications, postedBy, description);
  }

  public ListingState getListingState(String id) {
    try {
      Connection connection = ControllerManager.getConnection();

      String stateOfListing =
        "SELECT current_state " +
        "FROM PROPERTY p " +
        "WHERE p.id = '" + id + "';" ;

      Statement statment = connection.createStatement();
      ResultSet result = statment.executeQuery(stateOfListing);
      result.next();
      Integer state = (Integer)result.getObject("current_state");
      ListingState listingState = ListingState.values()[state];
      return listingState;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public ListingState changeListingState(String id, Integer new_state) {
    try {
      Connection connection = ControllerManager.getConnection();

      String stateOfListing =
        "UPDATE property " +
        "SET current_state = ?" +
        "WHERE id = '" + id + "';" ;

      PreparedStatement pStatement = connection.prepareStatement(stateOfListing);
      pStatement.setInt(1,new_state);
      int i = pStatement.executeUpdate();
      ListingState listingState = ListingState.values()[new_state];
      return listingState;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

}

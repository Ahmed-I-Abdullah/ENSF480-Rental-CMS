package src.main.controller;

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
}

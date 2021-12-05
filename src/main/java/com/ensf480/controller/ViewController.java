package com.ensf480.controller;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.ensf480.model.property.Address;
import com.ensf480.model.property.ListingDetails;
import com.ensf480.model.property.ListingState;
import com.ensf480.model.property.Property;

public class ViewController {

  private ArrayList<Property> allProperties;
  private PostingController postingController;
  private UserController userController;

  public ViewController() {
      allProperties = new ArrayList<Property>();
    try {
      Connection connection = ControllerManager.getConnection();
      String propertyQuery = "SELECT * FROM PROPERTY WHERE Current_state=?";

      PreparedStatement pStatment = connection.prepareStatement(propertyQuery);
      pStatment.setInt(1, ListingState.ACTIVE.ordinal());

      ResultSet result = pStatment.executeQuery();

      while (result.next()) {
        Address propertyAddress = new Address(
          "",
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
          "",
          false
        );

        allProperties.add(property);
        System.out.println(property.getHouseID());
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }

  public ArrayList<Property> getAllProperties() {
      return this.allProperties;
  }
}

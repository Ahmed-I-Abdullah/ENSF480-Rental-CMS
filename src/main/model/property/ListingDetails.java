package src.main.model.property;

public class ListingDetails {

  private ListingState state;
  private int numOfBedrooms;
  private int numOfBathrooms;
  private String housingType;
  private boolean furnished;
  private String cityQuadrant;

  //PROMISES: creates ListingDetails object
  //REQUIRES: the state, number of bedfrooms and bathrooms, type of house and city quadrant of the property
  public ListingDetails(
    ListingState state,
    int numOfBedrooms,
    int numBathrooms,
    String housingType,
    boolean furnished,
    String cityQuadrant
  ) {
    this.state = state;
    this.numOfBedrooms = numOfBedrooms;
    this.housingType = housingType;
    this.furnished = furnished;
    this.cityQuadrant = cityQuadrant;
    this.numOfBathrooms = numBathrooms;
  }

  //PROMISES: returns the state of the property (registered, active, cancelled etc..)
  public ListingState getState() {
    return this.state;
  }

  //PROMISES: sets the state of a property
  //REQUIRES: the listing state of the property
  public void setState(ListingState state) {
    this.state = state;
  }

  //PROMISES: returns the number of bedrooms in the property
  public int getNumOfBedrooms() {
    return this.numOfBedrooms;
  }

  //PROMISES; return the number of bathrooms in the property
  public int getNumOfBathrooms() {
    return this.numOfBathrooms;
  }

  //PROMISES: sets the number of bedrooms in the property
  //REQUIRES: the number of bedrooms
  public void setNumOfBedrooms(int numOfBedrooms) {
    this.numOfBedrooms = numOfBedrooms;
  }

  //PROMISES: sets the number of bathrooms in the property
  //REQUIRES: the number of bathrooms
  public void setNumOfBathrooms(int numOfBathrooms) {
    this.numOfBathrooms = numOfBathrooms;
  }

  //PROMISES: gets the type of house for the property (apartment, condo, townhouse etc..)
  public String getHousingType() {
    return this.housingType;
  }

  //PROMSIES: sets the type of house for the proerty
  //REQUIRES: the type of the house for the property
  public void setHousingType(String housingType) {
    this.housingType = housingType;
  }

  //PROMSIES: returns true if the house is furnished, false if the house does not come furnished
  public boolean getFurnished() {
    return this.furnished;
  }

  //PROMISES: sets the property to true or false if furnished or not
  //REQUIRES: if the house is furnished or not
  public void setFurnished(boolean furnished) {
    this.furnished = furnished;
  }

  //PROMISES: returns the quadrant of the property (NW, SW, NE, SE)
  public String getCityQuadrant() {
    return this.cityQuadrant;
  }

  //PROMISES: sets the quadrant of the property
  //REQUIRES: the quadrant the property is in
  public void setCityQuadrant(String cityQuadrant) {
    this.cityQuadrant = cityQuadrant;
  }
}

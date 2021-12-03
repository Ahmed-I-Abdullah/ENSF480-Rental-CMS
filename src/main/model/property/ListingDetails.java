package src.main.model.property;

public class ListingDetails {
    private ListingState state;
    private int numOfBedrooms;
    private String housingType;
    private boolean furnished;
    private String cityQuadrant;

    public ListingDetails(ListingState state, int numOfBedrooms, String housingType, boolean furnished, String cityQuadrant) {
        this.state = state;
        this.numOfBedrooms = numOfBedrooms;
        this.housingType = housingType;
        this.furnished = furnished;
        this.cityQuadrant = cityQuadrant;
    }

    public ListingState getState() {
        return this.state;
    }

    public void setState(ListingState state) {
        this.state = state;
    }

    public int getNumOfBedrooms() {
        return this.numOfBedrooms;
    }

    public void setNumOfBedrooms(int numOfBedrooms) {
        this.numOfBedrooms = numOfBedrooms;
    }

    public String getHousingType() {
        return this.housingType;
    }

    public void setHousingType(String housingType) {
        this.housingType = housingType;
    }

    public boolean isFurnished() {
        return this.furnished;
    }

    public boolean getFurnished() {
        return this.furnished;
    }

    public void setFurnished(boolean furnished) {
        this.furnished = furnished;
    }

    public String getCityQuadrant() {
        return this.cityQuadrant;
    }

    public void setCityQuadrant(String cityQuadrant) {
        this.cityQuadrant = cityQuadrant;
    }
}
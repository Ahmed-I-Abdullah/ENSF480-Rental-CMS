package src.main.model.property;

public class Address {
    private String city;
    private String province;
    private String country;
    private String street;
    private String postalCode;

    //PROMISES: creates Address object
    //REQUIRES: the city, province, country, street and postal code of the address
    public Address(String city, String province, String country, String street, String postalCode) {
        this.city = city;
        this.province = province;
        this.country = country;
        this.street = street;
        this.postalCode = postalCode;
    }

    //PROMISES: returns the city of the address
    public String getCity() {
        return this.city;
    }

    //PROMISES: sets the city
    //REQUIRES: the city of the address
    public void setCity(String city) {
        this.city = city;
    }

    //PROMISES: returns the province of the address
    public String getProvince() {
        return this.province;
    }

    //PROMISES: sets the province
    //REQUIRES: the province of the address
    public void setProvince(String province) {
        this.province = province;
    }

    //PROMISES: returns the country of the address
    public String getCountry() {
        return this.country;
    }

    //PROMISES: sets the country
    //REQUIRES: the country of the address
    public void setCountry(String country) {
        this.country = country;
    }

    //PROMISES: returns the street of the address
    public String getStreet() {
        return this.street;
    }

    //PROMISES: sets the street
    //REQUIRES: the street (street number and name) of the address
    public void setStreet(String street) {
        this.street = street;
    }

    //PROMISES: returns the postal code of the address
    public String getPostalCode() {
        return this.postalCode;
    }

    //PROMISES: sets the postal code
    //REQUIRES: the postal code of the address
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}

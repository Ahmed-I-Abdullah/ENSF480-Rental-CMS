package src.main.model.property;
import java.util.*;
import src.main.model.user.Landlord;

public class Property{
    private int houseID;
    private Address address;
    private ListingDetails specifications;
    private Landlord postedBy;
    private Date datePosted;
    private String description;

    public Property(int houseID, Address address, ListingDetails specifications, Landlord postedBy, Date datePosted, String description) {
        //connect DB, get house ID, set it here
        //this.houseID = 
        this.address = address;
        this.specifications = specifications;
        this.postedBy = postedBy;
        this.postedBy.addProperty(this);
        this.datePosted = datePosted;
        this.description = description;
    }

    public ListingState checkState(){
        return specifications.getState();
    }

    public int getHouseID() {
        return this.houseID;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ListingDetails getSpecifications() {
        return this.specifications;
    }

    public void setSpecifications(ListingDetails specifications) {
        this.specifications = specifications;
    }

    public Landlord getPostedBy() {
        return this.postedBy;
    }

    public void setPostedBy(Landlord postedBy) {
        this.postedBy = postedBy;
    }

    public Date getDatePosted() {
        return this.datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
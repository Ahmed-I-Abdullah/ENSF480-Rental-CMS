package src.main.model.user;

import src.main.model.property.Property;
import src.main.model.property.ListingDetails;

public class RegisteredRenter implements Observer, User{
    private String name;
    private String email;
    private Subject subject;
    private boolean isSubscribed;
    private ListingDetails searchCriteria;

    public RegisteredRenter(String email, String name, ListingDetails criteria){
        setEmail(email);
        setName(name);
        setSearchCriteria(criteria);
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public Subject getSubject(){
        return this.subject;
    }

    public ListingDetails getSearchCriteria(){
        return this.searchCriteria;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setSubject(Subject subject){
        this.subject = subject;
    }

    public void setSearchCriteria(ListingDetails criteria) {
        this.searchCriteria = criteria;
    }

    public void emailLandlord(){

    }

    public void update(Property property) {

    }

    public UserType getUserType() {
        return UserType.RENTER;
    }
}

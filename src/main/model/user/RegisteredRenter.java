package src.main.model.user;

import src.main.model.property.Property;

public class RegisteredRenter implements Observer, User{
    private String name;
    private String email;
    private Subject subject;

    public RegisteredRenter(String name, String email, Subject subject){
        setName(name);
        setEmail(email);
        setSubject(subject);
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

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setSubject(Subject subject){
        this.subject = subject;
    }

    public void emailLandlord(){

    }

    @Override
    public void update(Property property) {

    }

    @Override
    public UserType getUserType() {
        return UserType.RENTER;
    }
}

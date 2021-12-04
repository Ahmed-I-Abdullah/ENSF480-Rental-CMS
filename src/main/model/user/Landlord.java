package src.main.model.user;

import java.util.ArrayList;

import src.main.model.property.Property;

public class Landlord implements User {
    private String name;
    private String email;
    private ArrayList<Property> properties;

    public Landlord(String name, String email){
        setName(name);
        setEmail(email);
        this.properties = new ArrayList<>();
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setName(String name){
        this.name = name;
    }

    public int addProperty(Property property){
        this.properties.add(property);
        return this.properties.size();
    }

    public int removeProperty(Property property){
        this.properties.add(property);
        return this.properties.size();
    }

    @Override
    public UserType getUserType() {
        return UserType.LANDLORD;
    }
    
}

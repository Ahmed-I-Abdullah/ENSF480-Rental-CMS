package src.main.model.user;

import java.util.ArrayList;

public class Manager implements User {
    private String name;
    private String email;
    private ArrayList<Landlord> landlords;

    public Manager(String email, String name){
        setName(name);
        setEmail(email);
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name){
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }

    public User accessUser(User user){
        return user;
    }

    public int addLandlord(Landlord landlord){
        this.landlords.add(landlord);
        return this.landlords.size();
    }

    public int removeLandlord(Landlord landlord){
        this.landlords.remove(landlord);
        return this.landlords.size();
    }

    public UserType getUserType(){
        return UserType.MANAGER;
    }
}

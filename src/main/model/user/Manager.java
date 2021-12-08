package src.main.model.user;

import java.util.ArrayList;

public class Manager implements User {
    private String name;
    private String email;
    private ArrayList<Landlord> landlords;

    // Constructor
    public Manager(String email, String name){
        setName(name);
        setEmail(email);
    }

    // name getter
    public String getName() {
        return this.name;
    }
    
    // name setter
    public void setName(String name){
        this.name = name;
    }

    // email getter
    public String getEmail() {
        return this.email;
    }
    
    // email setter
    public void setEmail(String email){
        this.email = email;
    }

    // Promises: return specified User
    // Requires: target User to be accessed
    public User accessUser(User user){
        return user;
    }

    // Promises: add landlord to this manager's landlord array
    // Requires: landlord to be added
    public int addLandlord(Landlord landlord){
        this.landlords.add(landlord);
        return this.landlords.size();
    }

    // Promises: remove landlord to this manager's landlord array
    // Requires: landlord to be removed
    public int removeLandlord(Landlord landlord){
        this.landlords.remove(landlord);
        return this.landlords.size();
    }

    // Promises: return user type, which is MANAGER
    public UserType getUserType(){
        return UserType.MANAGER;
    }
}

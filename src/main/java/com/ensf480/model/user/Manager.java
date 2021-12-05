package com.ensf480.model.user;

import java.util.ArrayList;

public class Manager implements User {
    private String name;
    private ArrayList<Landlord> landlords;

    public Manager(String name){
        setName(name);
    }

    public String getname(){
        return this.name;
    }
    
    public void setName(String name){
        this.name = name;
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

    @Override
    public UserType getUserType(){
        return UserType.MANAGER;
    }
}

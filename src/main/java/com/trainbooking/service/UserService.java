package com.trainbooking.service;

import com.trainbooking.model.User;
import com.trainbooking.storage.UserStore;

public class UserService {

    private UserStore store;
    public UserService(){
        this.store = new UserStore();
    }

    public String createUser(String firstName, String lastName, String emailId){
        User user = new User(firstName, lastName, emailId);
        this.store.insert(emailId, user);
        return emailId;
    }

    public User getUser(String emailId){
        try{
         return (User)this.store.get(emailId);
        }catch (Exception e){
            System.out.printf("User Not Found");
        }
        return null;
    }
}

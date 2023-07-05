package com.example.troknite.troknite.model;

import com.example.troknite.troknite.domain.Users;

public class LoginResponse {
    private String message;
    private Users user;

    public LoginResponse(String message, Users authUser) {
        this.message = message;
        this.user = authUser;
    }
    
    public LoginResponse(String message){
    	this.message= message;
    }
    
    public LoginResponse(Users authUser){
    	this.user = authUser;
    }
    // getters and setters
}

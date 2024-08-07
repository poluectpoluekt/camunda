package com.edu.camunda.exception.user;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String username) {
        super("User " + username + " not found");
    }
}

package com.edu.hibernate.exception.user;

public class AccountNumberNotFoundException extends RuntimeException{

    public AccountNumberNotFoundException(String accountNumber) {
        super("User account" + accountNumber + " not found");
    }
}

package com.edu.camunda.exception.transaction;

public class NegativeUserBalanceException extends RuntimeException{

    public NegativeUserBalanceException() {
        super("Negative user balance, operation cannot be performed");
    }
}

package com.example.BAS.exceptions;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(String customerInformation) {
        super("Klant met " + customerInformation + " niet gevonden" );
    }
}

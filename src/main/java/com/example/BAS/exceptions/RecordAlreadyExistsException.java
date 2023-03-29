package com.example.BAS.exceptions;


public class RecordAlreadyExistsException extends RuntimeException{

    public RecordAlreadyExistsException(String customerNumber) {
        super("Klant met klantnummer " + customerNumber + " staat al in de database");
    }
}

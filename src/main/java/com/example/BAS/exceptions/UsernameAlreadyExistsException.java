package com.example.BAS.exceptions;

import java.io.Serial;

public class UsernameAlreadyExistsException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public UsernameAlreadyExistsException(String username) {

        super("Gebruiker " + username + " bestaat al");
    }
}

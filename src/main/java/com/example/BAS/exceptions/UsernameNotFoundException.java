package com.example.BAS.exceptions;

import java.io.Serial;

public class UsernameNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public UsernameNotFoundException(String username) {

        super("Gebruiker " + username + " niet gevonden");
    }

}
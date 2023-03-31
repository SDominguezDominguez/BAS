package com.example.BAS.exceptions;

import java.io.Serial;

public class AuthenticationNotValid extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public AuthenticationNotValid(String message) {

        super(message);
    }
}

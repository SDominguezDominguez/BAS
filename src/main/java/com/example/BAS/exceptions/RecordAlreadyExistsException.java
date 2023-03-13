package com.example.BAS.exceptions;

import java.io.Serial;

public class RecordAlreadyExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public RecordAlreadyExistsException(String message) {
        super(message);
    }
}

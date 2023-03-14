package com.example.BAS.exceptions;

import java.io.Serial;

public class RecordHasNoRelation extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    public RecordHasNoRelation(String message) {
        super(message);
    }
}

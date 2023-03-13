package com.example.BAS.controllers;

import com.example.BAS.exceptions.RecordAlreadyExistsException;
import com.example.BAS.exceptions.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<String> exception(RecordNotFoundException exception) {
        return new ResponseEntity<>("Voor dit verzoek geen gegevens in de database gevonden", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<Object> exception(IndexOutOfBoundsException exception) {
        return new ResponseEntity<>("Voor dit verzoek geen gegevens in de database gevonden", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler (RecordAlreadyExistsException.class)
    public ResponseEntity<Object> exception(RecordAlreadyExistsException exception) {
        return new ResponseEntity<>("Dit klantnummer staat al in de database", HttpStatus.BAD_REQUEST);
    }
}

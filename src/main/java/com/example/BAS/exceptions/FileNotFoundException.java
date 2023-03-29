package com.example.BAS.exceptions;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(String fileInformation) {

        super("Dossier met " + fileInformation + " niet gevonden");
    }
}

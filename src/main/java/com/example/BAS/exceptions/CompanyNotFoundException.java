package com.example.BAS.exceptions;

public class CompanyNotFoundException extends RuntimeException{

    public CompanyNotFoundException(String companyInformation) {

        super("Maatschappij met " + companyInformation + " niet gevonden" );
    }
}

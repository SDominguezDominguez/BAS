package com.example.BAS.exceptions;

public class PolicyNotFoundException extends RuntimeException{

    public PolicyNotFoundException(String policyInformation) {

        super("Polis met " + policyInformation + " niet gevonden");
    }
}

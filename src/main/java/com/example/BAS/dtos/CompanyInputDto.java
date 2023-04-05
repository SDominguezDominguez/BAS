package com.example.BAS.dtos;

import javax.validation.constraints.*;

public class CompanyInputDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private String contactPerson;
    @NotEmpty
    @Email
    private String email;

    public CompanyInputDto() {
    }

    public CompanyInputDto(String name, String contactPerson, String email) {
        this.name = name;
        this.contactPerson = contactPerson;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

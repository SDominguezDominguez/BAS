package com.example.BAS.dtos;

import com.example.BAS.models.Advisor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class CompanyInputDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String contactPerson;
    @NotEmpty
    @Email
    private String email;
    private Advisor advisor;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }
}

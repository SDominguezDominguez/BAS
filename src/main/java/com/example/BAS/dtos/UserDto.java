package com.example.BAS.dtos;

import com.example.BAS.models.Authority;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

public class UserDto {

    @NotEmpty
    private String username;

    @NotEmpty
    @Min(6)
    private String password;

    private String apiKey;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private Integer officeNumber;
    private Integer advisorNumber;

    @JsonSerialize
    public Set<Authority> authorities;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(Integer officeNumber) {
        this.officeNumber = officeNumber;
    }

    public Integer getAdvisorNumber() {
        return advisorNumber;
    }

    public void setAdvisorNumber(Integer advisorNumber) {
        this.advisorNumber = advisorNumber;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}

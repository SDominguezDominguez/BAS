package com.example.BAS.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(unique = true)
    private String username;
    private String password;
    private String apiKey;
    private String email;
    private Long officeNumber;
    @Column(unique = true)
    private Long advisorNumber;

    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<Authority> authorities = new HashSet<>();

    public User() {
    }

    public User(String username, String password, String apiKey, String email, Long officeNumber) {
        this.username = username;
        this.password = password;
        this.apiKey = apiKey;
        this.email = email;
        this.officeNumber = officeNumber;
    }

    public User(String username, String password, String apiKey, String email, Long officeNumber, Long advisorNumber) {
        this.username = username;
        this.password = password;
        this.apiKey = apiKey;
        this.email = email;
        this.officeNumber = officeNumber;
        this.advisorNumber = advisorNumber;
    }

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

    public Long getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(Long officeNumber) {
        this.officeNumber = officeNumber;
    }

    public Long getAdvisorNumber() {
        return advisorNumber;
    }

    public void setAdvisorNumber(Long advisorNumber) {
        this.advisorNumber = advisorNumber;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
    }


    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}

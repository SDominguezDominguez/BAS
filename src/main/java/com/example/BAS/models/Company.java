package com.example.BAS.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.List;

@Entity
public class Company {

    @Id
    @GeneratedValue(generator = "company_sequence")
    @GenericGenerator(
            name = "company_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "company_sequence", value = "customer_id"),
                    @Parameter(name = "initial_value", value = "4"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;
    private String name;
    private String contactPerson;
    private String email;

    @OneToMany(mappedBy = "company")
    private List<Policy> policies;

    public Company() {
    }

    public Company(Long id, String name, String contactPerson, String email) {
        this.id = id;
        this.name = name;
        this.contactPerson = contactPerson;
        this.email = email;
    }

    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

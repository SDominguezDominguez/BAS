package com.example.BAS.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(generator = "customer_sequence")
    @GenericGenerator(
            name = "customer_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "customer_sequence", value = "customer_id"),
                    @Parameter(name = "initial_value", value = "4"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;
    private String name;
    @Column(unique = true)
    private String customerNumber;
    private String brand;
    private String email;

    @ManyToOne
    private Advisor advisor;

    @OneToMany(mappedBy = "customer")
    private List<File> files;

    public Customer() {
    }

    public Customer(String name, String customerNumber, String brand) {
        this.name = name;
        this.customerNumber = customerNumber;
        this.brand = brand;
    }

    public Customer(String name, String customerNumber, String brand, String email) {
        this.name = name;
        this.customerNumber = customerNumber;
        this.brand = brand;
        this.email = email;
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

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}

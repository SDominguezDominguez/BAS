package com.example.BAS.dtos;

public class CustomerDto {
    private Long id;

    private String name;
    private String customerNumber;
    private String brand;
    private String email;

    public CustomerDto() {

    }

    public CustomerDto(String name, String customerNumber, String brand) {
        this.name = name;
        this.customerNumber = customerNumber;
        this.brand = brand;
    }

    public CustomerDto(String name, String customerNumber, String brand, String email) {
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
}

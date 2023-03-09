package com.example.BAS.dtos;

import jakarta.validation.constraints.*;

public class CustomerInputDto {
    @NotBlank(message = "Naam is verplicht")
    private String name;
    @NotBlank(message = "Klantnummer is verplicht")
    private String customerNumber;
    @NotBlank(message = "Merk is verplicht")
    private String brand;
    @Email(message = "Moet een e-mailadres zijn")
    private String email;

    public CustomerInputDto() {

    }

    public CustomerInputDto(String name, String customerNumber, String brand) {
        this.name = name;
        this.customerNumber = customerNumber;
        this.brand = brand;
    }

    public CustomerInputDto(String name, String customerNumber, String brand, String email) {
        this.name = name;
        this.customerNumber = customerNumber;
        this.brand = brand;
        this.email = email;
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

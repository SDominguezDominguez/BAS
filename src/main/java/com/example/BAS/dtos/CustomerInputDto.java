package com.example.BAS.dtos;

import com.example.BAS.models.Advisor;
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
    private Advisor advisor;

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
}

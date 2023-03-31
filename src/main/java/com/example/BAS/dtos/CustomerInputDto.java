package com.example.BAS.dtos;

import com.example.BAS.enumerations.Label;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CustomerInputDto {

    @NotEmpty(message = "Naam is verplicht")
    private String name;

    @NotEmpty(message = "Klantnummer is verplicht")
    private String customerNumber;

    @NotNull(message = "Merk is verplicht")
    private Label label;

    @Email
    private String email;

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

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

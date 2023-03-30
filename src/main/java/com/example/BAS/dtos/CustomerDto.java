package com.example.BAS.dtos;

import com.example.BAS.enumerations.Label;

import java.util.List;

public class CustomerDto {

    private Long id;
    private String name;
    private String customerNumber;
    private Label label;
    private String email;
    private List<FileDto> fileDto;

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

    public List<FileDto> getFileDto() {
        return fileDto;
    }

    public void setFileDto(List<FileDto> fileDto) {
        this.fileDto = fileDto;
    }
}

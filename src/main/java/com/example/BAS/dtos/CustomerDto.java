package com.example.BAS.dtos;

import com.example.BAS.enumerations.Label;

import java.util.List;

public class CustomerDto {

    private Long id;
    private String name;
    private String customerNumber;
    private Label label;
    private String email;
    private Long advisorNumber;
    private Long officeNumber;
    private List<FileDto> fileDto;

    public CustomerDto() {
    }

    public CustomerDto(Long id, String name, String customerNumber, Label label, String email) {
        this.id = id;
        this.name = name;
        this.customerNumber = customerNumber;
        this.label = label;
        this.email = email;
    }

    public CustomerDto(Long id, String name, String customerNumber, Label label) {
        this.id = id;
        this.name = name;
        this.customerNumber = customerNumber;
        this.label = label;
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

    public Long getAdvisorNumber() {
        return advisorNumber;
    }

    public void setAdvisorNumber(Long advisorNumber) {
        this.advisorNumber = advisorNumber;
    }

    public Long getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(Long officeNumber) {
        this.officeNumber = officeNumber;
    }
}

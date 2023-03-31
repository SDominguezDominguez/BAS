package com.example.BAS.dtos;

import java.util.List;

public class CustomerForAdvisorDto {

    private String name;
    private String customerNumber;
    private String email;
    private List<FileForAdvisorDto> fileDto;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<FileForAdvisorDto> getFileDto() {
        return fileDto;
    }

    public void setFileDto(List<FileForAdvisorDto> fileDto) {
        this.fileDto = fileDto;
    }
}

package com.example.BAS.dtos;

import java.time.LocalDate;

public class PolicyDto {

    private Long id;
    private String policyNumber;
    private Integer amount;
    private LocalDate receiveDateAmount;
    private LocalDate receiveDatePsk;
    private LocalDate reminderDatePsk;
    private FileDto fileDto;
    private CompanyDto companyDto;

    public PolicyDto() {
    }

    public PolicyDto(Long id, String policyNumber) {
        this.id = id;
        this.policyNumber = policyNumber;
    }

    public PolicyDto(Long id, String policyNumber, Integer amount, LocalDate receiveDateAmount, LocalDate receiveDatePsk, LocalDate reminderDatePsk) {
        this.id = id;
        this.policyNumber = policyNumber;
        this.amount = amount;
        this.receiveDateAmount = receiveDateAmount;
        this.receiveDatePsk = receiveDatePsk;
        this.reminderDatePsk = reminderDatePsk;
    }

    public CompanyDto getCompanyDto() {
        return companyDto;
    }

    public void setCompanyDto(CompanyDto companyDto) {
        this.companyDto = companyDto;
    }

    public FileDto getFileDto() {
        return fileDto;
    }

    public void setFileDto(FileDto fileDto) {
        this.fileDto = fileDto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDate getReceiveDateAmount() {
        return receiveDateAmount;
    }

    public void setReceiveDateAmount(LocalDate receiveDateAmount) {
        this.receiveDateAmount = receiveDateAmount;
    }

    public LocalDate getReceiveDatePsk() {
        return receiveDatePsk;
    }

    public void setReceiveDatePsk(LocalDate receiveDatePsk) {
        this.receiveDatePsk = receiveDatePsk;
    }

    public LocalDate getReminderDatePsk() {
        return reminderDatePsk;
    }

    public void setReminderDatePsk(LocalDate reminderDatePsk) {
        this.reminderDatePsk = reminderDatePsk;
    }
}

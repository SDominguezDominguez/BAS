package com.example.BAS.dtos;

import javax.validation.constraints.Future;
import javax.validation.constraints.Past;

import java.time.LocalDate;

public class PolicyInputDto {

    private String policyNumber;
    private Integer amount;
    @Past
    private LocalDate receiveDateAmount;
    @Past
    private LocalDate receiveDatePsk;
    @Future
    private LocalDate reminderDatePsk;

    public PolicyInputDto() {
    }

    public PolicyInputDto(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public PolicyInputDto(String policyNumber, Integer amount, LocalDate receiveDateAmount) {
        this.policyNumber = policyNumber;
        this.amount = amount;
        this.receiveDateAmount = receiveDateAmount;
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

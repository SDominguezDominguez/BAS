package com.example.BAS.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.time.LocalDate;

@Entity
public class Policy {

    @Id
    @GeneratedValue(generator = "policy_sequence")
    @GenericGenerator(
            name = "policy_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "policy_sequence", value = "customer_id"),
                    @Parameter(name = "initial_value", value = "4"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;
    private String policyNumber;
    private Integer amount;
    private LocalDate receiveDateAmount;
    private LocalDate receiveDatePsk;
    private LocalDate reminderDatePsk;

    @ManyToOne
    private File file;

    @ManyToOne
    private Company company;

    public Policy() {
    }

    public Policy(Long id, String policyNumber, Integer amount, LocalDate receiveDateAmount, LocalDate receiveDatePsk, LocalDate reminderDatePsk, Company company) {
        this.id = id;
        this.policyNumber = policyNumber;
        this.amount = amount;
        this.receiveDateAmount = receiveDateAmount;
        this.receiveDatePsk = receiveDatePsk;
        this.reminderDatePsk = reminderDatePsk;
        this.company = company;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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

package com.example.BAS.models;

import com.example.BAS.enumerations.Label;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(generator = "customer_sequence")
    @GenericGenerator(
            name = "customer_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "customer_sequence", value = "customer_id"),
                    @Parameter(name = "initial_value", value = "4"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;

    private String name;

    @Column(unique = true)
    private String customerNumber;

    @Enumerated(EnumType.STRING)
    private Label label;
    private String email;
    private Long advisorNumber;
    private Long officeNumber;

    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL)
    private List<File> files;

    public Customer() {
    }

    public Customer(Long id, String name, String customerNumber, Label label, String email) {
        this.id = id;
        this.name = name;
        this.customerNumber = customerNumber;
        this.label = label;
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

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}

package com.example.BAS.models;

import com.example.BAS.enumerations.FileType;
import com.example.BAS.enumerations.Status;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.List;

@Entity
public class File {

    @Id
    @GeneratedValue(generator = "file_sequence")
    @GenericGenerator(
            name = "file_sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "file_sequence", value = "customer_id"),
                    @Parameter(name = "initial_value", value = "4"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;
    private String statusComment;
    private String comment;

    @Enumerated(EnumType.STRING)
    private FileType fileType;
    private Integer contractAmount;
    @Lob
    private byte[] applicationForm;

    private boolean applicationFormPresent;

    @ManyToOne
    private Customer customer;

    @OneToMany(
            mappedBy = "file",
            cascade = CascadeType.ALL)
    private List<Policy> policies;

    public File() {
    }

    public File(Long id, Status status, String statusComment, String comment, FileType fileType, Integer contractAmount) {
        this.id = id;
        this.status = status;
        this.statusComment = statusComment;
        this.comment = comment;
        this.fileType = fileType;
        this.contractAmount = contractAmount;
    }

    public List<Policy> getPolicies() {
        return policies;
    }

    public void setPolicies(List<Policy> policies) {
        this.policies = policies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStatusComment() {
        return statusComment;
    }

    public void setStatusComment(String statusComment) {
        this.statusComment = statusComment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public Integer getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Integer contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public byte[] getApplicationForm() {
        return applicationForm;
    }

    public void setApplicationForm(byte[] applicationForm) {
        this.applicationForm = applicationForm;
    }

    public boolean isApplicationFormPresent() {
        return applicationFormPresent;
    }

    public void setApplicationFormPresent(boolean applicationFormPresent) {
        this.applicationFormPresent = applicationFormPresent;
    }
}

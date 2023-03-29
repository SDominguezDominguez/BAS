package com.example.BAS.models;

import com.example.BAS.enumerations.FileType;
import com.example.BAS.enumerations.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

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
    private Status status;
    private String statusComment;
    private String comment;
    private FileType fileType;
    private Integer contractAmount;

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
}

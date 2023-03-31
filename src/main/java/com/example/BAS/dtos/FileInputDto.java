package com.example.BAS.dtos;

import com.example.BAS.enumerations.FileType;
import com.example.BAS.enumerations.Status;
import javax.validation.constraints.NotNull;

public class FileInputDto {

    @NotNull(message = "Status is verplicht")
    private Status status;

    private String statusComment;
    private String comment;

    @NotNull(message = "Soort dossier is verplicht")
    private FileType fileType;

    private Integer contractAmount;

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

package com.example.BAS.dtos;

import com.example.BAS.enumerations.FileType;
import com.example.BAS.enumerations.Status;

import java.util.List;

public class FileDto {

    private Long id;
    private Status status;
    private String statusComment;
    private String comment;
    private FileType fileType;
    private Integer contractAmount;
    private boolean applicationFormPresent;
    private CustomerDto customerDto;
    private List<PolicyDto> policyDtos;

    public List<PolicyDto> getPolicyDtos() {
        return policyDtos;
    }

    public void setPolicyDtos(List<PolicyDto> policyDtos) {
        this.policyDtos = policyDtos;
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

    public CustomerDto getCustomerDto() {
        return customerDto;
    }

    public void setCustomerDto(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }

    public boolean isApplicationFormPresent() {
        return applicationFormPresent;
    }

    public void setApplicationFormPresent(boolean applicationFormPresent) {
        this.applicationFormPresent = applicationFormPresent;
    }
}

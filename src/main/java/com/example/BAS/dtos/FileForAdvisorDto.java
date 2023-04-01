package com.example.BAS.dtos;

public class FileForAdvisorDto {

    private String status;
    private String statusComment;
    private String fileType;
    private boolean applicationFormPresent;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusComment() {
        return statusComment;
    }

    public void setStatusComment(String statusComment) {
        this.statusComment = statusComment;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public boolean isApplicationFormPresent() {
        return applicationFormPresent;
    }

    public void setApplicationFormPresent(boolean applicationFormPresent) {
        this.applicationFormPresent = applicationFormPresent;
    }
}

package com.example.BAS.dtos;

public class AdvisorDto {
    private Long id;
    private Integer office;
    private String name;
    private String email;

    public AdvisorDto() {
    }

    public AdvisorDto(Integer office, String name, String email) {
        this.office = office;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOffice() {
        return office;
    }

    public void setOffice(Integer office) {
        this.office = office;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

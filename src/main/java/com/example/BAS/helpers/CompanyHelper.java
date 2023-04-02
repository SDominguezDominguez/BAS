package com.example.BAS.helpers;

import com.example.BAS.dtos.CompanyDto;
import com.example.BAS.dtos.CompanyInputDto;
import com.example.BAS.models.Company;

import java.util.ArrayList;
import java.util.List;

public class CompanyHelper {

    public static List<CompanyDto> transferCompanyListToDtoList(List<Company> companies) {

        List<CompanyDto> companyDtoList = new ArrayList<>();

        for (Company company : companies) {

            CompanyDto dto = transferCompanyToDto(company);

            companyDtoList.add(dto);
        }

        return companyDtoList;
    }

    public static CompanyDto transferCompanyToDto(Company company) {

        CompanyDto dto = new CompanyDto();

        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setContactPerson(company.getContactPerson());
        dto.setEmail(company.getEmail());

        return dto;
    }

    public static Company transferCompanyInputDtoToCompany(CompanyInputDto dto) {

        Company company = new Company();

        company.setEmail(dto.getEmail());
        company.setName(dto.getName());
        company.setContactPerson(dto.getContactPerson());

        return company;
    }

    public static Company transferCompanyDtoToCompany(CompanyDto dto) {

        Company company = new Company();

        company.setId(dto.getId());
        company.setName(dto.getName());
        company.setContactPerson(dto.getContactPerson());
        company.setEmail(dto.getEmail());

        return company;
    }

    public static List<Company> transferCompanyDtoListToCompanyList(List<CompanyDto> companies) {

        List<Company> companyList = new ArrayList<>();

        for (CompanyDto dto : companies) {

            companyList.add(transferCompanyDtoToCompany(dto));
        }

        return companyList;
    }
}

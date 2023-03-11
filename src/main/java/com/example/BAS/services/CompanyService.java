package com.example.BAS.services;

import com.example.BAS.dtos.CompanyDto;
import com.example.BAS.dtos.CompanyInputDto;
import com.example.BAS.models.Company;
import com.example.BAS.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<CompanyDto> getAllCompanies() {
        List<Company> companyList = companyRepository.findAll();
        return transferCompanyListToDtoList(companyList);
    }

    public CompanyDto createCompany(CompanyInputDto dto) {
        Company company = transferCompanyInputDtoToCompany(dto);
        companyRepository.save(company);

        return transferCompanyToDto(company);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    public void patchCompany(Long id, CompanyInputDto dto) {
        Optional<Company> optionalCompany = companyRepository.findById(id);

        if (optionalCompany.isPresent()) {
            Company company = optionalCompany.get();

            if (dto.getContactPerson() != null) {
                company.setContactPerson(dto.getContactPerson());
            }
            if (dto.getEmail() != null) {
                company.setEmail(dto.getEmail());
            }
            if (dto.getName() != null) {
                company.setEmail(dto.getEmail());
            }

            companyRepository.save(company);
        }
    }

    public List<CompanyDto> transferCompanyListToDtoList(List<Company> companies) {
        List<CompanyDto> companyDtoList = new ArrayList<>();

        for (Company company : companies) {
            CompanyDto dto = transferCompanyToDto(company);

            companyDtoList.add(dto);
        }
        return companyDtoList;
    }

    public CompanyDto transferCompanyToDto(Company company) {
        CompanyDto dto = new CompanyDto();

        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setContactPerson(company.getContactPerson());
        dto.setEmail(company.getEmail());

        return dto;
    }

    public Company transferCompanyInputDtoToCompany(CompanyInputDto dto) {
        Company company = new Company();

        company.setEmail(dto.getEmail());
        company.setName(dto.getName());
        company.setContactPerson(dto.getContactPerson());

        return company;
    }

}

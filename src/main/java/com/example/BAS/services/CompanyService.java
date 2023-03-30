package com.example.BAS.services;

import com.example.BAS.dtos.CompanyDto;
import com.example.BAS.dtos.CompanyInputDto;
import com.example.BAS.exceptions.CompanyNotFoundException;
import com.example.BAS.helpers.CompanyHelper;
import com.example.BAS.models.Company;
import com.example.BAS.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

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

        return CompanyHelper.transferCompanyListToDtoList(companyList);
    }

    public CompanyDto createCompany(CompanyInputDto dto) {

        Company company = CompanyHelper.transferCompanyInputDtoToCompany(dto);

        companyRepository.save(company);

        return CompanyHelper.transferCompanyToDto(company);
    }

    public void changeCompany(Long id, CompanyInputDto dto) {

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

        } else {

            throw new CompanyNotFoundException("id " + id);
        }
    }

    public void deleteCompany(Long id) {

        if (companyRepository.findById(id).isPresent()) {

            companyRepository.deleteById(id);

        } else {

            throw new CompanyNotFoundException("id " + id);
        }
    }
}

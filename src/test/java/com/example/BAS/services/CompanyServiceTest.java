package com.example.BAS.services;

import com.example.BAS.dtos.CompanyDto;
import com.example.BAS.dtos.CompanyInputDto;
import com.example.BAS.exceptions.CompanyNotFoundException;
import com.example.BAS.helpers.CompanyHelper;
import com.example.BAS.models.Company;
import com.example.BAS.models.Policy;
import com.example.BAS.repositories.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    CompanyRepository companyRepository;

    @InjectMocks
    CompanyService companyService;

    @Mock
    PolicyService policyService;

    @Captor
    ArgumentCaptor<Company> captor;

    Company company1;
    Company company2;
    Policy policy1;
    Policy policy2;
    Policy policy3;

    @BeforeEach
    void setUp() {

        company1 = new Company(1L, "SNS Bank", "Test Tester", "test@test.nl");

        company2 = new Company(2L, "RegioBank", "Testen Test", "tester@test.nl");

        policy1 = new Policy(1L, "L1", 100, LocalDate.of(2023, 3, 20), null, LocalDate.of(2023, 4, 3), company1);

        policy2 = new Policy(2L, "L2", null, null, LocalDate.of(2023, 4, 1), null, company2);

        policy3 = new Policy(3L, "L3", 500, LocalDate.of(2023, 3, 1), LocalDate.of(2023, 3, 10), null, company1);
    }

    @Test
    void getAllCompanies() {

        when(companyRepository.findAll()).thenReturn(List.of(company1, company2));

        List<Company> companiesFound = CompanyHelper.transferCompanyDtoListToCompanyList(companyService.getAllCompanies());

        assertEquals(company1.getName(), companiesFound.get(0).getName());
        assertEquals(company2.getName(), companiesFound.get(1).getName());

    }

    @Test
    void createAuthor() {

        CompanyInputDto company3 = new CompanyInputDto();
        company3.setName("Test");
        company3.setEmail("testen@test.nl");
        company3.setContactPerson("Tester");

        lenient().when(companyRepository.save(CompanyHelper.transferCompanyInputDtoToCompany(company3))).thenReturn(CompanyHelper.transferCompanyInputDtoToCompany(company3));

        companyService.createCompany(company3);

        verify(companyRepository, times(1)).save(captor.capture());

        Company company = captor.getValue();

        assertEquals(company3.getName(), company.getName());
        assertEquals(company3.getEmail(), company.getEmail());
        assertEquals(company3.getContactPerson(), company.getContactPerson());
    }

    @Test
    void changeAuthor() {

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company1));

        CompanyInputDto inputDto = new CompanyInputDto();
        inputDto.setName("SNS");
        inputDto.setContactPerson("Test Tester");
        inputDto.setEmail("test@test.nl");

        lenient().when(companyRepository.save(CompanyHelper.transferCompanyInputDtoToCompany(inputDto))).thenReturn(company1);

        companyService.changeCompany(1L, inputDto);

        verify(companyRepository, times(1)).save(captor.capture());

        Company captured = captor.getValue();

        assertEquals(inputDto.getName(), captured.getName());
        assertEquals(inputDto.getContactPerson(), captured.getContactPerson());
        assertEquals(inputDto.getEmail(), captured.getEmail());
    }

    @Test
    void changeCompanyThrowsExceptionTest() {

        CompanyInputDto companyDto = new CompanyInputDto();
        companyDto.setName("SNS");
        companyDto.setEmail("test@test.nl");
        companyDto.setContactPerson("Tester");

        assertThrows(CompanyNotFoundException.class, () -> companyService.changeCompany(null, companyDto));
    }

    @Test
    void deleteCompany() {

        List<Policy> policies = new ArrayList<>();
        policies.add(policy1);
        policies.add(policy2);

        Company company3 = new Company(1L, "SNS Bank", "Test Tester", "test@test.nl");
        company3.setPolicies(policies);

        when(companyRepository.findById(1L)).thenReturn(Optional.of(company3));

        companyService.deleteCompany(1L);

        verify(companyRepository).deleteById(1L);
    }

    @Test
    void deleteCompanyThrowsExceptionTest() {

        assertThrows(CompanyNotFoundException.class, () -> companyService.deleteCompany(null));
    }

    @Test
    void getCompaniesByNameContaining() {

        List<Company> companies = new ArrayList<>();
        companies.add(company1);
        companies.add(company2);

        when(companyRepository.getCompaniesByNameContainingIgnoreCase("a")).thenReturn(Optional.of(companies));

        List<CompanyDto> companiesFound = companyService.getCompaniesByNameContaining("a");

        assertEquals(company1.getName(), companiesFound.get(0).getName());
        assertEquals(company2.getName(), companiesFound.get(1).getName());
    }

    @Test
    void getCompaniesByNameContainingThrowsExceptionTest() {

        assertThrows(CompanyNotFoundException.class, () -> companyService.getCompaniesByNameContaining(null));
    }
}
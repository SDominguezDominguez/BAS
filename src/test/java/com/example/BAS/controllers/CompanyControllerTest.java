package com.example.BAS.controllers;

import com.example.BAS.dtos.CompanyDto;
import com.example.BAS.dtos.CompanyInputDto;
import com.example.BAS.models.Company;
import com.example.BAS.models.Policy;
import com.example.BAS.repositories.CompanyRepository;
import com.example.BAS.services.CompanyService;
import com.example.BAS.services.PolicyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")

class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PolicyService policyService;

    Company company1;
    Company company2;
    Company company3;
    CompanyDto companyDto1;
    CompanyDto companyDto2;
    CompanyInputDto companyInputDto1;
    CompanyInputDto companyInputDto2;
    Policy policy;

    @BeforeEach
    void setUp() {
//
//        if (companyRepository.count() > 0) {
//            companyRepository.deleteAll();
//        }

        company1 = new Company(1L, "SNS", "Test", "test@test.nl");
        company2 = new Company(2L, "BLG", "Test2", "test2@test.nl");
        company3 = new Company(1L, "SNS Bank", "Test", "test@test.nl");

        companyRepository.save(company1);
        companyRepository.save(company2);

        companyDto1 = new CompanyDto(1L, "SNS", "Test", "test@test.nl");
        companyDto2 = new CompanyDto(2L, "BLG", "Test2", "test2@test.nl");

        companyInputDto1 = new CompanyInputDto("Regiobank", "Test3", "test3@test.nl");
        companyInputDto2 = new CompanyInputDto("SNS BANK", "Test", "test@test.nl");

        policy = new Policy(1L, "L1", company1);
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void getAllCompanies() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("SNS"))
                .andExpect(jsonPath("$[0].contactPerson").value("Test"))
                .andExpect(jsonPath("$[0].email").value("test@test.nl"))
                .andExpect(jsonPath("$[1].name").value("BLG"))
                .andExpect(jsonPath("$[1].contactPerson").value("Test2"))
                .andExpect(jsonPath("$[1].email").value("test2@test.nl"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void getCompaniesByNameContaining() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/companies?name=SNS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("SNS"))
                .andExpect(jsonPath("$[0].contactPerson").value("Test"))
                .andExpect(jsonPath("$[0].email").value("test@test.nl"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void createCompany() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/companies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(companyInputDto1)))
                .andExpect(status().isCreated());
    }

//    @Test
//    @WithMockUser(username = "testuser", roles = "ADMIN")
//    void changeCompany() throws Exception {
//
//        mockMvc.perform(MockMvcRequestBuilders.patch("/companies/" + company1.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(companyInputDto2)))
//                .andExpect(status().isOk());
//                .andExpect(jsonPath("id").value(company1.getId().toString()))
//                .andExpect(jsonPath("name").value("SNS"))
//                .andExpect(jsonPath("contactPerson").value("Test"))
//                .andExpect(jsonPath("email").value("test@test.nl"));
//    }

//    @Test
//    @WithMockUser(username = "testuser", roles = "ADMIN")
//    void deleteCompany() throws Exception {
//
//        mockMvc.perform(MockMvcRequestBuilders.delete("/companies/1/"))
//                .andExpect(status().isNoContent());
//    }

    public static String asJsonString(final CompanyInputDto dto) {

        try {
            ObjectMapper mapper = new ObjectMapper();

            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            return mapper.writeValueAsString(dto);

        } catch (JsonProcessingException e) {

            throw new RuntimeException(e);
        }
    }
}
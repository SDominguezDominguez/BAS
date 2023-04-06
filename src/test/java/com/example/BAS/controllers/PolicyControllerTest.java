package com.example.BAS.controllers;

import com.example.BAS.dtos.PolicyDto;
import com.example.BAS.dtos.PolicyInputDto;
import com.example.BAS.enumerations.FileType;
import com.example.BAS.enumerations.Status;
import com.example.BAS.models.Company;
import com.example.BAS.models.File;
import com.example.BAS.models.Policy;
import com.example.BAS.repositories.CompanyRepository;
import com.example.BAS.repositories.FileRepository;
import com.example.BAS.repositories.PolicyRepository;
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

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PolicyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PolicyService policyService;

    @Autowired
    private PolicyRepository policyRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private CompanyRepository companyRepository;

    Policy policy1;
    Policy policy2;
    Policy policy3;
    PolicyDto policyDto1;
    PolicyDto policyDto2;
    PolicyDto policyDto3;
    PolicyInputDto policyInputDto1;
    PolicyInputDto policyInputDto2;
    Company company1;
    File file1;

    @BeforeEach
    void setUp() {

        if (policyRepository.count() > 0) {
            policyRepository.deleteAll();
        }

        policy1 = new Policy(1L, "L1");
        policy2 = new Policy(2L, "L2");

        policy3 = new Policy(2L, "L3");
        policy3.setAmount(10000);
        policy3.setReceiveDateAmount(LocalDate.of(2023, 1, 1));
        policy3.setReminderDatePsk(LocalDate.of(2023, 4, 6));

        policy1 = policyRepository.save(policy1);
        policy2 = policyRepository.save(policy2);
        policy3 = policyRepository.save(policy3);

        policyDto1 = new PolicyDto(1L, "L1");
        policyDto2 = new PolicyDto(2L, "L2");

        policyDto3 = new PolicyDto(2L, "L3");
        policyDto3.setAmount(10000);
        policyDto3.setReceiveDateAmount(LocalDate.of(2023, 1, 1));
        policyDto3.setReminderDatePsk(LocalDate.of(2023, 4, 6));

        policyInputDto1 = new PolicyInputDto("L3");
        policyInputDto2 = new PolicyInputDto("L1", 15000, LocalDate.of(2023, 4, 4));

        company1 = new Company(4L, "SNS", "Test", "test@test.nl");

        company1 = companyRepository.save(company1);

        file1 = new File(4L, Status.WACHTEN_OP_PSK_EN_BEDRAG, null, "Lhk nee", FileType.LUR, 25000);

        file1 = fileRepository.save(file1);
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void getAllPolicies() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/policies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].policyNumber").value("L1"))
                .andExpect(jsonPath("$[1].policyNumber").value("L2"))
                .andExpect(jsonPath("$[2].policyNumber").value("L3"))
                .andExpect(jsonPath("$[2].amount").value(10000))
                .andExpect(jsonPath("$[2].receiveDateAmount").value(LocalDate.of(2023, 1, 1).toString()))
                .andExpect(jsonPath("$[2].reminderDatePsk").value(LocalDate.of(2023, 4, 6).toString()));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void getAllPoliciesByReminderDate() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/policies?date=2023-04-06"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(6L))
                .andExpect(jsonPath("$[0].policyNumber").value("L3"))
                .andExpect(jsonPath("$[0].amount").value(10000))
                .andExpect(jsonPath("$[0].receiveDateAmount").value(LocalDate.of(2023, 1, 1).toString()))
                .andExpect(jsonPath("$[0].reminderDatePsk").value(LocalDate.of(2023, 4, 6).toString()));

    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void createPolicy() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/policies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(policyInputDto1)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void changePolicy() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.patch("/policies/" + policy1.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(policyInputDto2)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void deletePolicy() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/policies/" + policy1.getId().toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void getPoliciesByPolicyNumber() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/policies/" + policy1.getPolicyNumber()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(policy1.getId().toString()))
                .andExpect(jsonPath("policyNumber").value("L1"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void getPoliciesWhereAmountIsReceivedWithoutPsk() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/policies/amounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(policy3.getId().toString()))
                .andExpect(jsonPath("$[0].policyNumber").value("L3"))
                .andExpect(jsonPath("$[0].amount").value(10000))
                .andExpect(jsonPath("$[0].receiveDateAmount").value(LocalDate.of(2023, 1, 1).toString()))
                .andExpect(jsonPath("$[0].reminderDatePsk").value(LocalDate.of(2023, 4, 6).toString()));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void assignPolicyToFile() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/policies/" + policy1.getId().toString() + "/file/" + file1.getId().toString()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void assignCompanyToPolicy() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/policies/" + policy1.getId().toString() + "/company/" + company1.getId().toString()))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final PolicyInputDto dto) {

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
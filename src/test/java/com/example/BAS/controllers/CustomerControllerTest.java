package com.example.BAS.controllers;

import com.example.BAS.dtos.CustomerDto;
import com.example.BAS.dtos.CustomerInputDto;
import com.example.BAS.enumerations.Label;
import com.example.BAS.models.Customer;
import com.example.BAS.models.User;
import com.example.BAS.repositories.CustomerRepository;
import com.example.BAS.repositories.UserRepository;
import com.example.BAS.services.CustomerService;
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
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    Customer customer1;
    Customer customer2;
    CustomerDto customerDto1;
    CustomerDto customerDto2;
    CustomerInputDto inputDto1;
    CustomerInputDto inputDto2;

    User user1;
    User user2;

    @BeforeEach
    void setUp() {

        if (customerRepository.count() > 0) {
            customerRepository.deleteAll();
        }

        customer1 = new Customer(1L, "Test", "123", Label.SNS_BANK, "test@test.nl");
        customer2 = new Customer(2L, "Test2", "1234", Label.REGIOBANK);

        customer1 = customerRepository.save(customer1);
        customer2 = customerRepository.save(customer2);

        customerDto1 = new CustomerDto(1L, "Test", "123", Label.SNS_BANK, "test@test.nl");
        customerDto2 = new CustomerDto(2L, "Test2", "1234", Label.REGIOBANK);

        inputDto1 = new CustomerInputDto("Test3", "12345", Label.BLG_WONEN, "test3@test.nl");
        inputDto2 = new CustomerInputDto("Tester", "123", Label.SNS_BANK, "test@test.nl");

        user1 = new User("Advisor 1", "testen", "12345", "advisor1@test.nl", 710L);
        user2 = new User("Advisor 2", "testen", "123456", "advisor2@test.nl", 710L, 12L);

        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void getAllCustomers() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test"))
                .andExpect(jsonPath("$[0].customerNumber").value("123"))
                .andExpect(jsonPath("$[0].label").value(Label.SNS_BANK.toString()))
                .andExpect(jsonPath("$[0].email").value("test@test.nl"))
                .andExpect(jsonPath("$[1].name").value("Test2"))
                .andExpect(jsonPath("$[1].customerNumber").value("1234"))
                .andExpect(jsonPath("$[1].label").value(Label.REGIOBANK.toString()));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void getCustomersByNameContaining() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/customers?name=tes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test"))
                .andExpect(jsonPath("$[0].customerNumber").value("123"))
                .andExpect(jsonPath("$[0].label").value(Label.SNS_BANK.toString()))
                .andExpect(jsonPath("$[0].email").value("test@test.nl"))
                .andExpect(jsonPath("$[1].name").value("Test2"))
                .andExpect(jsonPath("$[1].customerNumber").value("1234"))
                .andExpect(jsonPath("$[1].label").value(Label.REGIOBANK.toString()));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void createCustomer() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputDto1)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void changeCustomer() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.patch("/customers/" + customer1.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(inputDto2)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void deleteCustomer() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/" + customer1.getId().toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void getCustomerByCustomerNumber() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/" + customer1.getCustomerNumber()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(customer1.getId().toString()))
                .andExpect(jsonPath("name").value("Test"))
                .andExpect(jsonPath("customerNumber").value("123"))
                .andExpect(jsonPath("label").value(Label.SNS_BANK.toString()))
                .andExpect(jsonPath("email").value("test@test.nl"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void assignAdvisorToCustomerByOfficeNumber() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.post("/customers/" +customer1.getId().toString() + "/advisors?officeNumber=710"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", roles = "ADMIN")
    void assignAdvisorToCustomerByAdvisorNumber() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.post("/customers/" +customer1.getId().toString() + "/advisors?officeNumber=710&advisorNumber=12"))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final CustomerInputDto dto) {

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
package com.example.BAS.services;

import com.example.BAS.dtos.CustomerDto;
import com.example.BAS.dtos.CustomerInputDto;
import com.example.BAS.exceptions.RecordAlreadyExistsException;
import com.example.BAS.exceptions.RecordNotFoundException;
import com.example.BAS.models.Customer;
import com.example.BAS.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AdvisorService advisorService;

    public CustomerService(CustomerRepository repo, AdvisorService advisorService) {
        this.customerRepository = repo;
        this.advisorService = advisorService;
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        return transferCustomerListToDtoList(customerList);
    }

    public CustomerDto getCustomerByCustomerNumber(String customerNumber) {
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByCustomerNumber(customerNumber);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();

            return transferCustomerToDto(customer);

        } else {
            throw new RecordNotFoundException("Geen klant gevonden");
        }
    }

    public List<CustomerDto> getCustomersByNameContaining(String name) {
        Optional<List<Customer>> optionalCustomers = customerRepository.findCustomerByNameContaining(name);

        if (optionalCustomers.isPresent()) {
            List<Customer> customers = optionalCustomers.get();

            return transferCustomerListToDtoList(customers);

        } else {
            throw new RecordNotFoundException("Geen klanten gevonden");
        }
    }

    public CustomerDto createCustomer(CustomerInputDto dto) {

        if (customerRepository.findCustomerByCustomerNumber(dto.getCustomerNumber()).isPresent()) {
            throw new RecordAlreadyExistsException("Klantnummer staat al in de database");
        } else {
            Customer customer = transferCustomerInputDtoToCustomer(dto);

            customerRepository.save(customer);

            return transferCustomerToDto(customer);
        }
    }

    public void deleteCustomer(String cn) {
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByCustomerNumber(cn);
        if (optionalCustomer.isPresent()) {
            Long customer = optionalCustomer.get().getId();
            customerRepository.deleteById(customer);
        } else {
            throw new RecordNotFoundException("Geen klant gevonden met dit klantnummer");
        }
    }

    public void patchCustomer(String cn, CustomerInputDto newCustomer) {
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByCustomerNumber(cn);

        if (optionalCustomer.isPresent()) {

            Customer customer = optionalCustomer.get();

            if (newCustomer.getName() != null) {
                customer.setName(newCustomer.getName());
            }
            if (newCustomer.getCustomerNumber() != null) {
                customer.setCustomerNumber(newCustomer.getCustomerNumber());
            }
            if (newCustomer.getBrand() != null) {
                customer.setBrand(newCustomer.getBrand());
            }
            if (newCustomer.getEmail() != null) {
                customer.setEmail(newCustomer.getEmail());
            }
            if (newCustomer.getAdvisor() != null) {
                customer.setAdvisor(newCustomer.getAdvisor());
            }

            customerRepository.save(customer);

        } else {
            throw new RecordNotFoundException("Geen klant gevonden");
        }
    }

    public List<CustomerDto> transferCustomerListToDtoList(List<Customer> customers) {
        List<CustomerDto> customerDtoList = new ArrayList<>();

        for (Customer customer : customers) {
            CustomerDto dto = transferCustomerToDto(customer);
            if (customer.getAdvisor() != null) {
                dto.setAdvisorDto(advisorService.transferAdvisorToDto(customer.getAdvisor()));
            }
            customerDtoList.add(dto);
        }
        return customerDtoList;
    }

    public CustomerDto transferCustomerToDto(Customer customer) {
        CustomerDto dto = new CustomerDto();

        dto.setName(customer.getName());
        dto.setCustomerNumber(customer.getCustomerNumber());
        dto.setBrand(customer.getBrand());
        dto.setEmail(customer.getEmail());
        dto.setId(customer.getId());
        if (customer.getAdvisor() != null) {
            dto.setAdvisorDto(advisorService.transferAdvisorToDto(customer.getAdvisor()));
        }

        return dto;
    }

    public Customer transferCustomerInputDtoToCustomer(CustomerInputDto dto) {
        Customer customer = new Customer();

        customer.setName(dto.getName());
        customer.setCustomerNumber(dto.getCustomerNumber());
        customer.setBrand(dto.getBrand());
        customer.setEmail(dto.getEmail());
        customer.setAdvisor(dto.getAdvisor());

        return customer;
    }
}

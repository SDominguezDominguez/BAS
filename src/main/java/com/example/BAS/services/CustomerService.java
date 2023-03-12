package com.example.BAS.services;

import com.example.BAS.dtos.CustomerDto;
import com.example.BAS.dtos.CustomerInputDto;
import com.example.BAS.models.Customer;
import com.example.BAS.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository repo) {
        this.customerRepository = repo;
    }

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        return transferCustomerListToDtoList(customerList);
    }

    public CustomerDto getCustomerByCustomerNumber(String customerNumber) {
        Customer customer = customerRepository.findCustomerByCustomerNumber(customerNumber);

        return transferCustomerToDto(customer);
    }

    public CustomerDto createCustomer(CustomerInputDto dto) {
        Customer customer = transferCustomerInputDtoToCustomer(dto);

        customerRepository.save(customer);

        return transferCustomerToDto(customer);
    }

    public void deleteCustomer(String cn) {
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.findCustomerByCustomerNumber(cn));
        if (optionalCustomer.isPresent()) {
            Long customer = optionalCustomer.get().getId();
            customerRepository.deleteById(customer);
        }
    }

    public void patchCustomer(String cn, CustomerInputDto newCustomer) {
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.findCustomerByCustomerNumber(cn));

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

            customerRepository.save(customer);
        }
    }

    public List<CustomerDto> transferCustomerListToDtoList(List<Customer> customers) {
        List<CustomerDto> customerDtoList = new ArrayList<>();

        for (Customer customer : customers) {
            CustomerDto dto = transferCustomerToDto(customer);
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

        return dto;
    }

    public Customer transferCustomerInputDtoToCustomer(CustomerInputDto dto) {
        Customer customer = new Customer();

        customer.setName(dto.getName());
        customer.setCustomerNumber(dto.getCustomerNumber());
        customer.setBrand(dto.getBrand());
        customer.setEmail(dto.getEmail());

        return customer;
    }
}

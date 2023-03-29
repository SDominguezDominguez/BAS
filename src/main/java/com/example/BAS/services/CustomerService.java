package com.example.BAS.services;

import com.example.BAS.dtos.CustomerDto;
import com.example.BAS.dtos.CustomerInputDto;
import com.example.BAS.exceptions.CustomerNotFoundException;
import com.example.BAS.exceptions.RecordAlreadyExistsException;
import com.example.BAS.helpers.CustomerHelper;
import com.example.BAS.models.Customer;
import com.example.BAS.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerDto> getAllCustomers() {

        List<Customer> customers = customerRepository.findAll();

        return CustomerHelper.transferCustomerListToDtoList(customers);
    }

    public CustomerDto createCustomer(CustomerInputDto dto) {

        if (customerRepository.findCustomerByCustomerNumber(dto.getCustomerNumber()).isPresent()) {

            throw new RecordAlreadyExistsException(dto.getCustomerNumber());

        } else {

            Customer customer = CustomerHelper.transferCustomerInputDtoToCustomer(dto);

            customerRepository.save(customer);

            return CustomerHelper.transferCustomerToDto(customer);
        }
    }

    public void changeCustomer(Long id, CustomerInputDto dto) {

        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isPresent()) {

            Customer customer = optionalCustomer.get();

            if (dto.getName() != null) {
                customer.setName(dto.getName());
            }

            if (dto.getCustomerNumber() != null) {
                customer.setCustomerNumber(dto.getCustomerNumber());
            }

            if (dto.getLabel() != null) {
                customer.setLabel(dto.getLabel());
            }

            if (dto.getEmail() != null) {
                customer.setEmail(dto.getEmail());
            }

            customerRepository.save(customer);

        } else {

            throw new CustomerNotFoundException("id " + id);
        }
    }

    public void deleteCustomer(Long id) {

        Optional<Customer> optionalCustomer = customerRepository.findById(id);

        if (optionalCustomer.isPresent()) {

            customerRepository.deleteById(id);

        } else {
            throw new CustomerNotFoundException("id " + id);
        }
    }
}

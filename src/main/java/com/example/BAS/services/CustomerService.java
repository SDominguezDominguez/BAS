package com.example.BAS.services;

import com.example.BAS.dtos.CustomerDto;
import com.example.BAS.dtos.CustomerForAdvisorDto;
import com.example.BAS.dtos.CustomerInputDto;
import com.example.BAS.exceptions.CustomerNotFoundException;
import com.example.BAS.exceptions.RecordAlreadyExistsException;
import com.example.BAS.exceptions.RecordNotFoundException;
import com.example.BAS.exceptions.UsernameNotFoundException;
import com.example.BAS.helpers.CustomerHelper;
import com.example.BAS.models.Customer;
import com.example.BAS.models.User;
import com.example.BAS.repositories.CustomerRepository;
import com.example.BAS.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public CustomerService(CustomerRepository customerRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
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

    public CustomerDto getCustomerByCustomerNumber(String customerNumber) {

        Optional<Customer> optionalCustomer = customerRepository.findCustomerByCustomerNumber(customerNumber);

        if (optionalCustomer.isPresent()) {

            Customer customer = optionalCustomer.get();

            return CustomerHelper.transferCustomerToDto(customer);

        } else {

            throw new CustomerNotFoundException("klantnummer " + customerNumber);
        }
    }

    public List<CustomerDto> getCustomersByNameContaining(String name) {

        Optional<List<Customer>> optionalCustomers = customerRepository.findCustomerByNameContainingIgnoreCase(name);

        if (optionalCustomers.isPresent() && optionalCustomers.get().size() > 0) {

            List<Customer> customers = optionalCustomers.get();

            return CustomerHelper.transferCustomerListToDtoList(customers);

        } else {

            throw new CustomerNotFoundException("naam " + name);
        }
    }

    public void assignAdvisorOfficeToCustomer(Long id, Long officeNumber) {

        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        Optional<List<User>> optionalUsers = userRepository.findUsersByOfficeNumber(officeNumber);

        if (optionalCustomer.isPresent() && optionalUsers.isPresent()) {

            Customer customer = optionalCustomer.get();

            customer.setOfficeNumber(officeNumber);

            customerRepository.save(customer);

        } else if (optionalCustomer.isPresent()) {

            throw new RecordNotFoundException("Kantoor niet gevonden");

        } else {

            throw new RecordNotFoundException("Klant en kantoor niet gevonden");
        }
    }

    public void assignAdvisorAndOfficeToCustomer(Long id, Long advisorNumber) {

        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        Optional<User> optionalUser = userRepository.findUserByAdvisorNumber(advisorNumber);

        if (optionalCustomer.isPresent() && optionalUser.isPresent()) {

            Customer customer = optionalCustomer.get();
            User user = optionalUser.get();

            customer.setAdvisorNumber(advisorNumber);
            customer.setOfficeNumber(user.getOfficeNumber());

            customerRepository.save(customer);

        } else if (optionalCustomer.isPresent()) {

            throw new RecordNotFoundException("Adviseur niet gevonden");

        } else {

            throw new RecordNotFoundException("Klant en adviseur niet gevonden");
        }
    }

    public List<CustomerForAdvisorDto> getCustomersForAdvisorByOfficeNumber(String username) {

        Optional<User> user = userRepository.findById(username);

        if (user.isPresent()) {

            Long officeNumber = user.get().getOfficeNumber();

            Optional<List<Customer>> optionalCustomers = customerRepository.findCustomersByOfficeNumber(officeNumber);

            if (optionalCustomers.isPresent() && optionalCustomers.get().size() > 0) {

                return CustomerHelper.transferCustomerListToForAdvisorDtoList(optionalCustomers.get());

            } else {

                throw new RecordNotFoundException("Geen klanten bij kantoor " + officeNumber + " gevonden");
            }
        } else {

            throw new UsernameNotFoundException(username);
        }
    }

    public CustomerForAdvisorDto getCustomerForAdvisorByCustomerNumberForOffice(String customerNumber, String username) {

        Optional<User> user = userRepository.findById(username);

        if (user.isPresent()) {

            Long officeNumber = user.get().getOfficeNumber();

            Optional<List<Customer>> optionalCustomers = customerRepository.findCustomersByOfficeNumber(officeNumber);

            if (optionalCustomers.isPresent() && optionalCustomers.get().size() > 0) {

                List<Customer> customers = optionalCustomers.get();

                Customer foundCustomer = new Customer();

                for (Customer customer : customers) {

                    if (customer.getCustomerNumber().equals(customerNumber)) {
                        foundCustomer = customer;
                    }
                }

                return CustomerHelper.transferCustomerToForAdvisorDto(foundCustomer);

            } else {

                throw new CustomerNotFoundException("klantnummer " + customerNumber);
            }
        } else {

            throw new UsernameNotFoundException(username);
        }
    }

    public CustomerForAdvisorDto getCustomersForAdvisorByCustomerNumberForAdvisor(String customerNumber, String username) {

        Optional<User> user = userRepository.findById(username);

        if (user.isPresent()) {

            Long advisorNumber = user.get().getAdvisorNumber();

            Optional<List<Customer>> optionalCustomers = customerRepository.findCustomersByAdvisorNumber(advisorNumber);

            if (optionalCustomers.isPresent() && optionalCustomers.get().size() > 0) {

                Customer foundCustomer = new Customer();

                for (Customer customer : optionalCustomers.get()) {

                    if (customer.getCustomerNumber().equals(customerNumber)) {
                        foundCustomer = customer;
                    }
                }

                return CustomerHelper.transferCustomerToForAdvisorDto(foundCustomer);

            } else {

                throw new RecordNotFoundException("Geen klanten voor adviseur " + advisorNumber + " gevonden");
            }
        } else {

            throw new UsernameNotFoundException(username);
        }
    }

    public List<CustomerForAdvisorDto> getCustomersForAdvisorByAdvisorNumber(String username) {

        Optional<User> user = userRepository.findById(username);

        if (user.isPresent()) {

            Long advisorNumber = user.get().getAdvisorNumber();

            Optional<List<Customer>> optionalCustomers = customerRepository.findCustomersByAdvisorNumber(advisorNumber);

            if (optionalCustomers.isPresent() && optionalCustomers.get().size() > 0) {

                return CustomerHelper.transferCustomerListToForAdvisorDtoList(optionalCustomers.get());

            } else {

                throw new RecordNotFoundException("Geen klanten voor adviseur " + advisorNumber + " gevonden");
            }
        } else {

            throw new UsernameNotFoundException(username);
        }
    }
}

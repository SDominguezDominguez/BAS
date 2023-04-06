package com.example.BAS.services;

import com.example.BAS.dtos.CustomerDto;
import com.example.BAS.dtos.CustomerForAdvisorDto;
import com.example.BAS.dtos.CustomerInputDto;
import com.example.BAS.enumerations.Label;
import com.example.BAS.exceptions.*;
import com.example.BAS.helpers.CustomerHelper;
import com.example.BAS.models.Customer;
import com.example.BAS.models.File;
import com.example.BAS.models.User;
import com.example.BAS.repositories.CustomerRepository;
import com.example.BAS.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Mock
    UserRepository userRepository;

    @Captor
    ArgumentCaptor<Customer> captor;

    Customer customer1;
    Customer customer2;
    CustomerInputDto inputDto;
    User user1;
    User user2;
    File file1;
    File file2;
    List<File> files;

    @BeforeEach
    void setUp() {

        file1 = new File();
        file1.setCustomer(customer1);
        file1.setId(1L);

        file2 = new File();
        file2.setCustomer(customer1);
        file2.setId(2L);

        files = new ArrayList<>();
        files.add(file1);
        files.add(file2);

        customer1 = new Customer(1L, "Test", "12345", Label.SNS_BANK, "test@test.nl");
        customer1.setFiles(files);

        customer2 = new Customer(2L, "Tester 2", "34566", Label.REGIOBANK, "tester2@test.nl");

        user1 = new User("advisor 1", "password", "123456", "adviseur@test.nl", 27L);

        user2 = new User("advisor 2", "password2", "987654", "adviseur2@test.nl", 27L, 985L);

        inputDto = new CustomerInputDto("Test", "123", Label.SNS_BANK, "test@test.nl");
    }

    @Test
    void getAllCustomers() {

        when(customerRepository.findAll()).thenReturn(List.of(customer1, customer2));

        List<Customer> foundCustomers = CustomerHelper.transferCustomerDtoListToCustomerList(customerService.getAllCustomers());

        assertEquals(customer1.getName(), foundCustomers.get(0).getName());
        assertEquals(customer2.getName(), foundCustomers.get(1).getName());
    }

    @Test
    void createCustomer() {

        lenient().when(customerRepository.save(CustomerHelper.transferCustomerInputDtoToCustomer(inputDto))).thenReturn(CustomerHelper.transferCustomerInputDtoToCustomer(inputDto));

        customerService.createCustomer(inputDto);

        verify(customerRepository, times(1)).save(captor.capture());

        Customer customer = captor.getValue();

        assertEquals(inputDto.getName(), customer.getName());
        assertEquals(inputDto.getCustomerNumber(), customer.getCustomerNumber());
        assertEquals(inputDto.getLabel(), customer.getLabel());
        assertEquals(inputDto.getEmail(), customer.getEmail());
    }

    @Test
    void createCustomerThrowsException() {

        lenient().when(customerRepository.findCustomerByCustomerNumber("123")).thenReturn(Optional.of(customer1));

        assertThrows(RecordAlreadyExistsException.class, () -> customerService.createCustomer(inputDto));
    }

    @Test
    void changeCustomer() {

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));

        lenient().when(customerRepository.save(CustomerHelper.transferCustomerInputDtoToCustomer(inputDto))).thenReturn(customer1);

        customerService.changeCustomer(1L, inputDto);

        verify(customerRepository, times(1)).save(captor.capture());

        Customer captured = captor.getValue();

        assertEquals(inputDto.getName(), captured.getName());
        assertEquals(inputDto.getCustomerNumber(), captured.getCustomerNumber());
        assertEquals(inputDto.getLabel(), captured.getLabel());
        assertEquals(inputDto.getEmail(), captured.getEmail());
    }

    @Test
    void changeCustomerThrowsExceptionTest() {

        assertThrows(CustomerNotFoundException.class, () -> customerService.changeCustomer(null, inputDto));
    }

    @Test
    void deleteCustomer() {

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));

        customerService.deleteCustomer(1L);

        verify(customerRepository).deleteById(1L);
    }

    @Test
    void deleteCompanyThrowsExceptionTest() {

        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(null));
    }

    @Test
    void getCustomerByCustomerNumber() {

        when(customerRepository.findCustomerByCustomerNumber("12345")).thenReturn(Optional.of(customer1));

        CustomerDto foundCustomer = customerService.getCustomerByCustomerNumber("12345");

        assertEquals(customer1.getCustomerNumber(), foundCustomer.getCustomerNumber());
    }

    @Test
    void getCustomerByCustomerNumberThrowsException() {

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerByCustomerNumber(null));
    }

    @Test
    void getCustomersByNameContaining() {

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        when(customerRepository.findCustomerByNameContainingIgnoreCase("tes")).thenReturn(Optional.of(customers));

        List<CustomerDto> foundCustomers = customerService.getCustomersByNameContaining("tes");

        assertEquals(customer1.getName(), foundCustomers.get(0).getName());
        assertEquals(customer2.getName(), foundCustomers.get(1).getName());
    }

    @Test
    void getCustomersByNameContainingThrowsException() {

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomersByNameContaining(null));
    }

    @Test
    void assignAdvisorOfficeToCustomer() {

        Customer customer = new Customer();
        List<User> users = new ArrayList<>();
        users.add(user1);

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(userRepository.findUsersByOfficeNumber(27L)).thenReturn(Optional.of(users));
        when(customerRepository.save(customer)).thenReturn(customer);

        customerService.assignAdvisorOfficeToCustomer(1L, 27L);

        verify(customerRepository, times(1)).save(captor.capture());

        assertEquals(27L, customer.getOfficeNumber());
    }

    @Test
    void assignAdvisorOfficeToCustomerThrowsRecordNotFoundException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));


        assertThrows(RecordNotFoundException.class, () -> customerService.assignAdvisorOfficeToCustomer(1L, null));

        assertThrows(RecordNotFoundException.class, () -> customerService.assignAdvisorOfficeToCustomer(null, 27L));
    }

    @Test
    void assignAdvisorAndOfficeToCustomer() {
        Customer customer = new Customer();
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        lenient().when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        lenient().when(userRepository.findUsersByOfficeNumber(27L)).thenReturn(Optional.of(users));
        lenient().when(userRepository.findUserByAdvisorNumber(985L)).thenReturn(Optional.of(user2));
        lenient().when(customerRepository.save(customer)).thenReturn(customer);

        customerService.assignAdvisorAndOfficeToCustomer(1L, 985L);

        verify(customerRepository, times(1)).save(captor.capture());

        assertEquals(985L, customer.getAdvisorNumber());
        assertEquals(27L, customer.getOfficeNumber());
    }

    @Test
    void assignAdvisorAndAdvisorOfficeToCustomerThrowsRecordNotFoundException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));


        assertThrows(RecordNotFoundException.class, () -> customerService.assignAdvisorAndOfficeToCustomer(1L, null));

        assertThrows(RecordNotFoundException.class, () -> customerService.assignAdvisorAndOfficeToCustomer(null, null));
    }

    @Test
    void getCustomersForAdvisorByOfficeNumber() {

        List<Customer> customers = new ArrayList<>();
        customer1.setFiles(files);
        customers.add(customer1);

        when(userRepository.findById("advisor 1")).thenReturn(Optional.of(user1));
        when(customerRepository.findCustomersByOfficeNumber(27L)).thenReturn(Optional.of(customers));

        List<CustomerForAdvisorDto> foundCustomers = customerService.getCustomersForAdvisorByOfficeNumber("advisor 1");

        assertEquals(customer1.getName(), foundCustomers.get(0).getName());

    }

    @Test
    void getCustomersForAdvisorByOfficeNumberThrowsExceptions() {

        List<Customer> customers = new ArrayList<>();

        when(userRepository.findById("advisor 1")).thenReturn(Optional.ofNullable(user1));
        lenient().when(customerRepository.findCustomersByOfficeNumber(27L)).thenReturn(Optional.of(customers));

        assertThrows(RecordNotFoundException.class, () -> customerService.getCustomersForAdvisorByOfficeNumber("advisor 1"));

        assertThrows(UsernameNotFoundException.class, () -> customerService.getCustomersForAdvisorByOfficeNumber(null));
    }

    @Test
    void getCustomerForAdvisorByCustomerNumberForOffice() {

        customer1.setOfficeNumber(27L);
        customer1.setFiles(files);

        List<Customer> customers2 = new ArrayList<>();
        customers2.add(customer1);

        when(userRepository.findById("advisor 1")).thenReturn(Optional.of(user1));

        when(customerRepository.findCustomersByOfficeNumber(27L)).thenReturn(Optional.of(customers2));

        CustomerForAdvisorDto foundCustomer = customerService.getCustomerForAdvisorByCustomerNumberForOffice("12345", "advisor 1");

        assertEquals(customer1.getName(), foundCustomer.getName());
    }

    @Test
    void getCustomerForAdvisorByCustomerNumberForOfficeThrowsExceptions() {

        List<Customer> customers = new ArrayList<>();

        when(userRepository.findById("advisor 1")).thenReturn(Optional.ofNullable(user1));

        lenient().when(customerRepository.findCustomersByOfficeNumber(27L)).thenReturn(Optional.of(customers));

        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerForAdvisorByCustomerNumberForOffice("12345","advisor 1"));

        assertThrows(UsernameNotFoundException.class, () -> customerService.getCustomerForAdvisorByCustomerNumberForOffice("12345",null));
    }

    @Test
    void getCustomersForAdvisorByCustomerNumberForAdvisor() {

        customer1.setAdvisorNumber(985L);
        customer1.setFiles(files);

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        when(userRepository.findById("advisor 2")).thenReturn(Optional.of(user2));

        when(customerRepository.findCustomersByAdvisorNumber(985L)).thenReturn(Optional.of(customers));

        CustomerForAdvisorDto foundCustomer = customerService.getCustomersForAdvisorByCustomerNumberForAdvisor("12345", "advisor 2");

        assertEquals(customer1.getName(), foundCustomer.getName());
    }

    @Test
    void getCustomersForAdvisorByCustomerNumberForAdvisorThrowsExceptions() {

        List<Customer> customers = new ArrayList<>();

        when(userRepository.findById("advisor 1")).thenReturn(Optional.ofNullable(user1));

        lenient().when(customerRepository.findCustomersByOfficeNumber(27L)).thenReturn(Optional.of(customers));

        assertThrows(RecordNotFoundException.class, () -> customerService.getCustomersForAdvisorByCustomerNumberForAdvisor("123", "advisor 1"));

        assertThrows(UsernameNotFoundException.class, () -> customerService.getCustomersForAdvisorByCustomerNumberForAdvisor("123", null));
    }

    @Test
    void getCustomersForAdvisorByAdvisorNumber() {

        List<Customer> customers = new ArrayList<>();
        customer1.setFiles(files);
        customers.add(customer1);

        when(userRepository.findById("advisor 2")).thenReturn(Optional.of(user2));

        when(customerRepository.findCustomersByAdvisorNumber(985L)).thenReturn(Optional.of(customers));

        List<CustomerForAdvisorDto> foundCustomers = customerService.getCustomersForAdvisorByAdvisorNumber("advisor 2");

        assertEquals(customer1.getName(), foundCustomers.get(0).getName());

    }

    @Test
    void getCustomersForAdvisorByAdvisorNumberThrowsExceptions() {

        List<Customer> customers = new ArrayList<>();

        when(userRepository.findById("advisor 1")).thenReturn(Optional.of(user1));

        lenient().when(customerRepository.findCustomersByOfficeNumber(27L)).thenReturn(Optional.of(customers));

        assertThrows(RecordNotFoundException.class, () -> customerService.getCustomersForAdvisorByAdvisorNumber("advisor 1"));

        assertThrows(UsernameNotFoundException.class, () -> customerService.getCustomersForAdvisorByAdvisorNumber(null));
    }
}
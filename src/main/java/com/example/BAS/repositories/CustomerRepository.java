package com.example.BAS.repositories;

import com.example.BAS.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findCustomersByCustomerNumber(String customerNumber);

    Customer findCustomerByCustomerNumber(String customerNumber);
}

package com.example.BAS.repositories;

import com.example.BAS.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByCustomerNumber(String customerNumber);

    Optional<List<Customer>> findCustomerByNameContaining(String name);
}

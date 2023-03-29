package com.example.BAS.repositories;

import com.example.BAS.enumerations.Label;
import com.example.BAS.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findCustomerByCustomerNumber(String customerNumber);

    Optional<List<Customer>> findCustomerByNameContainingIgnoreCase(String name);

    Optional<List<Customer>> findCustomerByLabel(Label label);

    Optional<List<Customer>> findCustomersByOfficeNumber(Long officeNumber);

    Optional<List<Customer>> findCustomersByAdvisorNumber(Long advisorNumber);
}

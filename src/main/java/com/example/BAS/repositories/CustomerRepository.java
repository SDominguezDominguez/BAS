package com.example.BAS.repositories;

import com.example.BAS.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findCustomerByCustomerNumber(String customerNumber);
}

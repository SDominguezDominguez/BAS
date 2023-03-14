package com.example.BAS.helpers;

import com.example.BAS.dtos.CustomerDto;
import com.example.BAS.models.Customer;

public class TransferCustomerToDto {

    public static CustomerDto transferCustomerToDto(Customer customer) {
            CustomerDto dto = new CustomerDto();

            dto.setName(customer.getName());
            dto.setCustomerNumber(customer.getCustomerNumber());
            dto.setBrand(customer.getBrand());
            dto.setEmail(customer.getEmail());
            dto.setId(customer.getId());

            return dto;
        }
    }
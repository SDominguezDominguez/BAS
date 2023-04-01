package com.example.BAS.helpers;

import com.example.BAS.dtos.CustomerDto;
import com.example.BAS.dtos.CustomerForAdvisorDto;
import com.example.BAS.dtos.CustomerInputDto;
import com.example.BAS.dtos.FileDto;
import com.example.BAS.models.Customer;
import com.example.BAS.models.File;

import java.util.ArrayList;
import java.util.List;

public class CustomerHelper {

    public static List<CustomerDto> transferCustomerListToDtoList(List<Customer> customers) {

        List<CustomerDto> customerDtoList = new ArrayList<>();

        for (Customer customer : customers) {

            CustomerDto dto = transferCustomerToDto(customer);

            customerDtoList.add(dto);
        }

        return customerDtoList;
    }

    public static CustomerDto transferCustomerToDto(Customer customer) {

        CustomerDto dto = new CustomerDto();

        dto.setName(customer.getName());
        dto.setCustomerNumber(customer.getCustomerNumber());
        dto.setLabel(customer.getLabel());
        dto.setEmail(customer.getEmail());
        dto.setId(customer.getId());

        if (customer.getFiles() != null) {

            List<File> files = customer.getFiles();
            List<FileDto> dtos = new ArrayList<>();

            for (File file : files) {

                FileDto fileDto = new FileDto();

                fileDto.setComment(file.getComment());
                fileDto.setId(file.getId());
                fileDto.setFileType(file.getFileType());
                fileDto.setStatus(file.getStatus());
                fileDto.setStatusComment(file.getStatusComment());
                fileDto.setContractAmount(file.getContractAmount());
                fileDto.setApplicationFormPresent(file.isApplicationFormPresent());

                dtos.add(fileDto);
            }

            dto.setFileDto(dtos);
        }

        return dto;
    }

    public static Customer transferCustomerInputDtoToCustomer(CustomerInputDto dto) {

        Customer customer = new Customer();

        customer.setName(dto.getName());
        customer.setCustomerNumber(dto.getCustomerNumber());
        customer.setLabel(dto.getLabel());
        customer.setEmail(dto.getEmail());

        return customer;
    }

    public static List<CustomerForAdvisorDto> transferCustomerListToForAdvisorDtoList(List<Customer> customers) {

        List<CustomerForAdvisorDto> customerForAdvisorDtos = new ArrayList<>();

        for (Customer customer : customers) {

            CustomerForAdvisorDto dto = transferCustomerToForAdvisorDto(customer);

            customerForAdvisorDtos.add(dto);
        }

        return customerForAdvisorDtos;
    }

    public static CustomerForAdvisorDto transferCustomerToForAdvisorDto(Customer customer) {

        CustomerForAdvisorDto dto = new CustomerForAdvisorDto();

        dto.setName(customer.getName());
        dto.setCustomerNumber(customer.getCustomerNumber());
        dto.setEmail(customer.getEmail());

        if (customer.getFiles().size() > 0 ) {

            dto.setFileDto(FileHelper.transferFileListToForAdvisorDtoList(customer.getFiles()));
        }

        return dto;
    }
}

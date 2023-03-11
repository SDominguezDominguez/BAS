package com.example.BAS.controllers;

import com.example.BAS.dtos.CustomerDto;
import com.example.BAS.dtos.CustomerInputDto;
import com.example.BAS.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> dtos = customerService.getAllCustomers();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{cn}")
    public CustomerDto getCustomerByCustomerNumber(@PathVariable String cn) {
        return customerService.getCustomerByCustomerNumber(cn);
    }


    @PostMapping("")
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody CustomerInputDto inputDto) {
        Long createdId = customerService.createCustomer(inputDto).getId();

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("customers/" + createdId)
                        .toUriString()
        );

        return ResponseEntity.created(uri).body("Klant aangemaakt");
    }

    @PatchMapping("")
    public ResponseEntity<Object> changeCustomer(@RequestParam(value = "customerNumber") String customerNumber, @RequestBody CustomerInputDto inputDto) {
        customerService.patchCustomer(customerNumber, inputDto);
        return ResponseEntity.ok("Klantgegevens gewijzigd");
    }

    @DeleteMapping("")
    public void deleteCustomer(@RequestParam(value = "customerNumber") String customerNumber) {
        customerService.deleteCustomer(customerNumber);
    }
}

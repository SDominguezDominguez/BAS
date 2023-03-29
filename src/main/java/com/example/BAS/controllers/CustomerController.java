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
@RequestMapping("customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {

        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PostMapping("")
    public ResponseEntity<Object> createCustomer(@Valid @RequestBody CustomerInputDto dto) {

        Long id = customerService.createCustomer(dto).getId();

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("customers/" + id)
                        .toUriString()
        );

        return ResponseEntity.created(uri).body("Klant aangemaakt");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> changeCustomer(@PathVariable Long id, @RequestBody CustomerInputDto dto) {

        customerService.changeCustomer(id, dto);

        return ResponseEntity.ok("Klantgegevens gewijzigd");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {

        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }
}

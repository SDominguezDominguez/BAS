package com.example.BAS.controllers;

import com.example.BAS.dtos.CustomerDto;
import com.example.BAS.dtos.CustomerForAdvisorDto;
import com.example.BAS.dtos.CustomerInputDto;
import com.example.BAS.exceptions.AuthenticationNotValid;
import com.example.BAS.services.CustomerService;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(readOnly = true)
    public ResponseEntity<List<CustomerDto>> getAllCustomers(@RequestParam(value = "name", required = false) String name) {

        if (name == null) {

            return ResponseEntity.ok(customerService.getAllCustomers());

        } else {

            return ResponseEntity.ok(customerService.getCustomersByNameContaining(name));
        }
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

    @GetMapping("/{cn}")
    @Transactional(readOnly = true)
    public ResponseEntity<CustomerDto> getCustomerByCustomerNumber(@PathVariable String cn) {

        return ResponseEntity.ok(customerService.getCustomerByCustomerNumber(cn));
    }

    @PostMapping("/{id}/advisors")
    public ResponseEntity<Object> assignAdvisorToCustomer(@PathVariable Long id, @RequestParam(required = false) Long officeNumber, @RequestParam(required = false) Long advisorNumber) {

        if (advisorNumber != null) {

            customerService.assignAdvisorAndOfficeToCustomer(id, advisorNumber);

        } else {

            customerService.assignAdvisorOfficeToCustomer(id, officeNumber);
        }

        return ResponseEntity.ok("Adviseur succesvol aan klant toegewezen");
    }

    @GetMapping("/advisors")
    @Transactional(readOnly = true)
    public ResponseEntity<List<CustomerForAdvisorDto>> getAllCustomersByAdvisorNumber() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails userDetails) {

            String username = userDetails.getUsername();

            return ResponseEntity.ok(customerService.getCustomersForAdvisorByAdvisorNumber(username));

        } else {

            throw new AuthenticationNotValid("Geen autorisatie voor toegang aanwezig");
        }
    }

    @GetMapping("/advisors/office")
    @Transactional(readOnly = true)
    public ResponseEntity<List<CustomerForAdvisorDto>> geAllCustomersByOfficeNumber() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails userDetails) {

            String username = userDetails.getUsername();

            return ResponseEntity.ok(customerService.getCustomersForAdvisorByOfficeNumber(username));

        } else {

            throw new AuthenticationNotValid("Geen autorisatie voor toegang aanwezig");
        }
    }

    @GetMapping("/advisors/{cn}")
    @Transactional(readOnly = true)
    public ResponseEntity<CustomerForAdvisorDto> getCustomerByCustomerNumberForAdvisor(@PathVariable String cn) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails userDetails) {

            String username = userDetails.getUsername();

            return ResponseEntity.ok(customerService.getCustomersForAdvisorByCustomerNumberForAdvisor(cn, username));

        } else {

            throw new AuthenticationNotValid("Geen autorisatie voor toegang aanwezig");
        }
    }

    @GetMapping("/advisors/office/{cn}")
    @Transactional(readOnly = true)
    public ResponseEntity<CustomerForAdvisorDto> getCustomerByCustomerNumberForOffice(@PathVariable String cn) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getPrincipal() instanceof UserDetails userDetails) {

            String username = userDetails.getUsername();

            return ResponseEntity.ok(customerService.getCustomerForAdvisorByCustomerNumberForOffice(cn, username));

        } else {

            throw new AuthenticationNotValid("Geen autorisatie voor toegang aanwezig");
        }
    }
}

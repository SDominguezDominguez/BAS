package com.example.BAS.controllers;

import com.example.BAS.dtos.AdvisorDto;
import com.example.BAS.dtos.AdvisorInputDto;
import com.example.BAS.dtos.CustomerDto;
import com.example.BAS.services.AdvisorService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/advisors")
public class AdvisorController {

    private final AdvisorService advisorService;

    public AdvisorController(AdvisorService advisorService) {
        this.advisorService = advisorService;
    }

    @GetMapping("")
    public ResponseEntity<List<AdvisorDto>> getAllAdvisors() {
        List<AdvisorDto> dtos = advisorService.getAllAdvisors();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/contains")
    public ResponseEntity<List<AdvisorDto>> getAdvisorByNameContaining(@RequestParam(value = "name") String name) {
            List<AdvisorDto> dtos = advisorService.getAdvisorsByNameContaining(name);
            return ResponseEntity.ok(dtos);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<List<CustomerDto>> getCustomersByAdvisorId(@PathVariable Long id) {
        return ResponseEntity.ok(advisorService.getAllCustomersByAdvisor(id));
    }

    @GetMapping("/customer/{cn}")
    public ResponseEntity<AdvisorDto> getAdvisorByCustomerNumber(@PathVariable String cn) {
        return ResponseEntity.ok(advisorService.getAdvisorByCustomerNumber(cn));
    }

    @PostMapping
    public ResponseEntity<Object> createAdvisor(@Valid @RequestBody AdvisorInputDto dto) {
        Long createdID = advisorService.createAdvisor(dto).getId();

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/advisors/" + createdID)
                        .toUriString()
        );

        return ResponseEntity.created(uri).body("Adviseur aangemaakt");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> changeAdvisor(@PathVariable Long id, @RequestBody AdvisorInputDto dto) {
        advisorService.patchAdvisor(id, dto);

        return ResponseEntity.ok("Gegevens adviseur gewijzigd");
    }

    @DeleteMapping("/{id}")
    public void deleteAdvisor(@PathVariable Long id) {
        advisorService.deleteAdvisor(id);
    }
}

package com.example.BAS.controllers;

import com.example.BAS.dtos.PolicyDto;
import com.example.BAS.dtos.PolicyInputDto;
import com.example.BAS.services.PolicyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("policies")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping("")
    public ResponseEntity<List<PolicyDto>> getAllPolicies() {

        return ResponseEntity.ok(policyService.getAllPolicies());

    }

    @PostMapping("")
    public ResponseEntity<Object> createPolicy(@Valid @RequestBody PolicyInputDto dto){

        Long id = policyService.createPolicy(dto).getId();

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/policies/" + id)
                        .toUriString()
        );

        return ResponseEntity.created(uri).body("Polis aangemaakt");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> changePolicy(@PathVariable Long id, @RequestBody PolicyInputDto dto) {

        policyService.changePolicy(id, dto);

        return ResponseEntity.ok("Polis gewijzigd");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePolicy(@PathVariable Long id) {

        policyService.deletePolicy(id);

        return ResponseEntity.noContent().build();
    }
}
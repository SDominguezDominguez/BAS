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
@RequestMapping("/policies")
public class PolicyController {
    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping("")
    public ResponseEntity<List<PolicyDto>> getAllPolicies() {
        List<PolicyDto> dtoList = policyService.getAllPolicies();
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("")
    public ResponseEntity<Object> createPolicy(@Valid @RequestBody PolicyInputDto policyInputDto){
        Long createdId = policyService.createPolicy(policyInputDto).getId();

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/policies/" + createdId)
                        .toUriString()
        );

        return ResponseEntity.created(uri).body("Polis aangemaakt");
    }

    @DeleteMapping("/{id}")
    public void deletePolicy(@PathVariable Long id) {
        policyService.deletePolicy(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> changePolicy(@PathVariable Long id, @RequestBody PolicyInputDto dto) {
        policyService.patchPolicy(id, dto);

        return ResponseEntity.ok("Polis gewijzigd");
    }
}

package com.example.BAS.controllers;

import com.example.BAS.dtos.PolicyDto;
import com.example.BAS.dtos.PolicyInputDto;
import com.example.BAS.services.PolicyService;
import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("policies")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping("")
    public ResponseEntity<List<PolicyDto>> getAllPolicies(@RequestParam(value = "date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        if (date == null) {

            return ResponseEntity.ok(policyService.getAllPolicies());

        } else {

            return ResponseEntity.ok(policyService.getAllPoliciesWhereReminderDateEqualsDate(date));
        }
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

    @GetMapping("/{policyNumber}")
    public ResponseEntity<PolicyDto> getPoliciesByPolicyNumber(@PathVariable String policyNumber) {

        return ResponseEntity.ok(policyService.getPolicyByPolicyNumber(policyNumber));
    }

    @GetMapping("/amounts")
    public ResponseEntity<List<PolicyDto>> getPoliciesWhereAmountIsReceivedWithoutPsk() {

        return ResponseEntity.ok(policyService.getPoliciesWhereAmountIsReceivedWithoutPsk());
    }

    @PutMapping("/{id}/file/{fileId}")
    public ResponseEntity<Object> assignPolicyToFile(@PathVariable Long id, @PathVariable Long fileId) {

       policyService.assignPolicyToFile(id, fileId);

       return ResponseEntity.ok("Polis aan dossier " + fileId + " gekoppeld");
    }

    @PutMapping("/{id}/company/{companyId}")
    public ResponseEntity<Object> assignCompanyToPolicy(@PathVariable Long id, @PathVariable Long companyId) {

        policyService.assignCompanyToPolicy(id, companyId);

        return ResponseEntity.ok("Polis aan maatschappij " + companyId + " gekoppeld");
    }
}

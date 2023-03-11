package com.example.BAS.controllers;

import com.example.BAS.dtos.CompanyDto;
import com.example.BAS.dtos.CompanyInputDto;
import com.example.BAS.services.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<CompanyDto>> getAllCompanies() {
        List<CompanyDto> dtos = companyService.getAllCompanies();

        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<Object> createCompany(@RequestBody CompanyInputDto inputDto) {
        Long createdId = companyService.createCompany(inputDto).getId();

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/companies/" + createdId)
                        .toUriString()
        );

        return ResponseEntity.created(uri).body("Maatschappij aangemaakt");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> changeCompany(@PathVariable Long id, @RequestBody CompanyInputDto dto) {
        companyService.patchCompany(id, dto);

        return ResponseEntity.ok("Gegevens maatschappij gewijzigd");
    }
}

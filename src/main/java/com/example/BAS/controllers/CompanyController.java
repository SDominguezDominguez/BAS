package com.example.BAS.controllers;

import com.example.BAS.dtos.CompanyDto;
import com.example.BAS.dtos.CompanyInputDto;
import com.example.BAS.services.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("")
    public ResponseEntity<List<CompanyDto>> getAllCompanies(@RequestParam(value = "name", required = false) String name) {

        if (name == null) {

            return ResponseEntity.ok(companyService.getAllCompanies());

        } else {

            return ResponseEntity.ok(companyService.getCompaniesByNameContaining(name));
        }
    }

    @PostMapping
    public ResponseEntity<Object> createCompany(@Valid @RequestBody CompanyInputDto inputDto) {

        Long id = companyService.createCompany(inputDto).getId();

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/companies/" + id)
                        .toUriString()
        );

        return ResponseEntity.created(uri).body("Maatschappij aangemaakt");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> changeCompany(@PathVariable Long id, @RequestBody CompanyInputDto dto) {

        companyService.changeCompany(id, dto);

        return ResponseEntity.ok("Gegevens maatschappij gewijzigd");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCompany(@PathVariable Long id) {

        companyService.deleteCompany(id);

        return ResponseEntity.ok().body("companie deleted");
    }
}

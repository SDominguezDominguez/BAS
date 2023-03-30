package com.example.BAS.repositories;

import com.example.BAS.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<List<Company>> getCompaniesByNameContainingIgnoreCase(String name);
}

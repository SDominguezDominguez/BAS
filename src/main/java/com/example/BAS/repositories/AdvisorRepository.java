package com.example.BAS.repositories;

import com.example.BAS.models.Advisor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdvisorRepository extends JpaRepository<Advisor, Long> {

    Optional<List<Advisor>> getAdvisorByNameContaining(String name);
}

package com.example.BAS.repositories;

import com.example.BAS.models.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    Optional<List<File>> findFilesByStatusIgnoreCase(String status);

}

package com.example.BAS.controllers;

import com.example.BAS.dtos.FileDto;
import com.example.BAS.dtos.FileInputDto;
import com.example.BAS.enumerations.Status;
import com.example.BAS.services.FileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("")
    public ResponseEntity<List<FileDto>> getAllFiles(@RequestParam(value = "status", required = false) Status status) {

        if (status == null) {

            return ResponseEntity.ok(fileService.getAllFiles());

        } else {

            return ResponseEntity.ok(fileService.getFilesWithStatus(status));
        }
    }

    @PostMapping("")
    public ResponseEntity<Object> createFile(@Valid @RequestBody FileInputDto fileInputDto) {

        Long id = fileService.createFile(fileInputDto).getId();

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/files/" + id)
                        .toUriString()
        );

        return ResponseEntity.created(uri).body("Dossier aangemaakt");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> changeFile(@PathVariable Long id, @RequestBody FileInputDto dto) {

        fileService.changeFile(id, dto);

        return ResponseEntity.ok("Dossier gewijzigd");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFile(@PathVariable Long id) {

        fileService.deleteFile(id);

        return ResponseEntity.noContent().build();
    }
}

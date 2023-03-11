package com.example.BAS.controllers;

import com.example.BAS.dtos.FileDto;
import com.example.BAS.dtos.FileInputDto;
import com.example.BAS.services.FileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("")
    public ResponseEntity<List<FileDto>> getAllFiles() {
        List<FileDto> dtoList = fileService.getAllFiles();
        return ResponseEntity.ok(dtoList);
    }

    @PostMapping("")
    public ResponseEntity<Object> createFile(@Valid @RequestBody FileInputDto fileInputDto) {
        Long createdId = fileService.createFile(fileInputDto).getId();

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/files/" + createdId)
                        .toUriString()
        );

        return ResponseEntity.created(uri).body("Dossier aangemaakt");
    }

    @DeleteMapping("/{id}")
    public void deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> changeFile(@PathVariable Long id, @RequestBody FileInputDto dto) {
        fileService.patchFile(id, dto);

        return ResponseEntity.ok("Dossier gewijzigd");
    }
}

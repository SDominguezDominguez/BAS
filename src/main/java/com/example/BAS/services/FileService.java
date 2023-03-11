package com.example.BAS.services;

import com.example.BAS.dtos.FileDto;
import com.example.BAS.dtos.FileInputDto;
import com.example.BAS.models.File;
import com.example.BAS.repositories.FileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<FileDto> getAllFiles() {
        List<File> files = fileRepository.findAll();
        return transferFileListToDtoList(files);
    }

    public FileDto createFile(FileInputDto inputDto) {
        File file = transferFileInputDtoToFile(inputDto);
        fileRepository.save(file);

        return transferFileToDto(file);
    }

    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }

    public void patchFile(Long id, FileInputDto dto) {
        Optional<File> optionalFile = fileRepository.findById(id);

        if (optionalFile.isPresent()) {
            File file = optionalFile.get();

            if (dto.getStatus() != null) {
                file.setStatus(dto.getStatus());
            }
            if (dto.getStatusComment() != null) {
                file.setStatusComment(dto.getStatusComment());
            }
            if (dto.getComment() != null) {
                file.setComment(dto.getComment());
            }
            if (dto.getFileType() != null) {
                file.setFileType(dto.getFileType());
            }
            if (dto.getContractAmount() != null) {
                file.setContractAmount(dto.getContractAmount());
            }
            fileRepository.save(file);
        }
    }

    public List<FileDto> transferFileListToDtoList(List<File> files) {
        List<FileDto> fileDtoList = new ArrayList<>();

        for (File file : files) {
            FileDto dto = transferFileToDto(file);
            fileDtoList.add(dto);
        }

        return fileDtoList;
    }

    public FileDto transferFileToDto(File file) {
        FileDto dto = new FileDto();

        dto.setComment(file.getComment());
        dto.setId(file.getId());
        dto.setFileType(file.getFileType());
        dto.setStatus(file.getStatus());
        dto.setStatusComment(file.getStatusComment());
        dto.setContractAmount(file.getContractAmount());

        return dto;
    }

    public File transferFileInputDtoToFile(FileInputDto dto) {
        File file = new File();

        file.setFileType(dto.getFileType());
        file.setStatus(dto.getStatus());
        file.setStatusComment(dto.getStatusComment());
        file.setComment(dto.getComment());
        file.setContractAmount(dto.getContractAmount());

        return file;
    }
}

package com.example.BAS.services;

import com.example.BAS.dtos.FileDto;
import com.example.BAS.dtos.FileInputDto;
import com.example.BAS.enumerations.Status;
import com.example.BAS.exceptions.FileNotFoundException;
import com.example.BAS.helpers.FileHelper;
import com.example.BAS.models.File;
import com.example.BAS.repositories.FileRepository;
import org.springframework.stereotype.Service;

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

        return FileHelper.transferFileListToDtoList(files);
    }

    public FileDto createFile(FileInputDto dto) {

        File file = FileHelper.transferFileInputDtoToFile(dto);

        fileRepository.save(file);

        return FileHelper.transferFileToDto(file);
    }

    public void changeFile(Long id, FileInputDto dto) {

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

        } else {

            throw new FileNotFoundException("id " + id);
        }
    }

    public void deleteFile(Long id) {

        if (fileRepository.findById(id).isPresent()) {

            fileRepository.deleteById(id);

        } else {

            throw new FileNotFoundException("id " + id);
        }
    }

    public List<FileDto> getFilesWithStatus(Status status) {

        Optional<List<File>> optionalFiles = fileRepository.findFilesByStatus(status);

        if (optionalFiles.isPresent() && optionalFiles.get().size() > 0) {

            return FileHelper.transferFileListToDtoList(optionalFiles.get());

        } else {

            throw new FileNotFoundException("status " + status);
        }
    }
}

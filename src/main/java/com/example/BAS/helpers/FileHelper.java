package com.example.BAS.helpers;

import com.example.BAS.dtos.FileDto;
import com.example.BAS.dtos.FileInputDto;
import com.example.BAS.models.File;

import java.util.ArrayList;
import java.util.List;

public class FileHelper {

    public static List<FileDto> transferFileListToDtoList(List<File> files) {

        List<FileDto> fileDtoList = new ArrayList<>();

        for (File file : files) {

            FileDto dto = transferFileToDto(file);

            fileDtoList.add(dto);
        }

        return fileDtoList;
    }

    public static FileDto transferFileToDto(File file) {

        FileDto dto = new FileDto();

        dto.setComment(file.getComment());
        dto.setId(file.getId());
        dto.setFileType(file.getFileType());
        dto.setStatus(file.getStatus());
        dto.setStatusComment(file.getStatusComment());
        dto.setContractAmount(file.getContractAmount());

        return dto;
    }

    public static File transferFileInputDtoToFile(FileInputDto dto) {

        File file = new File();

        file.setFileType(dto.getFileType());
        file.setStatus(dto.getStatus());
        file.setStatusComment(dto.getStatusComment());
        file.setComment(dto.getComment());
        file.setContractAmount(dto.getContractAmount());

        return file;
    }
}

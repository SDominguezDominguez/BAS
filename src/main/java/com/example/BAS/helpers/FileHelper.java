package com.example.BAS.helpers;

import com.example.BAS.dtos.FileDto;
import com.example.BAS.dtos.FileForAdvisorDto;
import com.example.BAS.dtos.FileInputDto;
import com.example.BAS.dtos.PolicyDto;
import com.example.BAS.models.File;
import com.example.BAS.models.Policy;

import java.util.ArrayList;
import java.util.List;

public class FileHelper {

    public static List<FileDto> transferFileListToDtoList(List<File> files) {

        List<FileDto> fileDtoList = new ArrayList<>();

        for (File file : files) {

            FileDto dto = transferFileToDto(file);

            if (file.getPolicies() != null) {

                List<Policy> policies = file.getPolicies();
                List<PolicyDto> dtos = new ArrayList<>();

                for (Policy policy : policies) {

                    dtos.add(PolicyHelper.transferPolicyToDto(policy));
                }

                dto.setPolicyDtos(dtos);
            }

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
        dto.setApplicationFormPresent(file.isApplicationFormPresent());

        if (file.getCustomer() != null) {
            dto.setCustomerDto(CustomerHelper.transferCustomerToDto(file.getCustomer()));
        }

        return dto;
    }

    public static File transferFileInputDtoToFile(FileInputDto dto) {

        File file = new File();

        file.setFileType(dto.getFileType());
        file.setStatus(dto.getStatus());
        file.setStatusComment(dto.getStatusComment());
        file.setComment(dto.getComment());
        file.setContractAmount(dto.getContractAmount());
        file.setApplicationFormPresent(false);

        return file;
    }

    public static List<FileForAdvisorDto> transferFileListToForAdvisorDtoList(List<File> files) {

        List<FileForAdvisorDto> fileForAdvisorDtos = new ArrayList<>();

        for (File file : files) {

            FileForAdvisorDto dto = transferFileToForAdvisorDto(file);

            fileForAdvisorDtos.add(dto);
        }

        return fileForAdvisorDtos;
    }

    public static FileForAdvisorDto transferFileToForAdvisorDto(File file) {

        FileForAdvisorDto dto = new FileForAdvisorDto();

        dto.setFileType(String.valueOf(file.getFileType()));
        dto.setStatus(String.valueOf(file.getStatus()));
        dto.setStatusComment(file.getStatusComment());
        dto.setApplicationFormPresent(file.isApplicationFormPresent());

        return dto;
    }

    public static File transferFileDtoToFile(FileDto dto) {

        File file = new File();

        file.setId(dto.getId());
        file.setStatus(dto.getStatus());
        file.setStatusComment(dto.getStatusComment());
        file.setComment(dto.getComment());
        file.setFileType(dto.getFileType());
        file.setContractAmount(dto.getContractAmount());

        return file;
    }

    public static List<File> transferFileDtoListToFileList(List<FileDto> files) {

        List<File> fileList = new ArrayList<>();

        for (FileDto dto : files) {

            fileList.add(transferFileDtoToFile(dto));
        }

        return fileList;
    }
}

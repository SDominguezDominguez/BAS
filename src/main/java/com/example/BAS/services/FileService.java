package com.example.BAS.services;

import com.example.BAS.dtos.FileDto;
import com.example.BAS.dtos.FileInputDto;
import com.example.BAS.enumerations.Label;
import com.example.BAS.enumerations.Status;
import com.example.BAS.exceptions.CustomerNotFoundException;
import com.example.BAS.exceptions.FileNotFoundException;
import com.example.BAS.exceptions.RecordNotFoundException;
import com.example.BAS.helpers.FileHelper;
import com.example.BAS.models.Customer;
import com.example.BAS.models.File;
import com.example.BAS.models.Policy;
import com.example.BAS.repositories.CustomerRepository;
import com.example.BAS.repositories.FileRepository;
import com.example.BAS.repositories.PolicyRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final CustomerRepository customerRepository;
    private final PolicyRepository policyRepository;

    public FileService(FileRepository fileRepository, CustomerRepository customerRepository, PolicyRepository policyRepository) {
        this.fileRepository = fileRepository;
        this.customerRepository = customerRepository;
        this.policyRepository = policyRepository;
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

    public void assignFileToCustomer(Long id, Long customerId) {

        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        Optional<File> optionalFile = fileRepository.findById(id);

        if (optionalCustomer.isPresent() && optionalFile.isPresent()) {

            Customer customer = optionalCustomer.get();
            File file = optionalFile.get();

            file.setCustomer(customer);

            fileRepository.save(file);

        } else if (optionalFile.isPresent()) {

            throw new CustomerNotFoundException("id " + id);

        } else if (optionalCustomer.isPresent()){

            throw new FileNotFoundException("id " + customerId);

        } else {

            throw new RecordNotFoundException("Dossier met id " +  id + " en klant met id " + customerId + " niet gevonden");
        }
    }

    public List<FileDto> getFilesByCustomerNumber(String customerNumber) {

        Optional<Customer> optionalCustomer = customerRepository.findCustomerByCustomerNumber(customerNumber);

        if (optionalCustomer.isPresent() && optionalCustomer.get().getFiles().size() > 0) {

            List<File> files = optionalCustomer.get().getFiles();

            return FileHelper.transferFileListToDtoList(files);

        } else {

            throw new FileNotFoundException("klantnummer " + customerNumber);
        }
    }

    public List<FileDto> getFilesByCustomerName(String name) {

        Optional<List<Customer>> optionalCustomer = customerRepository.findCustomerByNameContainingIgnoreCase(name);

        if (optionalCustomer.isPresent() && optionalCustomer.get().size() > 0) {

            List<Customer> customers = optionalCustomer.get();
            List<File> files = new ArrayList<>();

            for (Customer customer : customers) {

                if (customer.getFiles().size() > 0) {

                    files = customer.getFiles();
                }
            }

            return FileHelper.transferFileListToDtoList(files);

        } else {

            throw new FileNotFoundException("klantnummer " + name);
        }
    }

    public List<FileDto> getFilesByLabel(Label label) {

        Optional<List<Customer>> optionalCustomer = customerRepository.findCustomerByLabel(label);

        if (optionalCustomer.isPresent() && optionalCustomer.get().size() > 0) {

            List<Customer> customers = optionalCustomer.get();
            List<File> files = new ArrayList<>();

            for (Customer customer : customers) {

                if (customer.getFiles().size() > 0) {

                    files = customer.getFiles();
                }
            }

            return FileHelper.transferFileListToDtoList(files);

        } else {

            throw new FileNotFoundException("klantnummer " + label);
        }
    }

    public FileDto getFileByPolicyNumber(String policyNumber) {

        Optional<Policy> optionalPolicy = policyRepository.getPolicyByPolicyNumberIgnoreCase(policyNumber);

        if (optionalPolicy.isPresent() && optionalPolicy.get().getFile() != null) {

            File file = optionalPolicy.get().getFile();

            return FileHelper.transferFileToDto(file);

        } else {

            throw new FileNotFoundException("polisnummer " + policyNumber);
        }
    }

    public void uploadApplicationForm(Long id, MultipartFile application) throws IOException {

        Optional<File> optionalFile = fileRepository.findById(id);

        if (optionalFile.isPresent()) {

            File file = optionalFile.get();

            file.setApplicationForm(application.getBytes());

            fileRepository.save(file);
        }
    }

    public ResponseEntity<byte[]> downloadApplicationForm(Long id) {

        Optional<File> optionalFile = fileRepository.findById(id);

        if (optionalFile.isPresent()) {

            File file = optionalFile.get();

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body(file.getApplicationForm());

        } else {

            throw new FileNotFoundException("id" + id);
        }
    }
}

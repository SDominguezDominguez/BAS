package com.example.BAS.services;

import com.example.BAS.dtos.FileDto;
import com.example.BAS.dtos.FileInputDto;
import com.example.BAS.enumerations.FileType;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @Mock
    FileRepository fileRepository;

    @InjectMocks
    FileService fileService;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    PolicyRepository policyRepository;

    @Captor
    ArgumentCaptor<File> captor;

    File file1;
    File file2;
    Policy policy1;
    Policy policy2;
    FileInputDto inputDto;
    Customer customer1;

    @BeforeEach
    void setUp() {

        file1 = new File(1L, Status.WACHTEN_OP_BEDRAG, "Nabestaandendossier", "lhk ja", FileType.LUR, 25000);
        file2 = new File(2L, Status.BIJZONDERHEID, "niet compliant", "dep nee", FileType.LOR, null);

        policy1 = new Policy();
        policy1.setPolicyNumber("123");
        policy1.setId(1L);
        policy1.setAmount(200);

        policy2 = new Policy();
        policy2.setId(2L);
        policy2.setPolicyNumber("098");
        policy2.setAmount(300);

        inputDto = new FileInputDto();
        inputDto.setStatus(Status.WACHTEN_OP_PSK_EN_BEDRAG);
        inputDto.setStatusComment("Nabestaandendossier");
        inputDto.setComment("Lhk ja");
        inputDto.setFileType(FileType.GHUR);
        inputDto.setContractAmount(2500);

        customer1 = new Customer(1L, "Test", "12345", Label.SNS_BANK, "test@test.nl");

    }

    @Test
    void getAllFiles() {

        when(fileRepository.findAll()).thenReturn(List.of(file1, file2));

        List<File> foundFiles = FileHelper.transferFileDtoListToFileList(fileService.getAllFiles());

        assertEquals(file1.getStatus(), foundFiles.get(0).getStatus());
        assertEquals(file2.getStatus(), foundFiles.get(1).getStatus());
    }

    @Test
    void createFile() {

        lenient().when(fileRepository.save(FileHelper.transferFileInputDtoToFile(inputDto))).thenReturn(FileHelper.transferFileInputDtoToFile(inputDto));

        fileService.createFile(inputDto);

        verify(fileRepository, times(1)).save(captor.capture());

        File file = captor.getValue();

        assertEquals(inputDto.getStatus(), file.getStatus());
        assertEquals(inputDto.getFileType(), file.getFileType());
        assertEquals(inputDto.getComment(), file.getComment());
    }

    @Test
    void changeFile() {
        when(fileRepository.findById(1L)).thenReturn(Optional.of(file1));

        lenient().when(fileRepository.save(FileHelper.transferFileInputDtoToFile(inputDto))).thenReturn(file1);

        fileService.changeFile(1L, inputDto);

        verify(fileRepository, times(1)).save(captor.capture());

        File captured = captor.getValue();

        assertEquals(inputDto.getStatus(), captured.getStatus());
        assertEquals(inputDto.getFileType(), captured.getFileType());
        assertEquals(inputDto.getComment(), captured.getComment());
        assertEquals(inputDto.getStatusComment(), captured.getStatusComment());
        assertEquals(inputDto.getContractAmount(), captured.getContractAmount());
    }

    @Test
    void changeFileThrowsException() {

        assertThrows(FileNotFoundException.class, () -> fileService.changeFile(null, inputDto));
    }

    @Test
    void deleteFile() {

        when(fileRepository.findById(1L)).thenReturn(Optional.of(file1));

        fileService.deleteFile(1L);

        verify(fileRepository).deleteById(1L);
    }

    @Test
    void deleteFileThrowsException() {

        assertThrows(FileNotFoundException.class, () -> fileService.deleteFile(null));
    }

    @Test
    void getFilesWithStatus() {

        List<File> files = new ArrayList<>();
        files.add(file1);

        when(fileRepository.findFilesByStatus(Status.WACHTEN_OP_BEDRAG)).thenReturn(Optional.of(files));

        List<FileDto> foundFiles = fileService.getFilesWithStatus(Status.WACHTEN_OP_BEDRAG);

        assertEquals(file1.getStatus(), foundFiles.get(0).getStatus());
        assertEquals(file1.getId(), foundFiles.get(0).getId());
        assertEquals(file1.getFileType(), foundFiles.get(0).getFileType());
    }

    @Test
    void getFilesWithStatusThrowsException() {

        assertThrows(FileNotFoundException.class, () -> fileService.getFilesWithStatus(null));
    }

    @Test
    void assignFileToCustomer() {

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));

        when(fileRepository.findById(1L)).thenReturn(Optional.of(file1));

        when(fileRepository.save(file1)).thenReturn(file1);

        fileService.assignFileToCustomer(1L, 1L);

        verify(fileRepository, times(1)).save(captor.capture());

        File savedFile = captor.getValue();

        assertEquals(1L, savedFile.getCustomer().getId());
    }

    @Test
    void assignFileToCustomerThrowsException() {

        lenient().when(fileRepository.findById(1L)).thenReturn(Optional.of(file1));

        lenient().when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));

        assertThrows(CustomerNotFoundException.class, () -> fileService.assignFileToCustomer(1L, null));

        assertThrows(FileNotFoundException.class, () -> fileService.assignFileToCustomer(null, 1L));

        assertThrows(RecordNotFoundException.class, () -> fileService.assignFileToCustomer(null, null));
    }

    @Test
    void getFilesByCustomerNumber() {

        file1.setCustomer(customer1);

        List<File> files = new ArrayList<>();
        files.add(file1);
        customer1.setFiles(files);

        when(customerRepository.findCustomerByCustomerNumber("12345")).thenReturn(Optional.of(customer1));

        List<FileDto> fileList = fileService.getFilesByCustomerNumber("12345");

        assertEquals(customer1.getFiles().get(0).getId(), fileList.get(0).getCustomerDto().getId());
    }

    @Test
    void getFilesByCustomerNumberThrowsException() {

        assertThrows(CustomerNotFoundException.class, () -> fileService.getFilesByCustomerNumber("12345"));

    }

    @Test
    void getFilesByCustomerName() {

        file1.setCustomer(customer1);

        List<File> files = new ArrayList<>();
        files.add(file1);
        customer1.setFiles(files);

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);

        when(customerRepository.findCustomerByNameContainingIgnoreCase("tes")).thenReturn(Optional.of(customers));

        List<FileDto> fileList = fileService.getFilesByCustomerName("tes");

        assertEquals(customer1.getName(), fileList.get(0).getCustomerDto().getName());
    }

    @Test
    void getFilesByCustomerNameThrowsException() {

        assertThrows(FileNotFoundException.class, () -> fileService.getFilesByCustomerName(null));
    }

    @Test
    void getFilesByLabel() {

        file1.setCustomer(customer1);

        List<File> files = new ArrayList<>();
        files.add(file1);
        customer1.setFiles(files);

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);

        when(customerRepository.findCustomerByLabel(Label.SNS_BANK)).thenReturn(Optional.of(customers));

        List<FileDto> fileList = fileService.getFilesByLabel(Label.SNS_BANK);

        assertEquals(customer1.getName(), fileList.get(0).getCustomerDto().getName());

    }

    @Test
    void getFilesByLabelThrowsException() {

        assertThrows(FileNotFoundException.class, () -> fileService.getFilesByLabel(null));
    }

    @Test
    void getFileByPolicyNumber() {

        policy1.setFile(file1);

        List<Policy> policyList = new ArrayList<>();
        policyList.add(policy1);

        file1.setPolicies(policyList);

        when(policyRepository.getPolicyByPolicyNumberIgnoreCase("123")).thenReturn(Optional.of(policy1));

        FileDto foundFile = fileService.getFileByPolicyNumber("123");

        assertEquals(file1.getId(), foundFile.getId());
    }

    @Test
    void getFileByPolicyNumberThrowsException() {

        assertThrows(FileNotFoundException.class, () -> fileService.getFileByPolicyNumber(null));
    }

    @Test
    void uploadApplicationForm() throws IOException {

        MockMultipartFile multipartFile = new MockMultipartFile("test", "response.pdf", "multipart/form-data", "data".getBytes());

        when(fileRepository.findById(1L)).thenReturn(Optional.of(file1));

        fileService.uploadApplicationForm(1L, multipartFile);

        verify(fileRepository, times(1)).save(captor.capture());

        File captured = captor.getValue();

        assertEquals(multipartFile.getBytes(), captured.getApplicationForm());
    }

    @Test
    void downloadApplicationForm() throws IOException {

        MockMultipartFile multipartFile = new MockMultipartFile("test", "response.pdf", "multipart/form-data", "data".getBytes());

        byte[] bytes = multipartFile.getBytes();

        file1.setApplicationForm(multipartFile.getBytes());

        when(fileRepository.findById(1L)).thenReturn(Optional.of(file1));

        ResponseEntity<byte[]> foundFile = fileService.downloadApplicationForm(1L);

        assertEquals(bytes, foundFile.getBody());
    }

    @Test
    void downloadApplicationFormThrowsException() {

        assertThrows(FileNotFoundException.class, () -> fileService.downloadApplicationForm(null));
    }
}
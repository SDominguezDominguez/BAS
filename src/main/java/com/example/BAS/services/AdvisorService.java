package com.example.BAS.services;

import com.example.BAS.dtos.AdvisorDto;
import com.example.BAS.dtos.AdvisorInputDto;
import com.example.BAS.dtos.CustomerDto;
import com.example.BAS.exceptions.RecordHasNoRelation;
import com.example.BAS.exceptions.RecordNotFoundException;
import com.example.BAS.helpers.TransferCustomerToDto;
import com.example.BAS.models.Advisor;
import com.example.BAS.models.Customer;
import com.example.BAS.repositories.AdvisorRepository;
import com.example.BAS.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdvisorService {
    private final AdvisorRepository advisorRepository;
    private final CustomerRepository customerRepository;

    public AdvisorService(AdvisorRepository advisorRepository, CustomerRepository customerRepository) {
        this.advisorRepository = advisorRepository;
        this.customerRepository = customerRepository;
    }

    public List<AdvisorDto> getAllAdvisors() {
        List<Advisor> advisorList = advisorRepository.findAll();
        return transferAdvisorListToDto(advisorList);
    }

    public List<AdvisorDto> getAdvisorsByNameContaining(String name) {
        Optional<List<Advisor>> advisorList = advisorRepository.getAdvisorByNameContaining(name);

        if (advisorList.isPresent()) {
            List<Advisor> advisors = advisorList.get();
            return transferAdvisorListToDto(advisors);

        } else {
            throw new RecordNotFoundException("Geen adviseur gevonden");
        }
    }

    public List<CustomerDto> getAllCustomersByAdvisor(Long id) {
        Optional<Advisor> optionalAdvisor = advisorRepository.findById(id);

        if (optionalAdvisor.isPresent()) {
            List<Customer> customers = optionalAdvisor.get().getCustomers();
            if (customers.size() > 0) {
                List<CustomerDto> customerDtos = new ArrayList<>();

                for (Customer customer : customers) {
                    CustomerDto dto = TransferCustomerToDto.transferCustomerToDto(customer);
                    customerDtos.add(dto);
                }
                return customerDtos;
            } else {
                throw new RecordNotFoundException("Geen klanten gevonden");
            }
        } else {
            throw new RecordNotFoundException("Geen adviseur gevonden");
        }
    }

        public AdvisorDto getAdvisorByCustomerNumber (String customer){
            Optional<Customer> optionalCustomer = customerRepository.findCustomerByCustomerNumber(customer);

            if (optionalCustomer.isPresent()) {
                Advisor advisor = optionalCustomer.get().getAdvisor();

                if (advisor == null) {
                    throw new RecordHasNoRelation("Klant heeft geen adviseur");
                } else {
                    return transferAdvisorToDto(advisor);
                }
            } else {
                throw new RecordNotFoundException("Geen klant gevonden");
            }

        }

        public AdvisorDto createAdvisor (AdvisorInputDto dto){
            Advisor advisor = transferAdvisorInputDtoToAdvisor(dto);

            advisorRepository.save(advisor);

            return transferAdvisorToDto(advisor);
        }

        public void deleteAdvisor (Long id){
            Optional<Advisor> optionalAdvisor = advisorRepository.findById(id);

            if (optionalAdvisor.isPresent()) {
                advisorRepository.deleteById(id);
            } else {
                throw new RecordNotFoundException("Adviseur niet gevonden");
            }
        }

        public void patchAdvisor (Long id, AdvisorInputDto dto){
            Optional<Advisor> optionalAdvisor = advisorRepository.findById(id);

            if (optionalAdvisor.isPresent()) {
                Advisor advisor = optionalAdvisor.get();

                if (dto.getEmail() != null) {
                    advisor.setEmail(dto.getEmail());
                }
                if (dto.getName() != null) {
                    advisor.setName(dto.getName());
                }
                if (dto.getOffice() != null) {
                    advisor.setOffice(dto.getOffice());
                }

                advisorRepository.save(advisor);
            } else {
                throw new RecordNotFoundException("Geen adviseur gevonden");
            }
        }

        public List<AdvisorDto> transferAdvisorListToDto (List < Advisor > advisors) {
            List<AdvisorDto> advisorDtoList = new ArrayList<>();

            for (Advisor advisor : advisors) {
                AdvisorDto dto = transferAdvisorToDto(advisor);

                advisorDtoList.add(dto);
            }
            return advisorDtoList;
        }

        public AdvisorDto transferAdvisorToDto (Advisor advisor){
            AdvisorDto dto = new AdvisorDto();

            dto.setId(advisor.getId());
            dto.setName(advisor.getName());
            dto.setOffice(advisor.getOffice());
            dto.setEmail(advisor.getEmail());

            return dto;
        }

        public Advisor transferAdvisorInputDtoToAdvisor (AdvisorInputDto dto){
            Advisor advisor = new Advisor();

            advisor.setName(dto.getName());
            advisor.setEmail(dto.getEmail());
            advisor.setOffice(dto.getOffice());

            return advisor;
        }
    }

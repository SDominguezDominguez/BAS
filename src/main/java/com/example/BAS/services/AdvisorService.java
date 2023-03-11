package com.example.BAS.services;

import com.example.BAS.dtos.AdvisorDto;
import com.example.BAS.dtos.AdvisorInputDto;
import com.example.BAS.models.Advisor;
import com.example.BAS.repositories.AdvisorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdvisorService {
    private final AdvisorRepository advisorRepository;

    public AdvisorService(AdvisorRepository advisorRepository) {
        this.advisorRepository = advisorRepository;
    }

    public List<AdvisorDto> getAllAdvisors() {
        List<Advisor> advisorList = advisorRepository.findAll();
        return transferAdvisorListToDto(advisorList);
    }

    public AdvisorDto createAdvisor(AdvisorInputDto dto) {
       Advisor advisor = transferAdvisorInputDtoToAdvisor(dto);

       advisorRepository.save(advisor);

       return transferAdvisorToDto(advisor);
    }

    public void deleteAdvisor(Long id) {
        Optional<Advisor> optionalAdvisor = advisorRepository.findById(id);

        if (optionalAdvisor.isPresent()) {
            advisorRepository.deleteById(id);
        }
    }

    public void patchAdvisor(Long id, AdvisorInputDto dto) {
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
        }
    }

    public List<AdvisorDto> transferAdvisorListToDto(List<Advisor> advisors) {
        List<AdvisorDto> advisorDtoList = new ArrayList<>();

        for (Advisor advisor : advisors) {
            AdvisorDto dto = transferAdvisorToDto(advisor);

            advisorDtoList.add(dto);
        }
        return advisorDtoList;
    }

    public AdvisorDto transferAdvisorToDto(Advisor advisor) {
        AdvisorDto dto = new AdvisorDto();

        dto.setId(advisor.getId());
        dto.setName(advisor.getName());
        dto.setOffice(advisor.getOffice());
        dto.setEmail(advisor.getEmail());

        return dto;
    }

    public Advisor transferAdvisorInputDtoToAdvisor(AdvisorInputDto dto) {
        Advisor advisor = new Advisor();

        advisor.setName(dto.getName());
        advisor.setEmail(dto.getEmail());
        advisor.setOffice(dto.getOffice());

        return advisor;
    }
}

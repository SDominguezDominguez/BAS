package com.example.BAS.services;

import com.example.BAS.dtos.PolicyDto;
import com.example.BAS.dtos.PolicyInputDto;
import com.example.BAS.models.Policy;
import com.example.BAS.repositories.PolicyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PolicyService {
    private final PolicyRepository policyRepository;

    public PolicyService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    public List<PolicyDto> getAllPolicies() {
        List<Policy> policies = policyRepository.findAll();
        return transferPolicyListToDtoList(policies);
    }

    public PolicyDto createPolicy(PolicyInputDto inputDto) {
        Policy policy = transferPolicyInputDtoToPolicy(inputDto);
        policyRepository.save(policy);

        return transferPolicyToDto(policy);
    }

    public void deletePolicy(Long id) {
        policyRepository.deleteById(id);
    }

    public void patchPolicy(Long id, PolicyInputDto dto) {
        Optional<Policy> optionalPolicy = policyRepository.findById(id);

        if (optionalPolicy.isPresent()) {
            Policy policy = optionalPolicy.get();

            if (dto.getPolicyNumber() != null) {
                policy.setPolicyNumber(dto.getPolicyNumber());
            }
            if (dto.getAmount() != null) {
                policy.setAmount(dto.getAmount());
            }
            if (dto.getReceiveDateAmount() != null) {
                policy.setReceiveDateAmount(dto.getReceiveDateAmount());
            }
            if (dto.getReceiveDatePsk() != null) {
                policy.setReceiveDatePsk(dto.getReceiveDatePsk());
            }
            if (dto.getReminderDatePsk() != null) {
                policy.setReminderDatePsk(dto.getReminderDatePsk());
            }
            policyRepository.save(policy);
        }
    }

    public List<PolicyDto> transferPolicyListToDtoList(List<Policy> policies) {
        List<PolicyDto> policyDtos = new ArrayList<>();

        for (Policy policy : policies) {
            PolicyDto dto = transferPolicyToDto(policy);
           policyDtos.add(dto);
        }

        return policyDtos;
    }

    public PolicyDto transferPolicyToDto(Policy policy) {
        PolicyDto dto = new PolicyDto();

        dto.setPolicyNumber(policy.getPolicyNumber());
        dto.setId(policy.getId());
        dto.setAmount(policy.getAmount());
        dto.setReceiveDateAmount(policy.getReceiveDateAmount());
        dto.setReceiveDatePsk(policy.getReceiveDatePsk());
        dto.setReminderDatePsk(policy.getReminderDatePsk());

        return dto;
    }

    public Policy transferPolicyInputDtoToPolicy(PolicyInputDto dto) {
        Policy policy = new Policy();

        policy.setPolicyNumber(dto.getPolicyNumber());
        policy.setAmount(dto.getAmount());
        policy.setReceiveDateAmount(dto.getReceiveDateAmount());
        policy.setReceiveDatePsk(dto.getReceiveDatePsk());
        policy.setReminderDatePsk(dto.getReminderDatePsk());

        return policy;
    }
}

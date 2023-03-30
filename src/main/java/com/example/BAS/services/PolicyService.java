package com.example.BAS.services;

import com.example.BAS.dtos.PolicyDto;
import com.example.BAS.dtos.PolicyInputDto;
import com.example.BAS.exceptions.PolicyNotFoundException;
import com.example.BAS.helpers.PolicyHelper;
import com.example.BAS.models.Policy;
import com.example.BAS.repositories.PolicyRepository;
import org.springframework.stereotype.Service;

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

        return PolicyHelper.transferPolicyListToDtoList(policies);
    }

    public PolicyDto createPolicy(PolicyInputDto dto) {

        Policy policy = PolicyHelper.transferPolicyInputDtoToPolicy(dto);

        policyRepository.save(policy);

        return PolicyHelper.transferPolicyToDto(policy);
    }

    public void changePolicy(Long id, PolicyInputDto dto) {

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

        } else {

            throw new PolicyNotFoundException("id " + id);
        }
    }

    public void deletePolicy(Long id) {

        if (policyRepository.findById(id).isPresent()) {

            policyRepository.deleteById(id);

        } else {

            throw new PolicyNotFoundException("id " + id);
        }
    }
}
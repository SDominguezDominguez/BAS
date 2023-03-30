package com.example.BAS.services;

import com.example.BAS.dtos.PolicyDto;
import com.example.BAS.dtos.PolicyInputDto;
import com.example.BAS.exceptions.FileNotFoundException;
import com.example.BAS.exceptions.PolicyNotFoundException;
import com.example.BAS.exceptions.RecordNotFoundException;
import com.example.BAS.helpers.PolicyHelper;
import com.example.BAS.models.File;
import com.example.BAS.models.Policy;
import com.example.BAS.repositories.FileRepository;
import com.example.BAS.repositories.PolicyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final FileRepository fileRepository;

    public PolicyService(PolicyRepository policyRepository, FileRepository fileRepository) {
        this.policyRepository = policyRepository;
        this.fileRepository = fileRepository;
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

    public PolicyDto getPolicyByPolicyNumber(String policyNumber) {

        Optional<Policy> optionalPolicy = policyRepository.getPolicyByPolicyNumberIgnoreCase(policyNumber);

        if (optionalPolicy.isPresent()) {

            Policy policy = optionalPolicy.get();

            return PolicyHelper.transferPolicyToDto(policy);

        } else {

            throw new PolicyNotFoundException("polisnummer " + policyNumber);
        }
    }

    public List<PolicyDto> getAllPoliciesWhereReminderDateEqualsDate(LocalDate date) {

        Optional<List<Policy>> optionalPolicies = policyRepository.getPoliciesByReminderDatePskEquals(date);

        if (optionalPolicies.isPresent() && optionalPolicies.get().size() > 0) {

            return PolicyHelper.transferPolicyListToDtoList(optionalPolicies.get());

        } else {

            throw new PolicyNotFoundException("rappeldatum " + date);
        }
    }

    public List<PolicyDto> getPoliciesWhereAmountIsReceivedWithoutPsk() {

        Optional<List<Policy>> optionalPolicies = policyRepository.getPoliciesByAmountIsNotNullAndReceiveDatePskIsNull();

        if (optionalPolicies.isPresent() && optionalPolicies.get().size() > 0) {

            return PolicyHelper.transferPolicyListToDtoList(optionalPolicies.get());

        } else {

            throw new RecordNotFoundException("Geen polissen gevonden waarvan wij het bedrag al hebben ontvangen, maar de psk nog niet hebben ontvangen");
        }
    }

    public void assignPolicyToFile(Long id, Long fileId) {

        Optional<File> optionalFile = fileRepository.findById(fileId);
        Optional<Policy> optionalPolicy = policyRepository.findById(id);

        if (optionalFile.isPresent() && optionalPolicy.isPresent()) {

            File file = optionalFile.get();
            Policy policy = optionalPolicy.get();

            policy.setFile(file);

            policyRepository.save(policy);

        } else if (optionalPolicy.isPresent()) {

            throw new FileNotFoundException("id " + fileId);

        } else if (optionalFile.isPresent()) {

            throw new PolicyNotFoundException("id " + id);

        } else {

            throw new RecordNotFoundException("Polis met id " + id + " en dossier met id " + fileId + " niet gevonden");
        }
    }
}

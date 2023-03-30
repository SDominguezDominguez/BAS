package com.example.BAS.helpers;

import com.example.BAS.dtos.PolicyDto;
import com.example.BAS.dtos.PolicyInputDto;
import com.example.BAS.models.Policy;

import java.util.ArrayList;
import java.util.List;

public class PolicyHelper {

    public static List<PolicyDto> transferPolicyListToDtoList(List<Policy> policies) {

        List<PolicyDto> policyDtos = new ArrayList<>();

        for (Policy policy : policies) {

            PolicyDto dto = transferPolicyToDto(policy);

            policyDtos.add(dto);
        }

        return policyDtos;
    }

    public static PolicyDto transferPolicyToDto(Policy policy) {

        PolicyDto dto = new PolicyDto();

        dto.setPolicyNumber(policy.getPolicyNumber());
        dto.setId(policy.getId());
        dto.setAmount(policy.getAmount());
        dto.setReceiveDateAmount(policy.getReceiveDateAmount());
        dto.setReceiveDatePsk(policy.getReceiveDatePsk());
        dto.setReminderDatePsk(policy.getReminderDatePsk());

        return dto;
    }

    public static Policy transferPolicyInputDtoToPolicy(PolicyInputDto dto) {

        Policy policy = new Policy();

        policy.setPolicyNumber(dto.getPolicyNumber());
        policy.setAmount(dto.getAmount());
        policy.setReceiveDateAmount(dto.getReceiveDateAmount());
        policy.setReceiveDatePsk(dto.getReceiveDatePsk());
        policy.setReminderDatePsk(dto.getReminderDatePsk());

        return policy;
    }
}
